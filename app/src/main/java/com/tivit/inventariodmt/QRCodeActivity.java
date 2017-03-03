package com.tivit.inventariodmt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class QRCodeActivity extends AppCompatActivity {

    TextView txtQrc, txtBc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        txtQrc = (TextView) findViewById(R.id.txtQRCode);
        txtBc = (TextView) findViewById(R.id.txtBarCode);

    }

    public void lerCodigo(View v){
        Intent intent = new Intent(this, CodeReader.class);
        startActivityForResult(intent,3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String tipoDeCode = data.getStringExtra("barcodeFormat");
        String valorNoCode = data.getStringExtra("barcodeValue");

        if(tipoDeCode.equalsIgnoreCase("QR_Code")){
            txtQrc.setText(valorNoCode);
        }
        else{
            txtBc.setText(valorNoCode);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
