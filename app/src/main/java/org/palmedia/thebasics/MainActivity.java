package org.palmedia.thebasics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
// Needs to be imported for logcat logging
import android.util.Log;
// Needed for navigation and using the Intent object
import android.content.Intent;
// To make Toast badges possible
import android.widget.Toast;
// To handle Uris, e.g. for maps
import android.net.Uri;

/* This app contains the basics for creating an android app
It contains a lot of comments on how this works to an almost silly level
But I guess this will be good at some point...
 */

public class MainActivity extends AppCompatActivity {
    // Defining a key which is used to pass back response. The string assigned does not matter
    public static final String QUESTION_KEY = "question_key";
    // Declare mlogTextview as a textview component
    // so that we can get assign a reference to it further down
    private TextView mlogTextview;
    // Define a TAG which can be used in logging
    public static final String TAG = "MainActivity";
    // Define the key used to store a string for state changes
    public static final String LOG_TEXT_KEY = "An empty string here";
    // Defining a key which is used to pass back response from the question activity
    // It does not matter what value it is assigned
    public static final int ANSWER_REQUEST = 1000;
    // Define a key for coordinates to use in google map as default
    // This is coordinates to Totemo Ramen, Stockholm for some reason
    public static final String COORDINATES ="59.3385284,18.0348237";
    // Declare an instance of the broadcast receiver object
    // We name it br
    private BroadcastReceiver br;


    // Set up a broadcast receiver for local broadcasts (sent from MyIntentService in this example)
    BroadcastReceiver mLocalReceiver =
            new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    // Get the string (message) passed on as StringExtra from the sender
                    String message = intent.getStringExtra(
                            MyIntentService.MESSAGE_KEY
                    );
                    logMessage("Received:" + message);


                    
                }
            };

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

        // -- BROADCAST RECEIVER START --
        // Create an instance of the broadcast receiver so that it can be used
        // to listen to broadcast messages such as battery low, connectivity change etc.
        // (br is already declared higher up and the class is designed further down)
        br = new MyBroadcastReceiver();
        // Next, create an Intent filter to listen for only the broadcast messages
        // which I am interested in. In this case when power is connected

         // Well, registering without filter to see if something is received...
        IntentFilter filter = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
        // We could here add more things which we want to receive and trigger on
        // to do this, just add more with .addAction like below:
        filter.addAction(Intent.ACTION_BATTERY_LOW);
        // To finally get it going, register the receiver to connect our broadcast receiver to the filter
        registerReceiver(br, filter);
        // -- BROADCAST RECEIVER END --

        // Set up navigation programatically instead of from activity_main.xml
        // The id of the button (in activity_main.xml is "questionB" (B as in button)
        // Now create a textview object which reference this button id
        TextView qbutton = findViewById(R.id.questionB);
        // Next, set up the listener for the click event
        qbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlogTextview.append("\n"+"Clicked Question-button");
                /*
                Create an intent which we can use to start the activity
                Note that we need to use MainActivity.this and not only this
                Reason is that "this" would refer to the onClick listener object
                which is not what we want to reference
                Second argument is the class of the activity we want to navigate to
                */
                Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
                /*
                Pass in some data to the activity using putExtra()
                With this, we can pass int, string, arrays etc.
                When creating this, you can get help from AndroidStudio by writing
                intent.putExtra("question_key") - and then right click and select
                Refactor --> Constant
                This will change your "question_key" into:
                public static final String QUESTION_KEY = "question_key";
                and add the key below instead. (This can of course be done manually as well)
                Reason for the public key is so that it can be referenced from the other activity
                */
                intent.putExtra(QUESTION_KEY, "What is the number of the Beast?");

                /*
                If we were only to start the activity without wanting any result, we we
                start it using: startActivity(intent);
                Then you would also not have to define the public static final int REPONSE_REQUEST
                or public static final String RESPONSE_KEY
                However, let's kick the activity off in a way where we can get a response back:
                */
                startActivityForResult(intent, ANSWER_REQUEST);
            }
         });

        // Find and reference the button which shall navigate to the map
        Button mapbutton = findViewById(R.id.MapB);
        // And set up the onClick listener for it
        mapbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Create the uri in correct format
                // ?z=zoomlevel is the zoom level, higher is more zoomed in
                String zoomlevel = "?z=10";
                /*
                Adding &q=restaurants adds a listing of restaurants (duh...)
                so add this to your uri: "&q=restaurants"
                But you can of course search for just the name of a restaurant or other
                However, when using &q= the zoom level is not used - so choose...
                */
                String showthings = "&q=totemo ramen";
                //Uri uri = Uri.parse("geo:"+COORDINATES+zoomlevel);
                Uri uri = Uri.parse("geo:"+showthings);
                // Create an intent
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                /*
                Start the activity to handle the Intent
                In this case, it is not handled by this app
                but by "some other app" on the device. It is up to the user
                The user configures/selects if e.g. google maps or google earth is used
                */
                startActivity(intent);
            }

        });

        // Register a receiver for the local message from MyIntentService
        // Listen to the specific event name as published from the service sender
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mLocalReceiver, new IntentFilter("myown-intent"));
    }

    /*
    For broadcast messages handling, we also need to unregister the received
    as the activity is destroyed
    */
    @Override
    protected void onDestroy(){
        // Unregister the broadcast receiver
        unregisterReceiver(br);
        // Unregister the local receiver
        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(mLocalReceiver);
        
        super.onDestroy();
    }

    // Handle the returned data e.g. from QuestionActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ANSWER_REQUEST && resultCode == RESULT_OK){
            // If the requestCode shows this is the response back
            // from the QuestionActivity, for which we used this requestCode
            // and we got a good response, then get the string returned:
            String AnswerText = data.getStringExtra(QUESTION_KEY);
            // Display the answer entered in a Toaster
            Toast.makeText(this, "You did answer: "+ AnswerText, Toast.LENGTH_LONG).show();
            mlogTextview.append("\n" + "Your answer: "+AnswerText);
            // Check if the answer was correct or not + write that to the log-window
            if (AnswerText.equals("666")) {
                mlogTextview.append("\n" + "Right answer, congrats!");
            }
            else {
                 mlogTextview.append("\n" + "Sorry, wrong answer");
            }
        }
    }

    private void logMessage(String message) {
        // Add an entry in the logcat
        // TAG is indicating this specific activity/module
        Log.i(TAG, message);
        // Add an entry in the logging-window in the app
        mlogTextview.append("\n" + message);

        // Adjust scroll position to make last line visible
        Layout layout = mlogTextview.getLayout();
        if (layout != null){
            int scrollAmount = layout.getLineTop(mlogTextview.getLineCount()) - mlogTextview.getHeight();
            mlogTextview.scrollTo(0, scrollAmount > 0 ? scrollAmount : 0);
        }

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
        // Set up in activity_main.xml instead of programatically
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

    class MyBroadcastReceiver extends BroadcastReceiver {
        // Class to handle broadcast messages such as battery low, connectivity change etc.
        // This class will do nothing by itself. To make it active we need to
        // instantiate it and make activate it (further up in this file)
        @Override
        public void onReceive(Context context, Intent intent) {
            // Context is the string identifying what it is about
            // The intent contains more information such as "an action"
            // Let's just print it out for now
            logMessage("Action: "+ intent.getAction());
            mlogTextview.append("\n"+"Action: "+intent.getAction());

            // If we have multiple broadcast actions we are listening to, we need to check
            // which action it was and take different actions based on that - like below
            // Display this in a Toast. (Note that we have to use "MainActivity.this" and not "this")
            if (intent.getAction().equals("android.intent.action.ACTION_POWER_CONNECTED")){
                Toast.makeText(MainActivity.this, "Power connected", Toast.LENGTH_LONG).show();
            }
            else {
               Toast.makeText(MainActivity.this, "Other broadcast received", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void runCode(View view){
        // This is to test sending local messages in the same app
        // In this case between an activity and a service (MyIntentService)
        // This so that the service can do something and then return some data
        
        // Create a new intent pointing to the service class to execute
        Intent intent = new Intent(this, MyIntentService.class);
        // and start the service
        startService(intent);
    }
}
