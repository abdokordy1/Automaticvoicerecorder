package voicerecorder.premiumvoicerecorder;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import static android.content.Context.MODE_PRIVATE;

//import android.os.Bundle;


///port android.support.v4.content.ContextCompat;

//import android.support.v4.content.ContextCompat;

//import android.content.pm.PackageManager;

//import android.content.pm.PackageManager;


public class MainController extends Fragment {



     Handler handler =new Handler();
    public MainController() {
           // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_controller, container, false);

    }


    @Override
    public void onStart() {
        super.onStart();

        View rootview =getView();

      final   ToggleButton toggle = (ToggleButton) rootview.findViewById(R.id.togglebutton);
        SharedPreferences preferences = getActivity().getSharedPreferences("Abdo",MODE_PRIVATE);
        boolean tgpref = preferences.getBoolean(getResources().getString(R.string.enabling), false);  //default is
      final   TextView txt=(TextView) rootview.findViewById(R.id.statusofrecording);

        if (tgpref) //if (tgpref) may be enough, not sure
        {
            toggle.setChecked(true);
            txt.setText("Automatic call recording is on ");
        }
        else
        {
            toggle.setChecked(false);

        }
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
// Here, thisActivity is the current activity
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (ContextCompat.checkSelfPermission(getActivity(),
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(),
                                    Manifest.permission.READ_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED  || ContextCompat.checkSelfPermission(getActivity(),
                                    Manifest.permission.READ_PHONE_STATE)
                                    != PackageManager.PERMISSION_GRANTED|| ContextCompat.checkSelfPermission(getActivity(),
                                    Manifest.permission.RECORD_AUDIO)
                                    != PackageManager.PERMISSION_GRANTED&& Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {

                                // Should we show an explanation?
                                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                                    // Show an explanation to the user *asynchronously* -- don't block
                                    // this thread waiting for the user's response! After the user
                                    // sees the explanation, try again to request the permission.

                                } else {

                                    // No explanation needed, we can request the permission.

                                    ActivityCompat.requestPermissions(getActivity(),
                                            new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE},
                                            90);

                                }





                                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                                // app-defined int constant. The callback method gets the
                                // result of the request.

                            }
                        }
                    }).start();

                    SharedPreferences sharedPref = getActivity().getSharedPreferences("Abdo", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean(getString(R.string.enabling), true);
                    editor.commit();
                    getActivity().startService(new Intent(getActivity(), CallReceiver.class));

                    txt.setText("Automatic call recording is on ");



                } else {
                    SharedPreferences sharedPref = getActivity().getSharedPreferences("Abdo", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean(getString(R.string.enabling), false);
                    editor.commit();
                    getActivity().stopService(new Intent(getActivity(),CallReceiver.class));
                    txt.setText("Automatic call recording is off ");
                }
            }

        });



   //     AdRequest adRequest = new AdRequest.Builder()
              //  .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
  //      mAdView.loadAd(adRequest);


        AdView mAdView = (AdView) rootview.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()  .addTestDevice("33BE2250B43518CCDA7DE426D04EE232").build();
        mAdView.loadAd(adRequest);

    }
}
