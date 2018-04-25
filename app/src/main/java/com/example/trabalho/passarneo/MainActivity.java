package com.example.trabalho.passarneo;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //Define um objeto da classe GameView
    private GameView gameView;

    //Define o MediaPlayer pra tocar musica
    MediaPlayer mp = new MediaPlayer();

    //outras variaveis
    private Handler handler = new Handler();
    private final static long TIMER_INTERVAL = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //não vou usar activity main
        //setContentView(R.layout.activity_main);

        //tocar música de fundo
         mp.stop();
         mp = MediaPlayer.create(this, R.raw.mainmusic);
         mp.start();
         mp.setLooping(true);

        //Integra o GameView
        gameView = new GameView(this);
        setContentView(gameView);


        //Contagem
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        gameView.invalidate();
                    }
                });
            }
        },0,TIMER_INTERVAL);

    }
}
