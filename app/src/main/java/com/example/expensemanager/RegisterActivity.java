package com.example.expensemanager;

import android.content.Intent;
import android.media.MediaPlayer;
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

public class RegisterActivity extends AppCompatActivity {
    private VideoView videoView;
    TextInputEditText editTextEmail, editTextPassword;
    Button buttonReg;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextEmail = findViewById(R.id.username);
        mAuth=FirebaseAuth.getInstance();
        editTextPassword = findViewById(R.id.password);
        buttonReg = findViewById(R.id.btn_register);


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

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password;
                email=editTextEmail.getText().toString();
                Toast.makeText(RegisterActivity.this, email,
                        Toast.LENGTH_SHORT).show();
                password=editTextPassword.getText().toString();
                Toast.makeText(RegisterActivity.this, password,
                        Toast.LENGTH_SHORT).show();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this, "enetr email", Toast.LENGTH_SHORT).show();
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(RegisterActivity.this, "Registration Successfull!",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, TripMainActivity.class);
                                    startActivity(intent);
                                    finish(); // Finish the RegisterActivity
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegisterActivity.this, "Authentication failed: " + task.getException().getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });


            }

    });
        // Find the Login text
        TextView loginText = findViewById(R.id.loginText);
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to LoginActivity
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Finish the RegisterActivity
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

