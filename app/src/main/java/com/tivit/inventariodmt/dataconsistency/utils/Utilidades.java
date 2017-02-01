package com.tivit.inventariodmt.dataconsistency.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.toolbox.StringRequest;
import com.itextpdf.text.List;
import com.tivit.inventariodmt.dataconsistency.provider.EquipamentoContract;
import com.tivit.inventariodmt.dataconsistency.provider.LocalidadeContract;
import com.tivit.inventariodmt.dto.EquipamentoDTO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Created by kaique.rocha on 25/10/2016.
 */

public class Utilidades extends AppCompatActivity {


    public static boolean materialDesign() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }


    public static JSONObject deCursorAJSONObjectEquipamento(Cursor c) {
        JSONObject jObject = new JSONObject();
        String inv_fs_ic_numero_serie;
        String inv_fs_ic_Patrimonio;
        String inv_fs_ic_RFID;
        String tipo_equipamento_id;
        String status_id;
        String departamento_id;
        String localidade_id;
        String data_criacao;
        String estado;
        String fabricante;
        String modelo;

        inv_fs_ic_numero_serie = c.getString(c.getColumnIndex(EquipamentoContract.Columnas.N_SERIE));
        inv_fs_ic_Patrimonio = c.getString(c.getColumnIndex(EquipamentoContract.Columnas.PATRIMONIO));
        inv_fs_ic_RFID = c.getString(c.getColumnIndex(EquipamentoContract.Columnas.RFID));
        tipo_equipamento_id = c.getString(c.getColumnIndex(EquipamentoContract.Columnas.TIPO));
        status_id = c.getString(c.getColumnIndex(EquipamentoContract.Columnas.STATUS));
        departamento_id = c.getString(c.getColumnIndex(EquipamentoContract.Columnas.DEPARTAMENTO));
        localidade_id = c.getString(c.getColumnIndex(EquipamentoContract.Columnas.LOCALIDADE));
        data_criacao = c.getString(c.getColumnIndex(EquipamentoContract.Columnas.DATA));
        estado = c.getString(c.getColumnIndex(EquipamentoContract.Columnas.ESTADO));
        fabricante = c.getString(c.getColumnIndex(EquipamentoContract.Columnas.FABRICANTE));
        modelo = c.getString(c.getColumnIndex(EquipamentoContract.Columnas.MODELO));

        try {
            jObject.put(EquipamentoContract.Columnas.N_SERIE,inv_fs_ic_numero_serie);
            jObject.put(EquipamentoContract.Columnas.PATRIMONIO, inv_fs_ic_Patrimonio);
            jObject.put(EquipamentoContract.Columnas.RFID, inv_fs_ic_RFID);
            jObject.put(EquipamentoContract.Columnas.TIPO, tipo_equipamento_id);
            jObject.put(EquipamentoContract.Columnas.STATUS, status_id);
            jObject.put(EquipamentoContract.Columnas.DEPARTAMENTO, departamento_id);
            jObject.put(EquipamentoContract.Columnas.LOCALIDADE, localidade_id);
            jObject.put(EquipamentoContract.Columnas.DATA, data_criacao);
            jObject.put(EquipamentoContract.Columnas.ESTADO, estado);
            jObject.put(EquipamentoContract.Columnas.FABRICANTE, fabricante);
            jObject.put(EquipamentoContract.Columnas.MODELO, modelo);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Cursor a JSONObject", String.valueOf(jObject));

        return jObject;
    }

    public static JSONObject deCursorAJSONObjectLocalidade(Cursor c)
    {
        JSONObject j = new JSONObject();
        String inv_FS_Loc_Id_Localidade;
        String inv_FS_Loc_Endereco;
        String inv_FS_Loc_Descricao;
        String inv_FS_Loc_cep;
        String inv_FS_Loc_cidade;
        String inv_FS_Loc_estado;
        String inv_FS_Loc_nome_localidade;

        inv_FS_Loc_Id_Localidade = c.getString(c.getColumnIndex(LocalidadeContract.Colunas._ID));
        inv_FS_Loc_Endereco = c.getString(c.getColumnIndex(LocalidadeContract.Colunas.ENDERECO));
        inv_FS_Loc_Descricao = c.getString(c.getColumnIndex(LocalidadeContract.Colunas.DESCRICAO));
        inv_FS_Loc_cep = c.getString(c.getColumnIndex(LocalidadeContract.Colunas.CEP));
        inv_FS_Loc_estado = c.getString(c.getColumnIndex(LocalidadeContract.Colunas.ESTADO));
        inv_FS_Loc_cidade = c.getString(c.getColumnIndex(LocalidadeContract.Colunas.CIDADE));
        inv_FS_Loc_nome_localidade = c.getString(c.getColumnIndex(LocalidadeContract.Colunas.NOME));

        try{

            j.put(LocalidadeContract.Colunas._ID, inv_FS_Loc_Id_Localidade);
            j.put(LocalidadeContract.Colunas.ENDERECO, inv_FS_Loc_Endereco);
            j.put(LocalidadeContract.Colunas.DESCRICAO, inv_FS_Loc_Descricao);
            j.put(LocalidadeContract.Colunas.CEP, inv_FS_Loc_cep);
            j.put(LocalidadeContract.Colunas.ESTADO, inv_FS_Loc_estado);
            j.put(LocalidadeContract.Colunas.CIDADE, inv_FS_Loc_cidade);
            j.put(LocalidadeContract.Colunas.NOME, inv_FS_Loc_nome_localidade);
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return j;
    }


    public static boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void setTaskBarColored(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = context.getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //status bar height


            View view = new View(context);
            view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.getLayoutParams().height = 71;
            ((ViewGroup) w.getDecorView()).addView(view);
            view.setBackgroundColor(Color.RED);
        }
    }

    public static void escreverArquivo(String sFileName, String sBody) throws IOException {
        File root = new File(Environment.getExternalStorageDirectory(), "Relatorios");
        if (!root.exists()) {
            root.mkdirs();
        }
        File gpxfile = new File(root, sFileName);
        FileWriter writer = new FileWriter(gpxfile);
        writer.append(sBody);
        writer.flush();
        writer.close();
    }

    public void abrirArquivo(){
        Intent i = new Intent();
        File root = new File(Environment.getExternalStorageDirectory(), "Relatorios");
        File relat = new File(root,"Invetario.html");

        i.setDataAndType(Uri.fromFile(relat), "text/html");
        startActivity(i);
    }

}
