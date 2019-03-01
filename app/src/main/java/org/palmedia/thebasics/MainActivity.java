package org.palmedia.thebasics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
// Needs to be imported for logcat logging
import android.util.Log;

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
    }

    private void logMessage(String message) {
        Log.i(TAG, message);
    }


    public void PrintStory(View view) {
        // This is the OnClick action for the button
        // That this method is called when the button is clicked
        // is defined in the activity_main.xml by adding this to
        // the button definition: android:onClick="PrintStory"

        // Enable vertical scrolling for the textview component
        mlogTextview.setMovementMethod(new ScrollingMovementMethod());
        // Now, add apply some text to the textview component
        mlogTextview.setText("Hi there!");
        logMessage("BUTTON pressed");
    }
}
