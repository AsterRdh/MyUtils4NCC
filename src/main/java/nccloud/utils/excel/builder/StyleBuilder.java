package nccloud.utils.excel.builder;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class StyleBuilder {
    private XSSFWorkbook workbook;
    private DefaultIndexedColorMap defaultIndexedColorMap ;
    private XSSFColor defColor ;
    private Map<Integer,XSSFColor> colorMap ;

    public StyleBuilder(XSSFWorkbook workbook, DefaultIndexedColorMap defaultIndexedColorMap) {
        this.workbook = workbook;
        this.defaultIndexedColorMap = defaultIndexedColorMap;
        this.defColor = new XSSFColor(Color.BLACK, defaultIndexedColorMap);
        this.colorMap = new HashMap<>();
        this.colorMap.put(Color.BLACK.getRGB(),defColor);
    }

    public StyleBuilderInner createStyle(){
        return new StyleBuilderInner(workbook.createCellStyle());
    }


    public class StyleBuilderInner{
        private XSSFCellStyle style;
        private boolean hasSetVertical = false;
        private StyleBuilderInner(XSSFCellStyle style) {
            this.style = style;
        }

        public StyleBuilderInner setLocked() {
            style.setLocked(true);
            return this;
        }
        public StyleBuilderInner setUnlocked() {
            style.setLocked(false);
            return this;
        }
        public StyleBuilderInner setAlignment(HorizontalAlignment align) {
            style.setAlignment(align);
            if (!hasSetVertical) {
                style.setVerticalAlignment(VerticalAlignment.CENTER);
            }
            return this;
        }
        public StyleBuilderInner setVerticalAlignment(VerticalAlignment align) {
            style.setVerticalAlignment(align);
            hasSetVertical=true;
            return this;
        }
        public StyleBuilderInner setAlignment(HorizontalAlignment hAlign, VerticalAlignment vAlign) {
            style.setAlignment(hAlign);
            style.setVerticalAlignment(vAlign);
            hasSetVertical=true;
            return this;
        }

        public StyleBuilderInner setIndention(int indent) {
            style.setIndention((short) indent);
            return this;
        }
        public StyleBuilderInner setIndention(short indent) {
            style.setIndention(indent);
            return this;
        }

        public StyleBuilderInner setDataFormat(int dataFormat) {
            style.setDataFormat(dataFormat);
            return this;
        }
        public StyleBuilderInner setDataFormat(short dataFormat) {
            style.setDataFormat(dataFormat);
            return this;
        }
        public StyleBuilderInner setDataFormat(String dataFormat) {
            DataFormat dataFormatObj = workbook.getCreationHelper().createDataFormat();
            short format = dataFormatObj.getFormat(dataFormat);
            style.setDataFormat(format);
            return this;
        }

        public StyleBuilderInner setBorder(
                BorderStyle top,    Color topColor,
                BorderStyle right,  Color rightColor,
                BorderStyle bottom, Color bottomColor,
                BorderStyle left,   Color leftColor
        ) {
            style.setBorderTop   (top);
            style.setTopBorderColor(
                    topColor == null?defColor:
                            colorMap.computeIfAbsent(topColor.getRGB(), k -> new XSSFColor(topColor, defaultIndexedColorMap))
            );

            style.setBorderRight (right);
            style.setRightBorderColor(
                    topColor == null?defColor:
                            colorMap.computeIfAbsent(rightColor.getRGB(), k -> new XSSFColor(rightColor, defaultIndexedColorMap))
            );

            style.setBorderBottom(bottom);
            style.setBottomBorderColor(
                    topColor == null?defColor:
                            colorMap.computeIfAbsent(bottomColor.getRGB(), k -> new XSSFColor(bottomColor, defaultIndexedColorMap))
            );

            style.setBorderLeft  (left);
            style.setLeftBorderColor(
                    topColor == null?defColor:
                            colorMap.computeIfAbsent(leftColor.getRGB(), k -> new XSSFColor(leftColor, defaultIndexedColorMap))
            );
            return this;
        }
        public StyleBuilderInner setBorder(BorderStyle top,BorderStyle right,BorderStyle bottom,BorderStyle left) {
            return setBorder(
                    top,null,
                    right,null,
                    bottom,null,
                    left,null
            );
        }
        public StyleBuilderInner setBorder(BorderStyle vertical, BorderStyle horizontal) {
            return setBorder(vertical,horizontal,vertical,horizontal);
        }
        public StyleBuilderInner setBorder(BorderStyle vertical,Color verticalColor, BorderStyle horizontal,Color horizontalColor) {
            return setBorder(
                    vertical,verticalColor,
                    horizontal,horizontalColor,
                    vertical,verticalColor,
                    horizontal,horizontalColor
            );
        }
        public StyleBuilderInner setBorder(BorderStyle vertical,Color verticalColor, BorderStyle horizontal) {
            return setBorder(
                    vertical,verticalColor,
                    horizontal,null,
                    vertical,verticalColor,
                    horizontal,null
            );
        }
        public StyleBuilderInner setBorder(BorderStyle vertical, BorderStyle horizontal,Color horizontalColor) {
            return setBorder(
                    vertical,null,
                    horizontal,horizontalColor,
                    vertical,null,
                    horizontal,horizontalColor
            );
        }

        public StyleBuilderInner setBorder(
                BorderStyle top,        Color topColor,
                BorderStyle horizontal, Color horizontalColor,
                BorderStyle bottom,     Color bottomColor
        ) {
            return setBorder(top,topColor,horizontal,horizontalColor,bottom,bottomColor,horizontal,horizontalColor);
        }

        public StyleBuilderInner setBorder(
                BorderStyle top, Color topColor,
                BorderStyle horizontal,
                BorderStyle bottom
        ) {
            return setBorder(
                    top,topColor,
                    horizontal,null,
                    bottom,null,
                    horizontal,null);
        }
        public StyleBuilderInner setBorder(
                BorderStyle top,        Color topColor,
                BorderStyle horizontal, Color horizontalColor,
                BorderStyle bottom
        ) {
            return setBorder(
                    top,topColor,
                    horizontal,horizontalColor,
                    bottom,null,
                    horizontal,horizontalColor
            );
        }
        public StyleBuilderInner setBorder(
                BorderStyle top,    Color topColor,
                BorderStyle horizontal,
                BorderStyle bottom, Color bottomColor) {
            return setBorder(
                    top,topColor,
                    horizontal,null,
                    bottom,bottomColor,
                    horizontal,null
            );
        }

        public StyleBuilderInner setBorder(
                BorderStyle top,
                BorderStyle horizontal,
                BorderStyle bottom
        ) {
            return setBorder(top,horizontal,bottom,horizontal);
        }



        public StyleBuilderInner setBorder(BorderStyle border) {
            return setBorder(border,border,border,border);
        }
        public StyleBuilderInner setBorder(BorderStyle border,Color borderColor) {
            return setBorder(
                    border,borderColor,
                    border,borderColor,
                    border,borderColor,
                    border,borderColor
            );
        }


        public XSSFCellStyle build() {
            return style;
        }
    }


}
