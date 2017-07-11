package voicerecorder.premiumvoicerecorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tx =(TextView )findViewById(R.id.textview);

       tx.setText("ICon author : \n 1. https://www.iconfinder.com/Picons  \n  \n 2.https://www.iconfinder.com/icons/1181187/app_call_viber_icon#size=512 \n \n 3. http://www.iconarchive.com/show/100-flat-icons-by-graphicloads/phone-call-icon.html ");


    }

}
