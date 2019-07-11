package com.lixiaomi.baselibapplication.utils.MPAdnroidChartUtils;

import android.graphics.Color;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.List;

/**
 * Description: <br>
 *
 * @author : dell - XiaomiLi<br>
 * Date: 2018-09-10<br>
 * Time: 13:54<br>
 * UpdateDescription：<br>
 */
public class PieRadarChartManager {
    PieChart mPiechart;

    public PieRadarChartManager(PieChart pieChart) {
        mPiechart = pieChart;
        init();
        initLegend();
    }

    private void init() {
        //设置X轴的动画
//        mPiechart.animateX(1400);
        //使用百分比
        mPiechart.setUsePercentValues(false);
        //是否为空心
        mPiechart.setDrawHoleEnabled(true);
        //空心颜色
        mPiechart.setHoleColor(Color.WHITE);
        //透明区域颜色
        mPiechart.setTransparentCircleColor(Color.WHITE);
        //透明度应该是
        mPiechart.setTransparentCircleAlpha(110);
        //设置圆孔半径
        mPiechart.setHoleRadius(50f);
        //设置半透明圈的宽度
        mPiechart.setTransparentCircleRadius(20f);
        mPiechart.getLegend().setWordWrapEnabled(false);
        Description description = new Description();
        description.setText("");
        mPiechart.setDescription(description);
        //设置是否可转动
        mPiechart.setRotationEnabled(true);
        //设置不显示扇形上的文字
        mPiechart.setDrawSliceText(false);
    }

    private void initLegend() {
        Legend l = mPiechart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setTextColor(Color.RED);
        l.setDrawInside(false);
        l.setWordWrapEnabled(true);
        //设置是否启用图列
        l.setEnabled(true);
        //设置图列标识的大小
        l.setFormSize(14);
        //设置图列标识文字的大小
        l.setTextSize(14);
    }

    public void setMDescription(String text) {
        Description description = new Description();
        description.setText(text);
        description.setTextColor(Color.RED);
        mPiechart.setDescription(description);
        mPiechart.invalidate();
    }

    public void setData(List<PieEntry> entrys, List<Integer> colors) {
        //饼图数据集
        PieDataSet dataset = new PieDataSet(entrys, "");
        //饼图Item被选中时变化的距离
        dataset.setSelectionShift(10f);
        //颜色填充
        dataset.setColors(colors);
        dataset.setValueLineColor(Color.parseColor("#a1a1a1"));
        dataset.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataset.setValueLinePart1OffsetPercentage(80.f);
        dataset.setValueLinePart1Length(0.2f);
        dataset.setValueLinePart2Length(0.4f);
        setPieData(dataset);
    }

    private void setPieData(PieDataSet dataset) {
        //数据填充
        PieData piedata = new PieData();
        piedata.setDataSet(dataset);
        piedata.setValueFormatter(new PercentFormatter());
        piedata.setValueTextSize(11f);
        piedata.setValueTextColor(Color.BLACK);
        //设置饼图上显示数据的字体大小
        piedata.setValueTextSize(15);

        mPiechart.setData(piedata);
        invalidate();
    }

    public void invalidate() {
        mPiechart.invalidate();
    }

}
