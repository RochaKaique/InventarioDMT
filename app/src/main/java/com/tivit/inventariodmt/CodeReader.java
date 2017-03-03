package com.tivit.inventariodmt;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CodeReader extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            int permissionCheck = this.checkSelfPermission("Manifest.permission.CAMERA");
            permissionCheck += this.checkSelfPermission("Manifest.permission.CAMERA");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.CAMERA}, 1001); //Any number
            }
        }
        setContentView(R.layout.activity_code_reader);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void handleResult(Result result) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("barcodeFormat", result.getBarcodeFormat().toString());
        returnIntent.putExtra("barcodeValue", result.getText());
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
