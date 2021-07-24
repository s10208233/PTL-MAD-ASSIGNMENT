package sg.edu.np.mad.remembertodo;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Locale;

import static android.Manifest.permission.FOREGROUND_SERVICE;
import static sg.edu.np.mad.remembertodo.App.CHANNEL_1_ID;

//String variable from App class

public class TimerActivity extends AppCompatActivity {

    //Buttons used
    Button startButton;
    Button resetButton;
    Button backButton;

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
    boolean timerIsRunning;

    //Boolean to check if the timer has paused
    boolean timerIsPaused;
    //To keep the timer running
    boolean Run;

    //Total duration
    long duration;

    //Notification manager
    private NotificationManagerCompat notificationManager;

    LottieAnimationView lottieClock;

    Vibrator vibrator;

    String timeLeftFormatted;

    String timeTextString;

    boolean timerEndPart2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //Granting permission for FOREGROUND_SERVICE
        ActivityCompat.requestPermissions(this, new String[]{FOREGROUND_SERVICE}, PackageManager.PERMISSION_GRANTED);

        //Pre-setting values for the h,min,sec to 0 to reduce error possibility
        hour = 0;
        minutes = 0;
        seconds = 0;

        //Buttons used (Reset, start and pause)
        startButton = findViewById(R.id.startButton);
        resetButton = findViewById(R.id.resetButton);
        backButton = findViewById(R.id.backButton);

        //Text view (00:00:00)
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
        timerIsRunning = false;

        notificationManager = NotificationManagerCompat.from(this);

        lottieClock = findViewById(R.id.animationView);

        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        //Update value for Hours every time the Hours wheel spins to a new number
        numPickerHour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                hour = newVal;
                //Plays a click sound every time a user scrolls with the wheel picker
                picker.playSoundEffect(SoundEffectConstants.CLICK);
                vibrator.vibrate(50);
            }
        });

        //Update value for Minutes every time the Minutes wheel spins to a new number
        numPickerMin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                minutes = newVal;
                //Plays a click sound every time a user scrolls with the wheel picker
                picker.playSoundEffect(SoundEffectConstants.CLICK);
                vibrator.vibrate(50);
            }
        });

        //Update value for Seconds every time the Seconds wheel spins to a new number
        numPickerSec.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                seconds = newVal;
                //Plays a click sound every time a user scrolls with the wheel picker
                picker.playSoundEffect(SoundEffectConstants.CLICK);
                vibrator.vibrate(50);
            }
        });

        //Button to go back to main activity
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Button to reset the timer
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerIsRunning == true) {
                    AlertDialog.Builder resetBuilder = new AlertDialog.Builder(TimerActivity.this, R.style.AlertDialogCustom);
                    resetBuilder.setMessage("Are you sure you want to reset the timer?");
                    resetBuilder.setTitle("Reset Confirmation");
                    resetBuilder.setCancelable(false);

                    resetBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Resetting the TextView
                            timeText.setText("00:00:00");
                            //Cancelling the CountDownTimer
                            cdt.cancel();
                            //Changing boolean to state that the timer has not been activated
                            timerIsRunning = false;
                            //Resetting the start button
                            updateButton();
                            lottieClock.setProgress(0);
                            lottieClock.cancelAnimation();
                            Toast.makeText(TimerActivity.this, "Timer has been reset", Toast.LENGTH_SHORT).show();
                        }
                    });

                    resetBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    AlertDialog alert = resetBuilder.create();
                    alert.show();

                } else {
                    timeText.setText("00:00:00");
                }
            }
        });

        //OnClickListener for Start button
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if user has inputted / The timer has been activated before but just in paused state
                if (timerIsRunning == false) {
                    //Calculation of duration
                    long durationCal = (hour * 3600) + (minutes * 60) + seconds + 1;
                    //Changing it to milliseconds
                    duration = durationCal * 1000;
                    lottieClock.playAnimation();
                }

                //To keep the timer running
                if (Run = true) {
                    //Ability for the user to start the timer
                    if (startButton.getText().toString() != "PAUSE") {
                        //Changing the start button text to PAUSE and colour to light red since it is in active state
                        onTimer(duration);
                        timerIsRunning = true;
                        updateButton();
                        timerIsPaused = false;
                        lottieClock.resumeAnimation();
                    }
                    //Ability for the user to pause the timer
                    else {
                        //Changing the start button text to START and colour to green since it is in paused state
                        updateButton();
                        timerIsPaused = true;
                        lottieClock.pauseAnimation();
                        cdt.cancel();
                    }
                }
            }
        });
    }

    private void updateButton(){
        if(startButton.getText().toString() != "PAUSE"){
            startButton.setBackgroundColor(Color.parseColor("#FFAF4C4C"));
            startButton.setText("PAUSE");
        }
        else if(startButton.getText().toString() == "PAUSE" && timerIsPaused == false){
            startButton.setText("START");
            startButton.setBackgroundColor(Color.parseColor("#4CAF50"));
        }
    }

    //Send notification through channel, in this case channel 1
    public void sendOnChannel(View v) {
        //Ability for user to go back into the app's timer page through the notification
        Intent resultIntent = new Intent(this, TimerActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,1,resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        String notiTitle = "Time's up!";
        String notiText = "Have you completed your task?";
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                //Icons for the notification
                .setSmallIcon(R.drawable.ic_clock)
                //Title and message
                .setContentTitle(notiTitle)
                .setContentText(notiText)
                //So the message will drop down
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                //Same category as alarms as it counts as a alarm as well despite being a timer
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                //Vibrate when receive notification
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                //Will cancel it self when clicked
                .setAutoCancel(true)
                //Intent to go back to timer page
                .setContentIntent(resultPendingIntent)
                .build();
        notificationManager.notify(1, notification);
    }

    //Timer method
    private void onTimer(long dur) {
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
                //Displaying this if hours is less than 0
                if (tHours > 0) {
                    //Formatting string to get 00:00:00 style
                    timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hPassed, mPassed, sPassed);
                    timeText.setText(timeLeftFormatted);
                }
                //Displaying this otherwise
                else {
                    //Formatting string to get 00:00:00 style
                    timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", mPassed, sPassed);
                    timeText.setText(timeLeftFormatted);
                }

            }

            @Override
            //Actions when timer finishes
            public void onFinish() {
                //Resetting the TextView since timer has finished
                timeText.setText("00:00:00");
                //Resetting button since timer has finished
                startButton.setText("START");
                //Resetting boolean to state that the timer is not running and the next time it runs, it should be completely reset
                timerIsRunning = false;
                startButton.setBackgroundColor(Color.parseColor("#4CAF50"));

                //Send out a notification when the timer finishes
                sendOnChannel(null);

                //Alert Dialogue when the timer finishes
                timerEndDialogue();

                lottieClock.setProgress(0);
                lottieClock.cancelAnimation();

            }
        };
        //Starting the Timer.
        cdt.start();
    }

    public void timerEndDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        builder.setMessage("Is the task completed?");
        builder.setTitle("Timer Finished!");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                lottieClock.setProgress(0);
                lottieClock.cancelAnimation();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timerEndPart2 = true;
                AlertDialog.Builder noBuilder = new AlertDialog.Builder(TimerActivity.this, R.style.AlertDialogCustom);
                noBuilder.setMessage("Would you like to extend your time?");
                noBuilder.setTitle("Extend time?");
                noBuilder.setCancelable(false);

                noBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /////////////////////////
                    }
                });

                noBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent backIntent = new Intent(TimerActivity.this, MainActivity.class);
                        startActivity(backIntent);
                        finish();
                    }
                });
                AlertDialog backAlert = noBuilder.create();
                backAlert.show();
                lottieClock.setProgress(0);
                lottieClock.cancelAnimation();
            }
        });


        AlertDialog alert = builder.create();
        alert.show();
    }

    //Alert dialogue when user chooses to go back
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(TimerActivity.this, R.style.AlertDialogCustom);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("Go back?");
        builder.setMessage("Are you sure you want to go back?\n(Doing so will stop the timer)")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        cdt.cancel();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putLong("millisLeft", duration);
        outState.putString("timeText", timeText.getText().toString());
        outState.putBoolean("timerIsRunning", timerIsRunning);
        outState.putBoolean("timerIsPaused", timerIsPaused);
        outState.putBoolean("timerEndPart2", timerEndPart2);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        duration = savedInstanceState.getLong("millisLeft");
        timeTextString = savedInstanceState.getString("timeText");
        timerIsRunning = savedInstanceState.getBoolean("timerIsRunning");
        timerIsPaused = savedInstanceState.getBoolean("timerIsPaused");
        timerEndPart2 = savedInstanceState.getBoolean("timerEndPart2");
        if(timerIsRunning == true && timerIsPaused == false){
            onTimer(duration);
            updateButton();
        }
        else if(timerIsRunning == true && timerIsPaused == true){
            onTimer(duration);
            cdt.cancel();
            timeText.setText(timeTextString);
            startButton.setText("START");
            startButton.setBackgroundColor(Color.parseColor("#4CAF50"));
        }
    }
}