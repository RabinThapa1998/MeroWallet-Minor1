package com.example.merowalletv11;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AboutUsActivity extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        listView = (ListView)findViewById(R.id.aboutUs);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("DEVELOPERS:");
        arrayList.add("Nation Shakya");
        arrayList.add("Rabin Thapa");
        arrayList.add("Sanil Manandhar");
        arrayList.add("Saurav Munankarmi");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
    }
}
