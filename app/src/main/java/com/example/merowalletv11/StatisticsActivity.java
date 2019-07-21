package com.example.merowalletv11;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity {

    private BarChart mChart;
    float barWidth;
    float barSpace;
    float groupSpace;


    private final int orange = 0xFFFF3300;
    private final int darkGreen = 0xFF20A04D;

    DatabaseHelper MwDb;

    public static String username;
    public static String[] categoryArrayBarGraph = new String[50];
    public static double[] cashExpenseArray = new double[50];
    public static double[] cardExpenseArray = new double[50];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        MwDb= new DatabaseHelper(this);
        username = LoginActivity.throwUsername();

        categoryArrayBarGraph = MainActivity.retCategoryList();
        /*for(int i=0;i<14;i++) {
            categoryArrayBarGraph[i] = "0";
        }*/


        for(int i=0;i<14;i++){
            cardExpenseArray[i] = 0;
            cashExpenseArray[i] = 0;
        }


        Cursor res = MwDb.getExpenseTableData();
        if(res.getCount()==0){
            Intent in = new Intent(StatisticsActivity.this, MainActivity.class);
            startActivity(in);
            Toast.makeText(StatisticsActivity.this,"No data",Toast.LENGTH_SHORT).show();
        }
        else {
            res.moveToFirst();

            do {

                String user = res.getString(1);
                double expense = res.getDouble(2);
                String category = res.getString(4);
                String paymentmode = res.getString(5);


                if(username.equals(user) && paymentmode.equals("Card")){
                    for(int i=0;i<14;i++){
                        if(categoryArrayBarGraph[i].equals(category)){
                            cardExpenseArray[i]+=expense;
                        }
                    }
                }

                else if (username.equals(user)){
                    for(int i=0;i<14;i++){
                        if(categoryArrayBarGraph[i].equals(category)){
                            cashExpenseArray[i]+=expense;
                        }
                    }

                }
            } while (res.moveToNext());
        }
        res.close();


        barWidth = 0.35f;
        barSpace = 0f;
        groupSpace = 0.31f;




        mChart = (BarChart) findViewById(R.id.chart1);
        mChart.setDescription(null);
        mChart.setPinchZoom(false);
        mChart.setScaleEnabled(false);
        mChart.setDrawGridBackground(false);
        // mChart.setFitBars(true);
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(false);


        //int groupCount = 14;

        ArrayList xVals = new ArrayList();

        for(int i=0;i<14;i++){
            if(cardExpenseArray[i]!=0 || cashExpenseArray[i]!=0){
                xVals.add(categoryArrayBarGraph[i]);

            }



        }

        ArrayList yVals1 = new ArrayList();
        ArrayList yVals2 = new ArrayList();

        for(int i=0;i<14;i++){
            if(cardExpenseArray[i] != 0 || cashExpenseArray[i]!=0){
                yVals1.add(new BarEntry((i+1),(float)cardExpenseArray[i]));
                yVals2.add(new BarEntry((i+1),(float)cashExpenseArray[i]));
            }
        }

        BarDataSet set1,set2;
        set1 = new BarDataSet(yVals1,"Card");

        set1.setColor(orange);
        set2 = new BarDataSet(yVals2,"Cash");
        set2.setColor(darkGreen);
        BarData data = new BarData(set1,set2);
        data.setValueFormatter(new LargeValueFormatter());
        mChart.setData(data);
        mChart.getBarData().setBarWidth(barWidth);
        mChart.getXAxis().setAxisMinimum(0);
        // mChart.getXAxis().setAxisMaximum( 0 + mChart.getBarData().getGroupWidth(groupSpace,barSpace) * groupCount);
        mChart.groupBars(0,groupSpace,barSpace);
        mChart.setNoDataTextColor(Color.WHITE);

        Legend Legend1;
        Legend1 = mChart.getLegend();
        Legend1.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        Legend1.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        Legend1.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        Legend1.setDrawInside(true);
        Legend1.setYOffset(25f);
        Legend1.setXOffset(0f);
        Legend1.setYEntrySpace(0f);
        Legend1.setTextSize(14f);
        Legend1.setTextColor(Color.WHITE);

        //X-axis
        XAxis xAxis = mChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(true);
        xAxis.setAxisMaximum(14);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));

        xAxis.setTextColor(Color.WHITE);
        xAxis.setTextSize(14f);


        //Y-axis
        mChart.getAxisRight().setEnabled(false);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setTextSize(14f);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(true);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f);


        mChart.invalidate();
        mChart.animateY(500);
        mChart.setDragEnabled(true);

        mChart.setVisibleXRangeMaximum(4);

    }


}

