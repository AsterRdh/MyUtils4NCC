package nccloud.utils.sql;

public class TestBuilder {
    public static void main(String[] args) {
        String build = new SqlBuilderNew().query()
                .select("a", "b", "c")
                .from("table")
                .where("a").eq("a")
                .and("b").notEq(1)
                .append(
                        " and exists ( \n"+
                        new SqlBuilderNew().query()
                        .select("1")
                        .from("table2")
                        .where("table.a").eq("table2.a")
                        .buildNotEnd()+")"
                )
                .build();
        System.out.println(build);

    }
}
