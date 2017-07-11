package voicerecorder.premiumvoicerecorder;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by Abdallah on 6/22/2017.
 */


    public class CallReceiver extends Service {   //service used to work on background  to monitor recoding request
        private MediaRecorder mMediaRecorder=null; //intialize  Media recording as this class will be used for recording
int x;
    TelephonyReceiver mTelephonyReceiver =new TelephonyReceiver();
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    } //mandotory implemtation inside service class
     public   class  TelephonyReceiver extends BroadcastReceiver  {
            @Override
            public void onReceive(Context context, final  Intent intent) {
                // Changed order of equals to avoid extra null check.
//Broadcast to handle receiving intent of phonestate
                if (mMediaRecorder == null && TelephonyManager.EXTRA_STATE_OFFHOOK.equals(intent.getStringExtra(TelephonyManager.EXTRA_STATE)))
                {

                    SharedPreferences sharedPref = context.getSharedPreferences("Abdo",Context.MODE_PRIVATE);


                    Boolean highScore = sharedPref.getBoolean(getString(R.string.enabling),false);
                    if(highScore) {

                                recording(intent);

Toast.makeText(context,"Recording is started",Toast.LENGTH_SHORT ).show();

                    }
                }
                else if (TelephonyManager.EXTRA_STATE_IDLE.equals(intent.getStringExtra(TelephonyManager.EXTRA_STATE))) {
                    //stop recording
                    if (mMediaRecorder!=null)
                    {
                        Toast.makeText(context,"Recording call Stopped",Toast.LENGTH_SHORT).show();
                    }
                    cleanUp();

                }


            }
        }

        private void cleanUp() {
            if(mMediaRecorder != null) {

                    mMediaRecorder.stop();

                mMediaRecorder.release();
                mMediaRecorder = null;

            }
        }

        public void recording(Intent intent)
    {


        //start recording
        String CallNo = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
        mMediaRecorder  = new MediaRecorder();
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        mMediaRecorder.setOutputFile( makedirectory(CallNo).toString());
        try { mMediaRecorder.prepare(); } catch(IOException e) { e.printStackTrace(); }
        mMediaRecorder.start();
    }


    public File makedirectory (String callno)
    {
        File sampleDir = Environment.getExternalStorageDirectory();
        File sample = new File(sampleDir.getAbsolutePath() + "/Call Recorder");
        if (!sample.exists()) {
            sample.mkdirs();
        }
        String fileName = String.valueOf(System.currentTimeMillis());

        File audiofile = new File(sample.getAbsolutePath() + "/sound" + fileName +" "+callno+ ".mp4");

        return audiofile;
    }
        public void onCreate () {
            super.onCreate();
            IntentFilter filter = new IntentFilter();

        filter.addAction( "android.intent.action.PHONE_STATE");
           // filter.addAction("android.intent.action.PHONE_STATE");
       //   filter.addAction(Intent.ACTION_NEW_OUTGOING_CALL);
     //       filter.addAction("android.provider.Telephony.SMS_RECEIVED");
            filter.addAction("android.intent.action.NEW_OUTGOING_CALL");
     //       filter.addCategory("android.intent.category.LAUNCHER");

         registerReceiver(mTelephonyReceiver, filter);
        }

        public int onStartCommand(Intent intent, int flags, int startId) {

            return START_STICKY;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();

            unregisterReceiver(mTelephonyReceiver);
            cleanUp();
        }
    }
