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
    public static String deleteCategory;
    public static double cashSubtract;
    public static double cardSubtract;
    public static double finalCash;
    public static double finalCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        MwDb= new DatabaseHelper(this);
        username = LoginActivity.throwUsername();

        cashSubtract = 0;
        cardSubtract = 0;

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
        itemList.add("Accomodation");
        itemList.add("Other Expenses");



        View.OnClickListener addListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(itemList.size()>13)
                {
                    Toast.makeText(CategoriesActivity.this,"No more add",Toast.LENGTH_SHORT).show();
                }

                else{


                item = itemText.getText().toString().trim();
                if(item.equals(""))
                {
                    Toast.makeText(CategoriesActivity.this,"Field cannot be empty",Toast.LENGTH_SHORT).show();
                }
                else{
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



            }}}

        };

        //Retrive from database to show in category activity

        Cursor res = MwDb.getAllCategoryList();
        if(res.getCount()==0)
        {
            Toast.makeText(CategoriesActivity.this,"Add category list here",Toast.LENGTH_SHORT).show();
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

                        deleteCategory = itemList.get(item);
                        deleteCategoryExpenseTable(username,deleteCategory);    //kam update ho
                        MwDb.updateCategory(username,deleteCategory);
                        Intent intent = new Intent(CategoriesActivity.this,MainActivity.class);
                        startActivity(intent);
                       /* if(deletedRows > 0)
                        {
                            Toast.makeText(CategoriesActivity.this,"Deleted successfully",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CategoriesActivity.this, CategoriesActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(CategoriesActivity.this,"Cannot delete default category",Toast.LENGTH_SHORT).show(); //Delete from database
                        }*/


                        /*adapter.remove(itemList.get(item));
                        Toast.makeText(CategoriesActivity.this, "Item Deleted Successfully", Toast.LENGTH_SHORT).show();*/

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

    public void deleteCategoryExpenseTable(String username,String deleteCategory)
    {
        Cursor res1 = MwDb.getExpenseTableData();
        if(res1.getCount()==0)
        { }
        else {
            res1.moveToFirst();

            do {

                String user = res1.getString(1);

                String category = res1.getString(4);
                String paymentType = res1.getString(5);
                if (username.equals(user) && category.equals(deleteCategory)) {
                    double amount = res1.getDouble(2);
                    if(paymentType.equals("Card")){

                        cardSubtract += amount;
                    }
                    else{
                        cashSubtract += amount;
                    }
                    amount = 0;


                }
            } while (res1.moveToNext());
        }


        //MwDb.deleteCategoryFromExpenseTable(username,deleteCategory);
        MwDb.updateCategoryFromExpenseTable(username,deleteCategory);



        Cursor res2 = MwDb.getAllData();
        if(res2.getCount()==0)
        { }
        else {
            res2.moveToFirst();

            do {

                String user = res2.getString(3);
                double totalCashExpense = res2.getDouble(10);
                double totalCardExpense = res2.getDouble(11);
                if (username.equals(user)) {
                   // finalCash = totalCashExpense-cashSubtract;
                    //finalCard = totalCardExpense-cardSubtract;
                    MwDb.updateCardExpense(username,(totalCardExpense - cardSubtract));
                    MwDb.updateCashExpense(username,(totalCashExpense - cashSubtract));

                }
            } while (res1.moveToNext());
        }
        /*MwDb.updateCardExpense(username,finalCard);
        MwDb.updateCashExpense(username,finalCash);*/
    }




    public void onBackPressed() {
        finishAffinity();
        Intent in = new Intent(CategoriesActivity.this, MainActivity.class);
        startActivity(in);
        finish();
    }



}
