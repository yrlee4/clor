package org.techtown.diary;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Fragment3 extends AppCompatActivity {
    PieChart chart;
    BarChart chart2;

    Context context;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment3);

        chart = findViewById(R.id.chart1);
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);

        chart.setCenterText("색상조합 선택 비율");

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(true);

        chart.setHighlightPerTapEnabled(true);


        Legend legent1 = chart.getLegend();
        legent1.setEnabled(false);

        chart.setEntryLabelColor(Color.WHITE);
        chart.setEntryLabelTextSize(12f);

        setData1();


        chart2 = findViewById(R.id.chart2);
        chart2.setDrawValueAboveBar(true);

        chart2.getDescription().setEnabled(false);
        chart2.setDrawGridBackground(false);

        XAxis xAxis = chart2.getXAxis();
        xAxis.setEnabled(false);

        YAxis leftAxis = chart2.getAxisLeft();
        leftAxis.setLabelCount(6, false);
        leftAxis.setAxisMinimum(0.0f);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setGranularity(1f);


        YAxis rightAxis = chart2.getAxisRight();
        rightAxis.setEnabled(false);

        Legend legend2 = chart2.getLegend();
        legend2.setEnabled(false);

        chart2.animateXY(1500, 1500);
    }


    private void setData1() {
        ArrayList<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(20.0f, "",
                getResources().getDrawable(R.drawable.smile1_24)));
        entries.add(new PieEntry(20.0f, "",
                getResources().getDrawable(R.drawable.smile2_24)));
        entries.add(new PieEntry(20.0f, "",
                getResources().getDrawable(R.drawable.smile3_24)));
        entries.add(new PieEntry(20f, "",
                getResources().getDrawable(R.drawable.smile4_24)));
        entries.add(new PieEntry(20.0f, "",
                getResources().getDrawable(R.drawable.smile5_24)));

        PieDataSet dataSet = new PieDataSet(entries, "색상 조합 선택 비율");

        dataSet.setDrawIcons(true);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, -40));
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.JOYFUL_COLORS) {
            colors.add(c);
        }
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(22.0f);
        data.setValueTextColor(Color.WHITE);

        chart.setData(data);
        chart.invalidate();
    }

    private void setData2() {

        ArrayList<BarEntry> entries = new ArrayList<>();

        entries.add(new BarEntry(1.0f, 20.0f,
                getResources().getDrawable(R.drawable.smile1_24)));
        entries.add(new BarEntry(2.0f, 40.0f,
                getResources().getDrawable(R.drawable.smile2_24)));
        entries.add(new BarEntry(3.0f, 60.0f,
                getResources().getDrawable(R.drawable.smile3_24)));
        entries.add(new BarEntry(4.0f, 30.0f,
                getResources().getDrawable(R.drawable.smile4_24)));
        entries.add(new BarEntry(5.0f, 90.0f,
                getResources().getDrawable(R.drawable.smile5_24)));

        BarDataSet dataSet2 = new BarDataSet(entries, "Sinus Function");
        dataSet2.setColor(Color.rgb(240, 120, 124));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.JOYFUL_COLORS) {
            colors.add(c);
        }
        dataSet2.setColors(colors);
        dataSet2.setIconsOffset(new MPPointF(0, -10));

        BarData data = new BarData(dataSet2);
        data.setValueTextSize(10f);
        data.setDrawValues(false);
        data.setBarWidth(0.8f);

        chart2.setData(data);
        chart2.invalidate();
    }

}
