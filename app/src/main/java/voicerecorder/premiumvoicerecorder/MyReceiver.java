package voicerecorder.premiumvoicerecorder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()) || "android.intent.action.PACKAGE_INSTALL".equals(intent.getAction())|| "android.intent.action.PACKAGE_ADDED".equals(intent.getAction())) {
            //   Log.d("startuptest", "StartUpBootReceiver BOOT_COMPLETED");
           // Intent as = new Intent(context, CallReceiver.class);
            Intent as = new Intent(context, CallReceiver.class);

            context.startService(as);
        }



    }
}
