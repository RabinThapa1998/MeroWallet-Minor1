package com.example.merowalletv11;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class ExpenseActivity extends AppCompatActivity {
    private static double cashExpense=0;
    private static double cardExpense=0;
    public static double[] category = new double[9];
    DatabaseHelper MDb;
    public static String username;
    public static String item;

    //private static double expense=0;
    //private static String account;
    private static final String TAG = "ExpenseActivity";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Button btnCapture;
    private ImageView imgCapture;
    private static final int Image_Capture_Code = 1;
    EditText editExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        MDb = new DatabaseHelper(this);
        username = LoginActivity.throwUsername();



        EditText editExpense=(EditText) findViewById(R.id.expense);


        getCashExpense1();
        getCardExpense1();







        //Camera starts here
        btnCapture =(Button)findViewById(R.id.btnTakePicture);
        imgCapture = (ImageView) findViewById(R.id.capturedImage);
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cInt,Image_Capture_Code);
            }
        });

        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ExpenseActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date= dayOfMonth + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };


        //Categorylist
        Spinner dropdown1 = findViewById(R.id.spinner_category);
        String[] categories = {"Food", "Bill", "Shopping", "Clothing", "Travel", "Education", "Entertainment", "Credit Card", "Other Expenses"};



        Cursor res = MDb.getAllCategoryList();
        if(res.getCount()==0)
        {
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
            dropdown1.setAdapter(adapter1);
        }
        else {

            //Changing array to list
            ArrayList<String> myCategories = new ArrayList<String>(Arrays.asList(categories));
            res.moveToFirst();

            do {

                String user = res.getString(1);
                if (username.equals(user)) {
                    item = res.getString(2);

                    myCategories.add(item);
                }
            } while (res.moveToNext());

            categories = myCategories.toArray(categories); //Changing list to array as required
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
            dropdown1.setAdapter(adapter1);
        }









        //Payment type list
        Spinner dropdown2 = findViewById(R.id.spinner_account);
        String[] accounts = {"Card","Cash                    "};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, accounts);
        dropdown2.setAdapter(adapter2);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Image_Capture_Code) {
            if (resultCode == RESULT_OK) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                imgCapture.setImageBitmap(bp);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }
    }


    void validateExpense(View view){


        EditText editExpense=(EditText) findViewById(R.id.expense);
        double temp=Double.parseDouble(editExpense.getText().toString());
        Intent in = new Intent(ExpenseActivity.this, MainActivity.class);
        startActivity(in);
        finish();
        //Accounts
        Spinner spin=(Spinner) findViewById(R.id.spinner_account);
        String account=spin.getSelectedItem().toString();

        if(account=="Card"){
            cardExpense+=temp;

            MDb.updateCardExpense(username,cardExpense);



        }else {
            cashExpense+=temp;
            MDb.updateCashExpense(username,cashExpense);


        }

        //expense+=temp;

        //Categories
        Spinner spin1=(Spinner) findViewById(R.id.spinner_category);
        String cat = spin1.getSelectedItem().toString();

            switch (cat) {
                case "Food":
                    category[0]+=temp;
                    break;
                case "Bill":
                    category[1]+=temp;
                    break;
                case "Shopping":
                    category[2]+=temp;
                    break;
                case "Clothing":
                    category[3]+=temp;
                    break;
                case "Travel":
                    category[4]+=temp;
                    break;
                case "Education":
                    category[5]+=temp;
                    break;
                case "Entertainment":
                    category[6]+=temp;
                    break;
                case "Credit Card":
                    category[7]+=temp;
                    break;
                default:
                    category[8]+=temp;
                    break;
            }

        boolean isInserted = MDb.insertExpense(
                editExpense.getText().toString(), //merchantname
                cat, //category
                editExpense.getText().toString(), //amount
                mDisplayDate.getText().toString(), //date
                account, //paymenttype
                editExpense.getText().toString()); //receipt

        // editExpense.getText().toString()
//                mDisplayDate.getText().toString(),
        //             account
        //    );

        if (isInserted = true) {
            Intent intent = new Intent(ExpenseActivity.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(ExpenseActivity.this, "Expense Entered", Toast.LENGTH_SHORT).show();


        } else
            Toast.makeText(ExpenseActivity.this, "Error", Toast.LENGTH_LONG).show();






    }

    /*public void AddData1() {
    }*/

    public static double[] retArray(){
        return (category);
    }
    /*public static double getExpense(){
        return expense;
    }

    public static double getCardExpense(){
        return cardExpense;
    }

    public static double getCashExpense(){
        return cashExpense;
    }
    */



    public void getCashExpense1()
    {
        Cursor res = MDb.getAllData();
        if(res.getCount()==0)
        {
            Toast.makeText(ExpenseActivity.this,"Empty",Toast.LENGTH_SHORT).show();
        }
        else {
            res.moveToFirst();

            do {

                String user = res.getString(3);
                if (username.equals(user)) {

                    cashExpense = res.getDouble(10);

                }
            } while (res.moveToNext());
        }
    }

    public void getCardExpense1()
    {
        Cursor res = MDb.getAllData();
        if(res.getCount()==0)
        {
            Toast.makeText(ExpenseActivity.this,"Empty",Toast.LENGTH_SHORT).show();
        }
        else {
            res.moveToFirst();

            do {

                String user = res.getString(3);
                if (username.equals(user)) {

                    cardExpense = res.getDouble(11);

                }
            } while (res.moveToNext());
        }
    }




}
