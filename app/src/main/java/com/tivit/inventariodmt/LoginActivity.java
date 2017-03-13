package com.tivit.inventariodmt;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tivit.inventariodmt.dao.UsuarioDAO;
import com.tivit.inventariodmt.dataconsistency.sync.SyncAdapter;
import com.tivit.inventariodmt.dataconsistency.utils.Utilidades;

public class LoginActivity extends AppCompatActivity implements Runnable {
    SharedPreferences sPreferences = null;
    TextView txtLogin;
    TextView txtSenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Utilidades.setTaskBarColored(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sPreferences = getSharedPreferences("com.tivit.inventariodmt", MODE_PRIVATE);

        if (sPreferences.getBoolean("firstRun", true)) {
            if (!Utilidades.isConnected(getApplicationContext())) {
                AlertDialog.Builder msg = new AlertDialog.Builder(this);
                msg.setTitle("Verifique A Conexão");
                msg.setMessage("Você Necessita de Conexão à Internet na Primeira Execução!");
                msg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoginActivity.super.finish();
                        System.exit(0);
                    }
                });
                msg.show();

            }
        }
        txtLogin = (TextView) findViewById(R.id.txtLogin);
        txtSenha = (TextView) findViewById(R.id.txtSenha);
    }

    public void onClickLogin(View view)
    {
        ProgressBar pLogin = (ProgressBar) findViewById(R.id.pLoadLogin);
        MenuActivity.login = txtLogin.getText().toString();
        MenuActivity.pass = txtSenha.getText().toString();
        SyncAdapter.sincronizarAhora(getApplicationContext(), false, 3);
        pLogin.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        if(sPreferences.getBoolean("firstRun", true))
            handler.postDelayed(this, 3000);
        else
            handler.postDelayed(this, 1000);

    }

    @Override
    public void run() {
        ProgressBar pLogin = (ProgressBar) findViewById(R.id.pLoadLogin);
        UsuarioDAO usu = new UsuarioDAO(this);
        boolean b = usu.autenticaUsuario(MenuActivity.login.toLowerCase(), MenuActivity.pass);
        if(b){
            sPreferences.edit().putBoolean("firstRun", false).commit();
            startActivity(new Intent(this, MenuActivity.class));
            finish();
        }else{
            Toast.makeText(this, "Usuario ou Senha Invalidos!", Toast.LENGTH_SHORT).show();
            pLogin.setVisibility(View.INVISIBLE);
        }
    }
}
