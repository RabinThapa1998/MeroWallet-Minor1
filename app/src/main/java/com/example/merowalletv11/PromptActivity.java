package com.example.merowalletv11;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PromptActivity extends AppCompatActivity {

    private static double budget=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);
    }

    @Override
    public void onBackPressed() {

        finishAffinity();
        Intent in = new Intent(PromptActivity.this, MainActivity.class);
        startActivity(in);
        finish();
    }

    public void buttonBudget(View view) {

        EditText edt=findViewById(R.id.editTextDialogUserInput);
        budget=Double.parseDouble(edt.getText().toString());
        finishAffinity();
        Intent in = new Intent(PromptActivity.this, MainActivity.class);
        startActivity(in);
    }

    public static double getBudget(){
    return budget;
    }


}
