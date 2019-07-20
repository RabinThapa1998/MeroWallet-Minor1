package com.example.merowalletv11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class RecordDetails extends AppCompatActivity {
   // TextView amount;
    //String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent in = new Intent(RecordDetails.this, RecordsActivity.class);
                startActivity(in);
                finish();
            }


        }

        );

        TextView test = (TextView) findViewById(R.id.amountRec);
        test.setText(activity_recordsview.getAMOUNT());

       // test = activity_recordsview.getAMOUNT();





       /* amount.findViewById(R.id.amountRec);
        amount.setText("dfghj");*/
    }


}
