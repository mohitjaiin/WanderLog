package com.example.expensemanager;

import android.content.Intent;
import android.media.MediaPlayer;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private VideoView videoView;
    TextInputEditText editTextEmail, editTextPassword;
    Button buttonlogin;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        buttonlogin = findViewById(R.id.loginButton);
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        // Find the VideoView
        videoView = findViewById(R.id.videoView);

        // Set the video Uri (replace video_uri with your video file path)
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.launchsand);
        videoView.setVideoURI(uri);

        // Enable looping
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
                // Start playing the video
                videoView.start();
                // Adjust video dimensions to fit the screen
                adjustVideoDimensions(mediaPlayer);
            }
        });
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Enter email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Login Success.", Toast.LENGTH_SHORT).show();
                                    // Redirect to MainActivity
                                    Intent intent = new Intent(LoginActivity.this, TripMainActivity.class);
                                    startActivity(intent);
                                    finish(); // Finish the LoginActivity
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    System.out.println(task.getException().getMessage());
                                }
                            }
                        });
            }
        });

    }
    private void adjustVideoDimensions(MediaPlayer mediaPlayer) {
        float videoProportion = mediaPlayer.getVideoWidth() / (float) mediaPlayer.getVideoHeight();
        float screenProportion = getResources().getDisplayMetrics().widthPixels / (float) getResources().getDisplayMetrics().heightPixels;
        android.view.ViewGroup.LayoutParams lp = videoView.getLayoutParams();
        if (videoProportion > screenProportion) {
            lp.width = getResources().getDisplayMetrics().widthPixels;
            lp.height = (int) (getResources().getDisplayMetrics().widthPixels / videoProportion);
        } else {
            lp.width = (int) (getResources().getDisplayMetrics().heightPixels * videoProportion);
            lp.height = getResources().getDisplayMetrics().heightPixels;
        }
        videoView.setLayoutParams(lp);
    }
}