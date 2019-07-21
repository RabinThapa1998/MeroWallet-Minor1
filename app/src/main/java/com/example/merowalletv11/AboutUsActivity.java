package com.example.merowalletv11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AboutUsActivity extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("About Us");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(

                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Intent in = new Intent(AboutUsActivity.this, MainActivity.class);
                        startActivity(in);
                        finish();
                    }


                }
        );

        listView = (ListView)findViewById(R.id.aboutUs);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Nation Shakya");
        arrayList.add("Rabin Thapa");
        arrayList.add("Sanil Manandhar");
        arrayList.add("Saurav Munankarmi");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
    }
}
