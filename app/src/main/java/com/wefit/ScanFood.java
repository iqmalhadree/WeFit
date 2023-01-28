package com.wefit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wefit.ml.MobilenetV110224Quant;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ScanFood extends AppCompatActivity {

    private ImageView imageView;
    private Button scanButton;
    private Bitmap bitmap;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_food);
        
        getPermission();

        String[] labels = new String[1001];
        int cnt=0;
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(getAssets().open("labels.txt")));
            String line=bf.readLine();
            while(line!=null){
                labels[cnt]=line;
                cnt++;
                line=bf.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageView = findViewById(R.id.foodImage);
        scanButton = findViewById(R.id.scanButton);
        result = findViewById(R.id.resultScan);

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 12);
            }
        });
    }

    void getPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(ScanFood.this, new String[]{Manifest.permission.CAMERA}, 11);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==12){
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);

            String[] labels = new String[1001];
            int cnt=0;
            try {
                BufferedReader bf = new BufferedReader(new InputStreamReader(getAssets().open("labels.txt")));
                String line=bf.readLine();
                while(line!=null){
                    labels[cnt]=line;
                    cnt++;
                    line=bf.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                MobilenetV110224Quant model = MobilenetV110224Quant.newInstance(ScanFood.this);

                // Creates inputs for reference.
                TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.UINT8);

                bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);
                inputFeature0.loadBuffer(TensorImage.fromBitmap(bitmap).getBuffer());

                // Runs model inference and gets result.
                MobilenetV110224Quant.Outputs outputs = model.process(inputFeature0);

                TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                result.setText(labels[getMax(outputFeature0.getFloatArray())]+"");

                // Releases model resources if no longer used.
                model.close();
            } catch (IOException e) {
                // TODO Handle the exception
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    int getMax(float[] floatArray) {
        int max=0;
        for (int i=0; i<floatArray.length; i++){
            if(floatArray[i] > floatArray[max]) max=i;
        }
        return max;
    }
}