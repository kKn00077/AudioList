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
    Button butPlay, butStop;
    TextView textMusic;
    ProgressBar progress;
    String[] musics={"dean_admusic","dean_limbo_second","dean_outro"};
    int[] musicResIds={R.raw.dean_admusic,R.raw.dean_limbo_second,R.raw.dean_outro};
    int selectedMusicId;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list=(ListView)findViewById(R.id.list_music);
        butPlay=(Button)findViewById(R.id.but_play);
        butStop=(Button)findViewById(R.id.but_stop);
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
                selectedMusicId=musicResIds[i];
            }
        });

        butPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
            }
        });
        butStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
