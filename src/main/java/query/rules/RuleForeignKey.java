package query.rules;

import utils.Constants;

import java.sql.*;
import java.util.ArrayList;

public class RuleForeignKey implements Rule {

    @Override
    public boolean check(String [] textArea) throws SQLException {
        for(var b : textArea) {
            if (b.equalsIgnoreCase("on")) {


                TextAreaParse textAreaParse = new TextAreaParse();
                Connection connection;
                connection = DriverManager.getConnection("jdbc:mysql://" + Constants.MYSQL_IP + "/" + Constants.MYSQL_DATABASE, Constants.MYSQL_USERNAME, Constants.MYSQL_PASSWORD);
                DatabaseMetaData metaData = connection.getMetaData();
                ArrayList<String> imeTabele = new ArrayList<>();
                imeTabele = textAreaParse.parseTable(textArea);

                String tableType[] = {"TABLE"};
                ResultSet tables = metaData.getTables(connection.getCatalog(), null, null, tableType);

                ArrayList<String> pkey = new ArrayList<>();
                ArrayList<String> fkey = new ArrayList<>();

                ArrayList<String> kolone = textAreaParse.parseColumnAfterOn(textArea);

                while (tables.next()) {
                    if (tables.getString("TABLE_NAME").equals(imeTabele.get(0))) {
                        ResultSet pkeys = metaData.getPrimaryKeys(connection.getCatalog(), null, imeTabele.get(1));
                        while (pkeys.next())
                            if (!pkey.contains(pkeys.getString("COLUMN_NAME")))
                                pkey.add(pkeys.getString("COLUMN_NAME"));
                    } else if (tables.getString("TABLE_NAME").equals(imeTabele.get(1))) {
                        ResultSet fkeys = metaData.getImportedKeys(connection.getCatalog(), null, imeTabele.get(0));
                        while (fkeys.next())
                            if (!fkey.contains(fkeys.getString("FKCOLUMN_NAME")))
                                fkey.add(fkeys.getString("FKCOLUMN_NAME"));
                    }
                }
                try {
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } finally {
                    connection = null;
                }
                if (pkey.contains(kolone.get(0)) && fkey.contains(kolone.get(1)))
                    return true;
                return false;
            }
        }
        return true;

    }


}
