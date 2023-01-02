package com.wefit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //private BarChart chart;
    //private XAxis x;

    private FloatingActionButton userInfoBtn;
    private TextView tv_cal;

    List<FoodModel> list = new ArrayList<FoodModel>();
    FoodModel todayFood = new FoodModel();
    CalorieDBHelper calDB = new CalorieDBHelper(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_cal = findViewById(R.id.calCount);

        showCalAmountResult();
        setChart();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
        case 1:
        {
            super.onRestart();//refresh parentAcitivty
            showCalAmountResult();
            break;
        }
        case 2:
        break;
        }
    }

    private void showCalAmountResult() {
        List<FoodModel> list = new ArrayList<FoodModel>();
        CalorieDBHelper calDB = new CalorieDBHelper(MainActivity.this);

        list = calDB.sumCal();
        tv_cal.setText(String.valueOf(list.get(1).getCalAmount()));
    }

    public void setChart(){
        BarChart chart = findViewById(R.id.chart_bar);

        BarDataSet barDataSet1 = new BarDataSet(values(), "DataSet 1");

        BarData barData = new BarData();
        barData.addDataSet(barDataSet1);
        barData.setBarWidth(0.3f);
        barData.setValueTextColor(R.color.darkbrown);
        barDataSet1.setColor(R.color.darkbrown);
        barDataSet1.setDrawValues(false);

        chart.setDrawBorders(false);
        chart.setDescription(null);
        chart.setGridBackgroundColor(R.color.peach);
        chart.setDrawValueAboveBar(false);
        chart.setDrawBorders(false);
        chart.getLegend().setEnabled(false);

        XAxis xAxis= chart.getXAxis();
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(false);

        YAxis yl = chart.getAxisLeft();
        YAxis yr = chart.getAxisRight();

        yl.setDrawAxisLine(false);
        yr.setDrawAxisLine(false);
        yr.setDrawLabels(false);

        chart.setData(barData);
        chart.invalidate();
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);
    }

    public void openUserAct(View v){
        Intent intent = new Intent(this, AddInfo.class);
        startActivity(intent);
    }

    public void addCalIntake(View v){
        Intent intent = new Intent(this, AddCalIntake.class);
        startActivityForResult(intent, 1);
    }

    private ArrayList<BarEntry> values(){
        ArrayList<BarEntry> dataValues = new ArrayList<>();

        dataValues.add(new BarEntry(0, 300));
        dataValues.add(new BarEntry(1, 350));
        dataValues.add(new BarEntry(2,400));
        dataValues.add(new BarEntry(3,250));
        dataValues.add(new BarEntry(4,400));
        dataValues.add(new BarEntry(5,300));
        dataValues.add(new BarEntry(6,450));
        return dataValues;
    }
}