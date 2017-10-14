package com.example.appstek.recording;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String outputFile=null;
    private Button bt_record,bt_stop,bt_play;
    private TextView tv_recording;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!hasMicrophone())
        {
            mediaRecorder=new MediaRecorder();

            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFile(outputFile);

            Toast.makeText(getApplicationContext(), " MIC Found!!!!! ", Toast.LENGTH_LONG).show();
        }
        else
            {
                /*bt_record.setEnabled(true);
                bt_stop.setEnabled(true);
                bt_play.setEnabled(true);*/
                Toast.makeText(getApplicationContext(), " No MIC Found!!!!! ", Toast.LENGTH_LONG).show();
            }

         bt_record=(Button)findViewById(R.id.bt_start);
         bt_stop=(Button)findViewById(R.id.bt_stop);
         bt_play=(Button)findViewById(R.id.bt_play);

        outputFile= Environment.getExternalStorageDirectory().getAbsolutePath()  +"/recording.3gp";
        tv_recording=(TextView)findViewById(R.id.tv_recording);

        bt_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_start(v);
            }
            private void bt_start(View v) {

               // Toast.makeText(getApplicationContext(), "Pausing sound",Toast.LENGTH_SHORT).show();
                try {
                    mediaRecorder.prepare();
                    mediaRecorder.start();
                }catch (IllegalStateException ise){
                    ise.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        bt_record.setEnabled(true);
        bt_stop.setEnabled(false);
        Toast.makeText(getApplicationContext(),"Record ",Toast.LENGTH_LONG).show();

        bt_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder=null;
                bt_record.setEnabled(true);
                bt_stop.setEnabled(false);
                bt_play.setEnabled(false);

           //     Toast.makeText(getApplicationContext(),"Audio Record Successfully",Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"Audio Record Successfully",Toast.LENGTH_LONG).show();
            }
        });
        bt_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaplayer=new MediaPlayer();
                try {
                    mediaplayer.setDataSource(outputFile);
                    mediaplayer.prepare();
                    mediaplayer.start();

                    Toast.makeText(getApplicationContext(),"playing Audio Success",Toast.LENGTH_LONG).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    protected boolean hasMicrophone() {
        PackageManager pmanager = this.getPackageManager();
        return pmanager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE);
    }
}
