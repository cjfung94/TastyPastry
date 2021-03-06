package com.example.tastypastry;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class MainActivity extends AppCompatActivity {
    private EditText emailSign, passwordSign;
    private Button SignInButton;
    private TextView SignUp;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private String userId;
    private String className;
    DashBoardActivity dashBoardActivity = new DashBoardActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        className = this.getClass().getSimpleName();
        firebaseAuth = FirebaseAuth.getInstance();
        emailSign = findViewById(R.id.name_editText);
        passwordSign = findViewById(R.id.password);
        SignInButton = findViewById(R.id.sign_in_button);
        progressDialog = new ProgressDialog(this);
        SignUp = findViewById(R.id.sign_up);
        //Make the button register
        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        //Make the button Sign In
        SignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void Login() {
        String email = emailSign.getText().toString();
        String password = passwordSign.getText().toString();
        //If Empty fields
        if (TextUtils.isEmpty(email)) {
            emailSign.setError("Enter your email");
            return;
        } else if (TextUtils.isEmpty(password)) {
            passwordSign.setError("Enter your password");
            return;
        }
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        //Adds to Firebase
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    userId = firebaseAuth.getCurrentUser().getUid();
                    Toast.makeText(MainActivity.this, "Successfully Logged In", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, DashBoardActivity.class);
                    //Take the intent and store the email so that other classes can use it
//                    intent.putExtra("emailAddy", email);
//                    //Get userID for the user
//                    intent.putExtra("userID", userId);
//                    Log.d("userID", "putExtra" + userId);
//                    //dashBoardActivity.createUserDatabase(userId, email);
//                    dashBoardActivity.createDisplayRecipes();
                    intent.putExtra("className", this.getClass().getSimpleName());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }

}