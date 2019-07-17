package com.example.merowalletv11;

import android.content.Intent;
import android.database.Cursor;
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

    DatabaseHelper MwDb;
    ArrayList<String> itemList;
    ArrayAdapter<String> adapter;
    EditText itemText;
    Button addButton;
    ListView lv;
    public static String username;
    public static String item;

    public static String[] category1 = new String[15];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        MwDb= new DatabaseHelper(this);
        username = LoginActivity.throwUsername();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        category1[0]="Food";
        category1[1]="Bill";
        category1[2]="Shopping";
        category1[3]="Clothing";
        category1[4]="Travel";
        category1[5]="Education";
        category1[6]="Entertainment";
        category1[7]="Credit Card";
        category1[8]="Other Expenses";


        lv = (ListView)findViewById(R.id.listCat);
        itemText = (EditText) findViewById(R.id.cat);
        addButton = (Button) findViewById(R.id.addcat);

        itemList = new ArrayList<>();
        adapter = new ArrayAdapter<>(CategoriesActivity.this, android.R.layout.simple_list_item_multiple_choice,itemList);

        itemList.add(category1[0]);
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
                item = itemText.getText().toString();

                itemList.add(item);
                itemText.setText("");
                adapter.notifyDataSetChanged();


            //Entry in database
                boolean isInserted = MwDb.insertCategoryList(username,
                        item
                        );

                    if (isInserted = true) {

                    Toast.makeText(CategoriesActivity.this, "Category added", Toast.LENGTH_SHORT).show();



                    } else
                    Toast.makeText(CategoriesActivity.this, "Failed", Toast.LENGTH_LONG).show();



            }

        };

        //Retrive from database to show in category activity

        Cursor res = MwDb.getAllCategoryList();
        if(res.getCount()==0)
        {
            Toast.makeText(CategoriesActivity.this,"",Toast.LENGTH_SHORT).show();
        }
        else {
            res.moveToFirst();

            do {

                String user = res.getString(1);
                if (username.equals(user)) {
                    item = res.getString(2);

                    itemList.add(item);
                    itemText.setText("");
                    adapter.notifyDataSetChanged();

                }
            } while (res.moveToNext());
        }


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
