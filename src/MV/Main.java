package MV;

import Genoms.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, InterruptedException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement("CREATE MATERIALIZED VIEW test.log_first_mv as " +
                "SELECT dp.* from dblink('dbname=test', 'select * from test.log_first') as dp(id integer, name varchar, num integer)");
        ps.execute();
        System.out.println("initialized");

        while (true) {
            ps = connection.prepareStatement("REFRESH MATERIALIZED VIEW test.log_first_mv;"); //add conccurently
            ps.execute();
            System.out.println("refreshed");
            ps = connection.prepareStatement("select * from test.log_first_mv");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
            }
            Thread.sleep(10000);
        }
    }
}
