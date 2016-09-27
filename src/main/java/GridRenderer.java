import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYPolygonAnnotation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.PaintScale;
import org.jfree.chart.renderer.xy.XYBlockRenderer;
import org.jfree.chart.title.PaintScaleLegend;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYZDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import javax.swing.*;

import interpolation.Point2D;
import interpolation.Polygon;

/**
 * Created by alsm0813 on 26.09.2016.
 */
public class GridRenderer {

    public void render(Map<Point2D, Double> grid, Polygon polygon)
    {
        JFrame f = new JFrame("Grid");
        double minHeight = Collections.min(grid.values(), Double::compare);
        double maxHeight = Collections.max(grid.values(), Double::compare);
        XYZDataset dataset = createDataset(grid);
        JFreeChart chart = createChart(dataset, polygon, minHeight, maxHeight);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(640, 480));
        chartPanel.setMouseZoomable(true, false);
        f.add(chartPanel);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    private JFreeChart createChart(XYDataset dataset, Polygon polygon, double minHeight, double maxHeight) {
        NumberAxis xAxis = new NumberAxis("x Axis");
        /*xAxis.setAutoRange(false);
        xAxis.setRange(0, N);*/
        NumberAxis yAxis = new NumberAxis("y Axis");
        /*yAxis.setAutoRange(false);
        yAxis.setRange(0, M);*/
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, null);
        XYBlockRenderer r = new XYBlockRenderer();
        SpectrumPaintScale ps = new SpectrumPaintScale(minHeight, maxHeight);
        r.setPaintScale(ps);
        r.setBlockHeight(1.0f);
        r.setBlockWidth(1.0f);
        plot.setRenderer(r);

        JFreeChart chart = new JFreeChart("Title",
                JFreeChart.DEFAULT_TITLE_FONT, plot, false);

        NumberAxis scaleAxis = new NumberAxis("Scale");
        scaleAxis.setAxisLinePaint(Color.white);
        scaleAxis.setTickMarkPaint(Color.white);
        PaintScaleLegend legend = new PaintScaleLegend(ps, scaleAxis);
        legend.setSubdivisionCount(128);
        legend.setAxisLocation(AxisLocation.TOP_OR_RIGHT);
        legend.setPadding(new RectangleInsets(1, 1, 1, 1));
        legend.setStripWidth(2);
        legend.setPosition(RectangleEdge.RIGHT);
        legend.setBackgroundPaint(Color.WHITE);
        chart.addSubtitle(legend);
        chart.setBackgroundPaint(Color.white);

        XYPolygonAnnotation a = new XYPolygonAnnotation(polygon.getBoundaryPointsAs1DArray(), new BasicStroke(), new Color(0, 255, 0, 255));
        plot.addAnnotation(a);
        return chart;
    }

    private XYZDataset createDataset(Map<Point2D, Double> data) {
        DefaultXYZDataset dataset = new DefaultXYZDataset();
        int i = 0;
        double[] x = new double[data.size()];
        double[] y = new double[data.size()];
        double[] z = new double[data.size()];
        for (Map.Entry<Point2D, Double> point : data.entrySet())
        {
            x[i] = point.getKey().getX();
            y[i] = point.getKey().getY();
            z[i] = point.getValue();
            i++;
        }
        dataset.addSeries("Grid Series", new double[][]{x, y, z});
        return dataset;
    }

    private static class SpectrumPaintScale implements PaintScale {

        private final double lowerBound;
        private final double upperBound;

        public SpectrumPaintScale(double lowerBound, double upperBound) {
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
        }

        @Override
        public double getLowerBound() {
            return lowerBound;
        }

        @Override
        public double getUpperBound() {
            return upperBound;
        }

        @Override
        public Paint getPaint(double value) {
            int color = (int) (255 * (1 - value / upperBound));
            return new Color(color, color, color);
        }
    }
}
