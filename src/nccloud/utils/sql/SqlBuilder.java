package nccloud.utils.sql;

import nc.vo.pub.SuperVO;
import nccloud.utils.collection.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SqlBuilder {

    public static String buildBaseSql(String tableName,Set<String> columns, String condition){
        StringBuilder stringBuilder = new StringBuilder("SELECT ");
        if (CollectionUtils.isNotBlank(columns)){
            Iterator<String> iterator = columns.iterator();
            while (iterator.hasNext()){
                stringBuilder = stringBuilder.append(iterator.next()).append(" ");
                if (iterator.hasNext()){
                    stringBuilder = stringBuilder.append(", ");
                }
            }
        }else {
            stringBuilder = stringBuilder.append("* ");
        }
        stringBuilder = stringBuilder.append("FROM ").append(tableName).append(" WHERE 1=1 ").append(condition);
        return stringBuilder.toString();
    }


    /**
     * 构建InSql
     * @param tableName 要查询的表名
     * @param columns 要查询的字段
     * @param condition inSql In的字段
     * @param inItems 范围
     * @param otherSQL 其他sql语句
     * @return
     */
    public static String InSqlBuilder(String tableName, String columns, String condition, List<String> inItems, String otherSQL){
        StringBuffer sb=new StringBuffer("select ");
        sb.append(columns);
        sb.append(" from ");
        sb.append(tableName);
        sb.append(" where ");
        sb.append(inBuilder(condition,inItems));
        if (!StringUtils.isEmpty(otherSQL)){
            sb.append(otherSQL);
        }
        return sb.toString();
    }

    public static String inBuilder(String condition, Set<String> items){
        return inBuilder(condition,new ArrayList<>(items));
    }
    public static String notInBuilder(String condition, Set<String> items){
        return notInBuilder(condition,new ArrayList<>(items));
    }
    public static String notInBuilder(String condition, List<String> items){
        if (items.size()==0) return " 1=1 ";
        StringBuffer inBuffer=new StringBuffer(" ");
        inBuffer.append(condition);
        inBuffer.append(" not in (");
        int itemCount=0;
        Iterator<String> iterator = items.iterator();
        while (iterator.hasNext()){
            String str= iterator.next();
            if(str==null) continue;
            inBuffer.append("'").append(str).append("'");
            if (iterator.hasNext()) {
                if (++itemCount==200){
                    inBuffer.append(") and ").append(condition).append(" not in (");
                    itemCount=0;
                }else {
                    inBuffer.append(",");
                }
            }
        }
        inBuffer.append(") ");
        return inBuffer.toString();
    }
    /**
     * IN 语句构建器
     * @param condition 条件
     * @param items 内容
     * @return
     */
    public static String inBuilder(String condition, List<String> items){
        if (items.size()==0) return " 1=2 ";
        StringBuffer inBuffer=new StringBuffer(" ");
        inBuffer.append(condition);
        inBuffer.append(" in (");
        int itemCount=0;
        Iterator<String> iterator = items.iterator();
        int count = 0;
        while (iterator.hasNext()){
        	String str= iterator.next();
        	if(str==null) continue;
            count++;
            inBuffer.append("'").append(str).append("'");
            if (iterator.hasNext()) {
                if (++itemCount==200){
                    inBuffer.append(") or ").append(condition).append(" in (");
                    itemCount=0;
                }else {
                    inBuffer.append(",");
                }
            }
        }
        inBuffer.append(") ");
        if (count == 0)return "1=2";
        return inBuffer.toString();
    }
    public static String inBuilderByVOS(String inCondition, String condition, List< ? extends SuperVO> vos){
        if (vos.size()==0) return " 1=1 ";
        StringBuffer inBuffer=new StringBuffer(" ");
        inBuffer.append(inCondition);
        inBuffer.append(" in (");
        int itemCount=0;
        Iterator<SuperVO> iterator = (Iterator<SuperVO>) vos.iterator();
        while (iterator.hasNext()){
            inBuffer.append("'").append(iterator.next().getAttributeValue(condition)).append("'");
            if (iterator.hasNext()) {
                if (++itemCount==200){
                    inBuffer.append(") or ").append(inCondition).append(" in (");
                    itemCount=0;
                }else {
                    inBuffer.append(",");
                }
            }
        }
        inBuffer.append(") ");
        return inBuffer.toString();
    }

    public static String inBuilderByVOS(String condition, List< ? extends SuperVO> vos){

        return inBuilderByVOS(condition,condition,vos);
    }


    public static String whereBuilder(){
        return "";
    }



}
