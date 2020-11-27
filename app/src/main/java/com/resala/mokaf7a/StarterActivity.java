package com.resala.mokaf7a;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.resala.mokaf7a.SplashActivity.myRules;

public class StarterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);
        try {
            checkUpdate();
        } catch (Exception ignored) {
            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void checkUpdate() {
//        TextView versionTV = findViewById(R.id.version);
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            int verCode = pInfo.versionCode;
//            versionTV.setText("version : " + version + " (" + verCode + ")");
            if (verCode < myRules.last_important_update) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                alertDialogBuilder.setTitle(getString(R.string.youAreNotUpdatedTitle));
                alertDialogBuilder.setMessage(
                        getString(R.string.youAreNotUpdatedMessage)
                                + " "
                                + myRules.last_important_update
                                + " "
                                + getString(R.string.youAreNotUpdatedMessage1));
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton(
                        R.string.update,
                        (dialog, id) -> {
                            startActivity(
                                    new Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse(
                                                    myRules.playStoreUrl)));
                            dialog.cancel();
                        });
                alertDialogBuilder.show();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void goToSignup(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
    }

    public void goToLogin(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public void goToForm(View view) {
        startActivity(new Intent(getApplicationContext(), FormActivity.class));
    }
}