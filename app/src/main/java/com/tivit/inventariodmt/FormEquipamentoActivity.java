package com.tivit.inventariodmt;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Date;
import java.util.List;

import com.tivit.inventariodmt.RFID.DotR900.OnBtEventListener;
import com.tivit.inventariodmt.RFID.DotR900.R900;
import com.tivit.inventariodmt.dao.DatabaseHelper;
import com.tivit.inventariodmt.dao.PreencheCombosDao;
import com.tivit.inventariodmt.dataconsistency.provider.EquipamentoContract;
import com.tivit.inventariodmt.dataconsistency.utils.Utilidades;
import com.tivit.inventariodmt.dto.DepartamentoDTO;
import com.tivit.inventariodmt.dto.FabricanteDTO;
import com.tivit.inventariodmt.dto.LocalidadeDTO;
import com.tivit.inventariodmt.dto.ModeloDTO;
import com.tivit.inventariodmt.dto.StatusDTO;
import com.tivit.inventariodmt.dto.TipoEquipamentoDTO;
import com.tivit.inventariodmt.dto.UsuarioDTO;

public class FormEquipamentoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, OnBtEventListener {

    public static int ENABLE_BLUETOOTH = 1;
    public static int SELECT_PAIRED_DEVICE = 2;
    public static int SELECT_DISCOVERED_DEVICE = 3;

    private R900 leitor;
    public static final int MSG_ENABLE_LINK_CTRL = 10;
    public static final int MSG_DISABLE_LINK_CTRL = 11;
    public static final int MSG_ENABLE_DISCONNECT = 12;
    public static final int MSG_DISABLE_DISCONNECT = 13;
    public static final int MSG_SHOW_TOAST = 20;
    public static final int MSG_REFRESH_LIST_TAG = 22;
    public static final int MSG_BT_DATA_RECV = 10;
    private BluetoothAdapter blueAdapter;
    private BaseAdapter mAdapterTag;
    private static final int[] TX_DUTY_OFF =
            {10, 40, 80, 100, 160, 180};

    private static final int[] TX_DUTY_ON =
            {190, 160, 70, 40, 20};

    private static final String[] TXT_DUTY =
            {"90%", "80%", "60%", "41%", "20%"};

    private EditText serial, patrimonio;
    static TextView statusconexao, recebeRfid;
    private static String strRfid = "";
    private Spinner tipoEquipamento, departamento, localidade, fabricante, modelo, usuario, status;
    private DatabaseHelper helper;
    private List equipamentos, tipoEquipamentos;
    PreencheCombosDao combos;
    ConnectionThread connect = new ConnectionThread();
    private static boolean isDeviceConnected = false;
    ContentValues values = new ContentValues();
    private static int numLeituras = 0;


    private AlertDialog confirmaDepartamento;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            switch (msg.what) {
                case MSG_BT_DATA_RECV:
                    onNotifyBtDataRecv();
                    break;
                case MSG_SHOW_TOAST:
                    Toast.makeText(FormEquipamentoActivity.this, (String) msg.obj, Toast.LENGTH_LONG).show();
                    break;
                case MSG_REFRESH_LIST_TAG:
                    try {
                        mAdapterTag.notifyDataSetChanged();
                        recebeRfid.setText((String.valueOf(leitor.getListaPatrimonio().get(leitor.getListaPatrimonio().size()-1))));
                        //lblTotalTags.setText(String.valueOf(leitor.getListaPatrimonio()));
                    } catch (Exception ex) {
                        Log.d("ERRO", ex.getMessage());
                    }
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        leitor = new R900(this, mHandler, this);
        mAdapterTag = new TagAdapter(getApplicationContext(), leitor.getListaPatrimonio());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_equipamento);
            this.combos = new PreencheCombosDao(this);
        Utilidades.setTaskBarColored(this);

        //Traz os combos
        iniciaCombos();
        //Combo localidade
        comboLocalidade();
        fabricante = (Spinner) findViewById(R.id.spFabricante);
       //departamento = (Spinner) findViewById(R.id.spDepartamento);
        statusconexao = (TextView) findViewById(R.id.etRespConect);
        recebeRfid = (TextView) findViewById(R.id.tvRecebeRfid);

        serial = (EditText) findViewById(R.id.etSerial);
        patrimonio = (EditText) findViewById(R.id.etPatrimonio);

        //Roda as opções do banco
        helper = new DatabaseHelper(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }

//    @Override
//    protected void onDestroy() {
//        helper.close();
//        super.onDestroy();
//    }

    @TargetApi(Build.VERSION_CODES.N)
    public void salvarEquipamento(View view) {
        if (serial.getText().toString().isEmpty() || serial.getText().toString().equals("")) {
            serial.setBackgroundColor(Color.parseColor("#F6A6AF"));
            Toast.makeText(this, R.string.serial_empty, Toast.LENGTH_SHORT).show();
        } else {
            //Escrever no banco (Inserir dados)
            //SQLiteDatabase db = helper.getWritableDatabase();

            //Pega da data e hora neste instante
            Date dataAgora = new Date(System.currentTimeMillis());


            values.put("inv_fs_ic_numero_serie", serial.getText().toString());
            values.put("inv_fs_ic_Patrimonio", patrimonio.getText().toString());
            values.put("inv_fs_ic_RFID", recebeRfid.getText().toString().trim());
            values.put("inv_fs_ic_Data_Criacao", "" + dataAgora.getTime());
            //
            int codigoTipoEquipamento = ((TipoEquipamentoDTO) tipoEquipamento.getSelectedItem()).getInv_FS_TP_Id_Tipo_Equipamento();
            values.put("inv_fs_ic_Id_Tipo_Equipamento", "" + codigoTipoEquipamento);
            int codigoStatus = ((StatusDTO) status.getSelectedItem()).getInv_FS_St_id_Status();
            values.put("inv_fs_ic_Id_Status", "" + codigoStatus);
            int codigoDepartamento = ((DepartamentoDTO) departamento.getSelectedItem()).getInv_FS_Dep_Id_Departamento();
            values.put("inv_fs_ic_Id_Departamento", "" + codigoDepartamento);
            int codigoLocalidade = ((LocalidadeDTO) localidade.getSelectedItem()).getInv_FS_Loc_Id_Localidade();
            values.put("inv_fs_ic_Id_Localidade", +codigoLocalidade);
            values.put(EquipamentoContract.Columnas.INSERT_PENDING, 1);
            int codigoFabricante = ((FabricanteDTO) fabricante.getSelectedItem()).getInv_FS_Fab_Id_Fabricante();
            values.put(EquipamentoContract.Columnas.FABRICANTE, codigoFabricante);
            int codigoModelo = ((ModeloDTO) modelo.getSelectedItem()).getInv_FS_Mod_Id_Modelo();
            values.put(EquipamentoContract.Columnas.MODELO, codigoModelo);

            AlertDialog.Builder msg = new AlertDialog.Builder(this);
            msg.setTitle(R.string.confirm).setMessage(R.string.keep_location);
            msg.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    localidade.requestFocus();
                }
            });

            msg.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i){

                    getContentResolver().insert(EquipamentoContract.CONTENT_URI,values);
                    //SyncAdapter.sincronizarAhora(getApplicationContext(), true);
                    limpaCampos();
                    Toast.makeText(getApplicationContext(),getString(R.string.equipament_save),Toast.LENGTH_LONG).show();
                }
            });
            msg.show();


            //long resultado = db.insert("Inv_FS_Item_config", null, values);
            //getContentResolver().insert(EquipamentoContract.CONTENT_URI,values);

            /*if (resultado != -1) {
                Toast.makeText(this, getString(R.string.equipament_save), Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.confirm).setMessage(R.string.keep_location);
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        comboLocalidade();
                    }
                });
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
                limpaCampos();
            } else
                Toast.makeText(this, getString(R.string.error_save), Toast.LENGTH_SHORT).show();
            }*/
        }
    }

    public void verificaConexoes(View view) {

        Intent searchPairedDevicesIntent = new Intent(this, DiscoveredDevices.class);
        startActivityForResult(searchPairedDevicesIntent, SELECT_DISCOVERED_DEVICE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //DISPOSITIVO DE BLUETOOTH
//        if (requestCode == ENABLE_BLUETOOTH) {
//            if (resultCode == RESULT_OK) {
//                statusconexao.setText("Bluetooth ativado.");
//            } else {
//                statusconexao.setText("Bluetooth não ativado.");
//            }
//        } else if (requestCode == SELECT_PAIRED_DEVICE || requestCode == SELECT_DISCOVERED_DEVICE) {
//            if (resultCode == RESULT_OK) {
//                statusconexao.setText("Você selecionou " + data.getStringExtra("btDevName") + "\n"
//                        + data.getStringExtra("btDevAddress"));
//
//                //connect = new ConnectionThread(data.getStringExtra("btDevAddress"));
//                //connect.start();
//            } else {
//                statusconexao.setText("Nenhum dispositivo selecionado.");
//            }
//        }
//        //LEITOR CÓDIGO DE BARRAS
//        if (requestCode == 0) {
//            if (resultCode == RESULT_OK) {
//                String contents = data.getStringExtra("SCAN_RESULT");
//                String format = data.getStringExtra("SCAN_RESULT_FORMAT");
//
//            } else if (resultCode == RESULT_CANCELED) {
//
//            }
//        }

        String addressDispositivo = data.getStringExtra("btDevAddress");
        leitor.conectar(addressDispositivo);
        super.onActivityResult(requestCode, resultCode, data);
    }

//    public static Handler handler = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {
//           // recebeRfid.clearComposingText();
//            Bundle bundle = msg.getData();
//            byte[] data = bundle.getByteArray("data");
//            String dataString = new String(data);
//
//            if (dataString.equals("---N"))
//                statusconexao.setText("Ocorreu um erro durante a conexão.");
//            else if (dataString.equals("---S")) {
//                statusconexao.setText("Conectado.");
//                isDeviceConnected = true;
//            }
//            else {
//                String strData = new String(data);
//                if(strRfid != strData)
//                    strRfid = strRfid + strData;
////                recebeRfid.setText("");
//                recebeRfid.setText(strRfid.toUpperCase());
//                if(strRfid != "" && strData.length() < 10)
//                    numLeituras ++;
//                if(numLeituras == 2) {
//                    strRfid = "";
//                    numLeituras = 0;
//                } else if (numLeituras == 0)
//                    strRfid = "";
//            }
//        }
//    };

    public void habilitaLerRfid(View view) {
        strRfid = "";
        limpaRfid();
        if(isDeviceConnected)
            enviarMensagem("L");
        else
            new AlertDialog.Builder(FormEquipamentoActivity.this).setMessage("Conecte-se ao Leitor RFID!").setNeutralButton("OK",null).show();
    }

    public void enviarMensagem(String mensagem) {
        byte[] data = "22".getBytes();
        connect.write(data);
    }

    public void limpaRfid() {
        recebeRfid.setText("");

    }

    public void iniciaCombos() {
        ArrayAdapter<TipoEquipamentoDTO> adTipoEquipto = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, combos.listarTipoEquipamentos());
        tipoEquipamento = (Spinner) findViewById(R.id.spTipoEquipamento);
        tipoEquipamento.setAdapter(adTipoEquipto);


        ArrayAdapter<DepartamentoDTO> adDepartamento = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, combos.listarDepartamentos());
        departamento = (Spinner) findViewById(R.id.spDepartamento);
        departamento.setAdapter(adDepartamento);


        ArrayAdapter<FabricanteDTO> adFabricante = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, combos.listarFabricantes());
        fabricante = (Spinner) findViewById(R.id.spFabricante);
        fabricante.setAdapter(adFabricante);
        fabricante.setOnItemSelectedListener(this);

        comboModelo(fabricante.getSelectedItemPosition() + 1);

//        1ArrayAdapter<UsuarioDTO> adUsuario = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, combos.listarUsuarios());
//        usuario = (Spinner) findViewById(R.id.spUsuario);
//        usuario.setAdapter(adUsuario);

        ArrayAdapter<StatusDTO> adStatus = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, combos.listarStatus());
        status = (Spinner) findViewById(R.id.spStatus);
        status.setAdapter(adStatus);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void comboLocalidade() {
        ArrayAdapter<LocalidadeDTO> adLocalidade = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, combos.listarLocalidades());
        localidade = (Spinner) findViewById(R.id.spLocalidade);
        localidade.setAdapter(adLocalidade);
    }

    public void comboModelo(int codigoFabricante) {
        ArrayAdapter<ModeloDTO> adModelo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, combos.listarModelos(codigoFabricante));
        modelo = (Spinner) findViewById(R.id.spModelo);
        modelo.setAdapter(adModelo);
    }

    public void limpaCampos() {
        iniciaCombos();
        limpaRfid();
        serial.setText("");
        patrimonio.setText("");
    }

    private void showToastByOtherThread(String msg, int time) {
        mHandler.removeMessages(MSG_SHOW_TOAST);
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SHOW_TOAST, time, 0, msg));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spFabricante:
                ArrayAdapter<ModeloDTO> adModelo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, combos.listarModelos(((FabricanteDTO) fabricante.getSelectedItem()).getInv_FS_Fab_Id_Fabricante()));
                modelo = (Spinner) findViewById(R.id.spModelo);
                modelo.setAdapter(adModelo);
                break;
        }
    }

    private void setEnabledLinkCtrl(boolean bEnable) {
        if (bEnable)
            mHandler.sendEmptyMessageDelayed(MSG_ENABLE_LINK_CTRL, 50);
        else
            mHandler.sendEmptyMessageDelayed(MSG_DISABLE_LINK_CTRL, 50);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
    /*@Override
    public void onBackPressed()
    {
        connect.unpairAllDevices();
    }*/
}
