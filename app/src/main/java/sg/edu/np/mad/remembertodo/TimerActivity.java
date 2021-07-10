package sg.edu.np.mad.remembertodo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    //Buttons used
    Button startButton;
    Button resetButton;

    //TextView in the middle showing how much time is left or at starting "00:00:00"
    TextView timeText;

    //The three edit texts used for user input
    EditText timeHour;
    EditText timeMin;
    EditText timeSec;

    //CountDownTimer
    CountDownTimer cdt;

    //Boolean to check if the timer has been activated before
    boolean isRunning;
    //To keep the timer running
    boolean Run;

    //Total duration
    long duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //Buttons used
        startButton = findViewById(R.id.startButton);
        resetButton = findViewById(R.id.resetButton);

        //Edit texts and TextView
        timeText = findViewById(R.id.timeText);
        timeHour = findViewById(R.id.timeHour);
        timeMin = findViewById(R.id.timeMin);
        timeSec = findViewById(R.id.timeSec);

        //To state that the timer has not been run yet, since the button hasn't been pressed.
        isRunning = false;


        //OnTouchListener for Hours Edit Text.
        timeHour.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                timeHour.setText("");
                return false;
            }
        });

        //OnTouchListener for Minutes Edit Text.
        timeMin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                timeMin.setText("");
                return false;
            }
        });

        //OnTouchListener for Seconds Edit Text.
        timeSec.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                timeSec.setText("");
                return false;
            }
        });

        //Button to reset the timer
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Resetting the start button
                startButton.setText("START");
                //Resetting the TextView
                timeText.setText("00:00:00");
                //Cancelling the CountDownTimer
                cdt.cancel();
                //Changing boolean to state that the timer has not been activated
                isRunning = false;
            }
        });

        //OnClickListener for Start button
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if user has inputted / The timer has been activated before but just in paused state
                if(isRunning == false){
                    //Getting user input from the edit texts
                    String uHourText = timeHour.getText().toString();
                    int uHour = Integer.parseInt(uHourText);
                    String uMinText = timeMin.getText().toString();
                    int uMin = Integer.parseInt(uMinText);
                    String uSecText = timeSec.getText().toString();
                    int uSec = Integer.parseInt(uSecText);
                    //Calculation of duration
                    long durationCal = (uHour * 3600) + (uMin * 60) + uSec + 1;
                    //Changing it to milliseconds
                    duration = durationCal * 1000;
                }

                //To keep the timer running
                if(Run = true){
                    //Ability for the user to start the timer
                    if(startButton.getText().toString() != "PAUSE"){
                        //Changing the start button text to PAUSE since it is in active state
                        startButton.setText("PAUSE");
                        onTimer(duration);
                        isRunning = true;
                    }
                    //Ability for the user to pause the timer
                    else{
                        //Changing the start button text to START since it is in paused state
                        startButton.setText("START");
                        cdt.cancel();
                    }
                }
            }
        });
    }

    //Timer method
    private void onTimer(long dur){
        //Creating CountDownTimer
        cdt = new CountDownTimer(dur, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                duration = millisUntilFinished;
                long tSecs = 1000;
                long tMins = tSecs * 60;
                long tHours = tMins * 60;

                //Time math
                long hPassed = millisUntilFinished / tHours;
                millisUntilFinished = millisUntilFinished % tHours;

                long mPassed = millisUntilFinished / tMins;
                millisUntilFinished = millisUntilFinished % tMins;

                long sPassed = millisUntilFinished / tSecs;

                //Displaying the time via the TextView every tick
                String timeLeftFormatted;
                //Displaying this if hours is less than 0
                if(tHours > 0){
                    //Formatting string to get 00:00:00 style
                    timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d:%02d", hPassed, mPassed, sPassed);
                    timeText.setText(timeLeftFormatted);
                }
                //Displaying this otherwise
                else{
                    //Formatting string to get 00:00:00 style
                    timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", mPassed, sPassed);
                    timeText.setText(timeLeftFormatted);
                }
            }

            @Override
            //Actions when timer finishes
            public void onFinish() {
                //Toast to state that the timer has indeed finished
                Toast.makeText(TimerActivity.this, "Timer has finished", Toast.LENGTH_SHORT).show();
                //Resetting the TextView since timer has finished
                timeText.setText("00:00:00");
                //Resetting button since timer has finished
                startButton.setText("START");
                //Resetting boolean to state that the timer is not running and the next time it runs, it should be completely reset
                isRunning = false;
            }
        };
        //Starting the Timer.
        cdt.start();
    }
}