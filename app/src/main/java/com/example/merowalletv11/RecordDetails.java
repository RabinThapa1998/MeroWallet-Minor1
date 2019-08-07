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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecordDetails extends AppCompatActivity {
   // TextView amount;
    String eidString;
    ListView listView;
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


        ImageView img = (ImageView) findViewById(R.id.picRec);
        Bitmap bitmap = stringToBitmap(receipt1);
        img.setImageBitmap(bitmap);

        listView = (ListView)findViewById(R.id.recDetails);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Caategory: "+category1);
        arrayList.add("Amount: "+amountString1);
        arrayList.add("Payment Type: "+paymentType1);
        arrayList.add("Date: "+date1);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);



    }

    public static Bitmap stringToBitmap(String encodedString){
        try{
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0 ,encodeByte.length);
        }catch (Exception e){
            e.getMessage();
            return null;
        }
    }


}
