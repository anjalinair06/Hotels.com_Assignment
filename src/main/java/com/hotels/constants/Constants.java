package com.hotels.constants;

import com.hotels.testdata.TestDataReader;

import java.util.List;
import java.util.Map;

public class Constants {
    public static final String TEST_DATA_WORKBOOK_PATH = "src\\test\\resources\\TestData.xlsx";
    public static final String TEST_DATA_WORKSHEET = "InputData";
    public static final String SAMPLE_CSV_FILE = "src\\test\\resources\\TopThreeExpensiveHotels.csv";
    public static final List<Map<String, String>> TEST_DATA = TestDataReader.loadTestData();
    private Constants() {
    }
}
