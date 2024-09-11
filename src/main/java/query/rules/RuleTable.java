package query.rules;

import utils.Constants;

import java.sql.*;
import java.util.ArrayList;

public class RuleTable implements Rule{

    @Override
    public boolean check(String [] textArea) throws SQLException {
        TextAreaParse textAreaParse = new TextAreaParse();
        int iterator = 0;
        Connection connection;
        connection = DriverManager.getConnection("jdbc:mysql://" + Constants.MYSQL_IP + "/" + Constants.MYSQL_DATABASE, Constants.MYSQL_USERNAME, Constants.MYSQL_PASSWORD);
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables(connection.getCatalog(), null, null, null);

        ArrayList<String > imeTabele = new ArrayList<>();
        imeTabele = textAreaParse.parseTable(textArea);

        while (tables.next()) {
            String tableName = tables.getString("TABLE_NAME");

            if (imeTabele.contains(tableName)) {
                iterator++;
            }
            if(iterator == imeTabele.size()){
                try {
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } finally {
                    connection = null;
                }
                return true;
            }
        }
        try {
            connection.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            connection = null;
        }
        return false;
    }


}
