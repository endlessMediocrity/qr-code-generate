package qr;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;

public class TakeData {

    protected static ArrayList<ArrayList<String>> take(FileInputStream file) throws Exception {
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        ArrayList<ArrayList<String>> data = new ArrayList<>(sheet.getLastRowNum() + 1);
        for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
            Row row = sheet.getRow(i);
            ArrayList<String> name = new ArrayList<>(row.getLastCellNum());

            for (Cell cell : row) {
                name.add(cell.toString());
            }
            data.add(name);
        }
        return data;
    }
}
