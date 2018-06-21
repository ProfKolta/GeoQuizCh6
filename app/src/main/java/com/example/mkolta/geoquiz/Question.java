package com.example.mkolta.geoquiz;

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mBeenAnswered;


    public int getTextResId() {
        return mTextResId;
    }

    public void setResId(int textResId){
        mTextResId = textResId;
    }

   public  boolean isAnswerTrue(){
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue){
        mAnswerTrue = answerTrue;
    }

    public void setBeenAnswered(){
        mBeenAnswered = true;
    }

    public boolean beenAnswered(){
        return mBeenAnswered;
    }

    public Question(int textResId, boolean answerTrue)
    {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mBeenAnswered = false;

    }

}
