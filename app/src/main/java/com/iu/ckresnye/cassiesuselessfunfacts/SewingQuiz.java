package com.iu.ckresnye.cassiesuselessfunfacts;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class SewingQuiz extends AppCompatActivity {
    ArrayList<Question> questions;
    static int score;
    static int index;
    static boolean answered;
    RadioGroup currentQuestions;
    TextView scoreText;
    TextView questionText;
    Button bNext;
    Button bPrev;
    int totalAnswered;
    int saveRand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewing_quiz);

        scoreText = (TextView) findViewById(R.id.scoreText);
        questionText = (TextView) findViewById(R.id.questionText);
        currentQuestions = (RadioGroup) findViewById(R.id.questionGroup);

        bNext = (Button) findViewById(R.id.nextButton);
        bPrev = (Button) findViewById(R.id.previousButton);

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SewingQuiz.index < questions.size()) {
                    ++SewingQuiz.index;
                    SewingQuiz.this.onStart(); //reload question
                }
            }
        });

        bPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SewingQuiz.index > 0) {
                    --SewingQuiz.index; // double because already incremented
                    SewingQuiz.this.onStart(); //reload question
                }
            }
        });

        index = 0;
        score = 0;
        totalAnswered = 0;


        //Load up the Questions
        loadQuestions();

    }

    @Override
    //This will be rerun on start of new question
    protected void onStart()
    {
        super.onStart();
        //Prepare screen for current question
        loadNextQuestion();
    }

    protected void loadQuestions()
    {
        questions = new ArrayList<Question>();
        Resources res = getResources();
        questions.add(formatQ(res.obtainTypedArray(R.array.sq1)));
        questions.add(formatQ(res.obtainTypedArray(R.array.sq2)));
        questions.add(formatQ(res.obtainTypedArray(R.array.sq3)));
        questions.add(formatQ(res.obtainTypedArray(R.array.sq4)));
        questions.add(formatQ(res.obtainTypedArray(R.array.sq5)));

        Collections.shuffle(questions);
    }

    private Question formatQ(TypedArray q)
    {
        int i = 0;
        return (new Question(q.getText(i++).toString(),
                q.getText(i++).toString(),
                (new ArrayList<String>(Arrays.asList(q.getText(i++).toString(),
                        q.getText(i++).toString(),
                        q.getText(i++).toString(),
                        q.getText(i).toString())))));
    }

    protected void loadNextQuestion()
    {
        boolean enabled;

        if (index > questions.size() - 1) {
            //all questions have been answered, go to Main.
            Intent i = new Intent(SewingQuiz.this, MainActivity.class);
            i.putExtra("SewingScore", score);
            i.putExtra("return", true); // tells main that we've been there before
            startActivity(i);
        }
        else {
            if(answered)
            {
                questions.get(index).viewed = true;
                totalAnswered++;
                answered = false;
            }


            //if first question
            if (index < 1) {
                //Turn off Button
                bPrev.setEnabled(false);
            }
            //if last question
            else if ((index > questions.size() - 2) && (totalAnswered < questions.size()))
            {
                bNext.setEnabled(false);
            }
            else
            {
                bNext.setEnabled(true);
                bPrev.setEnabled(true);
            }

            Question q = questions.get(index);
            enabled = !q.viewed;
            scoreText.setText("Score: " + Integer.toString(score));
            questionText.setText(Integer.toString(index + 1) + ".\t\t" + q.question);

            for (int i = 0; i < currentQuestions.getChildCount(); ++i)
            {
                //Set the question up
                ((RadioButton) currentQuestions.getChildAt(i)).setText(q.getWrongAnswers().get(i));
                currentQuestions.getChildAt(i).setEnabled(enabled);
                if(!enabled)
                    ((RadioButton) currentQuestions.getChildAt(i)).setTextColor(Color.RED);
                else
                    ((RadioButton) currentQuestions.getChildAt(i)).setTextColor(Color.BLACK);

                //Set action for wrong Questions
                currentQuestions.getChildAt(i)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(SewingQuiz.this, "Wrong", Toast.LENGTH_SHORT).show();
                                ((RadioButton) findViewById(v.getId())).setChecked(false);
                                SewingQuiz.wrongAnswer();
                                SewingQuiz.this.onStart();
                            }
                        });
            }

            //setup Correct
            int rand = q.viewed ? saveRand : new Random().nextInt(4);
            ((RadioButton) currentQuestions.getChildAt(rand)).setText(q.answer);
            currentQuestions.getChildAt(rand).setEnabled(enabled);
            if(!enabled)
                ((RadioButton) currentQuestions.getChildAt(rand)).setTextColor(Color.GREEN);
            else
                ((RadioButton) currentQuestions.getChildAt(rand)).setTextColor(Color.BLACK);

            currentQuestions.getChildAt(rand)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(SewingQuiz.this, "Correct!", Toast.LENGTH_SHORT).show();
                            ((RadioButton) findViewById(v.getId())).setChecked(false);
                            SewingQuiz.correctAnswer();
                            SewingQuiz.this.onStart();
                        }
                    });
            saveRand = rand;

        }
    }

    static public void wrongAnswer()
    {
        if(score > 0)
        {
            --score;

        }
        answered = true;
    }

    static public void correctAnswer()
    {
        score = score + 2;
        answered = true;
    }



}
