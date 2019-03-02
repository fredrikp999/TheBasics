package org.palmedia.thebasics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
// Needed for navigation and us
import android.content.Intent;
import android.widget.TextView;

public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        // Get a reference to the activity which called this activity
        Intent intent = getIntent();
        // Get the String passed to us using setExtra method
        String question_passedin = intent.getStringExtra(MainActivity.QUESTION_KEY);
        // Find the reference to the text view we want to manipulate
        TextView questiontext = findViewById(R.id.questionText);
        // Set the text in the textview found to what was passed in
        questiontext.setText(question_passedin);
    }
}
