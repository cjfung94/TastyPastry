package com.example.tastypastry;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    //setUp Email and Password edit texts
    private EditText emailSign, passwordSign1, passwordSign2;
    private Button SignupButton;
    private TextView SignIn;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private String userId;
    private String className;
    private static DatabaseReference userDatabase;
    HashMap<String, Object> map = new HashMap<>();
    DashBoardActivity dashBoardActivity = new DashBoardActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        className = this.getClass().getSimpleName(); //Doesn't work inside methods outside of onCreate
        firebaseAuth = FirebaseAuth.getInstance();
        userDatabase = FirebaseDatabase.getInstance().getReference();
        emailSign = findViewById(R.id.email);
        passwordSign1 = findViewById(R.id.password1);
        passwordSign2 = findViewById(R.id.password2);
        SignupButton = findViewById(R.id.shareRecipe);
        progressDialog = new ProgressDialog(this);
        SignIn = findViewById(R.id.signIn);
        //Make the button register
        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
        //Make the button Sign In
        SignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //Registration and Errors
    private void Register() {
        String email = emailSign.getText().toString();
        String password1 = passwordSign1.getText().toString();
        String password2 = passwordSign2.getText().toString();
        //If Empty fields
        if (TextUtils.isEmpty(email)) {
            emailSign.setError("Enter your email");
            return;
        } else if (TextUtils.isEmpty(password1)) {
            passwordSign1.setError("Enter your password");
            return;
        } else if (TextUtils.isEmpty(password2)) {
            passwordSign2.setError("Confirm your password");
            return;
        } else if (!password1.equals(password2)) {
            passwordSign2.setError("Passwords do not match");
            return;
        } else if (password1.length() < 5) {
            passwordSign1.setError("Password is too short");
            return;
        } else if (!isValidEmail(email)) {
            emailSign.setError("Please enter a valid email");
            return;
        }
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        //Adds to Firebase
        firebaseAuth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    userId = firebaseAuth.getCurrentUser().getUid();
                    Toast.makeText(SignUpActivity.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpActivity.this, TutorialActivity.class);

                    //Store newly created email and userId to database
//                    intent.putExtra("emailAddy", email);
                    intent.putExtra("userID", userId);
                    createUserDatabase(email, userId);
//                    Log.d("What class", "is this " + className);
                    dashBoardActivity.setUpUserRecipe(userId);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(SignUpActivity.this, "Registration Failed", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }
    //Add new user into database
    public void createUserDatabase(String email, String userId){
        map.put("Email", email);
        userDatabase.child("UserList").child(userId).updateChildren(map);
    }

    private Boolean isValidEmail(CharSequence isEmail) {
        return (!TextUtils.isEmpty(isEmail) && Patterns.EMAIL_ADDRESS.matcher(isEmail).matches());
    }

}
