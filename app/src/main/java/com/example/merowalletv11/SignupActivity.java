package com.example.merowalletv11;

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

public class SignupActivity extends AppCompatActivity {

    DatabaseHelper MwDb;
    Button btnAddData;
    private TextInputLayout editfirst,editlast,edituser,editpassword,editconfirm,editaddress,editphone,editemail;
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


    }
    private boolean validateEmail() {
        String emailInput = editemail.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()){
            editemail.setError("Field Can't Be Empty");
            return false;
        }else{
            editemail.setError(null);
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

        if (!validateEmail() | !validateUsername() | !validatePassword()) {
            return;
        }

            boolean isInserted = MwDb.insertData(editfirst.getEditText().getText().toString(),
                    editlast.getEditText().getText().toString(),
                    edituser.getEditText().getText().toString(),
                    editpassword.getEditText().getText().toString(),
                    editconfirm.getEditText().getText().toString(),
                    editphone.getEditText().getText().toString(),
                    editaddress.getEditText().getText().toString(),
                    editemail.getEditText().getText().toString()
                   /* budget*/);

            if (isInserted = true) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(SignupActivity.this, "Successful", Toast.LENGTH_SHORT).show();


            } else
                Toast.makeText(SignupActivity.this, "Unsuccessful", Toast.LENGTH_LONG).show();



    }
    private void showMessage(String Message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(Message);
        builder.show();

    }
}
