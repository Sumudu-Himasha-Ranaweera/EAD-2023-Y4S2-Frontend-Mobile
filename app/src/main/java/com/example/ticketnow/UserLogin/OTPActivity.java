package com.example.ticketnow.UserLogin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ticketnow.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {

//    private EditText inputCode1, inputCode2, inputCode3, inputCode4, inputCode5, inputCode6;
//    private String verificationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

    }}

//        TextView textMobile = findViewById(R.id.textMobile);
//        textMobile.setText(String.format(
//                "+94-%s", getIntent().getStringExtra("userPhone")
//        ));
//
//        inputCode1 = findViewById(R.id.inputCode1);
//        inputCode2 = findViewById(R.id.inputCode2);
//        inputCode3 = findViewById(R.id.inputCode3);
//        inputCode4 = findViewById(R.id.inputCode4);
//        inputCode5 = findViewById(R.id.inputCode5);
//        inputCode6 = findViewById(R.id.inputCode6);
//
//        setupOTPInputs();
//
//        final ProgressBar progressBar = findViewById(R.id.progressBar);
//        final Button buttonVerify = findViewById(R.id.btnVerify);
//
//        verificationID = getIntent().getStringExtra("verificationID");
//
//        buttonVerify.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (inputCode1.getText().toString().trim().isEmpty()
//                ||  inputCode2.getText().toString().trim().isEmpty()
//                ||  inputCode3.getText().toString().trim().isEmpty()
//                ||  inputCode4.getText().toString().trim().isEmpty()
//                ||  inputCode5.getText().toString().trim().isEmpty()
//                ||  inputCode6.getText().toString().trim().isEmpty()) {
//
//                    Toast.makeText(OTPActivity.this, "Please Enter Valid Code", Toast.LENGTH_SHORT).show();
//                    return;
//
//                }
//                String code =
//                        inputCode1.getText().toString() +
//                                inputCode2.getText().toString() +
//                                inputCode3.getText().toString() +
//                                inputCode4.getText().toString() +
//                                inputCode5.getText().toString() +
//                                inputCode6.getText().toString();
//
//                if (verificationID != null){
//                    progressBar.setVisibility(View.VISIBLE);
//                    buttonVerify.setVisibility(View.INVISIBLE);
//                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
//                            verificationID,
//                            code
//                    );
//                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
//                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
//                            progressBar.setVisibility(View.GONE);
//                            buttonVerify.setVisibility(View.VISIBLE);
//
//                            if (task.isSuccessful()){
//                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                startActivity(intent);
//                            }else {
//                                Toast.makeText(OTPActivity.this, "OTP code is Invalid", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                }
//            }
//        });
//
//        // Resend OTP Function
//        findViewById(R.id.txtResendOtp).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //Phone Number Authentication
//                PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                        "+94" + getIntent().getStringExtra("userPhone"),
//                        60,
//                        TimeUnit.SECONDS,
//                        OTPActivity.this,
//                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
//
//                            @Override
//                            public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
//
//                            }
//
//                            @Override
//                            public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
//
//                                Toast.makeText(OTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                            }
//
//                            @Override
//                            public void onCodeSent(@NonNull @NotNull String New_VerificationID, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//
//                                verificationID = New_VerificationID;
//                                Toast.makeText(OTPActivity.this, "OTP Sent", Toast.LENGTH_SHORT).show();
//
//                            }
//                        }
//
//                );
//            }
//        });
//
//
//    }
//
//    private void setupOTPInputs() {
//        inputCode1.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                if (! charSequence.toString().trim().isEmpty())
//                {
//                    inputCode2.requestFocus();
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        inputCode2.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                if (! charSequence.toString().trim().isEmpty())
//                {
//                    inputCode3.requestFocus();
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        inputCode3.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                if (! charSequence.toString().trim().isEmpty())
//                {
//                    inputCode4.requestFocus();
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        inputCode4.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                if (! charSequence.toString().trim().isEmpty())
//                {
//                    inputCode5.requestFocus();
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        inputCode5.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                if (! charSequence.toString().trim().isEmpty())
//                {
//                    inputCode6.requestFocus();
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//    }
//}