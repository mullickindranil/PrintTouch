package com.example.mulli.navview;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;

import java.io.File;
import java.io.IOException;

public class captureimage extends AppCompatActivity  {
    private Intent CAMERA_CAPTURE ;
    //captured picture uri
    private Uri picUri;
    private int PIC_CROP ;
    private File file;

    protected void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.captureimage);
        Button captureBtn = (Button)findViewById(R.id.capture_btn);
//handle button clicks
        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getId() == R.id.capture_btn) {
                    try {
                        int REQUEST_CODE = 99;
                        int preference = ScanConstants.OPEN_CAMERA;
                        Intent intent = new Intent(view.getContext(), ScanActivity.class);
                        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
                        startActivityForResult(intent, REQUEST_CODE);
                    }
                    catch(ActivityNotFoundException anfe){
                        //display an error message
                        String errorMessage = "Whoops - your device doesn't support capturing images!";
                        Toast toast = Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getExtras().getParcelable(ScanConstants.SCANNED_RESULT);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                getContentResolver().delete(uri, null, null);
                ImageView scannedImageView=findViewById(R.id.picture);
                scannedImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    }

