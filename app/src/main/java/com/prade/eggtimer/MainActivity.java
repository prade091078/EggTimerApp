package com.prade.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import static com.prade.eggtimer.R.raw.chicken;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekbar ;
    TextView timerTextView;
    Button controllerButton;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;
    ImageView imageView;

    public void resetTimer()
    {

    }

    public void updateTimer(int secondsLeft){

        int minutes = (int) secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String secondString = Integer.toString(seconds);
        if(seconds <=9 & minutes == 0) {
            secondString = "0" + secondString;
        }
        else if(secondString.length() == 1) {
            secondString = secondString + "0";
        }



        timerTextView.setText(Integer.toString(minutes) + ":" + (secondString));

    }


    public void controlTimer(View view) {
        // Log.i("button pressed :","yes");
        if (counterIsActive == false) {
            counterIsActive = true;
            timerSeekbar.setEnabled(false);
            controllerButton.setText(R.string.stop);

            countDownTimer =  new CountDownTimer(timerSeekbar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) (millisUntilFinished / 1000));

                    if((int) millisUntilFinished/1000 == 10) {
                        MediaPlayer mplayer= MediaPlayer.create(getApplicationContext(), chicken);
                        mplayer.start();
                        imageView.setImageResource(R.drawable.chic);

                    }
                }

                @Override
                public void onFinish() {
                    timerTextView.setText("00:00");
                    MediaPlayer mplayer;
                    mplayer = MediaPlayer.create(getApplicationContext(), R.raw.haha);
                    mplayer.start();
                    counterIsActive = false;
                    timerSeekbar.setEnabled(true);
                    imageView.setImageResource(R.drawable.egg);
                    controllerButton.setText(R.string.Start);
                    //mplayer.stop();
                }
            }.start();
        }
        else
        {
            timerTextView.setText(R.string.TimerText);
            controllerButton.setText(R.string.Start);
            timerSeekbar.setProgress(30);
            countDownTimer.cancel();
            counterIsActive = false;
            timerSeekbar.setEnabled(true);
            imageView.setImageResource(R.drawable.egg);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         timerSeekbar = (SeekBar)findViewById(R.id.seekBarTimer);
         timerTextView = (TextView) findViewById(R.id.textViewTimer);
         controllerButton = (Button) findViewById(R.id.buttonControl);
         imageView = (ImageView) findViewById(R.id.imageView);

        timerSeekbar.setMax(600); // 6*10min
        timerSeekbar.setProgress(30);

        timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        }
    }
