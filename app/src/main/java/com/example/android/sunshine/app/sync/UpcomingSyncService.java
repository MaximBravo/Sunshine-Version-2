package com.example.android.sunshine.app.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpcomingSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static UpcomingSyncAdapter sUpcomingSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("UpcomingSyncService", "onCreate - UpcomingSyncService");
        synchronized (sSyncAdapterLock) {
            if (sUpcomingSyncAdapter == null) {
                sUpcomingSyncAdapter = new UpcomingSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sUpcomingSyncAdapter.getSyncAdapterBinder();
    }
}