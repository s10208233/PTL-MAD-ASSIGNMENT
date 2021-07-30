package sg.edu.np.mad.remembertodo;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
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
//String variable from App class
import static sg.edu.np.mad.remembertodo.App.CHANNEL_1_ID;

//By Daryl Chong Teck Yuan
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

    //Lottie animation
    LottieAnimationView lottieClock;

    //Vibration
    Vibrator vibrator;

    //Format of the time to pass to restoreinstance
    String timeLeftFormatted;

    //The text of the time to pass to restore instance
    String timeTextString;

    //Boolean to check if the first alert dialog has been called
    boolean timerEndPart2;

    //Media player to play the timer end music
    MediaPlayer mediaPlayer;

    //Boolean to check if the media player is running
    boolean isMediaRunning;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //  Receiving Information From Intent
        Intent intent_extra = getIntent();
        String task_category_name = intent_extra.getStringExtra("TaskCategoryName");
        String selected_json_task = intent_extra.getStringExtra("SelectedJSONTask");

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

        //The notification manager
        notificationManager = NotificationManagerCompat.from(this);

        //Lottie animation
        lottieClock = findViewById(R.id.animationView);

        //Vibration service
        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        //When app first launches, media player is not running
        isMediaRunning = false;


        //Update value for Hours every time the Hours wheel spins to a new number
        numPickerHour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                hour = newVal;
                //Plays a click sound every time a user scrolls with the wheel picker and vibrate as well
                picker.playSoundEffect(SoundEffectConstants.CLICK);
                vibrator.vibrate(50);
            }
        });

        //Update value for Minutes every time the Minutes wheel spins to a new number
        numPickerMin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                minutes = newVal;
                //Plays a click sound every time a user scrolls with the wheel picker and vibrate as well
                picker.playSoundEffect(SoundEffectConstants.CLICK);
                vibrator.vibrate(50);
            }
        });

        //Update value for Seconds every time the Seconds wheel spins to a new number
        numPickerSec.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                seconds = newVal;
                //Plays a click sound every time a user scrolls with the wheel picker and vibrate as well
                picker.playSoundEffect(SoundEffectConstants.CLICK);
                vibrator.vibrate(50);
            }
        });

        //Button to go back to main activity
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go back to home page method
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
                            //Resetting the progress of the lottie clock
                            lottieClock.setProgress(0);
                            lottieClock.cancelAnimation();
                            //Toast to alert the user that the timer has stopped
                            Toast.makeText(TimerActivity.this, "Timer has been reset", Toast.LENGTH_SHORT).show();
                        }
                    });

                    resetBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //If click no, cancel dialog
                            dialog.cancel();
                        }
                    });

                    //Initiate dialog
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
                    //Play the lottie animation on press of start button
                    lottieClock.playAnimation();
                }

                //To keep the timer running
                if (Run = true) {
                    //Ability for the user to start the timer
                    if (startButton.getText().toString() != "PAUSE") {
                        //Changing the start button text to PAUSE and colour to light red since it is in active state
                        onTimer(duration);
                        //Indicate that timer has started running
                        timerIsRunning = true;
                        //Update button from green to red and START to PAUSE
                        updateButton();
                        //Indicate that the timer is not paused
                        timerIsPaused = false;
                        //Resume animation of lottie clock
                        lottieClock.resumeAnimation();
                    }
                    //Ability for the user to pause the timer
                    else {
                        //Changing the start button text to START and colour to green since it is in paused state
                        updateButton();
                        //Indicate that the timer has paused
                        timerIsPaused = true;
                        //Pause the lottie clock animation
                        lottieClock.pauseAnimation();
                        //Stop the timer
                        cdt.cancel();
                    }
                }
            }
        });
    }

    //Method to update button status, green to red, START to PAUSE etc depending on conditions
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

        //Title of the notification
        String notificationTitle = "Time's up!";
        //Message of the notification
        String notificationText = "Have you completed your task?";
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                //Icons for the notification
                .setSmallIcon(R.drawable.ic_clock)
                //Title and message
                .setContentTitle(notificationTitle)
                .setContentText(notificationText)
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
        //Notify through channel 1
        notificationManager.notify(1, notification);
    }

    //Timer method
    private void onTimer(long dur) {
        //Creating CountDownTimer
        cdt = new CountDownTimer(dur, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                duration = millisUntilFinished;
                long timeSeconds = 1000;
                long timeMinutes = timeSeconds * 60;
                long timeHours = timeMinutes * 60;

                //Time math
                long hoursPassed = millisUntilFinished / timeHours;
                millisUntilFinished = millisUntilFinished % timeHours;

                long minutesPassed = millisUntilFinished / timeMinutes;
                millisUntilFinished = millisUntilFinished % timeMinutes;

                long secondsPassed = millisUntilFinished / timeSeconds;

                //Displaying the time via the TextView every tick
                //Displaying this if hours is less than 0
                if (timeHours > 0) {
                    //Formatting string to get 00:00:00 style
                    timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hoursPassed, minutesPassed, secondsPassed);
                    timeText.setText(timeLeftFormatted);
                }
                //Displaying this otherwise
                else {
                    //Formatting string to get 00:00:00 style
                    timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutesPassed, secondsPassed);
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

                //Reset lottie clock animation when timer ends
                lottieClock.setProgress(0);
                lottieClock.cancelAnimation();

                //Play the timer end music
                timerEndMusic(null);

            }
        };
        //Starting the Timer.
        cdt.start();
    }

    //Method to initiate the timer end music
    public void timerEndMusic(View v){
        if(isMediaRunning == false){
            mediaPlayer = MediaPlayer.create(this, R.raw.shuwa_shuwa_honey_lemon);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
            isMediaRunning = true;
        }
    }

    //Alert dialog that pops up when the timer finishes
    public void timerEndDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        //Dialog title and message
        builder.setMessage("Is the task completed?");
        builder.setTitle("Timer Finished!");
        //Prevent the timer from being cancelled via other means other than the buttons
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Reset the lottie clock animation
                lottieClock.setProgress(0);
                lottieClock.cancelAnimation();
                //If the media player is running, stop it else do nothing
                if(isMediaRunning == true){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    isMediaRunning = false;
                }
            }
        });

        //Second dialog option if the user states that they never finished their task, to ask for time extension
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //To indicate that the 2nd alert dialog has been launched
                timerEndPart2 = true;
                AlertDialog.Builder noBuilder = new AlertDialog.Builder(TimerActivity.this, R.style.AlertDialogCustom);
                noBuilder.setMessage("Would you like to extend your time?");
                noBuilder.setTitle("Extend time?");
                //Prevent the timer from being cancelled via other means other than the buttons
                noBuilder.setCancelable(false);

                noBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  OKAY CODE HERE KENGSHION
                    }
                });

                noBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //To go back to the home page
                        finish();
                    }
                });
                //Initiating the alert dialog
                AlertDialog backAlert = noBuilder.create();
                backAlert.show();

                //Resetting the lottie clock animation
                lottieClock.setProgress(0);
                lottieClock.cancelAnimation();

                //Release media player resources
                if(isMediaRunning == true){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    isMediaRunning = false;
                }
            }
        });

        //Initiating the alert dialog
        AlertDialog alert = builder.create();
        alert.show();
    }

    //Alert dialogue when user chooses to go back to home page
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TimerActivity.this, R.style.AlertDialogCustom);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("Go back?");
        //To give the average user the proper understanding of the activation of this button
        builder.setMessage("Are you sure you want to go back?\n(Doing so will stop the timer)")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Finish the activity when "Yes" is pressed
                        cdt.cancel();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Cancel the dialog
                        dialog.cancel();
                    }
                });
        //Initiate the dialog
        AlertDialog alert = builder.create();
        alert.show();

    }

    //Save information that is needed even when activity is reset
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //Duration left of the timer
        outState.putLong("millisLeft", duration);
        //The text of the time remaining
        outState.putString("timeText", timeText.getText().toString());
        //Boolean to indicate that if timer is running
        outState.putBoolean("timerIsRunning", timerIsRunning);
        //Boolean to indicate if timer is paused
        outState.putBoolean("timerIsPaused", timerIsPaused);
        //Boolean to indicate that the 2nd alert dialog has launched
        outState.putBoolean("timerEndPart2", timerEndPart2);
        //Boolean to indicate that the media player is running
        outState.putBoolean("isMediaRunning", isMediaRunning);
    }

    //Restore the information received from saveinstancestate when activity has been resetted
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        //Restoring information
        duration = savedInstanceState.getLong("millisLeft");
        timeTextString = savedInstanceState.getString("timeText");
        timerIsRunning = savedInstanceState.getBoolean("timerIsRunning");
        timerIsPaused = savedInstanceState.getBoolean("timerIsPaused");
        timerEndPart2 = savedInstanceState.getBoolean("timerEndPart2");
        isMediaRunning = savedInstanceState.getBoolean("isMediaRunning");
        if(timerIsRunning == true && timerIsPaused == false){
            //If timer was running, start the timer and update button appropriately
            onTimer(duration);
            updateButton();
        }
        else if(timerIsRunning == true && timerIsPaused == true){
            //Else if paused, start timer, immediately pause it and update button appropriately
            onTimer(duration);
            cdt.cancel();
            timeText.setText(timeTextString);
            startButton.setText("START");
            startButton.setBackgroundColor(Color.parseColor("#4CAF50"));
        }
    }
}