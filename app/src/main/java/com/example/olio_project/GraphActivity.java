package com.example.olio_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Button calorieButton;
    Button emissionButton;
    int userIndex;
    private LineChart mChart;

    int monCal;
    int tueCal;
    int wedCal;
    int thurCal;
    int friCal;
    int satCal;
    int sunCal;

    double monEmi;
    double tueEmi;
    double wedEmi;
    double thurEmi;
    double friEmi;
    double satEmi;
    double sunEmi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Intent intent = getIntent();
        Week week = (Week)intent.getExtras().get("currentWeek");
        userIndex = (int)intent.getExtras().get("Index");

        monCal = (int)week.getDay(0).getDaysCalories();
        tueCal = (int)week.getDay(1).getDaysCalories();
        wedCal = (int)week.getDay(2).getDaysCalories();
        thurCal = (int)week.getDay(3).getDaysCalories();
        friCal = (int)week.getDay(4).getDaysCalories();
        satCal = (int)week.getDay(5).getDaysCalories();
        sunCal = (int)week.getDay(6).getDaysCalories();

        monEmi = week.getDay(0).getDaysEmissions()*1000;
        tueEmi = week.getDay(1).getDaysEmissions()*1000;
        wedEmi = week.getDay(2).getDaysEmissions()*1000;
        thurEmi = week.getDay(3).getDaysEmissions()*1000;
        friEmi = week.getDay(4).getDaysEmissions()*1000;
        satEmi = week.getDay(5).getDaysEmissions()*1000;
        sunEmi = week.getDay(6).getDaysEmissions()*1000;

        ArrayList<String> xAxisLabel = new ArrayList<>();
        xAxisLabel.add("Monday");
        xAxisLabel.add("Tuesday");
        xAxisLabel.add("Wednesday");
        xAxisLabel.add("Thursday");
        xAxisLabel.add("Friday");
        xAxisLabel.add("Saturday");
        xAxisLabel.add("Sunday");

        mChart = (LineChart) findViewById(R.id.Linechart);
        //   mChart.setOnChartGestureListener(MainActivity.this);
        //   mChart.setOnChartValueSelectedListener(MainActivity.this);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setDrawLabels(false);

        System.out.println(monCal+" | "+tueCal+" | "+wedCal+" | "+thurCal+" | "+friCal+" | "+satCal+" | "+sunCal);

        mChart.setData(setNewData(monCal,tueCal,wedCal,thurCal,friCal,satCal,sunCal,monEmi,tueEmi,wedEmi,thurEmi,friEmi,satEmi,sunEmi));
    }

    public LineData setNewData(int monCal, int tueCal, int wedCal, int thurCal, int friCal, int satCal, int sunCal, double monEmi, double tueEmi, double wedEmi, double thurEmi, double friEmi, double satEmi, double sunEmi){
        ArrayList<Entry> yCalValues = new ArrayList<>();
        ArrayList<Entry> yEmiValues = new ArrayList<>();

        yCalValues.add(new Entry(0,monCal));
        yCalValues.add(new Entry(1,tueCal));
        yCalValues.add(new Entry(2,wedCal));
        yCalValues.add(new Entry(3,thurCal));
        yCalValues.add(new Entry(4,friCal));
        yCalValues.add(new Entry(5,satCal));
        yCalValues.add(new Entry(6,sunCal));

        yEmiValues.add(new Entry(0,(float)monEmi));
        yEmiValues.add(new Entry(1,(float)tueEmi));
        yEmiValues.add(new Entry(2,(float)wedEmi));
        yEmiValues.add(new Entry(3,(float)thurEmi));
        yEmiValues.add(new Entry(4,(float)friEmi));
        yEmiValues.add(new Entry(5,(float)satEmi));
        yEmiValues.add(new Entry(6,(float)sunEmi));

        LineDataSet set1 = new LineDataSet(yCalValues,"Calories kCal");
        LineDataSet set2 = new LineDataSet(yEmiValues,"Emissions g CO2");

        set1.setFillAlpha(110);
        set1.setColor(Color.RED);
        set1.setValueTextSize(20);
        set1.setCircleRadius(6);

        set2.setFillAlpha(110);
        set2.setColor(Color.BLUE);
        set2.setValueTextSize(20);
        set1.setCircleRadius(6);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);

        LineData data = new  LineData(dataSets);
        return data;
    }
}