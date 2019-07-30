package com.lixiaomi.baselibapplication.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import com.lixiaomi.baselibapplication.R;
import com.lixiaomi.baselibapplication.ui.baseui.XMBaseActivity;
import com.lixiaomi.baselibapplication.utils.MPAdnroidChartUtils.BarLineChartManager;
import com.lixiaomi.baselibapplication.utils.MPAdnroidChartUtils.PieRadarChartManager;
import com.lixiaomi.mvplib.base.BasePresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description:MPAdnroidChart <br>
 *
 * @author : dell - XiaomiLi<br>
 * Date: 2018-07-27<br>
 * Time: 16:50<br>
 * UpdateDescription：<br>
 */
public class MPChartActivity extends XMBaseActivity {
    Button mButton;
    LineChart lineChart;
    BarChart mBarChart;
    PieChart mPiechart;
    ArrayList<Integer> mLineChartDataList = new ArrayList<>();
    ArrayList<String> mLineChartNameList = new ArrayList<>();
    private boolean haveData = false;

    @Override
    protected Object setLayout() {
        return R.layout.activity_mpchart;
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        mButton = findViewById(R.id.make_data);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!haveData) {
                    makeData();
                }
                initLine();
                initBar();
                initPie();
            }
        });

        lineChart = findViewById(R.id.chart_line);
        mBarChart = findViewById(R.id.chart_bar);
        mPiechart = findViewById(R.id.chart_pie);

        lineChart.setNoDataText("暂无数据");
        mBarChart.setNoDataText("暂无数据");
        mPiechart.setNoDataText("暂无数据");

        lineChart.setNoDataTextColor(R.color.warning_color1);
        mBarChart.setNoDataTextColor(R.color.warning_color2);
        mPiechart.setNoDataTextColor(R.color.warning_color3);

    }

    private void initPie() {
        initView();
    }

    /**
     * 初始化图表
     */
    private void initView() {
        PieRadarChartManager pieRadarChartManager = new PieRadarChartManager(mPiechart);
        //设置标题
        List<PieEntry> entrys = new ArrayList<>();
        List<Integer> colors = new ArrayList<>(Arrays.asList(Color.rgb(0, 255, 255),
                Color.rgb(60, 179, 113),
                Color.rgb(255, 165, 0),
                Color.rgb(124, 252, 0),
                Color.rgb(255, 182, 193)));
        int sum = 0;
        for (int i = 0; i < 5; i++) {
            sum += mLineChartDataList.get(i);
        }
        entrys.add(new PieEntry((float) mLineChartDataList.get(0)/sum, mLineChartNameList.get(0)));
        entrys.add(new PieEntry((float) mLineChartDataList.get(1)/sum, mLineChartNameList.get(1)));
        entrys.add(new PieEntry((float) mLineChartDataList.get(2)/sum, mLineChartNameList.get(2)));
        entrys.add(new PieEntry((float) mLineChartDataList.get(3)/sum, mLineChartNameList.get(3)));
        entrys.add(new PieEntry((float) mLineChartDataList.get(4)/sum, mLineChartNameList.get(4)));
        pieRadarChartManager.setData(entrys, colors);
    }


    private void initLine() {
        ArrayList<String> time = new ArrayList<>();
        //显示边界
        lineChart.setDrawBorders(true);
        //设置数据
        List<Entry> entrie = new ArrayList<>();
        int size = mLineChartDataList.size();
        for (int i = 0; i < size; i++) {
            entrie.add(new Entry(i, (float) mLineChartDataList.get(i)));
            time.add(mLineChartNameList.get(i));
        }
        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(entrie, "折线图");
        //线模式为圆滑曲线（默认折线）
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setValueTextSize(11);
        LineData data = new LineData(lineDataSet);

        BarLineChartManager barLineChartManagerCod = new BarLineChartManager(lineChart);
        barLineChartManagerCod.setXAxis(time, getResources().getColor(R.color.default_color));
        barLineChartManagerCod.setYAxis();
        barLineChartManagerCod.setMDescription("");
        barLineChartManagerCod.setLegend(false);

        lineChart.setData(data);
        lineChart.invalidate();
    }

    private void initBar() {
        ArrayList<String> time = new ArrayList<>();
        ArrayList<BarEntry> entries = new ArrayList<>();
        int size = mLineChartDataList.size();
        for (int i = 0; i < size; i++) {
            entries.add(new BarEntry(i, (float) mLineChartDataList.get(i)));
            time.add(mLineChartNameList.get(i));
        }

        // 每一个BarDataSet代表一类柱状图
        BarDataSet barDataSet = new BarDataSet(entries, "柱状图");

        barDataSet.setColor(getResources().getColor(R.color.default_color));
        barDataSet.setValueTextSize(9f);
        barDataSet.setFormLineWidth(1f);
        barDataSet.setFormSize(15.f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);
        BarData data = new BarData(dataSets);

        BarLineChartManager barLineChartManager = new BarLineChartManager(mBarChart);
        barLineChartManager.setLegend(false);
        barLineChartManager.setMDescription("");
        barLineChartManager.setYAxis();
        barLineChartManager.setXAxis(time, getResources().getColor(R.color.default_color));

        mBarChart.setPinchZoom(false);
        mBarChart.setData(data);
        mBarChart.invalidate();
    }


    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected int setStatusBarColor() {
        return R.color.default_color;
    }

    private void makeData() {

        mLineChartDataList.add(0);
        mLineChartDataList.add(50);
        mLineChartDataList.add(73);
        mLineChartDataList.add(22);
        mLineChartDataList.add(100);
        mLineChartDataList.add(120);
        mLineChartDataList.add(152);
        mLineChartDataList.add(133);
        mLineChartDataList.add(155);
        mLineChartDataList.add(85);
        mLineChartDataList.add(79);
        mLineChartDataList.add(35);
        mLineChartDataList.add(53);
        mLineChartDataList.add(0);

        mLineChartNameList.add("09-01");
        mLineChartNameList.add("09-02");
        mLineChartNameList.add("09-03");
        mLineChartNameList.add("09-04");
        mLineChartNameList.add("09-05");
        mLineChartNameList.add("09-06");
        mLineChartNameList.add("09-07");
        mLineChartNameList.add("09-08");
        mLineChartNameList.add("09-09");
        mLineChartNameList.add("09-10");
        mLineChartNameList.add("09-11");
        mLineChartNameList.add("09-12");
        mLineChartNameList.add("09-13");
        mLineChartNameList.add("09-14");
        haveData = true;
    }

}
