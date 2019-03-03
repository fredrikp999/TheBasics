package org.palmedia.thebasics;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 **/

public class MyIntentService extends IntentService {
    public static final String MESSAGE_KEY = "message_key";
    public MyIntentService(){super ("MyIntentService");}

    @Override
    protected void onHandleIntent(Intent intent){
        Log.i("MyIntentService", "onHandleIntent");
        // Create an return intent so we have something to send back
        // Use a custom string as identified of the event to send back
        Intent returnIntent = new Intent("myown-intent");
        // Use a message key and send back something (e.g. "message from the service")
        returnIntent.putExtra(MESSAGE_KEY, "message from the service");
        // Use the local broadcast manager to finally send the message back
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(returnIntent);
    }
}