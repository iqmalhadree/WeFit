package com.wefit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewInfo extends AppCompatActivity {

    MyDBHelper myDBHelper = new MyDBHelper(ViewInfo.this);
    User user = new User();
    TextView nameOutput, ageOutput, genderOutput, fitnessOutput, weightOutput, heightOutput, goalOutput, goalInput;
    Button edit, setGoals;
    int goalVal;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_info);

        edit = findViewById(R.id.userEdit);
        setGoals = findViewById(R.id.setGoal);

        nameOutput = findViewById(R.id.userNameOutput);
        ageOutput = findViewById(R.id.userAgeOutput);
        genderOutput = findViewById(R.id.userGenderOutput);
        fitnessOutput = findViewById(R.id.userFitnessOutput);
        weightOutput = findViewById(R.id.userWeightOutput);
        heightOutput = findViewById(R.id.userHeightOutput);
        nameOutput = findViewById(R.id.userNameOutput);
        goalOutput = findViewById(R.id.weightGoalOutput);
        goalInput = findViewById(R.id.weightGoalInput);

        user = myDBHelper.extractUserDB();

        nameOutput.setText(user.getUserName());
        ageOutput.setText(String.valueOf(user.getUserAge()));
        genderOutput.setText(user.getUserGender());
        fitnessOutput.setText(user.getUserFitness());
        weightOutput.setText(String.valueOf(user.getUserWeight()) + " kg");
        heightOutput.setText(String.valueOf(user.getUserHeight()) + " m");
        nameOutput.setText(user.getUserName());

        if(myDBHelper.noGoal()==true){
            goalOutput.setText("No information");
        }
        else{
            goalVal = myDBHelper.extractUserGoal();
            goalOutput.setText(String.valueOf(goalVal) + " kg");
        }

        setGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goalVal = Integer.valueOf(goalInput.getText().toString());
                String id = user.getUserName();
                myDBHelper.addGoal(id, goalVal);
                finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDBHelper.deleteRows();
                Intent intent = new Intent(ViewInfo.this, AddInfo.class);
                startActivity(intent);
                finish();
            }
        });
    }
}