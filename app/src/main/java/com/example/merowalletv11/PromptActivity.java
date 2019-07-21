package com.example.merowalletv11;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PromptActivity extends AppCompatActivity {

    private static double budget=0;
    DatabaseHelper MDb;
    private static String username;
    private static String exp;
    private TextInputLayout edt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);
        username = LoginActivity.throwUsername();
        MDb = new DatabaseHelper(this);

        edt = findViewById(R.id.editTextDialogUserInput);
    }

    public void buttonBudget(View view) {

        if (!validateBudget()) {
            return;
        }


     /*   if (exp.equals("")) {
            Toast.makeText(this, "Field Can't Be Empty",Toast.LENGTH_SHORT).show();;
            return;
        }*/


            boolean isUpdate = MDb.updateBudget(username, budget);
            if (isUpdate == true) {
                Toast.makeText(PromptActivity.this, "Budget updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PromptActivity.this, "Budget update failed", Toast.LENGTH_SHORT).show();
            }


            Intent in = new Intent(PromptActivity.this, MainActivity.class);
            startActivity(in);

    }

        @Override
        public void onBackPressed () {
            finishAffinity();
            Intent in = new Intent(PromptActivity.this, MainActivity.class);
            startActivity(in);
            finish();
        }

    private boolean validateBudget() {

        String budgetInput = edt.getEditText().getText().toString().trim();
        if (budgetInput.matches("")) {
            edt.setError("Field Can't Be Empty");
            return false;
        } else {
            edt.setError(null);
            return true;
        }
    }

    /*public static double getBudget(){
    return budget;
    }*/



}
