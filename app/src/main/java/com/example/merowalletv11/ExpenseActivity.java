package com.example.merowalletv11;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    public static int thisMonth;
    public static int thisDay;
    public static int thisYear;
    public static int selectedDay;
    public static int selectedMonth;
    public static int selectedYear;
    public static String finalDate;
    public static String dayString;
    public static String monthString;


    private static final int GALLERY_REQUEST_CODE=100;
    private static final int CAMERA_REQUEST_CODE=200;
    private static final float PREFERRED_WIDTH=250;
    private static final float PREFERRED_HEIGHT=250;
    String img;

    public static double remainingBudget;





    //private static double expense=0;
    //private static String account;
    private static final String TAG = "ExpenseActivity";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Button btnCapture;
    private ImageView selectedImageView;
    //private static final int Image_Capture_Code = 1;
    EditText editExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(

                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Intent in = new Intent(ExpenseActivity.this, MainActivity.class);
                        startActivity(in);
                        finish();
                    }


                }
        );

        MDb = new DatabaseHelper(this);
        username = LoginActivity.throwUsername();

        selectedImageView=(ImageView)findViewById(R.id.capturedImage);



        EditText editExpense=(EditText) findViewById(R.id.expense);

        remainingBudget = MainActivity.getRemainingBudget();


        getCashExpense1();
        getCardExpense1();

        btnCapture =(Button)findViewById(R.id.btnTakePicture);







        //Camera starts here
       /* btnCapture =(Button)findViewById(R.id.btnTakePicture);
        imgCapture = (ImageView) findViewById(R.id.capturedImage);
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cInt,Image_Capture_Code);
            }
        });*/

        //test default date

        Calendar cal = Calendar.getInstance();
        int year1 = cal.get(Calendar.YEAR);
        int month1 = cal.get(Calendar.MONTH);
        int day1 = cal.get(Calendar.DAY_OF_MONTH);
        month1++;
        thisMonth =month1;
        thisYear=year1;
        thisDay= day1;
        selectedYear = year1;
        selectedMonth = month1;
        selectedDay = day1;

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

        String date= dayString + "/" + monthString + "/" + year1;



        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        mDisplayDate.setText(date);



        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
               // String dateDefault= day + "/" + month + "/" + year;
               // mDisplayDate.setText(dateDefault);





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


                selectedYear = year;
                selectedMonth = month;
                selectedDay = dayOfMonth;


                if(selectedDay<10){

                    dayString = "0"+selectedDay;

                }
                else{
                    dayString = ""+selectedDay;
                }

                if(selectedMonth<10){

                    monthString = "0"+selectedMonth;
                }

                else{
                    monthString =""+selectedMonth;
                }




                String date = dayString + "/" + monthString + "/" + selectedYear;


                mDisplayDate.setText(date);




            }
        };


        //Categorylist
        Spinner dropdown1 = findViewById(R.id.spinner_category);
        String[] categories = {"Food", "Bill", "Shopping", "Clothing", "Travel", "Education", "Entertainment", "Accomodation", "Other Expenses"};



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
        res.close();









        //Payment type list
        Spinner dropdown2 = findViewById(R.id.spinner_account);
        String[] accounts = {"Card","Cash                    "};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, accounts);
        dropdown2.setAdapter(adapter2);

    }
/*
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
*/

    void validateExpense(View view){


        EditText editExpense = (EditText) findViewById(R.id.expense);

        double temp = Double.parseDouble(editExpense.getText().toString());


        if((remainingBudget - temp)<= 0){

            Toast.makeText(ExpenseActivity.this,"Not enough budget remaining. Please reallocate budget",Toast.LENGTH_LONG).show();

        }
        else {

            if (selectedImageView.getDrawable() == null) {
                img = "null";
            } else {
                Bitmap image = ((BitmapDrawable) selectedImageView.getDrawable()).getBitmap();
                img = bitmapToString(resizeBitmap(image));
            }






            if (thisYear < selectedYear || (thisYear == selectedYear && thisMonth < selectedMonth) || (thisYear == selectedYear && thisMonth == selectedMonth && thisDay < selectedDay)) {
                Toast.makeText(ExpenseActivity.this, "Expense cannot be entered in future date", Toast.LENGTH_SHORT).show();
            } else if (thisYear > selectedYear || (thisYear == selectedYear && thisMonth > selectedMonth)) {
                Toast.makeText(ExpenseActivity.this, "Expense cannot be entered in previous month", Toast.LENGTH_SHORT).show();
            } else {


                if(selectedDay<10){

                    dayString = "0"+selectedDay;

                }
                else{
                    dayString = ""+selectedDay;
                }
                if(selectedMonth<10){

                    monthString = "0"+selectedMonth;
                }
                else{

                    monthString = ""+selectedMonth;
                }

                String date = dayString + "/" + monthString + "/" + selectedYear;
                finalDate = date;


                mDisplayDate.setText(date);

                //do nothing


                //Accounts
                Spinner spin = (Spinner) findViewById(R.id.spinner_account);
                String account = spin.getSelectedItem().toString();

                if (account == "Card") {
                    cardExpense += temp;

                    MDb.updateCardExpense(username, cardExpense);


                } else {
                    cashExpense += temp;
                    MDb.updateCashExpense(username, cashExpense);


                }

                //expense+=temp;

                //Categories
                Spinner spin1 = (Spinner) findViewById(R.id.spinner_category);
                String cat = spin1.getSelectedItem().toString();

                boolean isInserted = MDb.insertExpense(
                        username.toString(), //merchantname
                        cat, //category
                        editExpense.getText().toString(), //amount
                        //mDisplayDate.getText().toString(), //date
                        finalDate,
                        account, //paymenttype
                        img); //receipt

                Intent in = new Intent(ExpenseActivity.this, MainActivity.class);
                startActivity(in);
                finish();


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


        }

    }

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
        res.close();
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
        res.close();
    }

    //gallery section

    public void openGallery(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);
    }

    public void openCamera(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            try {
                Uri selectedImage = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                selectedImageView.setImageBitmap(BitmapFactory.decodeStream(imageStream));

            } catch (IOException exception) {
                exception.printStackTrace();
            }


        }
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            selectedImageView.setImageBitmap(imageBitmap);
        }

    }


    public void onBackPressed() {
        finishAffinity();
        Intent in = new Intent(ExpenseActivity.this, MainActivity.class);
        startActivity(in);
        finish();
    }

    private static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }


    public static Bitmap resizeBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = PREFERRED_WIDTH / width;
        float scaleHeight = PREFERRED_HEIGHT / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bitmap, 0, 0, width, height, matrix, false);
        bitmap.recycle();
        return resizedBitmap;
    }







}
