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
import javax.swing.filechooser.FileNameExtensionFilter;

import interpolation.*;
import interpolation.Point;

/**
 * Created by alsm0813 on 26.09.2016.
 */
public class GridRenderer {

    private GridCSVHelper gridCSVHelper = new GridCSVHelper();

    public void render(Grid grid)
    {
        JFrame frame = new JFrame("Grid");
        XYZDataset dataset = createDataset(grid.getData());
        JFreeChart chart = createChart(dataset, grid);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(640, 480));
        chartPanel.setMouseZoomable(true, false);
        addSaveAsCSVMenuItem(frame, chartPanel.getPopupMenu(), grid);
        frame.add(chartPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void addSaveAsCSVMenuItem(JFrame frame, JPopupMenu popup, Grid grid) {
        for (MenuElement element : popup.getSubElements()) {
            System.out.println(element.getClass());
            if (element instanceof JMenu) {
                JMenu menu = (JMenu) element;
                System.out.println(menu.getText());
                if ("Save as".equals(menu.getText())) {
                    JMenuItem csvMenuItem = new JMenuItem("CSV...");
                    csvMenuItem.setActionCommand("SAVE_AS_CSV");
                    csvMenuItem.addActionListener(event -> {
                        JFileChooser fileChooser = new JFileChooser();
                        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV_Files", "csv");
                        fileChooser.addChoosableFileFilter(filter);
                        fileChooser.setFileFilter(filter);
                        int option = fileChooser.showSaveDialog(null);
                        if(option == JFileChooser.APPROVE_OPTION) {
                            String filename = fileChooser.getSelectedFile().getPath();
                            try {
                                gridCSVHelper.write3DGrid(grid, filename);
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(
                                        frame,
                                        "Error has occurred while trying to create csv file",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE
                                );
                            }
                        }
                    });
                    menu.add(csvMenuItem);
                }
            }
        }
    }

    private JFreeChart createChart(XYDataset dataset, Grid grid) {
        NumberAxis xAxis = new NumberAxis("x Axis");
        /*xAxis.setAutoRange(false);
        xAxis.setRange(0, N);*/
        NumberAxis yAxis = new NumberAxis("y Axis");
        /*yAxis.setAutoRange(false);
        yAxis.setRange(0, M);*/
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, null);
        XYBlockRenderer r = new XYBlockRenderer();
        SpectrumPaintScale ps = new SpectrumPaintScale(grid.getzMin(), grid.getzMax());
        r.setPaintScale(ps);
        r.setBlockHeight(grid.getGridStep());
        r.setBlockWidth(grid.getGridStep());
        plot.setRenderer(r);

        JFreeChart chart = new JFreeChart("Grid",
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

        XYPolygonAnnotation a = new XYPolygonAnnotation(
                grid.getBoundaries().getBoundariesPointsAs1DArray(), new BasicStroke(), new Color(0, 255, 0, 255)
        );
        plot.addAnnotation(a);
        return chart;
    }

    private XYZDataset createDataset(Map<Point, Double> data) {
        DefaultXYZDataset dataset = new DefaultXYZDataset();
        int i = 0;
        double[] x = new double[data.size()];
        double[] y = new double[data.size()];
        double[] z = new double[data.size()];
        for (Map.Entry<interpolation.Point, Double> point : data.entrySet())
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
