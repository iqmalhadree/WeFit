package com.wefit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //private BarChart chart;
    //private XAxis x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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