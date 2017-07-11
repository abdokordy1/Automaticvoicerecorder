package voicerecorder.premiumvoicerecorder;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class File_viewer extends Fragment {

    MediaPlayer mp;
    SeekBar seekBar;
    long timeElapsed;
    private Handler durationHandler = new Handler();
    customized_adapter<File> apter;
    ArrayList<File> Arrayoffiles;
    File[] files;
    List<File> Lines;
  //  final int POS;
    public File_viewer() {
        // Required empty public constructor (fragment constructor)
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_file_viewer, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event

    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_callrecorder, container, false);
        return rootView;
    }

*/


    @Override
    public void onStart() {
        super.onStart();

        reviewfile();
        final View v = getView();
        final ListView lsv = (ListView) v.findViewById(R.id.lsv);

        lsv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                          final int pos, long id) {


 //  DialogFragment newFragment = new Dialogfragment();
 // newFragment.show(getActivity().getSupportFragmentManager(), "missiles");
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(getActivity());
                }

                builder.setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //  Object toRemove = apter.getItem(POS);
                                // apter.remove((File)toRemove);
                                if (files != null) {
                                 //   Toast.makeText(getActivity(),String.valueOf(pos),Toast.LENGTH_LONG).show();
                                    Arrayoffiles.get(pos).delete();

                                    Arrayoffiles.remove(pos);

        reviewfile();

                                        apter = new customized_adapter<>(getView().getContext(), Arrayoffiles);
                                        lsv.setAdapter(apter);


                                    apter.notifyDataSetChanged();


                                 //   apter.notifyDataSetChanged();

                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
            }
        });

        lsv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {


                //   mp.setAudioStreamType(AudioManager.STREAM_MUSIC);

                try {



                            if (files != null) {
                                Arrayoffiles.get(position);
                          //   play(position, v);

                              //  Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                              //  startActivityForResult(i,1);
                                Intent intent = new Intent();
                                intent.setAction(android.content.Intent.ACTION_VIEW);
                              //  File file = new File(YOUR_SONG_URI);
                                intent.setDataAndType(Uri.fromFile(Arrayoffiles.get(position)), "audio/*");
                                //startActivity(intent);
                                try {
                                    Intent i = Intent.createChooser(intent, "Play Music");
                                //    getActivity().startActivity(i);
                                } catch(ActivityNotFoundException ex) {
                                    // if no app handles it, do nothing
                                }

                               // Intent intents = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);
                           //     intents.setType("");
                                startActivity(intent);
                             //   play.setData(Uri.fromFile(Arrayoffiles.get(position)));

                            }



                    //final SeekBar seekBar =(SeekBar) v.findViewById(R.id.seekBar);






            /*        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if(fromUser)
                                mp.seekTo(progress);
                        }
                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });
*/

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        if (files != null) {
            apter = new customized_adapter<>(getView().getContext(), Arrayoffiles);
            lsv.setAdapter(apter);
        }


    }
public void reviewfile()
    {
        String path = Environment.getExternalStorageDirectory().toString() + "/Call Recorder";
        File directory = new File(path);
        files = directory.listFiles();

        if (files != null) {
            Lines = Arrays.asList(files);

            Arrayoffiles = new ArrayList<>();
            Arrayoffiles.addAll(Lines);
        }
    }
    class customized_adapter<T> extends ArrayAdapter<T> {
        public customized_adapter(Context context, ArrayList<T> users) {
            super(context, android.R.layout.select_dialog_item, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RecyclerView.ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, parent, false);
            }
            ImageView s = (ImageView) convertView.findViewById(R.id.iv);
            TextView view1s = (TextView) convertView.findViewById(R.id.tv);
            view1s.setText(files[position].getName());
            s.setImageResource(R.drawable.callrecordericon);
            return convertView;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public void play(int position, View v) {
        stopPlaying();
        mp = new MediaPlayer();
        try {
            mp.setDataSource(Arrayoffiles.get(position).toString());
            mp.prepare();
        } catch (IOException o) {

        }
        mp.start();

        // seekBar = (SeekBar) v.findViewById(R.id.seekBar);

        //  seekBar.setProgress((int) timeElapsed);

        //    durationHandler.post(updateSeekBarTime);

   /*     getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                seekBar.setMax(mp.getDuration());

                if (mp != null) {
                    timeElapsed = mp.getCurrentPosition();
                    //  int mCurrentPosition = mp.getCurrentPosition() /1000;
                    seekBar.setProgress((int) timeElapsed);
                }
                durationHandler.postDelayed(this, 1000);
            }
        });

    } */
  /*  private Runnable updateSeekBarTime = new Runnable() {

        public void run() {

            timeElapsed = mp.getCurrentPosition();

            while (mp.isPlaying()) {
                seekBar.setProgress((int) timeElapsed);


                durationHandler.post(this);
            }

        }
*/
    }

    private void stopPlaying() {
        if (mp != null && mp.isPlaying()) {
            mp.stop();
            mp.release();
            mp = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopPlaying();

    }


    @Override
    public void onPause() {
        super.onPause();
        // stopPlaying();


    }

    @Override
    public void onResume() {
        super.onResume();


    }
}

