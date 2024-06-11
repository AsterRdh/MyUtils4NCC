package nccloud.utils.excel;

public class ExcelUtils {

    /**
     * 列数转Excel列
     * @param col 列数 从1开始
     * @return Excel列 如AA
     */
    public static String caseToExcelColIndex(int col){
        int temp = col ;
        StringBuilder eCol = new StringBuilder();
        while (temp > 26){
            int i = temp % 26;
            temp = temp / 26;
            char a = (char) ( i + 64 );
            eCol.insert(0, a);
        }
        char a = (char) ( temp + 64 );
        eCol.insert(0, a);


        return eCol.toString();
    }

    /**
     * Excel列转列数
     * @param excelCol Excel列 如AAA
     * @return 列数 从1开始
     */
    public static int caseToColIndex(String excelCol){
        String temp = excelCol.toUpperCase();
        int i = 0;

        char c1 = temp.charAt(temp.length() - 1);
        i += c1-64;

        int mu = 0;
        for (int length = excelCol.length()-1; length > 0; length--) {
            char c = excelCol.charAt(length - 1);
            i += 26 * (int)Math.pow(26,mu++) * (c-64);

        }
        return i;
    }
}
