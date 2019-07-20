package com.example.merowalletv11;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecordsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecordsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    DatabaseHelper MwDb;
    public static String username;

    @Override
    public void onBackPressed() {
        Intent in = new Intent(RecordsActivity.this, MainActivity.class);
        startActivity(in);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        Toolbar toolbar = findViewById(R.id.toolbar);
        MwDb = new DatabaseHelper(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        username = LoginActivity.throwUsername();




        final ArrayList<activity_recordsview> recordsList = new ArrayList<>();

        Cursor res = MwDb.getExpenseTableData();
        if(res.getCount()==0)
        {
            Toast.makeText(RecordsActivity.this,"RECORDS HERE",Toast.LENGTH_SHORT).show();
        }
        else {
            res.moveToLast();

            do {

                String user = res.getString(1);
                String catName = res.getString(4);
                String paymentType = res.getString(5);
                Double amount = res.getDouble(2);

                String amountString = Double.toString(amount);
                String date = res.getString(3);


                int drawable1;


                if (username.equals(user)) {
                    switch (catName){
                        case "Food":
                            drawable1 = R.drawable.ic_food;
                            break;
                        case "Bill":
                            drawable1 = R.drawable.ic_bill;
                            break;
                        case "Shopping":
                            drawable1 = R.drawable.ic_shopping;
                            break;
                        case "Clothing":
                            drawable1 = R.drawable.ic_clothing;
                            break;
                        case "Travel":
                            drawable1 = R.drawable.ic_travel;
                            break;
                        case "Education":
                            drawable1 = R.drawable.ic_education;
                            break;
                        case "Entertainment":
                            drawable1 = R.drawable.ic_entertainment;
                            break;
                        case "Accomodation":
                            drawable1 = R.drawable.ic_accomodation;
                            break;
                        case "Other Expenses":
                            drawable1 = R.drawable.ic_otherexpenses;
                            break;
                        default:
                            drawable1 = R.drawable.ic_default;


                    }


                    recordsList.add(new activity_recordsview(drawable1,catName,paymentType,amountString,date));


                }
            } while (res.moveToPrevious());
        }
        res.close();




       // recordsList.add(new activity_recordsview(R.drawable.ic_food,"Food", "Card", "15000", "2019/12/15"));






        mRecyclerView = findViewById(R.id.records);
        mRecyclerView.setHasFixedSize(true);// increases the performance of the app, size wont change
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecordsAdapter(recordsList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecordsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                recordsList.get(position);
                Intent in = new Intent(RecordsActivity.this, RecordDetails.class);
                startActivity(in);
            }
        });

//hey

      /* mAdapter.setOnItemClickListener(new RecordsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(RecordsActivity.this, recDetails.class);
                startActivity(intent);
            }
        });*/


    }


}
