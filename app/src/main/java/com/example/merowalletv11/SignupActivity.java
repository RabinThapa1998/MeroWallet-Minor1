package com.example.merowalletv11;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.AlarmManager.INTERVAL_DAY;
import static android.app.AlarmManager.RTC_WAKEUP;
import static com.example.merowalletv11.ExpenseActivity.resizeBitmap;
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
    private static String signupDate1;



    public static int thisDay;
    public static int thisMonth;
    public static int thisYear;

    private CircleImageView profile;
    private static final int GALLERY_REQUEST_CODE=101;

    private static String imag;

    private String encryptedPassword;
    public String AES = "AES";


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

        profile=(CircleImageView)findViewById(R.id.profile_image);

        imag="null";

        profile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openGallery();
            }
        });



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

        signupDate1 = "2019-07-01";


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





        //camera section
        if (profile.getDrawable().equals(null)) {
            imag = "null";
        }
        else {
            Bitmap image = ((BitmapDrawable) profile.getDrawable()).getBitmap();
            imag = ExpenseActivity.bitmapToString(resizeBitmap(image));
        }

        if (!validateEmail() | !validateUsername() | !validatePassword() | !validatePhoneno()) {
            return;
        }

        try {


            encryptedPassword = encrypt(editpassword.getEditText().getText().toString(),edituser.getEditText().getText().toString());
        }
        catch (Exception e){
            e.printStackTrace();

        }



        boolean isInserted = MwDb.insertData(editfirst.getEditText().getText().toString(),
                    editlast.getEditText().getText().toString(),
                    edituser.getEditText().getText().toString(),
                  // editpassword.getEditText().getText().toString(),
                    encryptedPassword,
                encryptedPassword,
                   // editconfirm.getEditText().getText().toString(),
                    editphone.getEditText().getText().toString(),
                    editaddress.getEditText().getText().toString(),
                    editemail.getEditText().getText().toString(),
                   signupDate1,
                    imag
        );

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            try {
                Uri selectedImage = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                profile.setImageBitmap(BitmapFactory.decodeStream(imageStream));

            } catch (IOException exception) {
                exception.printStackTrace();
            }


        }


    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);
    }



    private String decrypt (String data, String passkey) throws Exception{

        SecretKeySpec key = generateKey(passkey);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE,key);
        byte[] decodedValue = Base64.decode(data,Base64.DEFAULT);
        byte[] decval = c.doFinal(decodedValue);
        String decryptedValue = new String(decval);
        return decryptedValue;



    }

    private String encrypt (String Data, String password) throws Exception{

        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = Base64.encodeToString(encVal,Base64.DEFAULT);
        return encryptedValue;

    }


    private SecretKeySpec generateKey(String password) throws Exception{

        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes,0,bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key,"AES");
        return secretKeySpec;
    }
}
