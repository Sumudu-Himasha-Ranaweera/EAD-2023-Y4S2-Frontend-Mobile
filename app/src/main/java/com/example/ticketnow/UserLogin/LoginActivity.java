package com.example.ticketnow.UserLogin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//import com.example.ticketnow.Navbar.HomeActivity;
import com.example.ticketnow.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    private View decorView;

    ImageButton btnGo;
    EditText IDnum, password;
    TextView TextSignUp;

//    //Firebase Connection
//    FirebaseAuth auth;
//    //realtime Database Connection
//    FirebaseDatabase database;
//    //realtime database reference
//    DatabaseReference reference;

    //progress bar
    ProgressBar progressBar;

    public LoginActivity(Context mMockContext) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        //firebase connection
//        auth = FirebaseAuth.getInstance();
//        //realtime Database Connection
//        database = FirebaseDatabase.getInstance();
//        reference = database.getReference("Registered Users");
//
        //progress bar
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
//
        IDnum = (EditText) findViewById(R.id.EditTxtID);
        password = (EditText) findViewById(R.id.EditTxtPassword);

        btnGo = (ImageButton) findViewById(R.id.btn_login);
        TextSignUp = (TextView) findViewById(R.id.txtSignUp);

        //After click "Sign Up" textView it will redirect to Sign Up Page
        TextSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });//End code

        //button click login to app
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                LoginUser();
                progressBar.setVisibility(View.VISIBLE);

            }
        }); //End Code

        //remove bottom status bar
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0)
                    decorView.setSystemUiVisibility(hideSystemBars());
            }
        });//end code

    }

    //remove bottom status bar
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

    private int hideSystemBars() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }
    //End of remove bottom status bar
}

//    private void LoginUser() {
//
//        //creating variables
//        String userEnteredUserID = IDnum.getText().toString();
//        String userEnteredPassword = password.getText().toString();
//
//        reference = database.getInstance().getReference("Registered Users");
//        Query checkUser = reference.orderByChild("userID").equalTo(userEnteredUserID);
//
//        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                if (snapshot.exists())
//                {
//                    IDnum.setError(null);
//
//                    String passwordFromDB = snapshot.child(userEnteredUserID).child("userPassword").getValue(String.class);
//
//                    if (passwordFromDB.equals(userEnteredPassword)){
//
//                        password.setError(null);
//
//                        String userIDFromDB = snapshot.child(userEnteredUserID).child("userID").getValue(String.class);
//                        String emailFromDB = snapshot.child(userEnteredUserID).child("userEmail").getValue(String.class);
//                        String numberFromDB = snapshot.child(userEnteredUserID).child("userPhone").getValue(String.class);
//
////                        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
//
////                        i.putExtra("userID", userIDFromDB);
////                        i.putExtra("email", emailFromDB);
////                        i.putExtra("number",numberFromDB);
////
////                        startActivity(i);
//
//
//                        //Initialize input manager
//                        InputMethodManager manager = (InputMethodManager) getSystemService(
//                                Context.INPUT_METHOD_SERVICE
//                        );
//                        //Hide soft keyboard
//                        manager.hideSoftInputFromWindow(IDnum.getApplicationWindowToken(), 0);
//                        manager.hideSoftInputFromWindow(password.getApplicationWindowToken(), 0);
//
//                    }
//                    else
//                    {
//                       password.setError("Wrong Password");
//                       password.requestFocus();
//                       progressBar.setVisibility(View.GONE);
//                    }
//                }
//                else
//                {
//                    IDnum.setError("No such user exist");
//                    IDnum.requestFocus();
//                    progressBar.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });
//
//    }
//
//    private boolean validateInfo(String userEmail, String userPassword) {
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
//
//    public String validate(String user, String user1) {
//
//        return "Invalid login!";
//    }
//}