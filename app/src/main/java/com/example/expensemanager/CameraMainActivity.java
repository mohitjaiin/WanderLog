package com.example.expensemanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CameraMainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_GALLERY = 101;
    private static final int REQUEST_IMAGE_CAPTURE = 102;
    //    private ImageView imageView;
    private GridView mImageGrid;
    private ImageAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_bills);

        // imageView = findViewById(R.id.image_view);
        mImageGrid=findViewById(R.id.image_grid);
        mAdapter=new ImageAdapter(this);
        mImageGrid.setAdapter(mAdapter);


        Button galleryBtn = findViewById(R.id.gallery_btn);
        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGallery();
            }
        });

        Button cameraBtn = findViewById(R.id.camera_btn);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(CameraMainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // Request camera permission if it has not been granted yet
                    ActivityCompat.requestPermissions(CameraMainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
                }
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
            }
        });
        mImageGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                // Prompt the user to confirm deletion
                AlertDialog.Builder builder = new AlertDialog.Builder(CameraMainActivity.this);
                builder.setMessage("Are you sure you want to delete this image?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Remove the image from the adapter and update the grid
                                String imageUri = (String) mAdapter.getItem(position);
                                deleteImage(imageUri);
                                mAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
                return true;
            }
        });
    }

    // Method to delete image from internal storage
    private void deleteImage(String imageUri) {
        File fileToDelete = new File(getFilesDir(), imageUri);
        if (fileToDelete.exists()) {
            if (fileToDelete.delete()) {
                Toast.makeText(CameraMainActivity.this, "Image deleted successfully", Toast.LENGTH_SHORT).show();
                mAdapter.removeImage(imageUri);
            } else {
                Toast.makeText(CameraMainActivity.this, "Failed to delete image", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK && data != null) {
//            imageView.setVisibility(View.VISIBLE);
//            imageView.setImageURI(data.getData());
            Uri selectedImageUri = data.getData();
            Bitmap selectedImageBitmap;
            try {
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
            } catch (IOException e) {
                e.printStackTrace();
                return; // Handle the error gracefully
            }
            String imageUri = SaveImageToSharedPreferences(selectedImageBitmap);

            mAdapter.addImage(imageUri);
            mAdapter.notifyDataSetChanged();
            mImageGrid.setVisibility(View.VISIBLE);

        } else
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            imageView.setVisibility(View.VISIBLE);
//            imageView.setImageBitmap(imageBitmap);

            String imageUri=SaveImageToSharedPreferences(imageBitmap);

            mAdapter.addImage(imageUri);
            mAdapter.notifyDataSetChanged();
            mImageGrid.setVisibility(View.VISIBLE);
        }
    }

    private String SaveImageToSharedPreferences(Bitmap imageBitmap) {
        SharedPreferences sharedPreferences=getPreferences(MODE_PRIVATE);
        int nextImageIndex= sharedPreferences.getInt("nextImageIndex",0);
        String imageUri="image_"+nextImageIndex+".jpeg";
        try{
            FileOutputStream fos=openFileOutput(imageUri, Context.MODE_PRIVATE);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("nextImageIndex",nextImageIndex+1);
        editor.apply();

        return imageUri;
    }
    private static class ImageAdapter extends BaseAdapter{

        private final Context mContext;
        private final List<String> mImageUris;

        private ImageAdapter(Context mContext) {
            this.mContext = mContext;
            mImageUris=new ArrayList<>();
            File[] files=mContext.getFilesDir().listFiles();
            if(files!=null){
                for(File file:files){
                    if(file.isFile()&& file.getName().startsWith("image_")){
                        mImageUris.add(file.getName());
                    }
                }
            }
            Toast.makeText(mContext, "hi", Toast.LENGTH_SHORT).show();
        }

        public void addImage(String imageUri){
            mImageUris.add(imageUri);
        }

        @Override
        public int getCount() {
            return mImageUris.size();
        }

        @Override
        public Object getItem(int i) {
            return mImageUris.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        public void removeImage(String imageUri) {
            mImageUris.remove(imageUri);
        }


        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ImageView imageView;
            if(view==null){
                imageView=new ImageView((mContext));
                imageView.setLayoutParams(new GridView.LayoutParams(250,250));
                imageView.setScaleType((ImageView.ScaleType.CENTER_CROP));
            }
            else{
                imageView=(ImageView) view;
            }
            String imageUri=mImageUris.get(i);
            try{
                FileInputStream fis=mContext.openFileInput(imageUri);
                Bitmap bitmap= BitmapFactory.decodeStream(fis);
                imageView.setImageBitmap(bitmap);
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return imageView;
        }
 }


}