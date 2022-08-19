package com.ashwetaw.util;

import com.ashwetaw.entities.Student;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * https://www.bezkoder.com/spring-boot-download-excel-file/
 **/

public class ExcelUtil {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = {"ID", "NAME", "ROLL NO", "YEAR", "SECTION", "ADDRESS", "EMAIL"};
    static String SHEET = "Tutorials";

    public static ByteArrayOutputStream studentsToExcel(List<Student> studentList) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);
            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }
            int rowIdx = 1;
            for (Student student : studentList) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(student.getId());
                row.createCell(1).setCellValue(student.getName());
                row.createCell(2).setCellValue(student.getRollNo());
                row.createCell(3).setCellValue(student.getYear());
                row.createCell(4).setCellValue(student.getSection());
                row.createCell(5).setCellValue(student.getAddress());
                row.createCell(6).setCellValue(student.getEmail());
            }
            workbook.write(out);
            return out;

        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }


}
