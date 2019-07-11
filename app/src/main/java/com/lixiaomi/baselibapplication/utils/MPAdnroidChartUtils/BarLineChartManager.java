package com.lixiaomi.baselibapplication.utils.MPAdnroidChartUtils;

import android.graphics.Color;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

/**
 * Description: <br>
 *
 * @author : dell - XiaomiLi<br>
 * Date: 2018-08-07<br>
 * Time: 14:26<br>
 * UpdateDescription：<br>
 */
public class BarLineChartManager {
    BarLineChartBase mBarLineChartBase;

    public BarLineChartManager(BarLineChartBase barLineChartBase) {
        this.mBarLineChartBase = barLineChartBase;
    }

    public void setXAxis(ArrayList<String> time, int color) {
        XAxis xAxis = mBarLineChartBase.getXAxis();
        xAxis.setGridColor(color);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(time.size());
        xAxis.setValueFormatter(new IndexAxisValueFormatter(time));
        xAxis.setTextSize(11);
        xAxis.setLabelRotationAngle(45);
        //设置最小间隔，防止当放大时出现重复标签
        xAxis.setGranularity(1f);
        //设置一页最大显示个数为6，超出部分就滑动
        float ratio = (float) time.size() / (float) 6;
        //显示的时候是按照多大的比率缩放显示,1f表示不放大缩小
        mBarLineChartBase.zoom(ratio, 1f, 0, 0);
        mBarLineChartBase.invalidate();
    }

    public void setLegend(boolean enable) {
        //折线图例 标签 设置
        Legend legend = mBarLineChartBase.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(11f);
        //显示位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setEnabled(enable);
        mBarLineChartBase.invalidate();
    }

    public void setMDescription(String text) {
        Description description = new Description();
        description.setText(text);
        description.setTextColor(Color.RED);
        mBarLineChartBase.setDescription(description);
        mBarLineChartBase.invalidate();
    }

    public void setYAxis() {
        YAxis leftYAxisAn = mBarLineChartBase.getAxisLeft();
        YAxis rightYAxisAn = mBarLineChartBase.getAxisRight();
        rightYAxisAn.setEnabled(false);
        leftYAxisAn.setSpaceTop(30);
        mBarLineChartBase.invalidate();
    }
}
