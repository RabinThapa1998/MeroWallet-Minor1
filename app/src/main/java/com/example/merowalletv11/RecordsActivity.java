package com.example.merowalletv11;

import android.content.Intent;
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

import java.util.ArrayList;

public class RecordsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ArrayList<activity_recordsview> recordsList = new ArrayList<>();
        recordsList.add(new activity_recordsview(R.drawable.ic_food,"Food", "Card", "15000", "2019/12/15"));

        mRecyclerView = findViewById(R.id.records);
        mRecyclerView.setHasFixedSize(true);// increases the performance of the app, size wont change
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecordsAdapter(recordsList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

      /* mAdapter.setOnItemClickListener(new RecordsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(RecordsActivity.this, recDetails.class);
                startActivity(intent);
            }
        });*/


    }


}
