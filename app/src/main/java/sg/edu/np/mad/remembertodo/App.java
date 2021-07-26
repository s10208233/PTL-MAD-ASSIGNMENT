package sg.edu.np.mad.remembertodo;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

//By Daryl Chong Teck Yuan
public class App extends Application {
    public static final String CHANNEL_1_ID = "channel1";

    @Override
    public void onCreate(){
        super.onCreate();
        //Creating the notification channel
        createNotificationChannel();
    }

    //Creating the Notification Channel
    private void createNotificationChannel(){
        //If build version is higher >= oreo, then perform the code
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    //Make the notification drop down
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.enableVibration(true);
            //Description of the channel
            channel.setDescription("Notification from RememberToDo that the timer has finished.");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
