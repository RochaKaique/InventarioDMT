package com.tivit.inventariodmt.RFID.DotR900;

/***********************************************************************************
* OnBtnEventListener revision history                                              *
*************+*************+********+***********************************************
* 2012.12.12	ver 1.0.0  	  eric     1. Generated(First release)                 *
************************************************************************************/

import android.bluetooth.BluetoothDevice;

public interface OnBtEventListener
{
    //void onNotifyBtDataRecv();
    //void onBtConnected(BluetoothDevice device);

    //--- synch functions.
    abstract void onBtFoundNewDevice( BluetoothDevice device );
    abstract void onBtScanCompleted();
    abstract void onBtConnected( BluetoothDevice device );
    abstract void onBtDisconnected( BluetoothDevice device );
    abstract void onBtConnectFail( BluetoothDevice device, String msg );
    abstract void onBtDataSent( byte[] data );
    abstract void onBtDataTransException( BluetoothDevice device, String msg );

    //--- asynch function.
    abstract void onNotifyBtDataRecv();
}


