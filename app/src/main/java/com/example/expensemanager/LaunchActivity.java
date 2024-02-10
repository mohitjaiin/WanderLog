package com.example.expensemanager;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        // Find the VideoView
        VideoView videoView = findViewById(R.id.videoView);

        // Set the video Uri (replace video_uri with your video file path)
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.launchsand);
        videoView.setVideoURI(uri);

        // Create MediaController
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        // Set MediaController to VideoView
        videoView.setMediaController(mediaController);

        // Enable looping
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });

        // Start playing the video
        videoView.start();



        // Find the Login text
        Button btnContinue = findViewById(R.id.btnRegister);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to MainActivity
                Intent intent = new Intent(LaunchActivity.this, TripMainActivity.class);
                startActivity(intent);
                finish(); // Finish the LaunchActivity
            }
        });

        TextView loginText = findViewById(R.id.textLogin);
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to MainActivity
                Intent intent = new Intent(LaunchActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Finish the LaunchActivity
            }
        });
    }
}
