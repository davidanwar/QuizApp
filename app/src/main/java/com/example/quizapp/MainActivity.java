package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String SCORE_KEY = "SCORE";
    private final String INDEX_KEY = "INDEX";

    private TextView mTxtQuestion;
    private Button btnTrue;
    private Button btnWrong;

    private int mQuestionIndex;
    private int mQuizQuestion;

    private ProgressBar mProgressBar;
    private TextView mQuizStatsTextView;
    private int userScore;

    private QuizModel [] questionCollection = new QuizModel[] {
        new QuizModel(R.string.q1, true),
        new QuizModel(R.string.q2, true),
        new QuizModel(R.string.q3, false),
        new QuizModel(R.string.q4, true),
        new QuizModel(R.string.q5, true),
        new QuizModel(R.string.q6, false),
        new QuizModel(R.string.q7, true),
        new QuizModel(R.string.q8, false),
        new QuizModel(R.string.q9, true),
        new QuizModel(R.string.q10, false)
};
    final int USER_PROGRESS = (int) Math.ceil(100.0 / questionCollection.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {

            userScore = savedInstanceState.getInt(SCORE_KEY);
            mQuestionIndex = savedInstanceState.getInt(INDEX_KEY);


        } else {

            userScore = 0;
            mQuestionIndex = 0;

        }

        Toast.makeText(this, "OnCreat Called", Toast.LENGTH_SHORT).show();

        mTxtQuestion = findViewById(R.id.txtQuestion);
        QuizModel q1 = questionCollection[mQuestionIndex];
        mQuizQuestion = q1.getmQuestion();
        mTxtQuestion.setText(mQuizQuestion);

        mProgressBar = findViewById(R.id.quizPB);
        mQuizStatsTextView = findViewById(R.id.textQuizState);
        mQuizStatsTextView.setText(userScore + "");
        btnTrue = findViewById(R.id.btnTrue);

//        final View.OnClickListener myClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (v.getId() == R.id.btnTrue) {
//                    Log.i("Listener", "Button True is Tapped NOW!");
//                    Toast myToastObject = Toast.makeText(getApplicationContext(), "Button True is Tapped NOW!", Toast.LENGTH_SHORT);
//                    myToastObject.show();
//                    // Toast.makeText(MainActivity.this, "Bla bla bla", Toast.LENGTH_SHORT).show(); --> Toast on the spot
//                }
//            }
//        };
//        btnTrue.setOnClickListener(myClickListener);

        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateUserAnswer(true);
                changeQuestionOnButtonClick();
                mProgressBar.incrementProgressBy(USER_PROGRESS);
                mQuizStatsTextView.setText(userScore + "");

            }
        });

        btnWrong = findViewById(R.id.btnWrong);
        // btnWrong.setOnClickListener(myClickListener);
        btnWrong.setOnClickListener(new View.OnClickListener() { // onClick on the spot
            @Override
            public void onClick(View v) {
                evaluateUserAnswer(false);
                changeQuestionOnButtonClick();
                mProgressBar.incrementProgressBy(USER_PROGRESS);
                mQuizStatsTextView.setText(userScore + "");



            }
        });

    }

    private void changeQuestionOnButtonClick(){
        // 5 % 10 = 5 --> jika pembagi lebih besar makan nilai modulus akan mengembalikan nilai yang pertama
        // 5 % 5 = 0;
        mQuestionIndex = (mQuestionIndex + 1) % 10;

        if (mQuestionIndex == 0){
            AlertDialog.Builder quizAlert = new AlertDialog.Builder(this);
            quizAlert.setCancelable(false);
            quizAlert.setTitle("Quiz is finished");
            quizAlert.setMessage("Your Score is " + userScore);
            quizAlert.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            quizAlert.show();
        }

//        if (mQuestionIndex == 0) {
//
//            AlertDialog.Builder quizAlert = new AlertDialog.Builder(this);
//            quizAlert.setCancelable(false);
//            quizAlert.setTitle("The quiz is finished");
//            quizAlert.setMessage("Your score is " + userScore);
//            quizAlert.setPositiveButton("Finish the quiz", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//
//                    finish();
//
//                }
//            });
//            quizAlert.show();
//        }
        mTxtQuestion.setText(mQuizQuestion);
        mQuizQuestion = questionCollection[mQuestionIndex].getmQuestion();
        mTxtQuestion.setText(mQuizQuestion);
    }

    private void evaluateUserAnswer(boolean userGuess){
        boolean currentQuestionAnswer = questionCollection[mQuestionIndex].ismAnswer();
        if (currentQuestionAnswer == userGuess){
            Toast.makeText(getApplicationContext(), R.string.correct_toast_message, Toast.LENGTH_SHORT).show();
            userScore = userScore + 1;
        } else {
            Toast.makeText(getApplicationContext(), R.string.incorrect_toast_message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "OnStart Called", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "OnResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "OnPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "OnStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "OnDestroy", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SCORE_KEY, userScore);
        outState.putInt(INDEX_KEY, mQuestionIndex);

    }
}
