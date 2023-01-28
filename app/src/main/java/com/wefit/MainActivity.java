package com.wefit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView tv_cal;
    private TextView tv_todayDate;
    private ProgressBar progressBar;
    private String dateParam;
    User user = new User();
    CalorieDBHelper calorieDBHelper = new CalorieDBHelper(MainActivity.this);
    MyDBHelper myDBHelper = new MyDBHelper(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assignWidget();
        dateParam = setDateToday();

        checkUserDB();
        try {
            DisplayDateToday();
            getUserDB();
            setBarProgress(user);
            showCalAmountResult(dateParam);
            setChart();

        }catch (Exception e){
            //ignore
        }

    }

    private void checkUserDB(){
        if(myDBHelper.noTable()==true){
            Intent intent = new Intent(this, AddInfo.class);
            startActivity(intent);
        }
    }

    private void getUserDB() {
        user = myDBHelper.extractUserDB();
    }

    private void setBarProgress(User user) {
        String gender = this.user.getUserGender();
        String fitness = this.user.getUserFitness();
        double BMR = 0;
        double calNeed = 0;
        double progress;
        int progress_int;
        
        if(gender.equalsIgnoreCase("female") == true){
            BMR =  655.1 + (9.563 * user.getUserWeight()) + (1.850 * user.getUserHeight()) - (4.676 * user.getUserAge());}
        else if(gender.equalsIgnoreCase("male") == true){
            BMR = 66.5 + (13.75 * user.getUserWeight()) + (5.003 * user.getUserHeight()) - (6.75 * user.getUserAge());}
        else {
            Toast.makeText(this, "User Info has not been set", Toast.LENGTH_SHORT).show();}

        if(fitness.equalsIgnoreCase("extra active") == true){
            calNeed = 1.9 * BMR;}
        else if(fitness.equalsIgnoreCase("very active") == true){
            calNeed = 1.725 * BMR;}
        else if(fitness.equalsIgnoreCase("moderately active") == true){
            calNeed = 1.55 * BMR;}
        else if(fitness.equalsIgnoreCase("lightly active") == true){
            calNeed = 1.375 * BMR;}
        else if(fitness.equalsIgnoreCase("passive") == true){
            calNeed = 1.2 * BMR;}
        else{
            Toast.makeText(this, "No information on Fitness Level", Toast.LENGTH_SHORT).show();}
        int goalVal = myDBHelper.extractUserGoal();
        final double calPerDay = 7700/30;
        double loseCal = goalVal * calPerDay;
        calNeed = calNeed - loseCal;
        progress = calorieDBHelper.sumCal(dateParam).get(0).getCalAmount()/calNeed * 100;
        progress_int = (int)Math.round(progress);
        progressBar.setProgress(progress_int);
    }

    private void assignWidget() {
        setContentView(R.layout.activity_main);
        tv_cal = findViewById(R.id.calCount);
        tv_todayDate = findViewById(R.id.dateToday);
        progressBar = findViewById(R.id.progressBar);
    }

    private void DisplayDateToday() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("dd MMM yyyy");
        String showToday = f.format(date);
        tv_todayDate.setText(showToday);
    }

    private String setDateToday(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
        return formatterDate.format(date);
    }

    private void showCalAmountResult(String date) {
        tv_cal.setText(String.valueOf(calorieDBHelper.sumCal(date).get(0).getCalAmount()));
    }

    public void setChart(){
        BarChart chart = findViewById(R.id.chart_bar);

        BarDataSet barDataSet1 = new BarDataSet(valuesChart(), "DataSet 1");

        BarData barData = new BarData();
        barData.addDataSet(barDataSet1);
        barData.setBarWidth(0.3f);
        barData.setValueTextColor(R.color.darkbrown);
        barDataSet1.setColor(Color.parseColor("#F4991A"));
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
        xAxis.setDrawLabels(true);
        final String[] weekdays = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
        xAxis.setValueFormatter(new IndexAxisValueFormatter(weekdays));

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
        MyDBHelper db = new MyDBHelper(MainActivity.this);
        if(db.noTable()==true){
            Intent intent = new Intent(MainActivity.this, AddInfo.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(MainActivity.this, ViewInfo.class);
            startActivity(intent);
        }
    }

    public void addCalIntake(View v){
        Intent intent = new Intent(this, AddCalIntake.class);
        startActivity(intent);
    }

    public void scanFoodActivity(View v){
        Intent intent = new Intent(this, ScanFood.class);
        startActivity(intent);
    }

    private ArrayList<BarEntry> valuesChart(){
        ArrayList<BarEntry> dataValues = new ArrayList<>();
        String[] dateWeek = getDateWeek();
        for(int i=0; i<7; i++){
            if (calorieDBHelper.sumCal(dateWeek[i]).isEmpty() == true) {
                dataValues.add(new BarEntry(i, 0));
            }
            else {
                dataValues.add(new BarEntry(i, Integer.valueOf(calorieDBHelper.sumCal(dateWeek[i]).get(0).getCalAmount())));
            }
        }
        return dataValues;
    }

    private String[] getDateWeek(){
        Date date = new Date();
        int k;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");

        c1.setTime(date);
        int i = c1.get(Calendar.DAY_OF_WEEK) - c1.getFirstDayOfWeek();
        if(i==0){
            c1.add(Calendar.DATE, -6);
            i=7;
            k = 1;
        }
        else{
            c1.add(Calendar.DATE, -i);
            k = 0;
        }
        Date lastWeekDate = c1.getTime();
        c2.setTime(lastWeekDate);

        String[] allDate = new String[7];

        allDate[0] = formatterDate.format(lastWeekDate);
        for(int j = k; j<i; j++){
            c2.add(Calendar.DATE, 1);
            Date newDate;
            newDate = c2.getTime();
            allDate[j] = formatterDate.format(newDate);
        }
        return allDate;
    }
}