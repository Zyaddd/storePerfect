package com.example.storePerfect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Register_Activity extends AppCompatActivity {

    private EditText inputPhone, inputPassword, inputName;
    private Button createAccountBtn;
    TextView loginTextBtn;
    FirebaseAuth fireAuth;
    ProgressBar progressBar;

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        inputPhone = findViewById(R.id.register_phone_input);
        inputPassword = findViewById(R.id.register_password_input);
        inputName = findViewById(R.id.register_username_input);
        createAccountBtn = findViewById(R.id.register_Btn);

        loadingBar = new ProgressDialog(this);

//        fireAuth = FirebaseAuth.getInstance();
//        progressBar = findViewById(R.id.progressBar);

//        if (fireAuth.getCurrentUser() != null) {
//            startActivity(new Intent(getApplicationContext(), Store_Activity.class));
//            progressBar.setVisibility(View.INVISIBLE);
//            finish();
//        }


        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
                //Backend FireBase

//                fireAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(Register_Activity.this, "Welcome to our shop!", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(getApplicationContext(), Store_Activity.class));
//
//                        } else {
//                            Toast.makeText(Register_Activity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            progressBar.setVisibility(View.GONE);
//                        }
//                    }
//                });
            }
        });


    }

    private void createAccount() {
        String name = inputName.getText().toString();
        String phone = inputPhone.getText().toString();
        String password = inputPassword.getText().toString();

        if (TextUtils.isEmpty(name)) {
            inputName.setError("Name is required.");
        } else if (TextUtils.isEmpty(phone)) {
            inputPhone.setError("Email is required.");
        } else if (password.length() < 6) {
            inputPassword.setError("Password must be more than 6 characters.");
        } else {
            loadingBar.setTitle("Creating Your Account!");
            loadingBar.setMessage("Please wait, While we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateEmail(name, phone, password);
        }

        //progressBar.setVisibility(View.VISIBLE);
    }

    private void ValidateEmail(final String name, final String phone, final String password) {
        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(phone).exists())) {
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("phone", phone);
                    userDataMap.put("password", password);
                    userDataMap.put("name", name);

                    rootRef.child("Users").child(phone).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Welcome to storePerfect!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), Store_Activity.class));
                                    } else {
                                        loadingBar.dismiss();
                                        Toast.makeText(getApplicationContext(), "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    loadingBar.dismiss();
                    Toast.makeText(getApplicationContext(), "This " + phone + " already exists.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login_Activity.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void goToLoginPage(View view) {
        startActivity(new Intent(getApplicationContext(), Login_Activity.class));
    }
}
