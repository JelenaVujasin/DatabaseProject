package query.rules;

import utils.Constants;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class RuleColumn extends TextAreaParse implements Rule {
    private String kolonaPala;
    private String uKojaTabela;
    @Override
    public boolean check(String[] textArea) throws SQLException {
        if (textArea[0].equalsIgnoreCase("Select") && textArea[1].equalsIgnoreCase("*"))
            return true;
        else if (textArea[0].equalsIgnoreCase("Select")) {

            TextAreaParse textAreaParse = new TextAreaParse();
            Connection connection;
            int increment = 0;
            connection = DriverManager.getConnection("jdbc:mysql://" + Constants.MYSQL_IP + "/" + Constants.MYSQL_DATABASE, Constants.MYSQL_USERNAME, Constants.MYSQL_PASSWORD);
            DatabaseMetaData metaData = connection.getMetaData();

            ArrayList<String> imeTabele = textAreaParse.parseTable(textArea);
            ResultSet columns = metaData.getColumns(connection.getCatalog(), null, imeTabele.get(0), null);

            if (textAreaParse.parseColumnAfterSelect(textArea).contains("*"))
                return true;
            ArrayList<String> kolone = textAreaParse.parseColumnAfterSelect(textArea);
            while (columns.next()) {
                String kolona = columns.getString("COLUMN_NAME");
                if (kolone.contains(kolona)) {
                    increment++;
                }
            }
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            } finally {
                connection = null;
            }
            if (increment == kolone.size())
                return true;
            return false;
        }else
            return true;
    }
}
