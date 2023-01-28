package com.wefit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddCalIntake extends AppCompatActivity {

    private EditText et_foodName;
    private EditText et_calAmount;
    private Button submit;
    private Button  dummySubmit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cal_intake);

        et_foodName = findViewById(R.id.inputFoodName);
        et_calAmount = findViewById(R.id.inputCal);
        submit = findViewById(R.id.addCalBtn);
        dummySubmit = findViewById(R.id.buttonDummy);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCal();
                Intent intent = new Intent(AddCalIntake.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        dummySubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCalDummy();
            }
        });
    }


    public void setCal(){
        CalorieDBHelper myDB = new CalorieDBHelper(AddCalIntake.this);
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatterDay = new SimpleDateFormat("EEEE");


        myDB.addCal(
                et_foodName.getText().toString(),
                Integer.valueOf(et_calAmount.getText().toString()),
                String.valueOf(formatterDate.format(date)),
                formatterDay.format(date)
        );
    }
    public void setCalDummy(){
        CalorieDBHelper myDB = new CalorieDBHelper(AddCalIntake.this);
        myDB.addCal("Burger", 50, "2023-01-24", "Tuesday");
        myDB.addCal("Burger", 100, "2023-01-24", "Tuesday");
        myDB.addCal("Burger", 150, "2023-01-23", "Monday");
        myDB.addCal("Burger", 200, "2023-01-23", "Monday");
        myDB.addCal("Burger", 400, "2023-01-22", "Sunday");
        myDB.addCal("Burger", 500, "2023-01-22", "Sunday");
        myDB.addCal("Burger", 600, "2022-01-21", "Saturday");
        myDB.addCal("Burger", 50, "2022-01-21", "Saturday");
        myDB.addCal("Burger", 100, "2023-01-20", "Friday");
        myDB.addCal("Burger", 150, "2023-01-20", "Friday");

    }
}