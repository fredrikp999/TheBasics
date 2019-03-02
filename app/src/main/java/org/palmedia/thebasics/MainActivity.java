package org.palmedia.thebasics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
// Needs to be imported for logcat logging
import android.util.Log;
// Needed for navigation and using the Intent object
import android.content.Intent;

/* This app contains the basics for creating an android app
It contains a lot of comments on how this works to an almost silly level
But I guess this will be good at some point...
 */

public class MainActivity extends AppCompatActivity {
    // Declare mlogTextview as a textview component
    // so that we can get assign a reference to it further down
    private TextView mlogTextview;
    // Define a TAG which can be used in logging
    public static final String TAG = "MainActivity";
    // Define the key used to store a string for state changes
    public static final String LOG_TEXT_KEY = "An empty string here";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // When the activity is created, this method is executed
        // First the default onCreate
        super.onCreate(savedInstanceState);
        // Then set the Contentview to activity_main
        setContentView(R.layout.activity_main);

        // Get reference to textWindow
        // The id of the button as assigned in activity_main.xml is used
        // meaning the id-part of : android:id="@+id/button"
        mlogTextview = findViewById(R.id.logTextview);

        // Now, add apply some text to the textview component
        // If it is the first time it is created, use default
        // else use data from what was stored by "onSaveInstanceState"
        if (savedInstanceState != null && savedInstanceState.containsKey(LOG_TEXT_KEY)){
            mlogTextview.setText(savedInstanceState.getString(LOG_TEXT_KEY));
        }   else {
            mlogTextview.setText("First time here?");
        }
    }

    private void logMessage(String message) {
        Log.i(TAG, message);
    }

    @Override
    protected void onSaveInstanceState (Bundle outstate) {
        // When we exit the state, let's save some stuff so that
        // we can get it back when we create the activity again
        // e.g. when we rotate the display

        // Store the data in the text view to a variable
        String logText = mlogTextview.getText().toString();
        // Store the text into a KEY
        outstate.putString(LOG_TEXT_KEY, logText);

        // Now, execute the default method from parent class
        super.onSaveInstanceState(outstate);
        
    }


    public void GotoSettings(View view) {
        // Action for "GotoLights button"
        // Takes us to SettingsActivity
        mlogTextview.append("\n"+"Light time!");
        // Create a new intent with the destination activity object as a paramater
        Intent intent = new Intent(this, SettingsActivity.class);
        // And start the actual activity by passing the intent object containing ref to activity
        startActivity(intent);

    }

    public void PrintStory(View view) {
        // This is the OnClick action for the button
        // That this method is called when the button is clicked
        // is defined in the activity_main.xml by adding this to
        // the button definition: android:onClick="PrintStory"

        // Enable vertical scrolling for the textview component
        mlogTextview.setMovementMethod(new ScrollingMovementMethod());

        mlogTextview.append("\n"+"Hi there!");
        logMessage("BUTTON pressed");
    }
}
