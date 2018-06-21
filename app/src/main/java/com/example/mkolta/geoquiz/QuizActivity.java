package com.example.mkolta.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    //testing git
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String IS_CHEATER = "cheater";
    private static final int REQUEST_CODE_CHEAT = 0;
    private static final int BUILD_VERSION = Build.VERSION.SDK_INT;
    private static final int MAX_CHEATS = 3;
    private static final String JUDGEMENT_TOAST = "Cheating is wrong.  You used your " +MAX_CHEATS+ " cheats.";

    private int score = 0;
    private int numQuestionsAnswered = 0;
    private static int mNumCheats = 0;


    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mPrevImageButton;
    private ImageButton mNextImageButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private TextView mBuildVersion;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };
    private int mCurrentIndex=0;
    private boolean mIsCheater = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            //mIsCheater = savedInstanceState.getBoolean(IS_CHEATER, false);
            //if (savedInstanceState.getBoolean(IS_CHEATER, false)){
                //mNumCheats ++;
            //}
        }
        mQuestionTextView = findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1 )% mQuestionBank.length;
                //int question = mQuestionBank[mCurrentIndex].getTextResId();
                //mQuestionTextView.setText(question);
                updateQuestion();
            }
        });
        mBuildVersion = findViewById((R.id.build_version));
        mBuildVersion.setText("API Level " + BUILD_VERSION);


        //int question = mQuestionBank[mCurrentIndex].getTextResId;
        //mQuestionTextView.setText(question);
       mTrueButton = findViewById(R.id.true_button);
       // mTrueButton = findViewById(R.id.question_text_view);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageResId = getString(R.string.incorrect_toast);

                //Toast toast = Toast.makeText(QuizActivity.this,
                  //      R.string.incorrect_toast,
                   //     Toast.LENGTH_SHORT);
                if(mQuestionBank[mCurrentIndex].beenAnswered()){
                    messageResId=getString(R.string.been_answered_toast);
                    //toast = Toast.makeText(QuizActivity.this,
                     //       R.string.been_answered_toast,
                      //      Toast.LENGTH_SHORT);
                }
                else {
                    numQuestionsAnswered++;
                    mQuestionBank[mCurrentIndex].setBeenAnswered();
                    if (mNumCheats >= MAX_CHEATS) {
                        messageResId = JUDGEMENT_TOAST;
                    } else {

                        if (mQuestionBank[mCurrentIndex].isAnswerTrue()) {
                            messageResId = getString(R.string.correct_toast);

                            score++;
                        }
                    }
                }

                Toast toast = Toast.makeText(QuizActivity.this,
                        messageResId,
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,0);
                toast.show();
                mTrueButton.setEnabled(false);
                if(numQuestionsAnswered >= mQuestionBank.length){
                    toast = Toast.makeText(QuizActivity.this,
                            "Score is " + score + "/" +numQuestionsAnswered,
                            Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageResId = getString(R.string.incorrect_toast);

                if(mQuestionBank[mCurrentIndex].beenAnswered()){
                            messageResId = getString(R.string.been_answered_toast);
                }
                else {
                    numQuestionsAnswered++;
                    mQuestionBank[mCurrentIndex].setBeenAnswered();
                    if (mNumCheats >= MAX_CHEATS) {
                        messageResId = JUDGEMENT_TOAST;
                    } else {

                        if (!mQuestionBank[mCurrentIndex].isAnswerTrue()) {
                            messageResId = getString(R.string.correct_toast);

                            score++;
                        }
                    }
                }
                Toast toast = Toast.makeText(QuizActivity.this,
                        messageResId,
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,0);
                toast.show();
                mFalseButton.setEnabled(false);
                if(numQuestionsAnswered >= mQuestionBank.length){
                    toast = Toast.makeText(QuizActivity.this,
                            "Score is " + score + "/" +numQuestionsAnswered,
                            Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
        mPrevImageButton = findViewById(R.id.prev_button);
        mPrevImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex - 1 )% mQuestionBank.length;
                if (mCurrentIndex < 0)
                    mCurrentIndex = mQuestionBank.length-1;
                //int question = mQuestionBank[mCurrentIndex].getTextResId();
                //mQuestionTextView.setText(question);
                //mIsCheater = false;
                updateQuestion();
            }
        });


        mNextImageButton = findViewById(R.id.next_button);
        mNextImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1 )% mQuestionBank.length;
                //int question = mQuestionBank[mCurrentIndex].getTextResId();
                //mQuestionTextView.setText(question);
                //mIsCheater = false;
                updateQuestion();
            }
        });

        mCheatButton = findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start cheat activity
                //Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                //startActivity(intent);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });
        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode!= Activity.RESULT_OK){
            return;
        }
        if (requestCode==REQUEST_CODE_CHEAT){
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
            if (mIsCheater){
                mNumCheats++;
            }


        }
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(IS_CHEATER, mIsCheater);
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }


    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        mTrueButton.setEnabled(true);
        mFalseButton.setEnabled(true);
    }



}
