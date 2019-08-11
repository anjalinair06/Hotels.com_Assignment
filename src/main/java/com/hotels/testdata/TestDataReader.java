package com.hotels.testdata;

import com.hotels.contants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class TestDataReader {

    private TestDataReader(){}

    public static List<Map<String, String>> loadTestData() {
        FileInputStream testDataFile = null;
        List<Map<String, String>> testData = new ArrayList<Map<String, String>>();
        Map<Integer, String> header = new HashMap<Integer, String>();
        try {
            testDataFile = new FileInputStream(Constants.TEST_DATA_WORKBOOK_PATH);
            XSSFWorkbook excelWorkbook = new XSSFWorkbook(testDataFile);
            XSSFSheet excelWorksheet = excelWorkbook.getSheet(Constants.TEST_DATA_WORKSHEET);
            for(Cell cell : excelWorksheet.getRow(0))
                header.put(cell.getColumnIndex(), cell.getStringCellValue());
            for (Row currentRow : excelWorksheet){
                Map<String, String> row = new HashMap<String, String>();
                for (Cell currentCell : currentRow){
                    String value = "";
                    switch (currentCell.getCellTypeEnum()){
                        case STRING:
                            value = currentCell.getStringCellValue();
                            break;
                        case NUMERIC:
                            value = Integer.toString((int)currentCell.getNumericCellValue());
                            break;
                    }
                    if(!header.get(currentCell.getColumnIndex()).equals(value)) {
                        row.put(header.get(currentCell.getColumnIndex()), value);
                    }
                }
                if(!row.isEmpty())
                    testData.add(row);
            }
        } catch (FileNotFoundException e) {
            log.error("Unable to find Test Data file", e);
        } catch (IOException e) {
            log.error("Error while reading Test Data file", e);
        } finally {
            try {
                if(testDataFile != null)
                    testDataFile.close();
            } catch (IOException e) {
                log.error("Error while closing Test Data file");
            }
        }
        return testData;
    }
}
