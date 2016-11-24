package com.tivit.inventariodmt;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tivit.inventariodmt.dao.UsuarioDAO;
import com.tivit.inventariodmt.dataconsistency.sync.SyncAdapter;
import com.tivit.inventariodmt.dataconsistency.utils.Utilidades;

public class LoginActivity extends AppCompatActivity{
    SharedPreferences sPreferences = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Utilidades.setTaskBarColored(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sPreferences = getSharedPreferences("firstRun", MODE_PRIVATE);

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
//
//        Handler handler = new Handler();
//        handler.post(this);

    }

//    @Override
//    public void run() {
//
//        if (sPreferences.getBoolean("firstRun", true)) {
//            if (!Utilidades.isConnected(getApplicationContext())) {
//                AlertDialog.Builder msg = new AlertDialog.Builder(this);
//                msg.setTitle("Verifique A Conexão");
//                msg.setMessage("Você Necessita de Conexão à Internet na Primeira Execução!");
//                msg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        LoginActivity.super.finish();
//                        System.exit(0);
//                    }
//                });
//                msg.show();
//
//            }
//        }
//    }

    public void onClickLogin(View view)
    {
        UsuarioDAO usu = new UsuarioDAO(this);
        TextView txtLogin = (TextView) findViewById(R.id.txtLogin);
        TextView txtSenha = (TextView) findViewById(R.id.txtSenha);

        MenuActivity.login = txtLogin.getText().toString();
        MenuActivity.pass = txtSenha.getText().toString();


        SyncAdapter.sincronizarAhora(getApplicationContext(), false, 3);

        boolean b = usu.autenticaUsuario(MenuActivity.login, MenuActivity.pass);
        if(b){
            startActivity(new Intent(this, MenuActivity.class));
            finish();
        }else{
            Toast.makeText(this, "Usuario ou Senha Ivalidos!", Toast.LENGTH_SHORT).show();
        }

    }
}
