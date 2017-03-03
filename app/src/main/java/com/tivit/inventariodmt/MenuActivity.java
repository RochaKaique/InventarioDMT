package com.tivit.inventariodmt;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.tivit.inventariodmt.dataconsistency.sync.SyncAdapter;
import com.tivit.inventariodmt.dataconsistency.utils.Utilidades;

public class MenuActivity extends AppCompatActivity implements Runnable{

    private BluetoothAdapter blueAdapter;
    private TextView verificaBluetooth, cadastrarEquipamento, contagem;
    SharedPreferences sPreferences = null;

    public static String login;
    public static String pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Utilidades.setTaskBarColored(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        verificaBluetooth = (TextView) findViewById(R.id.tvVerificaBluetooth);
        cadastrarEquipamento = (TextView) findViewById(R.id.tvCadastrarEquipamento);
        contagem = (TextView) findViewById(R.id.tvContagemEquipamentos);
        SyncAdapter.inicializarSyncAdapter(this);


        blueAdapter = BluetoothAdapter.getDefaultAdapter();

        if (blueAdapter == null) {
            verificaBluetooth.setTextColor(Color.RED);
            verificaBluetooth.setText(R.string.nothing_bluetooth);
            cadastrarEquipamento.setVisibility(View.INVISIBLE);
            contagem.setVisibility(View.INVISIBLE);
        } else {
            if (!blueAdapter.isEnabled()) {
                blueAdapter.enable();
            }
        }

        sPreferences = getSharedPreferences("firstRun", MODE_PRIVATE);
        Handler handler = new Handler();
        handler.post(this);

    }

    public void run(){
        SyncAdapter.sincronizarAhora(getApplicationContext(),false,2);
        if (sPreferences.getBoolean("firstRun", true)) {
            if (Utilidades.isConnected(getApplicationContext())) {
                AlertDialog.Builder msg = new AlertDialog.Builder(this);
                msg.setTitle("Bem Vindo!");
                msg.setMessage("Para a Realização do Inventário Selecione a Localidade Desejada\n");
                msg.setNeutralButton("OK",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sPreferences.edit().putBoolean("firstRun", false).apply();
                        //startActivity(new Intent(getApplicationContext(), DownloadActivity.class));
                    }
                });
                //msg.show();
            } else {
                AlertDialog.Builder msg = new AlertDialog.Builder(this);
                msg.setTitle("Verifique A Conexão");
                msg.setMessage("Você Necessita de Conexão à Internet na Primeira Execução!");
                msg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MenuActivity.super.finish();
                        System.exit(0);
                    }
                });
                msg.show();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void clicaAbrir(View view) {
        switch (view.getId()) {
            case R.id.tvCadastrarEquipamento:
                startActivity(new Intent(this, FormEquipamentoActivity.class));
                break;
            case R.id.tvEquipamentosCadastrados:
                startActivity(new Intent(this, EquipamentosActivity.class));
                break;
            case R.id.tvContagemEquipamentos:
                startActivity(new Intent(this, ContagemActivityNova.class));
                break;
            case R.id.tvDownloadDados:
                if(Utilidades.isConnected(getApplicationContext())) {
                    startActivity(new Intent(this, DownloadActivity.class));
                    SyncAdapter.sincronizarAhora(getApplicationContext(),false,1);
                }
                else
                {
                    AlertDialog.Builder msg = new AlertDialog.Builder(this);
                    msg.setTitle("Verifique A Conexão");
                    msg.setMessage("Conecte-se à Internet!");
                    msg.setNeutralButton("OK",null);
                    msg.show();
                }


                break;
            case R.id.tvQRcode:
                startActivity(new Intent(this, QRCodeActivity.class));
                break;
            case R.id.tvUploadDB:
                if(Utilidades.isConnected(getApplicationContext())) {
                    AlertDialog.Builder msg = new AlertDialog.Builder(this);
                    msg.setTitle("ATENÇÃO").setMessage("Este procedimento deve ser feito ao final de suas atividades \n\n <<Isto Irá Apagar Seu Dados Locais>> \n\n Confirma a Solicitação de Envio de Inventário?");
                    msg.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    msg.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SyncAdapter.sincronizarAhora(getApplicationContext(), true, 1);
                        }
                    });
                    msg.show();
                }
                else
                {
                    AlertDialog.Builder msg = new AlertDialog.Builder(this);
                    msg.setTitle("Verifique A Conexão");
                    msg.setMessage("Conecte-se à Internet!");
                    msg.setNeutralButton("OK",null);
                    msg.show();
                }
                break;
        }
    }



    public void abreAteste(View view)
    {
        startActivity(new Intent(this, TestActivity.class));
    }
}
