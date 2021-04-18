package com.example.olio_project;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        mChart = (LineChart) findViewById(R.id.Linechart);
        //   mChart.setOnChartGestureListener(MainActivity.this);
        //   mChart.setOnChartValueSelectedListener(MainActivity.this);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);

        ArrayList<Entry> yValues = new ArrayList<>();
        yValues.add(new Entry(0,40f));
        yValues.add(new Entry(1,50f));
        yValues.add(new Entry(2,20f));
        yValues.add(new Entry(3,70f));

        LineDataSet set1 = new LineDataSet(yValues,"Data set 1");

        set1.setFillAlpha(110);
        set1.setColor(Color.RED);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new  LineData(dataSets);

        mChart.setData(data);


    }
}