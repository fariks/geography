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
            printer.printRecord(grid.getMin().getX());
            printer.printRecord(grid.getMin().getY());
            for (Map.Entry<Point, Double> entry : grid.getData().entrySet())
            {
                printer.printRecord(entry.getKey().getX(), entry.getKey().getY(), String.format("%.2f", entry.getValue()));
            }
        } finally {
            if (printer != null) {
                printer.close();
            }
        }
    }
}
