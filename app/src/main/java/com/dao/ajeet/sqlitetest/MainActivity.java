package com.dao.ajeet.sqlitetest;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button addInDB, pickGallery, showPic;
    ImageView im;
    private int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 101;
    private int PICK_IMAGE=1;
    DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper=new DbHelper(this);
        im = findViewById(R.id.imageView);
        addInDB = findViewById(R.id.add_db);
        pickGallery = findViewById(R.id.gallery);
        showPic = findViewById(R.id.show);
        pickGallery.setOnClickListener(this);
        showPic.setOnClickListener(this);
        addInDB.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.gallery) {




            OpenGallery();
        }
        if(view.getId()==R.id.add_db){
            im.setDrawingCacheEnabled(true);
            im.buildDrawingCache();
            Bitmap bitmap = im.getDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            dbHelper.addToDb(data,"abc");


        }
        if(view.getId()==R.id.show){
            Intent i=new Intent(MainActivity.this,ShowActivity.class);
            startActivity(i);
        }
    }

    public void OpenGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
           Uri uri=data.getData();
            try {
                final InputStream inputStream=getContentResolver().openInputStream(uri);
                final Bitmap bitmap=BitmapFactory.decodeStream(inputStream);
                im.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == EXTERNAL_STORAGE_PERMISSION_CONSTANT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
              OpenGallery();
            } else {
                Toast.makeText(this, "Permission Required", Toast.LENGTH_SHORT).show();

            }
        }
    }
}



