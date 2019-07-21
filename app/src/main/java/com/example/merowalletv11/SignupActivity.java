package com.example.merowalletv11;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import static android.app.AlarmManager.INTERVAL_DAY;
import static android.app.AlarmManager.RTC_WAKEUP;
import static java.util.Calendar.DATE;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.getInstance;

public class SignupActivity extends AppCompatActivity {

    DatabaseHelper MwDb;
    Button btnAddData;
    private TextInputLayout editfirst,editlast,edituser,editpassword,editconfirm,editaddress,editphone,editemail;
    private static String dayString;
    private static String monthString;
    private static String yearString;

    private static String signupDate;


    public static int thisDay;
    public static int thisMonth;
    public static int thisYear;

    // private static double budget ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        MwDb= new DatabaseHelper(this);

        editfirst= findViewById(R.id.text_input_firstname);
        editlast=  findViewById(R.id.text_input_lastname);
        edituser=  findViewById(R.id.text_input_username);
        editpassword= findViewById(R.id.text_input_password);
        editconfirm= findViewById(R.id.text_input_confirmPassword);
        editaddress= findViewById(R.id.text_input_address);
        editphone = findViewById(R.id.text_input_phone);
        editemail= findViewById(R.id.text_input_email);

        Calendar cal = Calendar.getInstance();
        int year1 = cal.get(Calendar.YEAR);
        int month1 = cal.get(Calendar.MONTH);
        int day1 = cal.get(Calendar.DAY_OF_MONTH);
        month1++;
        thisMonth =month1;
        thisYear=year1;
        thisDay= day1;
        yearString = "" + year1;


        if(thisDay<10){

            dayString = "0"+thisDay;

        }
        else{
            dayString = ""+thisDay;
        }

        if(thisMonth<10){

            monthString = "0"+thisMonth;
        }

        else{
            monthString = ""+thisMonth;
        }

        signupDate= year1 + "-" + monthString + "-" + dayString;



    }
    private boolean validateEmail() {
        String emailInput = editemail.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()){
            editemail.setError("Field Can't Be Empty");
            return false;
        }
        else{
            editemail.setError(null);
            return true;
        }
    }

    private boolean validatePhoneno(){
        String phoneInput = editphone.getEditText().getText().toString().trim();
        if(phoneInput.length() < 10){
            editphone.setError("Too short");
            return false;
        }
        else{
            editphone.setError(null);
            return true;
        }

    }

    private boolean validateUsername(){
        String username = edituser.getEditText().getText().toString().trim();
        if(username.isEmpty()){
            edituser.setError("Field Can't Be Empty");
            return false;
        }else if(username.length()>15){
            edituser.setError("Username too long");
            return false;

        }else {

            Cursor res = MwDb.getAllData();
            if(res.getCount()==0)
            {
                edituser.setError(null);
                res.close();
                return true;
            }
            else {
                res.moveToFirst();
                do {
                    String user = res.getString(3);
                    // String editusername = edituser.getEditText().getText().toString();

                    if (user.equals(username)) {
                       // showMessage("Please insert another username");
                        edituser.setError("Username already taken");
                        return false;
                    }
                } while (res.moveToNext());

                edituser.setError(null);
                res.close();
                return true;
            }
        }

    }

    private boolean validatePassword() {
        String passwordInput = editpassword.getEditText().getText().toString().trim();
        String passwordConfirm = editconfirm.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()){
            editpassword.setError("Field Can't Be Empty");
            return false;
        }
        else if (passwordInput.length()<8){
            editpassword.setError("Password is too short");
            return false;
        }
        else if(!passwordInput.equals(passwordConfirm)){
            editpassword.setError("Passwords do not match");
            return false;
        }
        else{
            editpassword.setError(null);
            return true;
        }
    }

    public void AddData(View view) {

        if (!validateEmail() | !validateUsername() | !validatePassword() | !validatePhoneno()) {
            return;
        }

            boolean isInserted = MwDb.insertData(editfirst.getEditText().getText().toString(),
                    editlast.getEditText().getText().toString(),
                    edituser.getEditText().getText().toString(),
                    editpassword.getEditText().getText().toString(),
                    editconfirm.getEditText().getText().toString(),
                    editphone.getEditText().getText().toString(),
                    editaddress.getEditText().getText().toString(),
                    editemail.getEditText().getText().toString(),
                   signupDate
                   /* budget*/);

            if (isInserted = true) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(SignupActivity.this, "Successful", Toast.LENGTH_SHORT).show();


            } else
                Toast.makeText(SignupActivity.this, "Unsuccessful", Toast.LENGTH_LONG).show();

            //notify
        Calendar calendar = getInstance();
        calendar.set(HOUR_OF_DAY,19);
        calendar.set(MINUTE,0);
        calendar.set(SECOND,0);
        startAlarm(calendar);



    }
    private void showMessage(String Message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(Message);
        builder.show();

    }

    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        //  PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),1,intent,PendingIntent.FLAG_UPDATE_CURRENT);


        if (c.before(getInstance())) {
            c.add(DATE, 1);
        }

        //  alarmManager.setExact(RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        alarmManager.setRepeating(RTC_WAKEUP,c.getTimeInMillis(),INTERVAL_DAY,pendingIntent);
    }
}
