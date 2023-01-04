package com.wefit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView tv_cal;
    private TextView tv_todayDate;
    private TextView test;
    private String dateParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assignWidget();

        DisplayDateToday();
        dateParam = setDateToday();

        try {
            showCalAmountResult(dateParam);
            setChart();
        }catch (Exception e){
            //ignore
        }

    }

    private void assignWidget() {
        setContentView(R.layout.activity_main);
        tv_cal = findViewById(R.id.calCount);
        tv_todayDate = findViewById(R.id.dateToday);
        test = findViewById(R.id.test);
    }

    private void DisplayDateToday() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("dd MMM yyyy");
        String showToday = String.valueOf(f.format(date));
        tv_todayDate.setText(showToday);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1: {
                onRestart();
                showCalAmountResult(dateParam);
                break;
            }
            case 2:
                break;
        }
    }

    private String setDateToday(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
        return String.valueOf(formatterDate.format(date));
    }

    private void showCalAmountResult(String date) {
        CalorieDBHelper calDB = new CalorieDBHelper(MainActivity.this);
        tv_cal.setText(String.valueOf(calDB.sumCal(date).get(0).getCalAmount()));
    }

    public void setChart(){
        BarChart chart = findViewById(R.id.chart_bar);

        BarDataSet barDataSet1 = new BarDataSet(valuesChart(), "DataSet 1");

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

    private ArrayList<BarEntry> valuesChart(){
        ArrayList<BarEntry> dataValues = new ArrayList<>();
        CalorieDBHelper calDB = new CalorieDBHelper(MainActivity.this);

        String[] dateWeek = getDateWeek();
        test.setText(dateWeek[5]);

        for(int i=0; i<7; i++){
            if (dateWeek[i] != null) {
                dataValues.add(new BarEntry(i, Integer.valueOf(calDB.sumCal(dateWeek[i]).get(0).getCalAmount())));
            }
            else {
                dataValues.add(new BarEntry(i, 0));
            }
        }
        return dataValues;
    }

    private String[] getDateWeek(){
        Date date = new Date();
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");

        c1.setTime(date);
        int i = c1.get(Calendar.DAY_OF_WEEK) - c1.getFirstDayOfWeek();
        c1.add(Calendar.DATE, -i);
        Date lastWeekDate = c1.getTime();
        c2.setTime(lastWeekDate);

        String[] allDate = new String[7];

        allDate[0] = String.valueOf(formatterDate.format(lastWeekDate));
        for(int j=0; j<i; j++){
            c2.add(Calendar.DATE, 1);
            Date newDate = new Date();
            newDate = c2.getTime();
            allDate[j] = String.valueOf(formatterDate.format(newDate));
        }

        return allDate;
    }
}