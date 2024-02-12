package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImageActivity extends AppCompatActivity {
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        imageView=findViewById(R.id.image_view);
        Intent intent=getIntent();
        String imageUri=intent.getStringExtra("imageuri");
        try{
            FileInputStream fis=openFileInput(imageUri);
            Bitmap bitmap= BitmapFactory.decodeStream(fis);
            imageView.setImageBitmap(bitmap);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
 }
}
}