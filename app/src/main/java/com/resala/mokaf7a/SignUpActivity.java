package com.resala.mokaf7a;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.resala.mokaf7a.classes.User;

import static com.resala.mokaf7a.LoginActivity.branches;
import static com.resala.mokaf7a.LoginActivity.userId;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email_et;
    EditText password_et;
    FirebaseDatabase database;
    Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        email_et = findViewById(R.id.editTextTextEmail);
        password_et = findViewById(R.id.editTextPassword);
        spin = findViewById(R.id.branchSpinner);
        database = FirebaseDatabase.getInstance();

        ArrayAdapter<String> aa = new ArrayAdapter<>(this, R.layout.spinner_item, branches);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
    }

    private void createAccount(String email, String password) {
        if (!validateForm()) return;
        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        this,
                        task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                //                  task.getException().printStackTrace();
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Authentication failed ,maybe used email or weak password ",
                                        Toast.LENGTH_SHORT)
                                        .show();
                                updateUI(null);
                            }
                        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            userId = user.getUid();
            DatabaseReference usersRef = database.getReference("users");
            DatabaseReference currentUser = usersRef.child(userId);
            currentUser.setValue(
                    new User(
                            spin.getSelectedItem().toString(),
                            email_et.getText().toString().trim()));

            Toast.makeText(this, "Account created Successfully..", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = email_et.getText().toString();
        if (TextUtils.isEmpty(email)) {
            email_et.setError("Required.");
            valid = false;
        } else {
            email_et.setError(null);
        }

        String password = password_et.getText().toString();
        if (TextUtils.isEmpty(password)) {
            password_et.setError("Required.");
            valid = false;
        } else {
            password_et.setError(null);
        }

        return valid;
    }

    public void newAccClick(View view) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            if (connectivityManager.getActiveNetworkInfo() == null
                    || !connectivityManager.getActiveNetworkInfo().isConnected()) {
                //          Toast.makeText(getApplicationContext(), "No Internet",
                // Toast.LENGTH_SHORT).show();
                Snackbar.make(view, "No Internet", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                return;
            }
        }
        createAccount(email_et.getText().toString(), password_et.getText().toString());

    }

    public void goToLogin(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}