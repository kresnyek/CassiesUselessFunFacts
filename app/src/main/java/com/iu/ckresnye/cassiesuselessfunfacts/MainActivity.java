package com.iu.ckresnye.cassiesuselessfunfacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {

    static int TMNTScore = 0;
    static int whalesScore = 0;
    static int sewingScore = 0;

    static boolean tmntComplete = false;
    static boolean whalesComplete = false;
    static boolean sewingComplete = false;

    TextView tmntScoreText;
    TextView tmntScoreValue;
    TextView whalesScoreText;
    TextView whalesScoreValue;
    TextView sewingScoreText;
    TextView sewingScoreValue;
    TextView scoreText;
    ImageView tmntCelebration;

    Button bTMNT;
    Button bWhales;
    Button bSewing;
    Button bReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tmntScoreText = (TextView)findViewById(R.id.tmntScoreText);
        whalesScoreText= (TextView)findViewById(R.id.whalesScoreText);
        sewingScoreText = (TextView)findViewById(R.id.sewingScoreText);
        tmntScoreValue = (TextView)findViewById(R.id.tmntScoreValue);
        whalesScoreValue = (TextView)findViewById(R.id.whalesScoreValue);
        sewingScoreValue = (TextView)findViewById(R.id.sewingScoreValue);
        scoreText = (TextView) findViewById(R.id.scoreText);
        tmntCelebration = (ImageView) findViewById(R.id.imageView);
        tmntCelebration.setVisibility(View.INVISIBLE);

        tmntScoreValue.setText(Integer.toString(0));
        whalesScoreValue.setText(Integer.toString(0));
        sewingScoreValue.setText(Integer.toString(0));

        tmntScoreText.setVisibility(View.INVISIBLE);
        whalesScoreText.setVisibility(View.INVISIBLE);
        sewingScoreText.setVisibility(View.INVISIBLE);
        tmntScoreValue.setVisibility(View.INVISIBLE);
        whalesScoreValue.setVisibility(View.INVISIBLE);
        sewingScoreValue.setVisibility(View.INVISIBLE);
        scoreText.setVisibility(View.INVISIBLE);



        bTMNT = (Button) findViewById(R.id.tmntButton);
        bWhales = (Button) findViewById(R.id.whaleButton);
        bSewing = (Button) findViewById(R.id.sewingButton);
        bReset = (Button) findViewById(R.id.resetButton);
        bReset.setVisibility(View.INVISIBLE);

        bTMNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TMNTQuiz.class);
                startActivity(i);
            }
        });

        bWhales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, WhalesQuiz.class);
                startActivity(i);
            }
        });

        bSewing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SewingQuiz.class);
                startActivity(i);
            }
        });

        bReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TMNTScore = 0;
                whalesScore = 0;
                sewingScore = 0;

                tmntComplete = false;
                sewingComplete = false;
                whalesComplete = false;

                //remove return so all reset
               if(MainActivity.this.getIntent().hasExtra("return"))
                   MainActivity.this.getIntent().removeExtra("return");

                tmntScoreText.setVisibility(View.INVISIBLE);
                whalesScoreText.setVisibility(View.INVISIBLE);
                sewingScoreText.setVisibility(View.INVISIBLE);
                tmntScoreValue.setVisibility(View.INVISIBLE);
                whalesScoreValue.setVisibility(View.INVISIBLE);
                sewingScoreValue.setVisibility(View.INVISIBLE);
                scoreText.setVisibility(View.INVISIBLE);
                bReset.setVisibility(View.INVISIBLE);

                MainActivity.this.onStart();
            }
        });


    }

    @Override
    protected  void onStart()
    {
        super.onStart();
        if(this.getIntent().hasExtra("TMNTscore"))
        {
            TMNTScore = this.getIntent().getIntExtra("TMNTscore", 0);
            this.getIntent().removeExtra("TMNTscore");
            tmntComplete = true;
        }
        else if(this.getIntent().hasExtra("SewingScore")) {
            sewingScore = this.getIntent().getIntExtra("SewingScore", 0);
            this.getIntent().removeExtra("SewingScore");
            sewingComplete = true;
        }
        else if(this.getIntent().hasExtra("WhalesScore")) {
            whalesScore = this.getIntent().getIntExtra("WhalesScore", 0);
            this.getIntent().removeExtra("WhalesScore");
            whalesComplete = true;
        }
        //Update scores
        tmntScoreValue.setText(Integer.toString(TMNTScore));
        whalesScoreValue.setText(Integer.toString(whalesScore));
        sewingScoreValue.setText(Integer.toString(sewingScore));

        if(this.getIntent().hasExtra("return")) //if already opened quiz, display scores
        {
            //Incase return
            tmntCelebration.setVisibility(View.INVISIBLE);
            //make them visible
            tmntScoreText.setVisibility(View.VISIBLE);
            whalesScoreText.setVisibility(View.VISIBLE);
            sewingScoreText.setVisibility(View.VISIBLE);
            tmntScoreValue.setVisibility(View.VISIBLE);
            whalesScoreValue.setVisibility(View.VISIBLE);
            sewingScoreValue.setVisibility(View.VISIBLE);
            scoreText.setVisibility(View.VISIBLE);
            bReset.setVisibility(View.VISIBLE);
        }

        bTMNT.setEnabled(!tmntComplete); // so can't go back to quiz
        bWhales.setEnabled(!whalesComplete); // so can't go back to quiz
        bSewing.setEnabled(!sewingComplete); // so can't go back to quiz

        if(tmntComplete&& whalesComplete && sewingComplete)
        {
            //all categories complete
            Toast.makeText(this, "Completed! You got: "
                    + Integer.toString((((TMNTScore + sewingScore + whalesScore)*100)/30))
                    + "% correct\n"
                    + "TMNT: " + Integer.toString(TMNTScore) + "/10"
                    + "\nWhales: " + Integer.toString(whalesScore)  + "/10"
                    + "\nSewing: " + Integer.toString(sewingScore)  + "/10",
                    Toast.LENGTH_LONG).show();
            tmntCelebration.setVisibility(View.VISIBLE);
            bReset.performClick();
        }

    }

}


