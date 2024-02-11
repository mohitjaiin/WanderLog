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

public class LaunchActivity extends AppCompatActivity {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

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

        // Find the Login button
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

        // Find the Login text
        TextView loginText = findViewById(R.id.textLogin);
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to LoginActivity
                Intent intent = new Intent(LaunchActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Finish the LaunchActivity
            }
        });
    }

    // Adjust video dimensions to fit the screen
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
