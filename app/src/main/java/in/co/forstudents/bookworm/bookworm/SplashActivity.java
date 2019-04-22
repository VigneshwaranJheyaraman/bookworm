package in.co.forstudents.bookworm.bookworm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    public static boolean SP_VALUE = false;
    public static int LP_VALUE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final Thread splash = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    SharedPreferences pref = getSharedPreferences("SignupActPREF", Context.MODE_PRIVATE);
                    SharedPreferences loginpref = getSharedPreferences("LoginActPREF", Context.MODE_PRIVATE);
                    if (pref.getBoolean("activity_executed", false)) {
                        String user=pref.getString("useridas",null);
                        SP_VALUE = true;
                        Intent intent = new Intent(SplashActivity.this, BookDatabaseActivity.class);
                        intent.putExtra("userid",user);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, SignupactivityActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if(loginpref.getBoolean("activity_executed",false)){
                        String user=loginpref.getString("useridal",null);
                        LP_VALUE = 4321;
                        SP_VALUE = false;
                        Intent intent = new Intent(SplashActivity.this, BookDatabaseActivity.class);
                        intent.putExtra("useridfromsplashal",user);
                        startActivity(intent);
                        finish();
                    }

                        /*Intent intent = new Intent(SplashActivity.this, SignupactivityActivity.class);
                        startActivity(intent);
                        finish();*/


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        splash.start();
    }
}
