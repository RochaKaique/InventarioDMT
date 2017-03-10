package com.tivit.inventariodmt;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tivit.inventariodmt.RFID.DotR900.OnBtEventListener;
import com.tivit.inventariodmt.RFID.DotR900.R900;
import com.tivit.inventariodmt.RFID.DotR900.R900Protocol;
import com.tivit.inventariodmt.dao.DatabaseHelper;
import com.tivit.inventariodmt.dao.EquipamentoDAO;
import com.tivit.inventariodmt.dao.PreencheCombosDao;
import com.tivit.inventariodmt.dataconsistency.provider.EquipamentoContract;
import com.tivit.inventariodmt.dataconsistency.utils.Utilidades;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.TreeSet;

import static com.tivit.inventariodmt.R.styleable.View;

public class ContagemActivityNova extends AppCompatActivity implements OnBtEventListener {

//    public static int ENABLE_BLUETOOTH = 1;
//    public static int SELECT_PAIRED_DEVICE = 2;
//    public static int SELECT_DISCOVERED_DEVICE = 3;

    private R900 leitor;
    public static final int MSG_ENABLE_LINK_CTRL = 10;
    public static final int MSG_DISABLE_LINK_CTRL = 11;
    public static final int MSG_ENABLE_DISCONNECT = 12;
    public static final int MSG_DISABLE_DISCONNECT = 13;
    public static final int MSG_SHOW_TOAST = 20;
    public static final int MSG_REFRESH_LIST_TAG = 22;
    public static final int MSG_BT_DATA_RECV = 10;
    private BaseAdapter mAdapterTag;
    private static final int[] TX_DUTY_OFF =
            {10, 40, 80, 100, 160, 180};

    private static final int[] TX_DUTY_ON =
            {190, 160, 70, 40, 20};

    private static final String[] TXT_DUTY =
            {"90%", "80%", "60%", "41%", "20%"};

    ProgressBar progressBar;
    TextView llblInfo;
    private static TextView lblLeitor;
    Button btnFinaliza;
    Button btnConecta;
    TextView lblStatus;
    TextView lblSintese;
    TextView lblEncontrados;
    TextView lblNaoEncontrados;
    TextView lblNaoInventLoc;
    TextView txtEncontrados;
    TextView txtNaoEncontrados;
    TextView txtNaoInventLoc;
    Button btnRelat;
    TextView txtMaisInfo;

    private DatabaseHelper helper;
    private SQLiteDatabase db;
    PreencheCombosDao equip;


    private static final String[] PROJECTION = new String[]{
            EquipamentoContract.Columnas._ID,
            EquipamentoContract.Columnas.ID_REMOTA,
            EquipamentoContract.Columnas.N_SERIE,
            EquipamentoContract.Columnas.PATRIMONIO,
            EquipamentoContract.Columnas.RFID,
            EquipamentoContract.Columnas.TIPO,
            EquipamentoContract.Columnas.STATUS,
            EquipamentoContract.Columnas.DEPARTAMENTO,
            EquipamentoContract.Columnas.LOCALIDADE,
            EquipamentoContract.Columnas.DATA,
            EquipamentoContract.Columnas.ESTADO,
    };
    ConnectionThreadContagem connect;
    private static String strRfid = "";
    private static int numLeituras = 0;
//    private BluetoothAdapter blueAdapter;
    private boolean verificaProgress;
    static Set<String> rfids;
    static Set<String> rfidsBanco;
    static Set<String> rfidValido;
    static Set<String> rfidInvalido;
    private static boolean isDeviceConnected = false;
    private int totalEqLocalizados;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        leitor = new R900(this, mHandler, this);
        mAdapterTag = new TagAdapter(getApplicationContext(), leitor.getListaPatrimonio());
        this.equip = new PreencheCombosDao(this);
        Utilidades.setTaskBarColored(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contagem_nova);
        llblInfo = (TextView) findViewById(R.id.txtInfo);
        progressBar = (ProgressBar) findViewById(R.id.progressBar4);
        lblLeitor = (TextView) findViewById(R.id.txtLeitor);
        btnFinaliza = (Button) findViewById(R.id.btnFinaliza);
        btnConecta = (Button) findViewById(R.id.btnConectar);
        lblStatus = (TextView) findViewById(R.id.txtStatus);
        lblSintese = (TextView) findViewById(R.id.txtApresentacao);
        lblEncontrados = (TextView) findViewById(R.id.txtLblEncontrados);
        lblNaoEncontrados = (TextView) findViewById(R.id.txtLblFaltantes);
        lblNaoInventLoc = (TextView) findViewById(R.id.txtLblNaoInventariadosLoc);
        btnRelat = (Button) findViewById(R.id.btnRelat);
        txtMaisInfo = (TextView) findViewById(R.id.txtMaisInfo);
        txtEncontrados = (TextView) findViewById(R.id.txtEncontrados);
        txtNaoEncontrados = (TextView) findViewById(R.id.txtFaltantes);
        txtNaoInventLoc = (TextView) findViewById(R.id.txtNaoInventariadosLoc);

//        this.blueAdapter = BluetoothAdapter.getDefaultAdapter();
        this.verificaProgress = false;
        this.rfids = new TreeSet<>();
        this.rfidValido = new TreeSet<>();
        this.rfidInvalido = new TreeSet<>();
        this.rfidsBanco = new TreeSet<>();
//        this.blueAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    private SQLiteDatabase getDb() {

        helper = new DatabaseHelper(getApplicationContext());
        if (db == null) {
            db = helper.getReadableDatabase();
        }
        return db;
    }

    public void conectaLeitor(View v)
    {
//        if (blueAdapter.isEnabled()) {
            Intent intent = new Intent(this, DiscoveredDevices.class);
            startActivityForResult(intent, BluetoothActivity.SELECT_DISCOVERED_DEVICE);
//        }
        if(lblLeitor.getText() != "Desconectado." && lblLeitor.getText() != "Nenhum dispositivo selecionado.") {
            btnFinaliza.setVisibility(android.view.View.VISIBLE);
            btnConecta.setVisibility(android.view.View.INVISIBLE);
            llblInfo.setVisibility(android.view.View.VISIBLE);
            progressBar.setVisibility(android.view.View.VISIBLE);
        }
    }

    public void finalizaContagem(View v)
    {
//        rfidValido.clear();
//        rfidInvalido.clear();
//        rfids.clear();
        verificarRfids();
        String idsInvalidos = "\n" +rfidInvalido.size();
        String idsValidos = "" + rfidValido.size();
        String idsFaltantes = "\n" + (totalEqLocalizados - rfidValido.size());
        txtNaoEncontrados.setText(idsFaltantes);
        txtNaoInventLoc.setText(idsInvalidos);
        txtEncontrados.setText("\n" + idsValidos);

        verificaProgress = false;

        lblStatus.setVisibility(android.view.View.INVISIBLE);
        btnConecta.setVisibility(android.view.View.INVISIBLE);
        btnFinaliza.setVisibility(android.view.View.INVISIBLE);
        llblInfo.setVisibility(android.view.View.INVISIBLE);
        progressBar.setVisibility(android.view.View.INVISIBLE);
        lblLeitor.setVisibility(android.view.View.INVISIBLE);
        lblSintese.setVisibility(android.view.View.VISIBLE);
        lblEncontrados.setVisibility(android.view.View.VISIBLE);
        txtEncontrados.setVisibility(android.view.View.VISIBLE);
        lblNaoEncontrados.setVisibility(android.view.View.VISIBLE);
        txtNaoEncontrados.setVisibility(android.view.View.VISIBLE);
        lblNaoInventLoc.setVisibility(android.view.View.VISIBLE);
        txtNaoInventLoc.setVisibility(android.view.View.VISIBLE);
        txtMaisInfo.setVisibility(android.view.View.VISIBLE);
        btnRelat.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String addressDispositivo = data.getStringExtra("btDevAddress");
            leitor.conectar(addressDispositivo);
        }
        else{
            btnFinaliza.setVisibility(android.view.View.INVISIBLE);
            btnConecta.setVisibility(android.view.View.VISIBLE);
            llblInfo.setVisibility(android.view.View.INVISIBLE);
            progressBar.setVisibility(android.view.View.INVISIBLE);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            switch (msg.what) {
                case MSG_BT_DATA_RECV:
                    onNotifyBtDataRecv();
                    break;
                case MSG_SHOW_TOAST:
                    Toast.makeText(ContagemActivityNova.this, (String) msg.obj, Toast.LENGTH_LONG).show();
                    break;
                case MSG_REFRESH_LIST_TAG:
                    try {
                        mAdapterTag.notifyDataSetChanged();
                        rfids.add(String.valueOf(leitor.getListaPatrimonio().get(leitor.getListaPatrimonio().size()-1)));
                    } catch (Exception ex) {
                        Log.d("ERRO", ex.getMessage());
                    }
            }
        }
    };

    public void verificarRfids() {
        Uri uri = EquipamentoContract.CONTENT_URI;

        //Iterator i = rfids.iterator();
        //int loc = ((LocalidadeDTO) localidade.getSelectedItem()).getInv_FS_Loc_Id_Localidade();
        String where = EquipamentoContract.Columnas.RFID + " != ?";
        String[] param = new String[]{""};
        Cursor cCount = getContentResolver().query(uri, PROJECTION, where, param, null);
        cCount.moveToFirst();
        do{
            rfidsBanco.add(cCount.getString(cCount.getColumnIndex(EquipamentoContract.Columnas.RFID)));
        }while(cCount.moveToNext());
        totalEqLocalizados = cCount.getCount();
        cCount.close();
        for (String i : rfids) {
            String selection = EquipamentoContract.Columnas.RFID + " = ?";
            String[] selectionArgs = new String[]{i};
            Cursor c = getContentResolver().query(uri, PROJECTION, selection, selectionArgs, null);


            if (c.getCount() > 0) {
                rfidValido.add(i);
            } else {
                rfidInvalido.add(i);
            }
            Log.i("RFID", i);
            c.close();
        }
    }

    public void gerarRelatorioHtml(View v)
    {
        //INICIO DO ARQUIVO HTML
        StringBuilder str = new StringBuilder();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        str.append("<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<font face=\"Arial\">" +
                "<style type=\"text/css\">" +
                ".flat-table {" +
                //"display: block;" +
                "font-family: sans-serif;" +
                "-webkit-font-smoothing: antialiased;" +
                "font-size: 70%;" +
                "overflow: auto;" +
                "width: auto;" +
                "" +
                "}" +
                ".flat-table th {" +
                "background-color: #CD0000;" +
                "color: white;" +
                "font-weight: normal;" +
                "padding: 10px 20px;" +
                "text-align: center;" +
                "}" +
                ".flat-table td {" +
                "background-color: #eeeeee;" +
                "color: #6f6f6f;" +
                "padding: 10px 20px;" +
                "}" +
                "</style>" +
                "<p>Relatório emitido por: "+MenuActivity.login+"</p>" +
                "<p>Relatótio emitido em: "+dateFormat.format(System.currentTimeMillis())+"</p>"+
                "</head>" +
                "<body>" +
                "<center>" +
                "<div>" +
                //"<br/>" +
                //"<br/>" +
                "<div class=\"row\">" +
                "<div class=\"col-md-4\">" +
               // "<img src=\"file:///android_asset/tivit.png\">" +
                "</div> " +
                "<div class=\"col-md-8\">" +
                "<h3>Inventário de Equipamentos</h3>" +
                "</div>" +
                "</div>" +
                "<br/>" +
                "<br/>" +
                "<div class=\"row\">" +
                "<div class=\"col-md-8\">" +
                "Equipamentos Encontrados " +
                "<table class=\"flat-table\">" +
                "<tbody>" +
                "<thead> " +
                "<th>RFID</th>" +
                "<th>Nº Série</th>" +
                "<th>Tipo</th>" +
                "<th>Marca</th>" +
                "<th>Modelo</th>" +
                "<th>Localidade</th>" +
                "</thead>");
        //ECREVENDO O CONTEUDO NA TABELA DE ENCONTADOS
        for(String i : rfidValido)
        {
//            Uri uri = EquipamentoContract.CONTENT_URI;
//            String selection = EquipamentoContract.Columnas.RFID + " = ?";
//            String[] selectionArgs = new String[]{i.toUpperCase().replace("\n","").replace("\r","")};

            Cursor c = equip.selcionaPorRfid(i);
            c.moveToFirst();
            do{
                str.append(
                        "<tr>" +
                                "<td>" + c.getString(c.getColumnIndex("inv_fs_ic_RFID")) + "</td>" +
                                "<td>" + c.getString(c.getColumnIndex("inv_fs_ic_numero_serie")) + "</td>" +
                                "<td>" + c.getString(c.getColumnIndex("inv_FS_TP_Nome_Equipamento")) + "</td>" +
                                "<td>" + c.getString(c.getColumnIndex("inv_FS_Fab_Nome_Fabricante")) + "</td>" +
                                "<td>" + c.getString(c.getColumnIndex("inv_FS_Mod_Nome_Modelo")) + "</td>" +
                                "<td>" + c.getString(c.getColumnIndex("inv_FS_Loc_Descricao")) + "</td>" +
                                "</tr>");
            }while(c.moveToNext());

            rfidsBanco.remove(i);
        }


        //FECHANDO AS TAGS DA TABELA DE ENCONTRADOS
        str.append("</tbody></table>" +
                "</div>" +
                "<br/>" +
                "<br/>" +
                "<br/>");

        //INICIANDO A TABELA DE NÃO ENCONTRADOS
        str.append("<div class=\"col-md-8\">" +
                "Equipamentos Não Encontrados " +
                "<table class=\"flat-table\">" +
                "<thead>" +
                "<tbody>" +
                "<th>RFID</th>" +
                "<th>Nº Série</th>" +
                "<th>Tipo</th>" +
                "<th>Marca</th>" +
                "<th>Modelo</th>" +
                "<th>Localidade</th>" +
                "</thead>");
        //ESCREVENDO O CONTEÚDO DOS NÃO ENCONTRADOS
        for(String i : rfidsBanco) {
            Cursor c = equip.selcionaPorRfid(i);
            c.moveToFirst();
            do{
                str.append(
                        "<tr>" +
                                "<td>" + c.getString(c.getColumnIndex("inv_fs_ic_RFID")) + "</td>" +
                                "<td>" + c.getString(c.getColumnIndex("inv_fs_ic_numero_serie")) + "</td>" +
                                "<td>" + c.getString(c.getColumnIndex("inv_FS_TP_Nome_Equipamento")) + "</td>" +
                                "<td>" + c.getString(c.getColumnIndex("inv_FS_Fab_Nome_Fabricante")) + "</td>" +
                                "<td>" + c.getString(c.getColumnIndex("inv_FS_Mod_Nome_Modelo")) + "</td>" +
                                "<td>" + c.getString(c.getColumnIndex("inv_FS_Loc_Descricao")) + "</td>" +
                                "</tr>");
            }while(c.moveToNext());
        }
        //FECHANDO O HTML
        str.append("</tbody></table>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<table><br/><br/><br/><br/><br/><br/><br/><tr><td>_______________________________   <br/><center>Técnico</center></td><td>   _______________________________<br/><center>Gestor Responsável</center></td></tr></table>" +
                "</center>" +
                "</body>" +
                "</font>" +
                "</html>");
        try {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int permissionCheck = this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
                permissionCheck += this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
                if (permissionCheck != 0) {

                    this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001); //Any number
                    Utilidades.escreverArquivo("Inventario.html",str.toString());
                    Toast.makeText(this, "Realtório Salvo", Toast.LENGTH_SHORT).show();
                }
            }
            Utilidades.escreverArquivo("Inventario.html",str.toString());
            Toast.makeText(this, "Realtório Salvo", Toast.LENGTH_SHORT).show();
            Utilidades u = new Utilidades();
            //u.abrirArquivo();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //    region MÉTODOS BLUETOOTH
    @Override
    public void onBtFoundNewDevice(BluetoothDevice device) {

    }

    @Override
    public void onBtScanCompleted() {

    }

    @Override
    public void onBtConnected(BluetoothDevice device) {
        setEnabledLinkCtrl(true);
        showToastByOtherThread("Conectou: " + leitor.getDispositivo().getName(), Toast.LENGTH_SHORT);
        leitor.sendCmdOpenInterface1();
        sendBeep(0);
        leitor.sendSettingTxCycle(TX_DUTY_ON[0], TX_DUTY_OFF[0]);
    }

    @Override
    public void onBtDisconnected(BluetoothDevice device) {

    }

    @Override
    public void onBtConnectFail(BluetoothDevice device, String msg) {

    }

    @Override
    public void onBtDataSent(byte[] data) {

    }

    @Override
    public void onBtDataTransException(BluetoothDevice device, String msg) {

    }

    @Override
    public void onNotifyBtDataRecv() {

        if (leitor == null)
            return;

        try {
            leitor.leitura();
            //mHandler.sendEmptyMessage(MSG_REFRESH_LIST_TAG);
        }
        catch (Exception ex) {
            Log.d("ERRO", ex.getMessage());
        }

    }
//    endregion

    //    region METODOS LEITOR
    private void setEnabledLinkCtrl(boolean bEnable) {
        if (bEnable)
            mHandler.sendEmptyMessageDelayed(MSG_ENABLE_LINK_CTRL, 50);
        else
            mHandler.sendEmptyMessageDelayed(MSG_DISABLE_LINK_CTRL, 50);
    }
    private void showToastByOtherThread(String msg, int time) {
        mHandler.removeMessages(MSG_SHOW_TOAST);
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SHOW_TOAST, time, 0, msg));
    }

    public void sendSettingTxPower( int a )
    {
        if( leitor != null )
        {
            leitor.sendData(R900Protocol.makeProtocol( R900Protocol.CMD_SET_TX_POWER, new int[]{ a } ) );
        }
    }
    public void sendBeep( int f_on )
    {
        if( leitor != null )
        {
            leitor.sendData(R900Protocol.makeProtocol( R900Protocol.CMD_BEEP,
                    new int[]{ f_on } ) );
        }
    }
//    endregion
}
