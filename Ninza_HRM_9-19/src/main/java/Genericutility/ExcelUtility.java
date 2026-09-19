package Genericutility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

// Add these methods inside your ExcelUtility class

public class ExcelUtility {

	String filePath;
	FileInputStream fis;
	FileOutputStream fos;
	Workbook workbook;

	public void openExcel(String filePath) {
		this.filePath = filePath;
		try {
			fis = new FileInputStream(filePath);
			workbook = WorkbookFactory.create(fis);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to open Excel file: " + filePath, e);
		}
	}

	public void closeExcel() {
		try {
			if (fis != null) {
				fis.close();
			}
			if (fos != null) {
				fos.close();
			}
			if (workbook != null) {
				workbook.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveExcel() {
		try {
			fos = new FileOutputStream(filePath);
			workbook.write(fos);
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to save Excel file: " + filePath, e);
		}
	}

	public Sheet getSheet(String sheetName) {
		if (workbook == null) {
			throw new IllegalStateException("Workbook is not opened. Call openExcel() first.");
		}
		Sheet sheet = workbook.getSheet(sheetName);
		if (sheet == null) {
			throw new IllegalArgumentException("Sheet with name '" + sheetName + "' does not exist.");
		}
		return sheet;
	}

	public Sheet getSheet(int sheetIndex) {
		if (workbook == null) {
			throw new IllegalStateException("Workbook is not opened. Call openExcel() first.");
		}
		try {
			return workbook.getSheetAt(sheetIndex);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Sheet at index " + sheetIndex + " does not exist.", e);
		}
	}
	public String getCellData(Workbook wb,String sheetName, int rowNum, int cellNum) {
	    Sheet sheet = wb.getSheet(sheetName);
	    Row row = sheet.getRow(rowNum);
	    if (row == null) return "";
	    Cell cell = row.getCell(cellNum);
	    if (cell == null) return "";

	    DataFormatter formatter = new DataFormatter();
	    return formatter.formatCellValue(cell);
	}

	public String getStringCellData(Workbook wb,String sheetName, int rowNum, int cellNum) {
	    Sheet sheet = wb.getSheet(sheetName);
	    Row row = sheet.getRow(rowNum);
	    if (row == null) return "";
	    Cell cell = row.getCell(cellNum);
	    if (cell == null) return "";

	    return cell.getStringCellValue();
	}

	public double getNumericCellData(String sheetName, int rowNum, int cellNum) {
	    Sheet sheet = getSheet(sheetName);
	    Row row = sheet.getRow(rowNum);
	    if (row == null) return 0.0;
	    Cell cell = row.getCell(cellNum);
	    if (cell == null) return 0.0;

	    return cell.getNumericCellValue();
	}

	public boolean getBooleanCellData(String sheetName, int rowNum, int cellNum) {
	    Sheet sheet = getSheet(sheetName);
	    Row row = sheet.getRow(rowNum);
	    if (row == null) return false;
	    Cell cell = row.getCell(cellNum);
	    if (cell == null) return false;

	    return cell.getBooleanCellValue();
	}

	public Date getDateCellData(String sheetName, int rowNum, int cellNum) {
	    Sheet sheet = getSheet(sheetName);
	    Row row = sheet.getRow(rowNum);
	    if (row == null) return null;
	    Cell cell = row.getCell(cellNum);
	    if (cell == null) return null;

	    if (DateUtil.isCellDateFormatted(cell)) {
	        return cell.getDateCellValue();
	    }
	    throw new IllegalStateException("Cell at row " + rowNum + ", cell " + cellNum + " is not a valid date format.");
	}

	public void setCellData(String sheetName, int rowNum, int cellNum, String value) {
	    Sheet sheet = getSheet(sheetName);
	    Row row = sheet.getRow(rowNum);
	    if (row == null) {
	        row = sheet.createRow(rowNum);
	    }
	    Cell cell = row.getCell(cellNum);
	    if (cell == null) {
	        cell = row.createCell(cellNum);
	    }
	    cell.setCellValue(value);
	}

	public void setCellData(String sheetName, int rowNum, int cellNum, int value) {
	    Sheet sheet = getSheet(sheetName);
	    Row row = sheet.getRow(rowNum);
	    if (row == null) {
	        row = sheet.createRow(rowNum);
	    }
	    Cell cell = row.getCell(cellNum);
	    if (cell == null) {
	        cell = row.createCell(cellNum);
	    }
	    cell.setCellValue(value);
	}

	public void setCellData(String sheetName, int rowNum, int cellNum, double value) {
	    Sheet sheet = getSheet(sheetName);
	    Row row = sheet.getRow(rowNum);
	    if (row == null) {
	        row = sheet.createRow(rowNum);
	    }
	    Cell cell = row.getCell(cellNum);
	    if (cell == null) {
	        cell = row.createCell(cellNum);
	    }
	    cell.setCellValue(value);
	}

	public void setCellData(String sheetName, int rowNum, int cellNum, boolean value) {
	    Sheet sheet = getSheet(sheetName);
	    Row row = sheet.getRow(rowNum);
	    if (row == null) {
	        row = sheet.createRow(rowNum);
	    }
	    Cell cell = row.getCell(cellNum);
	    if (cell == null) {
	        cell = row.createCell(cellNum);
	    }
	    cell.setCellValue(value);
	}

	public int getRowCount(String sheetName) {
	    Sheet sheet = getSheet(sheetName);
	    return sheet.getPhysicalNumberOfRows();
	}

	public int getLastRowNum(String sheetName) {
	    Sheet sheet = getSheet(sheetName);
	    return sheet.getLastRowNum();
	}

	public Row createRow(String sheetName, int rowNum) {
	    Sheet sheet = getSheet(sheetName);
	    Row row = sheet.getRow(rowNum);
	    if (row != null) {
	        sheet.removeRow(row); // Clears existing row before creating fresh
	    }
	    return sheet.createRow(rowNum);
	}

	public void deleteRow(String sheetName, int rowNum) {
	    Sheet sheet = getSheet(sheetName);
	    Row row = sheet.getRow(rowNum);
	    if (row != null) {
	        sheet.removeRow(row);
	        
	        // Shifts remaining rows up to avoid empty gaps
	        int lastRow = sheet.getLastRowNum();
	        if (rowNum >= 0 && rowNum < lastRow) {
	            sheet.shiftRows(rowNum + 1, lastRow, -1);
	        }
	    }
	}

	public boolean isRowExists(String sheetName, int rowNum) {
	    Sheet sheet = getSheet(sheetName);
	    Row row = sheet.getRow(rowNum);
	    return row != null;
	}
	public int getCellCount(String sheetName, int rowNum) {
	    Sheet sheet = getSheet(sheetName);
	    Row row = sheet.getRow(rowNum);
	    if (row == null) return 0;
	    return row.getLastCellNum(); // Returns total count of cells (1-based index equivalent)
	}

	public Cell createCell(String sheetName, int rowNum, int cellNum) {
	    Sheet sheet = getSheet(sheetName);
	    Row row = sheet.getRow(rowNum);
	    if (row == null) {
	        row = sheet.createRow(rowNum);
	    }
	    Cell cell = row.getCell(cellNum);
	    if (cell != null) {
	        row.removeCell(cell); // Clears existing cell before creating fresh
	    }
	    return row.createCell(cellNum);
	}

	public void deleteCell(String sheetName, int rowNum, int cellNum) {
	    Sheet sheet = getSheet(sheetName);
	    Row row = sheet.getRow(rowNum);
	    if (row != null) {
	        Cell cell = row.getCell(cellNum);
	        if (cell != null) {
	            row.removeCell(cell);
	        }
	    }
	}

	public boolean isCellExists(String sheetName, int rowNum, int cellNum) {
	    Sheet sheet = getSheet(sheetName);
	    Row row = sheet.getRow(rowNum);
	    if (row == null) return false;
	    Cell cell = row.getCell(cellNum);
	    return cell != null;
	}

	public CellType getCellType(String sheetName, int rowNum, int cellNum) {
	    Sheet sheet = getSheet(sheetName);
	    Row row = sheet.getRow(rowNum);
	    if (row == null) return CellType.BLANK;
	    Cell cell = row.getCell(cellNum);
	    if (cell == null) return CellType.BLANK;
	    return cell.getCellType();
	}


	public Sheet createSheet(String sheetName) {
	    if (workbook == null) {
	        throw new IllegalStateException("Workbook is not opened. Call openExcel() first.");
	    }
	    if (workbook.getSheet(sheetName) != null) {
	        throw new IllegalArgumentException("Sheet with name '" + sheetName + "' already exists.");
	    }
	    return workbook.createSheet(sheetName);
	}

	public void deleteSheet(String sheetName) {
	    if (workbook == null) {
	        throw new IllegalStateException("Workbook is not opened. Call openExcel() first.");
	    }
	    int index = workbook.getSheetIndex(sheetName);
	    if (index != -1) {
	        workbook.removeSheetAt(index);
	    } else {
	        throw new IllegalArgumentException("Sheet with name '" + sheetName + "' does not exist.");
	    }
	}

	public void renameSheet(String oldName, String newName) {
	    if (workbook == null) {
	        throw new IllegalStateException("Workbook is not opened. Call openExcel() first.");
	    }
	    int index = workbook.getSheetIndex(oldName);
	    if (index != -1) {
	        workbook.setSheetName(index, newName);
	    } else {
	        throw new IllegalArgumentException("Sheet with name '" + oldName + "' does not exist.");
	    }
	}

	public int getSheetCount() {
	    if (workbook == null) {
	        throw new IllegalStateException("Workbook is not opened. Call openExcel() first.");
	    }
	    return workbook.getNumberOfSheets();
	}

	public List<String> getSheetNames() {
	    if (workbook == null) {
	        throw new IllegalStateException("Workbook is not opened. Call openExcel() first.");
	    }
	    List<String> sheetNames = new ArrayList<>();
	    int count = workbook.getNumberOfSheets();
	    for (int i = 0; i < count; i++) {
	        sheetNames.add(workbook.getSheetName(i));
	    }
	    return sheetNames;
	}


	public Object[][] readAllData(String sheetName) {
	    Sheet sheet = getSheet(sheetName);
	    int rowCount = sheet.getPhysicalNumberOfRows();
	    if (rowCount == 0) return new Object[0][0];

	    int colCount = sheet.getRow(0).getLastCellNum();
	    Object[][] data = new Object[rowCount][colCount];
	    DataFormatter formatter = new DataFormatter();

	    for (int i = 0; i < rowCount; i++) {
	        Row row = sheet.getRow(i);
	        for (int j = 0; j < colCount; j++) {
	            if (row != null) {
	                Cell cell = row.getCell(j);
	                data[i][j] = (cell != null) ? formatter.formatCellValue(cell) : "";
	            } else {
	                data[i][j] = "";
	            }
	        }
	    }
	    return data;
	}

	public List<String> readColumnData(String sheetName, int columnNum) {
	    Sheet sheet = getSheet(sheetName);
	    List<String> columnData = new ArrayList<>();
	    DataFormatter formatter = new DataFormatter();
	    int lastRow = sheet.getLastRowNum();

	    for (int i = 0; i <= lastRow; i++) {
	        Row row = sheet.getRow(i);
	        if (row != null) {
	            Cell cell = row.getCell(columnNum);
	            columnData.add((cell != null) ? formatter.formatCellValue(cell) : "");
	        } else {
	            columnData.add("");
	        }
	    }
	    return columnData;
	}

	public List<String> readRowData(String sheetName, int rowNum) {
	    Sheet sheet = getSheet(sheetName);
	    List<String> rowData = new ArrayList<>();
	    Row row = sheet.getRow(rowNum);
	    
	    if (row != null) {
	        DataFormatter formatter = new DataFormatter();
	        int lastCell = row.getLastCellNum();
	        for (int j = 0; j < lastCell; j++) {
	            Cell cell = row.getCell(j);
	            rowData.add((cell != null) ? formatter.formatCellValue(cell) : "");
	        }
	    }
	    return rowData;
	}

	public String getDataBasedOnKey(String sheetName, String key) {
	    Sheet sheet = getSheet(sheetName);
	    DataFormatter formatter = new DataFormatter();
	    int lastRow = sheet.getLastRowNum();

	    for (int i = 0; i <= lastRow; i++) {
	        Row row = sheet.getRow(i);
	        if (row != null) {
	            Cell keyCell = row.getCell(0); // Assuming key is in the first column (Column 0)
	            if (keyCell != null && formatter.formatCellValue(keyCell).trim().equalsIgnoreCase(key.trim())) {
	                Cell valueCell = row.getCell(1); // Assuming target value is in the second column (Column 1)
	                return (valueCell != null) ? formatter.formatCellValue(valueCell) : "";
	            }
	        }
	    }
	    return ""; // Key not found
	}

	public void updateCellValue(String sheetName, String key, String value) {
	    Sheet sheet = getSheet(sheetName);
	    DataFormatter formatter = new DataFormatter();
	    int lastRow = sheet.getLastRowNum();
	    boolean updated = false;

	    for (int i = 0; i <= lastRow; i++) {
	        Row row = sheet.getRow(i);
	        if (row != null) {
	            Cell keyCell = row.getCell(0); // Search key in Column 0
	            if (keyCell != null && formatter.formatCellValue(keyCell).trim().equalsIgnoreCase(key.trim())) {
	                Cell valueCell = row.getCell(1); // Target value in Column 1
	                if (valueCell == null) {
	                    valueCell = row.createCell(1);
	                }
	                valueCell.setCellValue(value);
	                updated = true;
	                break;
	            }
	        }
	    }

	    if (!updated) {
	        // If key doesn't exist, append it at the end
	        int newRowNum = lastRow + 1;
	        Row newRow = sheet.createRow(newRowNum);
	        newRow.createCell(0).setCellValue(key);
	        newRow.createCell(1).setCellValue(value);
	    }}
	public void newcellcreate(Workbook uwb,int sheet, int row, int col, String newvalue) throws Exception {
		uwb.getSheetAt(sheet).getRow(row).createCell(col).setCellValue(newvalue);
	}
	public void updatecellvalue(Workbook uwb,int sheet, int row, int cell, String updatedvalue) throws Exception {
		uwb.getSheetAt(sheet).getRow(row).getCell(cell).setCellValue(updatedvalue);
	}
}
