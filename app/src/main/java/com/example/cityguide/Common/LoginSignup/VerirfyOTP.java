package com.example.cityguide.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.cityguide.Databases.UserHelperClass;
import com.example.cityguide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.concurrent.TimeUnit;

public class VerirfyOTP extends AppCompatActivity {
    PinView pinView;
    String codeBySystem;
    String email,fullname,username,password,date,selectedGender,phoneNo,whatToDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verirfy_o_t_p);
        pinView=findViewById(R.id.pin_view);

        phoneNo = getIntent().getStringExtra("PhoneNo.");
        fullname = getIntent().getStringExtra("fullname");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        email = getIntent().getStringExtra("email");
        date = getIntent().getStringExtra("date");
        selectedGender = getIntent().getStringExtra("selectGender");
        whatToDo = getIntent().getStringExtra("whatToDo");

        sendVerificationCodetoUser(phoneNo);
    }

    private void sendVerificationCodetoUser(String phoneNo) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNo,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem=s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code!=null) {
                        pinView.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(VerirfyOTP.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem,code);
        signInWithPhoneAuthCredential(credential);

    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                                storeNewUsersData();


                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(VerirfyOTP.this, "Verification Not Completed! Try it Again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }



    private void storeNewUsersData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        UserHelperClass addNewUser = new UserHelperClass(fullname,username,email,password,selectedGender,date,phoneNo);

        reference.child(phoneNo).setValue(addNewUser);
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));

    }

    public void callnextscreenfromOtp(View view){
        String code= pinView.getText().toString();
        if(!code.isEmpty()){
            verifyCode(code);
        }
    }
}