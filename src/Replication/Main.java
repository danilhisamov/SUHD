package Replication;

import Genoms.DBConnection;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DBConnection.getConnection();
        for (int i = 0; i < 10; i++) {
            Statement stm = connection.createStatement();
            stm.execute("insert into test.log_first(name, num) values(" + "\'simple_name_" + i + "\', " + i + ")");
        }

        for (int i = 0; i < 10; i++) {
            Statement stm = connection.createStatement();
            stm.execute("update test.log_first set name = " + "\'ch_simple_name_" + i + "\' where num = " + i);
        }

        PreparedStatement ps = connection.prepareStatement("select * from test.log_table where commited = ? order by query_date asc");
        ps.setBoolean(1, false);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String logTableQuery = rs.getString("query");
            Timestamp ts = rs.getTimestamp("query_date");
            String tableName = rs.getString("table_name");
            String q = logTableQuery.replace("log_first", "log_second");

            PreparedStatement ps2 = connection.prepareStatement(q);
            ps2.execute();

            PreparedStatement ps3 = connection.prepareStatement("update test.log_table set commited = ? where table_name = ? and query_date = ?");
            ps3.setBoolean(1, true);
            ps3.setString(2, tableName);
            ps3.setTimestamp(3, ts);
            ps3.execute();
        }
    }
}
