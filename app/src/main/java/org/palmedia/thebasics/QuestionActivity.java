package org.palmedia.thebasics;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
// Needed for navigation and us
import android.content.Intent;
import android.view.MenuItem;
import android.widget.TextView;

public class QuestionActivity extends AppCompatActivity {

    // Define a reference to be assigned the TextInputLayout later down
    // to be able to use it
    private TextInputEditText tiAnswerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // Change the "up button" icon to a checkmark instead
        // (First add this by New-->Vector_asset and choose your icon + name it)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_checkmark);

        // Get a reference to the activity which called this activity
        Intent intent = getIntent();
        // Get the String passed to us using setExtra method
        String question_passedin = intent.getStringExtra(MainActivity.QUESTION_KEY);
        // Find the reference to the text view we want to manipulate
        TextView questiontext = findViewById(R.id.questionText);
        // Set the text in the textview found to what was passed in
        questiontext.setText(question_passedin);

        // Find the reference to the Text input so that we can
        // later get whatever was entered here and return back
        tiAnswerText = findViewById(R.id.textInput);
    }

    @Override
    public void onBackPressed() {
        // When back button is pressed, extract the data we have entered
        // and return it back to the one started the activity
        String answerTextToReturn = tiAnswerText.getText().toString();

        // Create an intent-object which will only be used to pass back data
        Intent intent = new Intent();
        // Next, put the data into this intent-object
        // (You could return many things like this, just use more KEYs and putExtra etc.)
        intent.putExtra(MainActivity.QUESTION_KEY, answerTextToReturn);
        // Set the return code to RESULT_OK and include the intent which contains variable to return
        setResult(RESULT_OK, intent);
        finish();
    }

    // Here we code what actions to take when items in the action-bar (at the top of the screen)
    // is selected. E.g. the "up"/home-button
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            // If the home button is pressed, execute onBackPressed
            onBackPressed();
            return true;
        }
        //
        return super.onOptionsItemSelected(item);
    }
}
