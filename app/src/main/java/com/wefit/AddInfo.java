package com.wefit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class AddInfo extends AppCompatActivity {

    int age, weight, height;

    EditText ti_age;
    EditText ti_weight;
    EditText ti_height;
    EditText ti_name;
    RadioGroup active;
    RadioGroup gender;
    RadioButton degActive;
    RadioButton typeGender;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        ti_age = findViewById(R.id.userAgeInput);
        ti_weight = findViewById(R.id.userWeightInput);
        ti_height = findViewById(R.id.userHeightInput);
        ti_name = findViewById(R.id.userNameInput);
        gender = findViewById(R.id.radioGender);
        active = findViewById(R.id.radioActive);

        Button buttonSubmit = findViewById(R.id.userSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUser();
                finish();
            }
        });
    }

    public void setUser(){
        typeGender = findViewById(gender.getCheckedRadioButtonId());
        degActive = findViewById(active.getCheckedRadioButtonId());

        UserDBHelper myDB = new UserDBHelper(AddInfo.this);
        myDB.addUser(
                    ti_name.getText().toString(),
                    Integer.valueOf(ti_age.getText().toString()),
                    Integer.valueOf(ti_height.getText().toString()),
                    Integer.valueOf(ti_weight.getText().toString()),
                    typeGender.getText().toString(),
                    degActive.getText().toString()
            );

    }
}