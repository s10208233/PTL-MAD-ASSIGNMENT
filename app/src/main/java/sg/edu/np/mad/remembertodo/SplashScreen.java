package sg.edu.np.mad.remembertodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import java.util.Timer;
import java.util.TimerTask;


public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Timer().schedule(new TimerTask(){
            public void run() {
                startActivity(new Intent(SplashScreen.this, AddTask.class));
            }
        }, 2500 );
    }
}