package com.tivit.inventariodmt;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.tivit.inventariodmt.RFID.DotR900.OnBtEventListener;
import com.tivit.inventariodmt.RFID.DotR900.R900;
import com.tivit.inventariodmt.RFID.DotR900.R900Manager;
import com.tivit.inventariodmt.RFID.DotR900.R900Protocol;
import com.tivit.inventariodmt.dataconsistency.provider.EquipamentoContract;
import com.tivit.inventariodmt.dataconsistency.provider.LocalidadeContract;
import com.tivit.inventariodmt.dataconsistency.sync.SyncAdapter;
import com.tivit.inventariodmt.dataconsistency.utils.Criptografia;

public class TestActivity extends AppCompatActivity implements OnBtEventListener {
    public static final int MSG_ENABLE_LINK_CTRL = 10;
    public static final int MSG_DISABLE_LINK_CTRL = 11;
    public static final int MSG_ENABLE_DISCONNECT = 12;
    public static final int MSG_DISABLE_DISCONNECT = 13;
    public static final int MSG_SHOW_TOAST = 20;
    public static final int MSG_REFRESH_LIST_TAG = 22;
    public static final int MSG_BT_DATA_RECV = 10;
    private BluetoothAdapter blueAdapter;
    private BaseAdapter mAdapterTag;
    TextView lblTotalTags;
    private R900 mR900Manager;
    boolean mSingleTag = false;
    boolean mUseMask;
    boolean mQuerySelected;
    int mTimeout;
    int potenciaTxCiclo;

    private static final int[] TX_DUTY_OFF =
            {10, 40, 80, 100, 160, 180};

    private static final int[] TX_DUTY_ON =
            {190, 160, 70, 40, 20};

    private static final String[] TXT_DUTY =
            {"90%", "80%", "60%", "41%", "20%"};
    ConnectionThreadContagem connect;

    @Override
    public void onNotifyBtDataRecv() {
        if (mR900Manager == null)
            return;

        try {
            mR900Manager.leitura();
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

        showToastByOtherThread("Conectou: " + mR900Manager.getDispositivo().getName(), Toast.LENGTH_SHORT);
        mR900Manager.sendCmdOpenInterface1();

        mR900Manager.sendSettingTxCycle(TX_DUTY_ON[0], TX_DUTY_OFF[0]);

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



    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            switch (msg.what) {
                case MSG_BT_DATA_RECV:
                    onNotifyBtDataRecv();
                    break;
                case MSG_SHOW_TOAST:
                    Toast.makeText(TestActivity.this, (String) msg.obj, Toast.LENGTH_LONG).show();
                    break;
                case MSG_REFRESH_LIST_TAG:
                    try {
                        mAdapterTag.notifyDataSetChanged();

                        lblTotalTags.setText(String.valueOf(mR900Manager.getListaPatrimonio().get(mR900Manager.getListaPatrimonio().size()-1)));
                    } catch (Exception ex) {
                        Log.d("ERRO", ex.getMessage());
                    }
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        mR900Manager = new R900(this, mHandler, this);
        mAdapterTag = new TagAdapter(getApplicationContext(), mR900Manager.getListaPatrimonio());
        setContentView(R.layout.activity_test);
        lblTotalTags = (TextView) findViewById(R.id.lblCountTags);
        this.blueAdapter = BluetoothAdapter.getDefaultAdapter();
        super.onCreate(savedInstanceState);

    }

    public void conectaLeitor(View v) {

        if (blueAdapter.isEnabled()) {
            Intent intent = new Intent(this, DiscoveredDevices.class);
            startActivityForResult(intent, BluetoothActivity.SELECT_DISCOVERED_DEVICE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        String addressDispositivo = data.getStringExtra("btDevAddress");
        mR900Manager.conectar(addressDispositivo);
        super.onActivityResult(requestCode, resultCode, data);
    }


    public static class AccessAddress
    {
        public int bank;
        public int offset;
        public int len;
    }

    public void pararBeep(View v){
//        sendBeep(0);
        sendReadTag(1, 1, 0, "0");
    }

//region COMANDOS DO LEITOR
public void sendBeep( int f_on )
    {
        if( mR900Manager != null )
        {
            mR900Manager.sendData(R900Protocol.makeProtocol( R900Protocol.CMD_BEEP,
                    new int[]{ f_on } ) );
        }
    }

    public void sendReadTag( int w_count, int mem, int w_offset, String ACS_PWD )
    {
        if( mR900Manager != null )
        {
            mR900Manager.sendData(R900Protocol.makeProtocol( R900Protocol.CMD_READ_TAG_MEM,
                    new int[]{ w_count, mem, w_offset },
                    ACS_PWD,
                    new int[]{ mSingleTag ? 1 : 0, mUseMask ? ( mQuerySelected ? 3 : 2 ) : 0, mTimeout } ) );
        }
    }
//endregion
}
