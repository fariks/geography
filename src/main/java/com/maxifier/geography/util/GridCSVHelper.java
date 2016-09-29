package com.maxifier.geography.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.maxifier.geography.interpolation.model.Grid;
import com.maxifier.geography.interpolation.model.Point;

public class GridCSVHelper {

    public Map<Point, Double> read3DGrid(String fileName) throws IOException {
        Map<Point, Double> res = new HashMap<>();
        CSVParser parser = null;
        try {
            parser = CSVParser.parse(new File(fileName), Charset.defaultCharset(), CSVFormat.DEFAULT.withDelimiter(';'));
            for (CSVRecord csvRecord : parser) {
                res.put(new Point(
                                Integer.parseInt(csvRecord.get(0)),
                                Integer.parseInt(csvRecord.get(1))
                        ),
                        Double.parseDouble(csvRecord.get(2))
                );
            }
        } finally {
            if (parser != null) {
                parser.close();
            }
        }
        return res;
    }

    public List<Point> read2DGrid(String fileName) throws IOException {
        List<Point> res = new ArrayList<>();
        CSVParser parser = null;
        try {
            parser = CSVParser.parse(new File(fileName), Charset.defaultCharset(), CSVFormat.DEFAULT.withDelimiter(';'));
            for (CSVRecord csvRecord : parser) {
                res.add(new Point(
                        Integer.parseInt(csvRecord.get(0)),
                        Integer.parseInt(csvRecord.get(1))
                ));
            }
        } finally {
            if (parser != null) {
                parser.close();
            }
        }
        return res;
    }

    public void write3DGrid(Grid grid, String fileName) throws IOException {
        CSVPrinter printer = null;
        try {
            printer = new CSVPrinter(new FileWriter(fileName), CSVFormat.DEFAULT.withDelimiter(';'));
            printer.printRecord(grid.getGridStep());
            Point min = grid.getMin();
            Point max = grid.getMax();
            Map<Point, Double> data = grid.getData();
            printer.printRecord(min.getX());
            printer.printRecord(min.getY());
            for (int i = min.getX(); i <= max.getX(); i += grid.getGridStep()) {
                for (int j = min.getY(); j <= max.getY(); j += grid.getGridStep()) {
                    Double value = data.get(new Point(i, j));
                    if (value != null) {
                        printer.print(String.format("%.2f", value));
                    } else {
                        printer.print(null);
                    }
                }
                printer.println();
            }
        } finally {
            if (printer != null) {
                printer.close();
            }
        }
    }
}
