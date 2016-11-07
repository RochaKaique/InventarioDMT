package com.tivit.inventariodmt.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by kaique.rocha on 19/10/2016.
 */

public class ConexaoInternet {
   private Context context;

    public ConexaoInternet(Context context){
        this.context = context;
    }

    /**
     * Retorna se o app está conectado na internet
     * @return
     */
    public boolean isDisconnectedToInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
