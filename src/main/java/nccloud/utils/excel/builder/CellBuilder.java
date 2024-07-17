package nccloud.utils.excel.builder;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.function.Consumer;

public class CellBuilder {
    private XSSFRow row;

    public CellBuilder() {
    }

    public CellBuilder(XSSFRow row) {
        this.row = row;
    }

    public XSSFRow nextRow(XSSFRow row) {
        this.row = row;
        return row;
    }
    public XSSFRow nextRow(XSSFRow row,short height) {
        this.row = row;
        this.row.setHeight(height);
        return row;
    }

    public CellBuilderInner nextCell(int col) {
        return new CellBuilderInner(col);
    }
    public void batchCreateCell(int col, int count, Consumer<CellBuilderInner> builder) {
        for (int i = 0; i < count; i++) {
            builder.accept(new CellBuilderInner(col+i));
        }
    }


    public class CellBuilderInner{
        private XSSFCell cell;

        private CellBuilderInner(int col) {
            this.cell = row.createCell(col);
        }
        public CellBuilderInner setCellStyle(CellStyle style) {
            cell.setCellStyle(style);
            return this;
        }

        public CellBuilderInner setCellValue(String value) {
            if (value != null) {
                cell.setCellValue(value);
            }
            return this;
        }

        public CellBuilderInner setCellValue(Double value) {
            if (value != null) {
                cell.setCellValue(value);
            }
            return this;
        }


        public CellBuilderInner setCellValue(Date value) {
            if (value != null) {
                cell.setCellValue(value);
            }
            return this;
        }


        public CellBuilderInner setCellValue(Boolean value) {
            if (value != null) {
                cell.setCellValue(value);
            }
            return this;
        }

        public CellBuilderInner setCellFormula(String formula) {
            if (formula != null) {
                cell.setCellFormula(formula);
            }
            return this;
        }

        public XSSFCell build() {
            return cell;
        }
    }



}
