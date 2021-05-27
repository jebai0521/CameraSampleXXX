package com.example.camerasample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AutoFitTextureView mTextureView;
    private Camera2Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        helper.open();  //会动态请求权限，请重写 onRequestPermissionsResult
    }

    public void init() {
        mTextureView = findViewById(R.id.texture);
        helper = new Camera2Helper(this, mTextureView);

        helper.setOnImageAvailableListener(new Camera2Helper.OnPreviewCallbackListener() {
            @Override
            public void onImageAvailable(Image image) {
                Log.d("weijw1", "helper onImageAvailable");
            }
        });
    }

    @Override
    public void onPause() {
        helper.closeCamera();
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == helper.getCameraRequestCode()) {
            if (grantResults.length != 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "111", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}