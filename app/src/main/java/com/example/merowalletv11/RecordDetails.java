package com.example.merowalletv11;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class RecordDetails extends AppCompatActivity {
   // TextView amount;
    String eidString;
    int eidInt;
    DatabaseHelper MwDb;
    public static String username;
    private static String amountString1;
    private  static String date1;
    private static String category1;
    private static String paymentType1;
    private static String receipt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Record Details");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MwDb = new DatabaseHelper(this);
        username = LoginActivity.throwUsername();

        toolbar.setNavigationOnClickListener(

                new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent in = new Intent(RecordDetails.this, RecordsActivity.class);
                startActivity(in);
                finish();
            }


        }

        );

        eidString = activity_recordsview.importEid();
        eidInt = Integer.parseInt(eidString);
       // eidInt++;

        //test.setText(eidInt);



        Cursor res = MwDb.getExpenseTableData();
        if(res.getCount()==0)
        {
            Toast.makeText(RecordDetails.this,"Empty database",Toast.LENGTH_SHORT).show();
        }
        else {
            res.moveToFirst();

            do {


                int ID = res.getInt(0);
                String user = res.getString(1);
                Double amount = res.getDouble(2);
                String amountString = Double.toString(amount);
                String date = res.getString(3);
                String category = res.getString(4);
                String paymentType = res.getString(5);
                String reciept = res.getString(6);
                if (username.equals(user) && ID == eidInt ) {

                    amountString1 = amountString;
                    date1 = date;
                    category1 = category;
                    paymentType1 = paymentType;
                    receipt1 = reciept;

                }
            } while (res.moveToNext());
        }
        res.close();



        TextView amountTextView = (TextView) findViewById(R.id.amountRec);
        TextView categoryTextView = (TextView) findViewById(R.id.catRec);
        TextView paymentTypeTextView = (TextView) findViewById(R.id.paymenttypeRec);
        TextView dateTextView = (TextView) findViewById(R.id.dateRec);
        ImageView img = (ImageView) findViewById(R.id.picRec);

        amountTextView.setText(amountString1);
        categoryTextView.setText(category1);
        paymentTypeTextView.setText(paymentType1);
        dateTextView.setText(date1);

        Bitmap bitmap = stringToBitmap(receipt1);
        img.setImageBitmap(bitmap);



    }

    private static Bitmap stringToBitmap(String encodedString){
        try{
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0 ,encodeByte.length);
        }catch (Exception e){
            e.getMessage();
            return null;
        }
    }


}
