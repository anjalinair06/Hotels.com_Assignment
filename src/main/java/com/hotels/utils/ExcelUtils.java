package com.hotels.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ExcelUtils {

    public static void exportToCSV(List<Map<String, String>> exportList, String csvName) {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(csvName));
            String headers = String.join(",", exportList.get(0).keySet());
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(headers));
            for (Map<String, String> value : exportList) {
                String detail = value.keySet().stream().map(value::get).collect(Collectors.joining(","));
                csvPrinter.printRecord(detail);
            }
            csvPrinter.flush();
        } catch (IOException e) {
            log.error("Unable to export to CSV", e);
        }
    }

}
