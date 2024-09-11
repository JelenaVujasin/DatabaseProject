package controller;

import com.mysql.cj.xdevapi.Schema;
import utils.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class QueryChecker {
    private Connection connection;

    public boolean daLiTabelaPostoji(String imeTabele) throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://" + Constants.MYSQL_IP + "/" + Constants.MYSQL_DATABASE, Constants.MYSQL_USERNAME, Constants.MYSQL_PASSWORD);
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables(connection.getCatalog(), null, null, null);
        while (tables.next()) {
            String tableName = tables.getString("TABLE_NAME");
            ;
            if (tableName.equals(imeTabele) || imeTabele.equals(Constants.MYSQL_DATABASE + "." + tableName)) {
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

    public boolean SQLJoin(String imeTabele1, String imeTabele2, String kolona1, String kolona2) throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://" + Constants.MYSQL_IP + "/" + Constants.MYSQL_DATABASE, Constants.MYSQL_USERNAME, Constants.MYSQL_PASSWORD);
        DatabaseMetaData metaData = connection.getMetaData();
        imeTabele1= imeTabele1.replaceAll("bp_tim90.", "");
        imeTabele2= imeTabele2.replaceAll("bp_tim90.", "");

        String tableType[] = {"TABLE"};
        ResultSet tables = metaData.getTables(connection.getCatalog(), null, null, tableType);

        ArrayList <String> pkey = new ArrayList<>();
        ArrayList <String> fkey = new ArrayList<>();

        while(tables.next()){
            if(tables.getString("TABLE_NAME").equals(imeTabele1)){
                ResultSet pkeys = metaData.getPrimaryKeys(connection.getCatalog(), null, imeTabele2);
                while (pkeys.next())
                    if(!pkey.contains(pkeys.getString("COLUMN_NAME")))
                        pkey.add(pkeys.getString("COLUMN_NAME"));
            }else if(tables.getString("TABLE_NAME").equals(imeTabele2)){
                ResultSet fkeys = metaData.getImportedKeys(connection.getCatalog(), null, imeTabele1);
                while (fkeys.next())
                    if(!fkey.contains(fkeys.getString("FKCOLUMN_NAME")))
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
        if(pkey.contains(kolona1) && fkey.contains(kolona2))
            return true;

        return false;
    }

    public boolean daLiKolonePostoje(ArrayList<String> kolone, String imeTabele) throws SQLException {
        int increment = 0;
        connection = DriverManager.getConnection("jdbc:mysql://" + Constants.MYSQL_IP + "/" + Constants.MYSQL_DATABASE, Constants.MYSQL_USERNAME, Constants.MYSQL_PASSWORD);
        DatabaseMetaData metaData = connection.getMetaData();

        imeTabele= imeTabele.replaceAll("bp_tim90.", "");
        ResultSet columns = metaData.getColumns(connection.getCatalog(), null, imeTabele, null);


        while(columns.next()){
            String kolona = columns.getString("COLUMN_NAME");
            if(kolone.contains(kolona)){
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
        if(increment == kolone.size())
            return true;
        return false;
    }
}
