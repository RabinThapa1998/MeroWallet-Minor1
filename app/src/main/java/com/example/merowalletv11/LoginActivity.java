package com.example.merowalletv11;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.StringTokenizer;

public class LoginActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editusername,editpassword;
    Button b1;
    Boolean check=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myDb = new DatabaseHelper(this);
        b1 = (Button) findViewById(R.id.signin);
        editusername=(EditText) findViewById(R.id.username1);
        editpassword=(EditText) findViewById(R.id.pass1);
        b1=(Button)findViewById(R.id.signin);
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
                       showMessage("Please register first");
                   }
                   else {
                   res.moveToFirst();
                   do {

                       String username = editusername.getText().toString();
                       String password = editpassword.getText().toString();


                       String user = res.getString(3);
                       String pass = res.getString(4);
                       if (user.equals(username) && pass.equals(password)) {

                           Intent in = new Intent(LoginActivity.this, MainActivity.class);
                           startActivity(in);
                           finish();
                           check=true;
                       }
                   }while(res.moveToNext());

                   if(check==false)
                       //showMessage("Invalid Credentials");
                       Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();



               }}

           });
       }
    public void showMessage(String Message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(Message);
        builder.show();

    }

    }



