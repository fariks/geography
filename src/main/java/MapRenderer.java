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
import java.util.Map;

import javax.swing.*;

/**
 * Created by alsm0813 on 26.09.2016.
 */
public class MapRenderer {

    public void render(Map<Point2D, Double> zondData, Map<Point2D, Double> interpolationData, Polygon polygon)
    {
        JFrame f = new JFrame("Interpolated Map");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ChartPanel chartPanel = new ChartPanel(createChart(createDataset(interpolationData, polygon), polygon)) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(640, 480);
            }
        };
        ChartPanel chartPanelBefore = new ChartPanel(createChart(createDataset(zondData, polygon), polygon)) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(640, 480);
            }
        };
        chartPanel.setMouseZoomable(true, false);
        f.add(chartPanel);
        //f.add(chartPanelBefore);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    private static JFreeChart createChart(XYDataset dataset, Polygon polygon) {
        NumberAxis xAxis = new NumberAxis("x Axis");
        /*xAxis.setAutoRange(false);
        xAxis.setRange(0, N);*/
        NumberAxis yAxis = new NumberAxis("y Axis");
        /*yAxis.setAutoRange(false);
        yAxis.setRange(0, M);*/
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, null);
        XYBlockRenderer r = new XYBlockRenderer();
        SpectrumPaintScale ps = new SpectrumPaintScale(0, 20);
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

    private static XYZDataset createDataset(Map<Point2D, Double> data, Polygon polygon) {
        DefaultXYZDataset dataset = new DefaultXYZDataset();
        int k = 0;
        for (Map.Entry<Point2D, Double> point : data.entrySet())
        {
            dataset.addSeries("Series " + k, new double[][]{{point.getKey().getX()}, {point.getKey().getY()}, {point.getValue()}});
            k++;
        }
        return dataset;
        /*for (int i = 0; i < N; i = i + 1) {
            double[][] data = new double[3][M];
            for (int j = 0; j < M; j = j + 1) {
                data[0][j] = i;
                data[1][j] = j;
                data[2][j] = A[i][j];
            }
            //if (i==1) continue;
            dataset.addSeries("Series" + i, data);
        }
        return dataset;*/
    }

    private static class SpectrumPaintScale implements PaintScale {

        private static final float H1 = 0f;
        private static final float H2 = 1f;
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
            float scaledValue = (float) (value / (getUpperBound() - getLowerBound()));
            //float scaledH = H1 + scaledValue * (H2 - H1);
            return new Color(0, 0, (int) (255 * value / upperBound));//Color.getHSBColor(scaledH, 1f, 1f);
        }
    }

}
