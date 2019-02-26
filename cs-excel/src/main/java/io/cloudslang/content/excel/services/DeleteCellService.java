package io.cloudslang.content.excel.services;

import io.cloudslang.content.excel.entities.DeleteCellInputs;
import org.apache.poi.ss.usermodel.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

import static io.cloudslang.content.excel.services.ExcelServiceImpl.*;
import static io.cloudslang.content.utils.OutputUtilities.getFailureResultsMap;
import static io.cloudslang.content.utils.OutputUtilities.getSuccessResultsMap;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;

public class DeleteCellService {

    public static Map<String, String> deleteCell(@NotNull final DeleteCellInputs deleteCellInputs) {
        try {
            final String excelFileName = deleteCellInputs.getCommonInputs().getExcelFileName();
            final Workbook excelDoc = getExcelDoc(excelFileName);
            final Sheet worksheet = getWorksheet(excelDoc, deleteCellInputs.getCommonInputs().getWorksheetName());

            final int firstRowIndex = worksheet.getFirstRowNum();
            final int firstColumnIndex = 0;
            final int lastRowIndex = worksheet.getLastRowNum();
            final int lastColumnIndex = getLastColumnIndex(worksheet, firstRowIndex, lastRowIndex);

            final String rowIndexDefault = firstRowIndex + ":" + lastRowIndex;
            final String columnIndexDefault = firstColumnIndex + ":" + lastColumnIndex;
            final String rowIndex = defaultIfEmpty(deleteCellInputs.getRowIndex(), rowIndexDefault);
            final String columnIndex = defaultIfEmpty(deleteCellInputs.getColumnIndex(), columnIndexDefault);

            final List<Integer> rowIndexList = validateIndex(processIndex(rowIndex), firstRowIndex, lastRowIndex, true);
            final List<Integer> columnIndexList = validateIndex(processIndex(columnIndex), firstColumnIndex, lastColumnIndex, false);

            if (rowIndexList.size() != 0 && columnIndexList.size() != 0) {
                final int deleteCellResult = deleteCell(worksheet, rowIndexList, columnIndexList);
                //update formula cells
                final FormulaEvaluator evaluator = excelDoc.getCreationHelper().createFormulaEvaluator();
                for (Row r : worksheet) {
                    for (Cell c : r) {
                        if (c.getCellType() == CellType.FORMULA) {
                            evaluator.evaluateFormulaCell(c);
                        }
                    }
                }
                updateWorkbook(excelDoc, excelFileName);
                return getSuccessResultsMap(String.valueOf(deleteCellResult));

            } else {
                return getSuccessResultsMap("0");
            }
        } catch (Exception e) {
            return getFailureResultsMap(e.getMessage());
        }
    }

    public static int deleteCell(final Sheet worksheet, final List<Integer> rowIndex, final List<Integer> columnIndex) {
        int rowsDeleted = 0;

        for (Integer rIndex : rowIndex) {
            Row row = worksheet.getRow(rIndex);

            if (row != null) {
                for (Integer cIndex : columnIndex) {
                    Cell cell = row.getCell(cIndex);
                    if (cell != null) {
                        row.removeCell(cell);
                    }
                }
                rowsDeleted++;
            }
        }

        return rowsDeleted;
    }
}
