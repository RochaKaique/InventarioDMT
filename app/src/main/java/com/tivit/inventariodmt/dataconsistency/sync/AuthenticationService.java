package com.tivit.inventariodmt.dataconsistency.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by kaique.rocha on 25/10/2016.
 */

public class AuthenticationService extends Service {
    // Instancia del autenticador
    private InventarioAuthenticator autenticador;

    @Override
    public void onCreate() {
        // Nueva instancia del autenticador
        autenticador = new InventarioAuthenticator(this);
    }

    /*
     * Ligando el servicio al framework de Android
     */
    @Override
    public IBinder onBind(Intent intent) {
        return autenticador.getIBinder();
    }
}
