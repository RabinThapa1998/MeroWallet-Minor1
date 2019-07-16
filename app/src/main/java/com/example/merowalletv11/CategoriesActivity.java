package com.example.merowalletv11;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {

    ArrayList<String> itemList;
    ArrayAdapter<String> adapter;
    EditText itemText;
    Button addButton;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv = (ListView)findViewById(R.id.listCat);
        itemText = (EditText) findViewById(R.id.cat);
        addButton = (Button) findViewById(R.id.addcat);

        itemList = new ArrayList<>();
        adapter = new ArrayAdapter<>(CategoriesActivity.this, android.R.layout.simple_list_item_multiple_choice,itemList);

        itemList.add("Food");
        itemList.add("Bill");
        itemList.add("Shopping");
        itemList.add("Clothing");
        itemList.add("Travel");
        itemList.add("Education");
        itemList.add("Entertainment");
        itemList.add("Credit Card");
        itemList.add("Other Expenses");



        View.OnClickListener addListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.add(itemText.getText().toString());
                itemText.setText("");
                adapter.notifyDataSetChanged();
            }
        };
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SparseBooleanArray positionchecker = lv.getCheckedItemPositions();
                int count = lv.getCount();
                for(int item=count-1;item>=0; item--){
                    if(positionchecker.get(item)){
                        adapter.remove(itemList.get(item));
                        Toast.makeText(CategoriesActivity.this, "Item Deleted Successfully", Toast.LENGTH_SHORT).show();

                    }

                }
                positionchecker.clear();

                adapter.notifyDataSetChanged();
                return false;
            }
        });

        addButton.setOnClickListener(addListener);
        lv.setAdapter(adapter);

    }

}
