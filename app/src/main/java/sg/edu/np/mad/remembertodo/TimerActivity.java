package sg.edu.np.mad.remembertodo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    //Buttons used
    Button startButton;
    Button resetButton;

    //TextView in the middle showing how much time is left or at starting "00:00:00"
    TextView timeText;

    //The three number pickers
    NumberPicker numPickerHour;
    NumberPicker numPickerMin;
    NumberPicker numPickerSec;

    //Global variables for the hours, minutes and seconds
    int hour;
    int minutes;
    int seconds;

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

        //Pre-setting values for the h,min,sec to 0 to reduce error possibility
        hour = 0;
        minutes = 0;
        seconds = 0;

        //Buttons used (Reset, start and pause)
        startButton = findViewById(R.id.startButton);
        resetButton = findViewById(R.id.resetButton);

        //Textview (00:00:00)
        timeText = findViewById(R.id.timeText);

        //Setting min/max numbers for Hours in the wheel
        numPickerHour = findViewById(R.id.numPickerHour);
        numPickerHour.setMinValue(0);
        numPickerHour.setMaxValue(99);

        //Setting min/max numbers for Minutes in the wheel
        numPickerMin = findViewById(R.id.numPickerMin);
        numPickerMin.setMinValue(0);
        numPickerMin.setMaxValue(59);

        //Setting min/max numbers for Seconds in the wheel
        numPickerSec = findViewById(R.id.numPickerSec);
        numPickerSec.setMinValue(0);
        numPickerSec.setMaxValue(59);

        //To state that the timer has not been run yet, since the button hasn't been pressed.
        isRunning = false;

        //Update value for Hours every time the Hours wheel spins to a new number
        numPickerHour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                hour = newVal;
            }
        });

        //Update value for Minutes every time the Minutes wheel spins to a new number
        numPickerMin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                minutes = newVal;
            }
        });

        //Update value for Seconds every time the Seconds wheel spins to a new number
        numPickerSec.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                seconds = newVal;
            }
        });

        //Button to reset the timer
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRunning == true){
                    //Resetting the start button
                    startButton.setText("START");
                    //Resetting the TextView
                    timeText.setText("00:00:00");
                    //Cancelling the CountDownTimer
                    cdt.cancel();
                    //Changing boolean to state that the timer has not been activated
                    isRunning = false;
                    startButton.setBackgroundColor(Color.parseColor("#4CAF50"));
                }
                else{
                    timeText.setText("00:00:00");
                }
            }
        });

        //OnClickListener for Start button
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if user has inputted / The timer has been activated before but just in paused state
                if(isRunning == false){
                    //Calculation of duration
                    long durationCal = (hour* 3600) + (minutes * 60) + seconds + 1;
                    //Changing it to milliseconds
                    duration = durationCal * 1000;
                }

                //To keep the timer running
                if(Run = true){
                    //Ability for the user to start the timer
                    if(startButton.getText().toString() != "PAUSE"){
                        //Changing the start button text to PAUSE since it is in active state
                        startButton.setBackgroundColor(Color.parseColor("#FFAF4C4C"));
                        startButton.setText("PAUSE");
                        onTimer(duration);
                        isRunning = true;
                    }
                    //Ability for the user to pause the timer
                    else{
                        //Changing the start button text to START since it is in paused state
                        startButton.setText("START");
                        startButton.setBackgroundColor(Color.parseColor("#4CAF50"));
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
                startButton.setBackgroundColor(Color.parseColor("#4CAF50"));
            }
        };
        //Starting the Timer.
        cdt.start();
    }
}