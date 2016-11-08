package com.tivit.inventariodmt;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tivit.inventariodmt.dataconsistency.provider.EquipamentoContract;
import com.tivit.inventariodmt.dataconsistency.sync.SyncAdapter;
import com.tivit.inventariodmt.dataconsistency.utils.Utilidades;
import com.tivit.inventariodmt.util.ConexaoInternet;

public class MenuActivity extends AppCompatActivity {

    private BluetoothAdapter blueAdapter;
    private TextView verificaBluetooth, cadastrarEquipamento, contagem;
    //private Button cadastrarEquipamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ConexaoInternet netConn = new ConexaoInternet(this);

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
                startActivity(new Intent(this, ContagemActivity.class));
                break;
            case R.id.tvDownloadDados:
                if(Utilidades.isConnected(getApplicationContext())) {
                    SyncAdapter.sincronizarAhora(getApplicationContext(), false);
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
            case R.id.tvUploadDB:
                if(Utilidades.isConnected(getApplicationContext())) {
                    AlertDialog.Builder msg = new AlertDialog.Builder(this);
                    msg.setTitle("ATENÇÃO").setMessage("Este procedimento deve ser feito ao final de suas atividades \n\n <<Isto Irá Apagar Seus Dados Locais>> \n\n Condifima a Solicitação de Upload de Dados?");
                    msg.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    msg.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SyncAdapter.sincronizarAhora(getApplicationContext(), true);
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
        Intent it = new Intent(this, TestActivity.class);
        startActivity(it);
    }
}
