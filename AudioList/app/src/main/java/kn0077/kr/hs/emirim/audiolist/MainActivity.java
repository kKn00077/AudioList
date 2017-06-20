package kn0077.kr.hs.emirim.audiolist;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView list;
    Button butPlay, butStop,butPause;
    TextView textMusic;
    ProgressBar progress;
    String[] musics={"dean_admusic","dean_limbo_second","iu_palette"};
    int[] musicResIds={R.raw.dean_admusic,R.raw.dean_limbo_second,R.raw.iu_palette};
    int selectedMusicId;
    MediaPlayer mediaPlayer;
    boolean selectePauseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list=(ListView)findViewById(R.id.list_music);
        butPlay=(Button)findViewById(R.id.but_play);
        butStop=(Button)findViewById(R.id.but_stop);
        butPause=(Button)findViewById(R.id.but_pause);
        textMusic=(TextView)findViewById(R.id.text_music);
        progress=(ProgressBar)findViewById(R.id.progress_music);
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
                progress.setVisibility(View.INVISIBLE);
            }
        });

        butPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectePauseButton) {
                    selectePauseButton=false;
                }
                else {
                    mediaPlayer = MediaPlayer.create(MainActivity.this, selectedMusicId);
                }
                mediaPlayer.start();
                progress.setVisibility(View.VISIBLE);
            }
        });
        butPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectePauseButton=true;
                mediaPlayer.pause();
                progress.setVisibility(View.INVISIBLE);

            }
        });
        butStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectePauseButton=false;
                mediaPlayer.stop();
                progress.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
    }
}
