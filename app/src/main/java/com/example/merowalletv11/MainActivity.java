package com.example.merowalletv11;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.merowalletv11.Receiver2.CHANNEL_1_ID;
import static com.example.merowalletv11.Receiver2.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NotificationManagerCompat notificationManager;

    PieChart pieChart;

    private long backPressedTime;
    private Toast backToast;
    public static  String  analysisCategory ;
    private static String imag;




    private static double temp;
    private static double budget;
    public static double[] expenseArray = new double[50];
    public static String[] categoryArray = new String[50];
    public static String username;
    public static double cardExpense;
    public static double cashExpense;
    public static String email;
    DatabaseHelper MwDb;
    private DrawerLayout drawer;
    public static int k;
    public static int[] A = new int[15];
    public static int[] repeat = new int[15];

    private static String dayString;
    private static String monthString;
    private static String yearString;

    private static String thisDate;


    public static int thisDay;
    public static int thisMonth;
    public static int thisYear;

    public static double amountCategory[] = new double[15];
    public static double averageCategory[] = new double[15];

    public static int numberOfDays;



    public static String signupDate; //piggybacked from getBudget mehhod

    public void validateFloatingAction(View view) {
        if(budget==0){
            Toast.makeText(this,"Please Set Your Budget First!",Toast.LENGTH_SHORT).show();
        }
        else{
            Intent in = new Intent(com.example.merowalletv11.MainActivity.this, ExpenseActivity.class);
            startActivity(in);
            finish();}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Toast.makeText(MainActivity.this,LoginActivity.passusername,Toast.LENGTH_SHORT).show();

        Toolbar toolbar=findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);


        MwDb= new DatabaseHelper(this);
        username = LoginActivity.throwUsername();
        k=9;
        for(int i=0;i<15;i++)
        {
            A[i]=1;
            repeat[i] = 0;
        }

        if(ExpenseActivity.analysisNotification ==true){
             analysisCategory = ExpenseActivity.getAnalysisData();

            notificationManager = NotificationManagerCompat.from(this);
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                    .setSmallIcon(R.drawable.nati)
                    .setContentTitle("Exceeded daily average expense")
                    .setContentText("Today's expense in "+analysisCategory +" category has exceeded it's daily expense")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build();

            notificationManager.notify(2, notification);



        }









        //Retrieve from database
        getBudget();
        getCardExpense1();
        getCashExpense1();
        getEmail();





        //calender code for todays date

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

        thisDate= year1 + "-" + monthString + "-" + dayString;


        numberOfDays = (int)(  (thisYear - Integer.parseInt(signupDate.substring(0,4)))*365.25 +  (  thisMonth - Integer.parseInt(signupDate.substring(5,7)) )*30.4375  +   (thisDay - Integer.parseInt(signupDate.substring(8,10)))   );

        //-----------double to int ---difference in year times the number of days in a year---------------difference in month ------------------------------------------------------diff in day










        //Piechart starts here
        pieChart=(PieChart) findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.99f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setHoleRadius(35f);
        pieChart.setTransparentCircleRadius(40f);



        ArrayList<PieEntry> yvalues= new ArrayList<>();


        categoryArray[0] = "Food";
        categoryArray[1] = "Bill";
        categoryArray[2] = "Shopping";
        categoryArray[3] = "Clothing";
        categoryArray[4] = "Travel";
        categoryArray[5] = "Education";
        categoryArray[6] = "Entertainment";
        categoryArray[7] = "Accomodation";
        categoryArray[8] = "Other Expenses";
        categoryArray[9] = "0";
        categoryArray[10] = "0";
        categoryArray[11] = "0";
        categoryArray[12] = "0";
        categoryArray[13] = "0";

        expenseArray[0] = 0;
        expenseArray[1] = 0;
        expenseArray[2] = 0;
        expenseArray[3] = 0;
        expenseArray[4] = 0;
        expenseArray[5] = 0;
        expenseArray[6] = 0;
        expenseArray[7] = 0;
        expenseArray[8] = 0;
        expenseArray[9] = 0;
        expenseArray[10] = 0;
        expenseArray[11] = 0;
        expenseArray[12] = 0;
        expenseArray[13] = 0;

        for(int i=0; i<15;i++){
            amountCategory[i] =0;
        }





      /*  for(int i=0;i<14;i++){

            averageCategory[i] = amountCategory[i]/numberOfDays;
        }
*/




        //Retriving category list

        Cursor res = MwDb.getAllCategoryList();
        if(res.getCount()==0){}
        else {
            res.moveToFirst();

            do {

                String user = res.getString(1);
                if(user.equals(null))
                {throw new NullPointerException("Test");
                }

                if (username.equals(user)  && !user.equals(null)) {
                    categoryArray[k] = res.getString(2);
                    k++;
                }
            } while (res.moveToNext());
        }
        res.close();

        //Retrieve amount according to category array
      Cursor res1 = MwDb.getExpenseTableData();
        if(res1.getCount()==0)
        { }
        else {
            res1.moveToFirst();

            do {

                String user1 = res1.getString(1);
                String category1 = res1.getString(4);
                Double amount1 = res1.getDouble(2);
                if (username.equals(user1)) {
                    for(int j=0;j<14;j++){
                        if(categoryArray[j].equals(category1)) {
                            expenseArray[j] += amount1;
                        }
                    }
                }
            } while (res1.moveToNext());
        }
        res1.close();

       for(int i = 0;i<14;i++) {

          if(expenseArray[i] != 0) {

              yvalues.add(new PieEntry((float) expenseArray[i], categoryArray[i]));
          }
      }


        getDataAnalysis();



        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
        PieDataSet dataSet = new PieDataSet(yvalues, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);


        //Remaining budget and display in dashboard
        temp = budget-(cardExpense + cashExpense);

        TextView txt1=(TextView) findViewById(R.id.editTextResult);
        txt1.setText(""+temp);

        TextView txt2=(TextView) findViewById(R.id.expense1);
        txt2.setText(""+(cardExpense + cashExpense));

        TextView txt3=(TextView) findViewById(R.id.cash_amt);
        TextView txt4=(TextView) findViewById(R.id.card_amt);
        txt4.setText(""+cardExpense);
        txt3.setText(""+cashExpense);

        if(budget!=0) {

            if (temp <= 0.1 * budget) {
                notificationManager = NotificationManagerCompat.from(this);
                Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.nati)
                        .setContentTitle("Low Budget")
                        .setContentText("Please maintain your budget")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build();

                notificationManager.notify(1, notification);
            }


        }



        //Code for drawer
       drawer=findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
       ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawer, toolbar,
               R.string.navigation_drawer_open,R.string.navigation_drawer_close);
       drawer.addDrawerListener(toggle);
       toggle.syncState();




       // NavigationView navigationUsername = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.nav_username);
        TextView navEmail = (TextView) headerView.findViewById(R.id.nav_email);
        CircleImageView navImage =(CircleImageView) headerView.findViewById(R.id.navProfile);
        navUsername.setText(""+username);
        navEmail.setText(""+email);
        Bitmap bitmap = RecordDetails.stringToBitmap(imag);
        navImage.setImageBitmap(bitmap);






        //Toast.makeText(MainActivity.this,"  " + numberOfDays,Toast.LENGTH_SHORT).show();









    }


    public void validate1(View view) {
        Intent in = new Intent(com.example.merowalletv11.MainActivity.this, PromptActivity.class);
        startActivity(in);
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer((GravityCompat.START));
        }else{

        if(backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            finishAffinity();
            super.onBackPressed();
            return;

        }
        else{
            backToast = Toast.makeText(MainActivity.this,"Press again to exit",Toast.LENGTH_SHORT);
            backToast.show();
        }


        backPressedTime = System.currentTimeMillis();
    }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_categories:
                Intent in0 = new Intent(com.example.merowalletv11.MainActivity.this, CategoriesActivity.class);
                startActivity(in0);


                /*
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CategoriesFragment()).commit();*/
                break;
            case R.id.nav_records:
                Intent in1 = new Intent(com.example.merowalletv11.MainActivity.this, RecordsActivity.class);
                startActivity(in1);

               /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new RecordsFragment()).commit();*/
                        break;
           /* case R.id.nav_reports:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ReportsFragment()).commit();
                        break;*/
            case R.id.nav_stats:
                Intent in3 = new Intent(com.example.merowalletv11.MainActivity.this, StatisticsActivity.class);
                startActivity(in3);
                /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new StatisticsFragment()).commit();*/
                        break;
            case R.id.nav_share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("Text/Plain");
                String shareBody = "Hey! Download Our App HERE: https://drive.google.com/open?id=10sZDWuw57pN2eklgDnp9RzuAvGUwzvHY";
                String shareSubject = "Mero Wallet";

                sharingIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);

                startActivity(Intent.createChooser(sharingIntent, "Share Using"));
                break;
            case R.id.nav_aboutus:
                Intent in4 = new Intent(com.example.merowalletv11.MainActivity.this, AboutUsActivity.class);
                startActivity(in4);
                break;
            case R.id.nav_logout:
                int status =0;
                MwDb.updateOnlineStatus(username,status);
                Intent in5 = new Intent(com.example.merowalletv11.MainActivity.this, LoginActivity.class);
                startActivity(in5);
                finish();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getBudget()
    {
        Cursor res = MwDb.getAllData();
        if(res.getCount()==0)
        {
            Toast.makeText(MainActivity.this,"Sign up empty",Toast.LENGTH_SHORT).show();
        }
        else {
            res.moveToFirst();

            do {

                String user = res.getString(3);
                if (username.equals(user)) {

                    budget = res.getDouble(9);
                    signupDate = res.getString(13);

                }
            } while (res.moveToNext());
        }
        res.close();
    }

    //Retriving from database values of card and cash expense
    public void getCashExpense1()
    {
        Cursor res = MwDb.getAllData();
        if(res.getCount()==0)
        {
            Toast.makeText(MainActivity.this,"Empty",Toast.LENGTH_SHORT).show();
        }
        else {
            res.moveToFirst();

            do {

                String user = res.getString(3);
                if (username.equals(user)) {

                    cashExpense = res.getDouble(10);

                }
            } while (res.moveToNext());
        }res.close();
    }

    public void getCardExpense1()
    {
        Cursor res = MwDb.getAllData();
        if(res.getCount()==0)
        {
            Toast.makeText(MainActivity.this,"Empty",Toast.LENGTH_SHORT).show();
        }
        else {
            res.moveToFirst();

            do {

                String user = res.getString(3);
                if (username.equals(user)) {

                    cardExpense = res.getDouble(11);

                }
            } while (res.moveToNext());
        }res.close();
    }

    public void getEmail()
    {
        Cursor res = MwDb.getAllData();
        if(res.getCount()==0)
        {
            Toast.makeText(MainActivity.this,"Empty",Toast.LENGTH_SHORT).show();
        }
        else {
            res.moveToFirst();

            do {

                String user = res.getString(3);
                if (username.equals(user)) {

                    email = res.getString(8);
                    imag = res.getString(14);



                }
            } while (res.moveToNext());
        }
        res.close();
    }

    public void getDataAnalysis(){

        Cursor res = MwDb.getExpenseTableData();
        if(res.getCount()==0)
        {
            //Toast.makeText(MainActivity.this,"Start your budget tracking",Toast.LENGTH_SHORT).show();
        }
        else {
            res.moveToFirst();

            do {


                String user = res.getString(1);
                String tableCategory = res.getString(4);
                Double tableAmount = Double.parseDouble(res.getString(2));
                String retrievedDate = res.getString(3);
                String retrievedYear = retrievedDate.substring(0,4);
                String retrievedMonth = retrievedDate.substring(5,7);
                String retrievedDay = retrievedDate.substring(8,10);


                if (username.equals(user)  ) {

                    for(int i=0; i<14;i++) {


                        if( (" "+categoryArray[i]).equals(" "+tableCategory) && Integer.parseInt(retrievedYear) <= thisYear && Integer.parseInt(retrievedMonth) <= thisMonth && Integer.parseInt(retrievedDay) < thisDay  ){
                            amountCategory[i] += tableAmount;
                            if(repeat[i] == 0){
                                repeat[i] = Integer.parseInt(retrievedDay);
                            }
                            else{
                                if(repeat[i] != Integer.parseInt(retrievedDay)){
                                    repeat[i] = Integer.parseInt(retrievedDay); //Date change bhako herna lai
                                    A[i]++; //Date change bhaye matra value update garna lai
                                }

                            }

                        }



                    }

                }

                //Toast.makeText(MainActivity.this,"check",Toast.LENGTH_SHORT).show();
            } while (res.moveToNext());
        }
        res.close();


        for(int i=0;i<14;i++){

            averageCategory[i] = amountCategory[i]/A[i];
        }












    }

    public static double getRemainingBudget(){
        return temp;
    }

    public static  String[] retCategoryList(){
        return categoryArray;
    }



   /* public static int getNumberOfDays()
    {
        return numberOfDays;
    }*/

    public static String[] getCategoryArray(){
        return categoryArray;
    }

    public static double[] getAverageCategory(){

        return averageCategory;
    }
}

