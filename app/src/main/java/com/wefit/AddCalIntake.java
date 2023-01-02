package com.wefit;

import androidx.appcompat.app.AppCompatActivity;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cal_intake);

        et_foodName = findViewById(R.id.inputFoodName);
        et_calAmount = findViewById(R.id.inputCal);
        submit = findViewById(R.id.addCalBtn);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCal();
                finish();
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
}