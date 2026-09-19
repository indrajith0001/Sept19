package ddt;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReadDataFromExcel {
	public static void main(String[] args) throws Exception {
		FileInputStream fis = new FileInputStream("./src/test/resources/comp_data.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sh = wb.getSheetAt(0);
		Row rw = sh.getRow(1);
		Cell cl=rw.getCell(1);		
		System.out.println(cl.getStringCellValue());
		System.out.println("physical no of cells: "+rw.getPhysicalNumberOfCells());
		System.out.println("last cell num: "+rw.getLastCellNum());
		
		System.out.println("++++++++++sheet2++++++++++++");
		Sheet sh_emp = wb.getSheetAt(1);
		Row rw_emp = sh_emp.getRow(1);
		Cell cl_emp = rw_emp.getCell(1);
		System.out.println("Empname:  " + cl_emp.getStringCellValue());
		System.out.println(rw_emp.getLastCellNum());
		System.out.println(rw_emp.getPhysicalNumberOfCells());
	}
}
