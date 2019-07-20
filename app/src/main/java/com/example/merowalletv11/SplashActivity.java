package com.example.merowalletv11;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    public static String passusername;
    Boolean check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        myDb = new DatabaseHelper(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                Cursor res = myDb.getAllData();
                if(res.getCount()==0)
                {
                    Toast.makeText(SplashActivity.this,"WELCOME",Toast.LENGTH_SHORT).show();
                }
                else {
                    res.moveToFirst();

                    do {

                        String user = res.getString(3);
                        int status = res.getInt(12);

                        if (status ==1) {

                           LoginActivity.passusername = user;

                            Intent in = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(in);
                            finish();

                            check =false;

                        }
                    } while (res.moveToNext());
                }
                res.close();


                if(check==true) {
                    Intent in = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(in);
                    // overridePendingTransition(R.anim.opening_animation, R.anim.closing_animation);
                    finish();

                }



            }
        }, 2000);
    }
}
