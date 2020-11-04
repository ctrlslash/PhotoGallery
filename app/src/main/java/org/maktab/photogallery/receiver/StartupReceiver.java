package org.maktab.photogallery.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.maktab.photogallery.services.PollService;
import org.maktab.photogallery.utilities.QueryPreferences;

public class StartupReceiver extends BroadcastReceiver {

    private static final String TAG = "PGStartupReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Received boot broadcast intent: " + intent.getAction());

        boolean isOn = QueryPreferences.isAlarmOn(context);
        if (isOn)
            PollService.setServiceAlarm(context, isOn);

        String text = "Set Alarm Manager " + isOn;
        Log.d(TAG, text);
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}