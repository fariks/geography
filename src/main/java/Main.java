import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.List;

import static java.lang.Math.*;

/**
 * Created by smirnov on 24.09.2016.
 */
public class Main {

    public static final int NEIGHBORHOOD_COUNT = 3;

    public static final int MAX_DISTANCE_STEP = 3;

    static double[][] A = new double[][]{
            {3, 4, 5, 2},
            {-1, 4, -1, 0},
            {3, -1, -1, -1}
    };
    static int n = A.length;
    static int m = A[0].length;
    static double[][] B = new double[n][m];

    public static void main(String[] args) throws IOException {
        /*interpolate();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(B[i][j] + " ");
            }
            System.out.println();
        }
        // create a chart...
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Scatter Plot", // chart title
                "X", // x axis label
                "Y", // y axis label
                createDataset(), // data  ***-----PROBLEM------***
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips
                false // urls
        );
        XYPlot plot = (XYPlot) chart.getPlot();
        XYItemRenderer renderer = new MyRenderer(false, true);
        //renderer.setSeriesShape(0, new Rectangle2D.Double(-10, -10, 10, 10));
        plot.setRenderer(renderer);
        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setRange(0, n);
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(0, m);
        // create and display a frame...
        ChartFrame frame = new ChartFrame("First", chart);
        frame.pack();
        frame.setVisible(true);*/

        Map<Point2D, Double> zondData = new HashMap<Point2D, Double>();
        CSVParser parser = CSVParser.parse(new File("src/main/resources/zondData.csv"), Charset.defaultCharset(), CSVFormat.DEFAULT.withDelimiter(';'));
        for (CSVRecord csvRecord : parser) {
            zondData.put(new Point2D(
                            Integer.parseInt(csvRecord.get(0)),
                            Integer.parseInt(csvRecord.get(1))
                    ),
                    Double.parseDouble(csvRecord.get(2))
            );
        }
        System.out.println(zondData);

        List<Point2D> boundaries = new ArrayList<Point2D>();
        parser = CSVParser.parse(new File("src/main/resources/boundary.csv"), Charset.defaultCharset(), CSVFormat.DEFAULT.withDelimiter(';'));
        for (CSVRecord csvRecord : parser) {
            boundaries.add(new Point2D(
                    Integer.parseInt(csvRecord.get(0)),
                    Integer.parseInt(csvRecord.get(1))
            ));
        }
        System.out.println(boundaries);

        Polygon polygon = new Polygon(boundaries);
        System.out.println(polygon.contains(new Point2D(1,1)));
        System.out.println(polygon.contains(new Point2D(1,2)));
        System.out.println(polygon.contains(new Point2D(5,6)));
        System.out.println(polygon.contains(new Point2D(7,5)));

        MapInterpolator interpolator = new MapInterpolator(zondData, polygon, 1);
        System.out.println(interpolator.calcValue(5,6));
        Map<Point2D, Double> interpolationData = interpolator.getInterpolationData();
        System.out.println(interpolationData);

        CSVPrinter printer = new CSVPrinter(new FileWriter("src/main/resources/out.csv"), CSVFormat.DEFAULT.withDelimiter(';'));
        for (Map.Entry<Point2D, Double> entry : interpolationData.entrySet())
        {
            printer.printRecord(entry.getKey().getX(), entry.getKey().getY(), String.format("%.2f", entry.getValue()));
        }
        printer.close();

        new MapRenderer().render(zondData, interpolationData, polygon);
    }

    private static XYDataset createDataset() {
        XYSeriesCollection result = new XYSeriesCollection();
        XYSeries series = new XYSeries("Map");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                series.add(i, j);
            }
        }
        result.addSeries(series);
        return result;
    }

    static class MyRenderer extends XYLineAndShapeRenderer {

        public MyRenderer(boolean lines, boolean shapes) {
            super(lines, shapes);
        }

        @Override
        public Paint getItemPaint(int row, int col) {
            try {
                return new Color(0, 0, ((int) B[row][col]) * (255 / 5));
            } catch (Exception e) {
                return new Color(255, 255, 255);
            }
        }
    }

    static void interpolate() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (A[i][j] != -1) {
                    B[i][j] = A[i][j];
                } else {
                    B[i][j] = calcValue(i, j);
                }
            }
        }
    }

    static class Neighbor {
        double height;
        double distance;

        public Neighbor(double height, double distance) {
            this.height = height;
            this.distance = distance;
        }
    }

    //fix bug with angle element
    private static double calcValue(int x, int y) {
        List<Neighbor> neighbors = new ArrayList<Neighbor>();
        int k = 1;
        while (neighbors.size() < NEIGHBORHOOD_COUNT && k < MAX_DISTANCE_STEP) {
            for (int i = max(0, x - k); i < min(n, x + k); i++) {
                if (y - k >= 0 && A[i][y - k] != -1) {
                    neighbors.add(new Neighbor(A[i][y - k], distance(x, y, i, y - k)));
                }
                if (y + k < m && A[i][y + k] != -1) {
                    neighbors.add(new Neighbor(A[i][y + k], distance(x, y, i, y + k)));
                }
            }
            for (int j = max(0, y - k); j < min(m, y + k); j++) {
                if (x - k >= 0 && A[x - k][j] != -1) {
                    neighbors.add(new Neighbor(A[x - k][j], distance(x, y, x - k, j)));
                }
                if (x + k < n && A[x + k][j] != -1) {
                    neighbors.add(new Neighbor(A[x + k][j], distance(x, y, x + k, j)));
                }
            }
            k++;
        }
        return calcValueByNeighbors(neighbors);
    }

    static double distance(int x1, int y1, int x2, int y2) {
        return sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    static double calcValueByNeighbors(List<Neighbor> neighbors) {
        double sum = 0;
        double weigh_sum = 0;
        for (Neighbor neighbor : neighbors) {
            double weight = pow(1 / neighbor.distance, 2);
            weigh_sum += weight;
            sum += weight * neighbor.height;
        }
        return sum / weigh_sum;
    }
}

