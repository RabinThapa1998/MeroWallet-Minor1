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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;

public class StatisticsActivity extends AppCompatActivity {

    private BarChart mChart;
    LineChart mpLineChart;
    float barWidth;
    float barSpace;
    float groupSpace;


    private final int orange = 0xFFE24444;
    private final int darkGreen = 0xFF49D37A;

    DatabaseHelper MwDb;

    public static String username;
    public static String[] categoryArrayBarGraph = new String[50];
    public static double[] cashExpenseArray = new double[50];
    public static double[] cardExpenseArray = new double[50];

    public static double[] cardExpenseLineChart = new double[50];
    public static double[] cashExpenseLineChart = new double[50];

    private static String dayString;
    private static String monthString;
    private static String yearString;

    public static int thisDay;
    public static int thisMonth;
    public static int thisYear;
    public static String retrievedYear;
    public static String retrievedMonth;
    public static String retrievedDay;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        MwDb= new DatabaseHelper(this);
        username = LoginActivity.throwUsername();
        retrievedDay = "";
        retrievedMonth = "";
        retrievedYear = "";


        Calendar cal = Calendar.getInstance();
        int year1 = cal.get(Calendar.YEAR);
        int month1 = cal.get(Calendar.MONTH);
        int day1 = cal.get(Calendar.DAY_OF_MONTH);
        month1++;
        thisMonth =month1;
        thisYear=year1;
        thisDay= day1;
        yearString = "" + year1;


        if(thisDay<10){

            dayString = "0"+thisDay;

        }
        else{
            dayString = ""+thisDay;
        }

        if(thisMonth<10){

            monthString = "0"+thisMonth;
        }

        else{
            monthString = ""+thisMonth;
        }

        String date= year1 + "-" + monthString + "-" + dayString;




        categoryArrayBarGraph = MainActivity.retCategoryList();
        /*for(int i=0;i<14;i++) {
            categoryArrayBarGraph[i] = "0";
        }*/


        for(int i=0;i<14;i++){
            cardExpenseArray[i] = 0;
            cashExpenseArray[i] = 0;
        }



        getDataForBarGraph();




        barWidth = 0.35f;
        barSpace = 0f;
        groupSpace = 0.3f;




        mChart = (BarChart) findViewById(R.id.chart1);
        mChart.setDescription(null);
        mChart.setPinchZoom(false);
        mChart.setScaleEnabled(false);
        mChart.setDrawGridBackground(false);
       // mChart.setFitBars(true);
        mChart.setDrawBarShadow(false);


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
        set1.setValueTextColor(Color.WHITE);
        set1.setValueTextSize(10);
        set2 = new BarDataSet(yVals2,"Cash");
        set2.setColor(darkGreen);
        set2.setValueTextColor(Color.WHITE);
        set2.setValueTextSize(10);
        BarData data = new BarData(set1,set2);
        data.setValueFormatter(new LargeValueFormatter());
        mChart.setData(data);
        mChart.getBarData().setBarWidth(barWidth);
        mChart.getXAxis().setAxisMinimum(0);
       // mChart.getXAxis().setAxisMaximum( 0 + mChart.getBarData().getGroupWidth(groupSpace,barSpace) * groupCount);
        mChart.groupBars(0,groupSpace,barSpace);

        Legend Legend1;
        Legend1 = mChart.getLegend();
        Legend1.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        Legend1.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        Legend1.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        Legend1.setDrawInside(true);
        Legend1.setYOffset(25f);
        Legend1.setXOffset(0f);
        Legend1.setYEntrySpace(0f);
        Legend1.setTextSize(13f);
        Legend1.setTextColor(Color.WHITE);

        //X-axis
        XAxis xAxis = mChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
       // xAxis.setAxisMaximum(14);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
        mChart.setExtraOffsets(20,0,20,10);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setTextSize(13f);


        //Y-axis
        mChart.getAxisRight().setEnabled(false);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setTextSize(13f);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(true);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f);

        mChart.invalidate();
        mChart.animateY(500);
        mChart.setDragEnabled(true);

        mChart.setVisibleXRangeMaximum(3);




        //Line chart


        for(int i=0;i<33;i++)
        {
            cardExpenseLineChart[i] = 0;
            cashExpenseLineChart[i] = 0;
        }

         getDataForLineChart();

        mpLineChart = (LineChart) findViewById(R.id.linechart);

        ArrayList<Entry> dataVals1 = new ArrayList<Entry>();
        ArrayList<Entry> dataVals2 = new ArrayList<Entry>();


        for(int i=0;i<32;i++){




                dataVals1.add(new Entry((i + 1), (float) cardExpenseLineChart[i]));
                dataVals2.add(new Entry((i + 1), (float) cashExpenseLineChart[i]));


        }
      /*  dataVals1.add(new Entry(0,20));
        dataVals1.add(new Entry(1,24));
        dataVals1.add(new Entry(2,2));
        dataVals1.add(new Entry(3,10));
        dataVals1.add(new Entry(4,28));
        dataVals1.add(new Entry(5,28));
        dataVals1.add(new Entry(6,28));
        dataVals1.add(new Entry(7,28));
        dataVals1.add(new Entry(8,28));
        dataVals1.add(new Entry(9,28));
        dataVals1.add(new Entry(10,28));
        dataVals1.add(new Entry(11,28));
        dataVals1.add(new Entry(12,28));
        dataVals1.add(new Entry(13,28));
        dataVals1.add(new Entry(14,28));
        dataVals1.add(new Entry(15,28));
        dataVals1.add(new Entry(16,28));
        dataVals1.add(new Entry(17,28));
        dataVals1.add(new Entry(18,28));
        dataVals1.add(new Entry(19,28));
        dataVals1.add(new Entry(20,28));
        dataVals1.add(new Entry(21,28));
        dataVals1.add(new Entry(22,28));
        dataVals1.add(new Entry(23,28));
        dataVals1.add(new Entry(24,28));
        dataVals1.add(new Entry(25,28));
        dataVals1.add(new Entry(26,28));
        dataVals1.add(new Entry(27,28));
        dataVals1.add(new Entry(28,28));
        dataVals1.add(new Entry(29,28));
        dataVals1.add(new Entry(30,28));
        dataVals1.add(new Entry(31,28));





        //Second line

        dataVals2.add(new Entry(0,12));
        dataVals2.add(new Entry(1,18));
        dataVals2.add(new Entry(2,20));
        dataVals2.add(new Entry(3,26));
        dataVals2.add(new Entry(4,30));
        dataVals2.add(new Entry(5,35));
        dataVals2.add(new Entry(6,20));
        dataVals2.add(new Entry(7,29));
        dataVals2.add(new Entry(8,50));
        dataVals2.add(new Entry(9,22));
        dataVals2.add(new Entry(10,10));
        dataVals2.add(new Entry(11,19));
        dataVals2.add(new Entry(12,26));
        dataVals2.add(new Entry(13,30));
        dataVals2.add(new Entry(14,32));
        dataVals2.add(new Entry(15,54));
        dataVals2.add(new Entry(16,24));
        dataVals2.add(new Entry(17,23));
        dataVals2.add(new Entry(18,50));
        dataVals2.add(new Entry(19,23));
        dataVals2.add(new Entry(20,24));
        dataVals2.add(new Entry(21,40));
        dataVals2.add(new Entry(22,43));
        dataVals2.add(new Entry(23,25));
        dataVals2.add(new Entry(24,35));
        dataVals2.add(new Entry(25,34));
        dataVals2.add(new Entry(26,24));
        dataVals2.add(new Entry(27,32));
        dataVals2.add(new Entry(28,29));
        dataVals2.add(new Entry(29,27));
        dataVals2.add(new Entry(30,33));
        dataVals2.add(new Entry(31,36));*/












        LineDataSet lineDataSet1 = new LineDataSet(dataVals1,"Card");
        lineDataSet1.setColor(Color.RED);
        lineDataSet1.setValueTextSize(10);
        lineDataSet1.setValueTextColor(Color.WHITE);
        LineDataSet lineDataSet2 = new LineDataSet(dataVals2,"Cash");
        lineDataSet2.setColor(Color.GREEN);
        lineDataSet2.setValueTextColor(Color.WHITE);
        lineDataSet2.setValueTextSize(10);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);
        dataSets.add(lineDataSet2);

        LineData dataLine = new LineData(dataSets);
        mpLineChart.setData(dataLine);
        mpLineChart.invalidate();

        mpLineChart.setVisibleXRangeMaximum(6);
        mpLineChart.setDescription(null);


        Legend Legend2;
        Legend2 = mpLineChart.getLegend();
        Legend2.setTextSize(13f);
        Legend2.setTextColor(Color.WHITE);
        Legend2.setYEntrySpace(15);



        //X-axis
        XAxis xAxis1 =mpLineChart.getXAxis();
        xAxis1.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis1.setTextColor(Color.WHITE);
        xAxis1.setTextSize(13f);



        //Y-axis
        mpLineChart.getAxisRight().setEnabled(false);
        YAxis leftAxis1 = mpLineChart.getAxisLeft();
        leftAxis1.setTextColor(Color.WHITE);
        leftAxis1.setTextSize(13f);



        mpLineChart.animateX(500);
        mpLineChart.setPinchZoom(false);
        mpLineChart.setScaleEnabled(false);


    }


    public void getDataForBarGraph()
    {
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
    }

    public void getDataForLineChart(){
        Cursor res = MwDb.getExpenseTableData();
        if(res.getCount()==0)
        {
            Intent in = new Intent(StatisticsActivity.this, MainActivity.class);
            startActivity(in);
            Toast.makeText(StatisticsActivity.this,"No data",Toast.LENGTH_SHORT).show();
        }
        else {
            res.moveToFirst();

            do {

                String user = res.getString(1);
                String retrievedDate = res.getString(3);
                retrievedYear = retrievedDate.substring(0,4);
                retrievedMonth = retrievedDate.substring(5,7);
                retrievedDay = retrievedDate.substring(8,10);
                int retrievedDayint = Integer.parseInt(retrievedDay);
                double retrievedAmount = res.getDouble(2);
                String retrievedPaymentType = res.getString(5);
                if (username.equals(user) && retrievedYear.equals(yearString) && retrievedMonth.equals(monthString)) {

                    if(retrievedPaymentType.equals("Card")) {
                        cardExpenseLineChart[retrievedDayint-1] += retrievedAmount;

                        }
                    else{
                        cashExpenseLineChart[retrievedDayint-1] += retrievedAmount;
                    }
                }
            } while (res.moveToNext());
        }
        res.close();

    }


}
