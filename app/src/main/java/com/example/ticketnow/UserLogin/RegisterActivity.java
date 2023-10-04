package com.example.ticketnow.UserLogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ticketnow.R;
//import com.example.ticketnow.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {

//    //attribute for remove bottom status bar
//    private View decorView;
//
//    //All the components in the XML design file
//    ImageButton btnCheck;
//    TextView txt_signIn;
//    EditText IDnum,email,number,password,confPassword;
//
//    //Firebase Connection
//    FirebaseAuth auth;
//    //realtime Database Connection
//    FirebaseDatabase database; //rootNode
//    //realtime database reference
//    DatabaseReference reference;
//
//    //progress bar
//    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }}

//        //firebase connection
//        auth = FirebaseAuth.getInstance();
//        //realtime Database Connection
//        database = FirebaseDatabase.getInstance();
//        reference = database.getReference("Registered Users");
//
//        //progress bar
//        progressBar = (ProgressBar) findViewById(R.id.progressbar);
//        progressBar.setVisibility(View.GONE);
//
//        IDnum = (EditText) findViewById(R.id.EditTxtID);
//        email = (EditText) findViewById(R.id.EditTxtEmail);
//        number = (EditText) findViewById(R.id.EditTxtNumber);
//        password = (EditText) findViewById(R.id.EditTxtPassword);
//        confPassword = (EditText) findViewById(R.id.EditTxtConfPassword);
//
//
//        btnCheck = (ImageButton) findViewById(R.id.img_btn_check);
//        txt_signIn = (TextView) findViewById(R.id.HaveAccount);
//
//        //Already have a account link connect
//        txt_signIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//            }
//        });
//
//        //Register Button
//        btnCheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                progressBar.setVisibility(View.VISIBLE);
////                btnCheck.setVisibility(View.INVISIBLE);
//
//                createUser();
//
//            }
//        });
//
//        //Remove bottom status bar
//        decorView = getWindow().getDecorView();
//        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
//            @Override
//            public void onSystemUiVisibilityChange(int visibility) {
//                if (visibility == 0)
//                    decorView.setSystemUiVisibility(hideSystemBars());
//            }
//        });
//
//    }
//
//    //remove bottom status bar
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus)
//        {
//            decorView.setSystemUiVisibility(hideSystemBars());
//        }
//    }
//
//    private int hideSystemBars() {
//        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//    }
//
//
//    private void createUser() {
//        //get all the values
//        String userID = IDnum.getText().toString().trim();
//        String userEmail = email.getText().toString().trim();
//        String userPhone = number.getText().toString().trim();
//        String userPassword = password.getText().toString().trim();
//        String userConfPassword = confPassword.getText().toString().trim();
//
//        boolean check = validateInfo(userID,userEmail,userPhone,userPassword);
//
//        if (check == true) {
//            //create user
//            auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
//                    if (task.isSuccessful()) {
//
//                        //send data to Real-time database
//                        UserModel userModel = new UserModel(userID, userEmail, userPhone, userPassword, userConfPassword);
//                         reference.child(userID).setValue(userModel);
//
//                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
//
//
//                        //Phone Number Authentication
//                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                                "+94" + number.getText().toString(),
//                                20,
//                                TimeUnit.SECONDS,
//                                RegisterActivity.this,
//                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
//
//                                    @Override
//                                    public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
//
//                                        progressBar.setVisibility(View.GONE);
//                                        btnCheck.setVisibility(View.VISIBLE);
//
//                                    }
//
//                                    @Override
//                                    public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
//
//                                        progressBar.setVisibility(View.GONE);
//                                        btnCheck.setVisibility(View.VISIBLE);
//                                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                                    }
//
//                                    @Override
//                                    public void onCodeSent(@NonNull @NotNull String verificationID, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//
//                                        progressBar.setVisibility(View.GONE);
//                                        btnCheck.setVisibility(View.VISIBLE);
//
//                                        //connection to the OTP Page
//                                        Intent intent  = new Intent(getApplicationContext(), OTPActivity.class);
//                                        intent.putExtra("userPhone", number.getText().toString());
//                                        intent.putExtra("verificationID", verificationID);
//                                        startActivity(intent);
//                                        //end code
//
//                                    }
//                                }
//
//                        );
//
//
//
//                    } else {
//                        progressBar.setVisibility(View.GONE);
//                        Toast.makeText(RegisterActivity.this, "Error : " + task.getException(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//        else
//        {
//            Toast.makeText(RegisterActivity.this, "Sorry check the Information again", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private boolean validateInfo(String userID, String userEmail, String userPhone, String userPassword)
//    {
//        //User ID validation
//        if (userID.length() == 0)
//        {
//            IDnum.requestFocus();
//            IDnum.setError("ID Number is Required");
//            return false;
//        }
//
//        //User Email Validation
//        if (userEmail.length() == 0)
//        {
//            email.requestFocus();
//            email.setError("Email cannot be empty");
//            return false;
//        }
//        else if (!userEmail.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
//        {
//            email.requestFocus();
//            email.setError("VALID User Email is Required");
//            return false;
//        }
//
//        //User Phone Validation
//        if (userPhone.length()==0)
//        {
//            number.requestFocus();
//            number.setError("Mobile Number cannot be empty");
//            return false;
//        }
//        else if (!userPhone.matches("^(?:7|0|(?:\\+94))[0-9]{9,10}$"))
//        {
//            number.requestFocus();
//            number.setError("Valid Mobile number should be +94xxxxxxxxx");
//            return false;
//        }
//
//        //Password validation
//        if(userPassword.length() < 6)
//        {
//            password.requestFocus();
//            password.setError("minimum 6 character required");
//            return false;
//        }
//        else
//        {
//            return true;
//        }
//    }
//}