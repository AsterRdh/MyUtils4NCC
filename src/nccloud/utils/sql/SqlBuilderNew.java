package nccloud.utils.sql;

import com.ufida.web.html.Q;
import nccloud.utils.collection.CollectionUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SqlBuilderNew {

    public QueryBuilder query(){
        return new QueryBuilder();
    }


    public class WhereBuilder {
        private StringBuffer stringBuffer = new StringBuffer();
        private Condition condition;
        private IBuilder topBuilder;

        public WhereBuilder(IBuilder topBuilder, String firstCondition) {
            condition = new Condition(firstCondition, this);
            this.topBuilder = topBuilder;
        }
        public Condition and(String condition) {
            stringBuffer = stringBuffer.append(this.condition.toSql()).append(" and");
            this.condition = new Condition(condition, this);
            return this.condition;
        }
        public WhereBuilder append(String subSql) {
            stringBuffer = stringBuffer.append(this.condition.toSql()).append(" ").append(subSql);
            condition = null;
            return this;
        }

        public Condition or(String condition) {
            stringBuffer = stringBuffer.append(this.condition.toSql()).append(" or");
            this.condition = new Condition(condition, this);
            return this.condition;
        }

        public String build() {
            if (condition!=null) stringBuffer = stringBuffer.append(this.condition.toSql()).append(";");
            return topBuilder.build() + " \nwhere" + stringBuffer.toString();
        }
        public String buildNotEnd() {
            if (condition!=null) stringBuffer = stringBuffer.append(this.condition.toSql());
            return topBuilder.build() + " \nwhere" + stringBuffer.toString();
        }
    }



    public class Condition{
        String conditionAttr ;
        WhereBuilder whereBuilder ;
        String sql = " ";
        public Condition(String conditionAttr, WhereBuilder whereBuilder) {
            this.conditionAttr = conditionAttr;
            this.whereBuilder = whereBuilder;
        }

        private String getValue(Object value){
            if (value==null) return null;
            if (value instanceof Number){
                return value.toString();
            }else {
                return "'"+value+"'";
            }
        }

        public WhereBuilder eq(Object value){
            String value1 = getValue(value);
            if (value1==null) return isNull();
            sql+=conditionAttr+" = "+value1;
            return whereBuilder;
        }
        public WhereBuilder notEq(Object value){
            String value1 = getValue(value);
            if (value1==null) return isNotNull();
            sql+=conditionAttr+" != "+value1;
            return whereBuilder;
        }
        public WhereBuilder largerThan(Object value){
            String value1 = getValue(value);
            if (value1==null) return isNotNull();
            sql+=conditionAttr+" > "+value1;
            return whereBuilder;
        }
        public WhereBuilder lowerThan(Object value){
            String value1 = getValue(value);
            if (value1==null) return isNotNull();
            sql+=conditionAttr+" < "+value1;
            return whereBuilder;
        }
        public WhereBuilder largerThanOrEq(Object value){
            String value1 = getValue(value);
            if (value1==null) return isNotNull();
            sql+=conditionAttr+" >= "+value1;
            return whereBuilder;
        }
        public WhereBuilder lowerThanOrEq(Object value){
            String value1 = getValue(value);
            if (value1==null) return isNotNull();
            sql+=conditionAttr+" <= "+value1;
            return whereBuilder;
        }
        public WhereBuilder between(Object value1,Object value2){
            String from = getValue(value1);
            String to = getValue(value2);
            if (value1==null) return isNotNull();
            sql+="("+conditionAttr+" between "+from+" and "+to+")";
            return whereBuilder;
        }
        public WhereBuilder isNull(){
            sql+=conditionAttr+" is null";
            return whereBuilder;
        }
        public WhereBuilder isNotNull(){
            sql+=conditionAttr+" is not null";
            return whereBuilder;
        }
        public <E> WhereBuilder in(Collection<E> values){
            Set<String> collect = values.stream().map(i -> getValue(i)).collect(Collectors.toSet());
            sql+=SqlBuilder.inBuilder(conditionAttr,collect);
            return whereBuilder;
        }
        public <E> WhereBuilder notIn(Collection<E> values){
            Set<String> collect = values.stream().map(i -> getValue(i)).collect(Collectors.toSet());
            sql+=SqlBuilder.notInBuilder(conditionAttr,collect);
            return whereBuilder;
        }

        private String toSql(){
            return sql;
        }

    }

    public interface IBuilder{
        String build();
    }

    public class QueryBuilder implements IBuilder{

        private String tableName = null;
        private Set<String> attrs = null;
        private WhereBuilder whereBuilder ;

        public QueryBuilder select(String ... attrs){
            this.attrs = new HashSet<>(Arrays.asList(attrs));
            return this;
        }

        public QueryBuilder from(String tableName){
            this.tableName = tableName;
            return this;
        }

        public Condition where(String condition){
            whereBuilder = new WhereBuilder(this,condition);
            return whereBuilder.condition;
        }


        @Override
        public String build() {
            return "select "+ CollectionUtils.caseToString(attrs)+" \nfrom "+tableName;
        }
    }
}
