package kn0077.kr.hs.emirim.audiolist;

import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView list;
    Button butPlay, butStop,butPause;
    TextView textMusic, textTime;
    SeekBar progress;
    String[] musics={"dean_admusic","dean_limbo_second","iu_palette"};
    int[] musicResIds={R.raw.dean_admusic,R.raw.dean_limbo_second,R.raw.iu_palette};
    int selectedMusicId;
    MediaPlayer mediaPlayer;
    boolean selectePauseButton;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list=(ListView)findViewById(R.id.list_music);
        butPlay=(Button)findViewById(R.id.but_play);
        butStop=(Button)findViewById(R.id.but_stop);
        butPause=(Button)findViewById(R.id.but_pause);
        textMusic=(TextView)findViewById(R.id.text_music);
        textTime=(TextView)findViewById(R.id.text_time);
        progress=(SeekBar)findViewById(R.id.seek_music);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,musics);
        list.setAdapter(adapter);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list.setItemChecked(0,true);
        selectedMusicId=musicResIds[0];
        mediaPlayer=MediaPlayer.create(this,selectedMusicId);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                selectePauseButton=false;
                mediaPlayer.stop();
                selectedMusicId=musicResIds[i];
                MainActivity.this.i=i;
            }
        });

        butPlay.setOnClickListener(new View.OnClickListener() {
            SimpleDateFormat timeFormat=new SimpleDateFormat("mm:ss");
            @Override
            public void onClick(View v) {
                textMusic.setText(musics[i]);
                if(selectePauseButton) {
                    selectePauseButton=false;
                }
                else {
                    mediaPlayer = MediaPlayer.create(MainActivity.this, selectedMusicId);
                }
                mediaPlayer.start();

                Thread musicThread=new Thread(){
                  public void run(){
                      if(mediaPlayer==null) return;

                      progress.setMax(mediaPlayer.getDuration());
                      while(mediaPlayer.isPlaying()){
                          progress.setProgress(mediaPlayer.getCurrentPosition()); // 플레이 중인 음악의 현재 시간 위치
                          runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  textTime.setText(timeFormat.format(mediaPlayer.getCurrentPosition())); // textview 같은 곳에 값을 넣어주는 것은 단순 쓰레드에서 하기에 어려워 runOnUiThread() 사용해야함
                              }
                          });

                          SystemClock.sleep(200); //0.2초 간격으로 bar가 움직임

                      }
                  }
                };

                musicThread.start();

            }
        });
        butPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectePauseButton=true;
                mediaPlayer.pause();
            }
        });
        butStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectePauseButton=false;
                mediaPlayer.stop();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
    }
}