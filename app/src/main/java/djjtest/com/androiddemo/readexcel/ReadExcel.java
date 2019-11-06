package djjtest.com.androiddemo.readexcel;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import djjtest.com.androiddemo.utils.CommonUtils;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcel {

    public static void read(String fileName) {
        Map<String, List<List<String>>> map = new HashMap<>();
        List<List<String>> rows;
        List<String> columns = null;
        try {
            Workbook workbook = Workbook.getWorkbook(new File(fileName));
            CommonUtils.log("workbook", "Thread：", Thread.currentThread().getName());
            CommonUtils.log("workbook", "  workbook.getNumberOfSheets()：" + workbook.getNumberOfSheets());

            Sheet[] sheets = workbook.getSheets();
            for (Sheet sheet : sheets) {
                rows = new ArrayList<>();
                String sheetName = sheet.getName();
                CommonUtils.log("workbook", "sheetName：" + sheetName);
                CommonUtils.log("workbook", "列数" + sheet.getColumns());
                CommonUtils.log("workbook", "行数" + sheet.getRows());
                for (int i = 0; i < sheet.getRows(); i++) {
                    Cell[] sheetRow = sheet.getRow(i);
                    int columnNum = sheet.getColumns();
                    for (int j = 0; j < sheetRow.length; j++) {
                        if (j % columnNum == 0) {  //按行存数据
                            columns = new ArrayList<>();
                        }
                        columns.add(sheetRow[j].getContents());
                    }
                    rows.add(columns);
                    Log.e("FlutterActivity", " " + columns);
                }
                map.put(sheetName, rows);
            }


        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (BiffException e1) {
            e1.printStackTrace();
        }

    }


}
