package com.example.merowalletv11;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.security.MessageDigest;
import java.util.StringTokenizer;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class LoginActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    private long backPressedTime;
    private Toast backToast;

    EditText editusername,editpassword;
    Button b1;
    Boolean check=false;
    public static String passusername;
    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myDb = new DatabaseHelper(this);
        b1 = (Button) findViewById(R.id.signin);
        editusername=(EditText) findViewById(R.id.username1);
        editpassword=(EditText) findViewById(R.id.pass1);
        b1=(Button)findViewById(R.id.signin);

        TextView textView = findViewById(R.id.signup);
        String text = "Don't have an account? Sign Up.";
        SpannableString ss =new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                    Intent in = new Intent(LoginActivity.this, SignupActivity.class);
                    startActivity(in);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.WHITE);

            }
        };
        ss.setSpan(clickableSpan, 22,31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());


       /* boolean check = true;


        Cursor res = myDb.getAllData();
        if(res.getCount()==0)
        {
            Toast.makeText(LoginActivity.this,"WELCOME",Toast.LENGTH_SHORT).show();
        }
        else {
            res.moveToFirst();

            do {

                String user = res.getString(1);
                int status = res.getInt(12);

                if (status ==1) {

                    passusername = user;

                    Intent in = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(in);
                    finish();

                    check =false;

                }
            } while (res.moveToNext());
        }
        res.close();
        */


         viewAll();



    }


        void validate1 (View view){
            Intent in = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(in);

        }

       public void viewAll()
       {
           b1.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Cursor res = myDb.getAllData();
                   if(res.getCount()==0)
                   {
                       Toast.makeText(LoginActivity.this,"Please sign up first",Toast.LENGTH_SHORT).show();
                   }
                   else {
                   res.moveToFirst();
                   do {

                       String username = editusername.getText().toString().trim();
                       String password = editpassword.getText().toString();


                       String user = res.getString(3);
                       String pass1 = res.getString(4);


                       try{
                           pass = decrypt(pass1,user);


                       }

                       catch(Exception e){
                           e.printStackTrace();
                       }




                       if (user.equals(username) && pass.equals(password)) {

                           Intent in = new Intent(LoginActivity.this, MainActivity.class);
                           startActivity(in);

                           passusername = username;

                           int status =1;
                           myDb.updateOnlineStatus(username,status);

                           finish();
                           check=true;
                       }
                   }while(res.moveToNext());

                   if(check==false)
                       //showMessage("Invalid Credentials");
                       Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();



               }
               res.close();}

           });
       }
    public void showMessage(String Message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(Message);
        builder.show();

    }

    public static String throwUsername()
    {
        return passusername;

    }

    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            finishAffinity();
            super.onBackPressed();
            return;

        }
        else{
            backToast = Toast.makeText(LoginActivity.this,"Press again to exit",Toast.LENGTH_SHORT);
            backToast.show();
        }


        backPressedTime = System.currentTimeMillis();
    }


    public String decrypt (String data, String passkey) throws Exception{

        SecretKeySpec key = generateKey(passkey);
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.DECRYPT_MODE,key);
        byte[] decodedValue = Base64.decode(data,Base64.DEFAULT);
        byte[] decval = c.doFinal(decodedValue);
        String decryptedValue = new String(decval);
        return decryptedValue;



    }

    private SecretKeySpec generateKey(String passkey) throws Exception{

        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = passkey.getBytes("UTF-8");
        digest.update(bytes,0,bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key,"AES");
        return secretKeySpec;
    }


}



