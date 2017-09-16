package com.iu.ckresnye.cassiesuselessfunfacts;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Cassie on 9/11/2017.
 * This class is designed to create question objects that allow for
 * questions and answers to be linked together.
 */

public class Question {
    String question;
    String answer;
    ArrayList<String> wrongAnswers;
    boolean viewed;

    Question(String question, String answer, ArrayList<String> wrongAnswers)
    {
        this.question = question;
        this.answer = answer;
        this.wrongAnswers = wrongAnswers;
        Collections.shuffle(wrongAnswers);
        viewed = false;
    }

    public ArrayList<String> getWrongAnswers()
    {
        return wrongAnswers;
    }

    @Override
    public String toString()
    {
        return "Q: " + question
                + "\n A: " + answer
                + "\n W1: " + wrongAnswers.get(0)
                + "\n W1: " + wrongAnswers.get(1)
                + "\n W1: " + wrongAnswers.get(2)
                + "\n W1: " + wrongAnswers.get(3);
    }
}
