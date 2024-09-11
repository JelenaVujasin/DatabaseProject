package app;

import database.Database;
import database.DatabaseImplementation;
import database.MYSQLrepository;
import database.settings.Settings;
import database.settings.SettingsImplementation;
import gui.MainFrame;
import gui.table.TableModel;
import lombok.Getter;
import lombok.Setter;
import observer.Notification;
import observer.enums.NotificationCode;
import observer.implementation.PublisherImplementation;
import org.json.simple.parser.ParseException;
import query.Checker;
import query.Description;
import resource.data.Row;
import resource.implementation.InformationResource;
import tree.Tree;
import tree.implementation.TreeImplementation;
import utils.Constants;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@Getter
@Setter
public class AppCore extends PublisherImplementation {

    private static String lastQuery;
    private Database database;
    private Settings settings;
    private TableModel tableModel;
    private DefaultTreeModel defaultTreeModel;
    private Tree tree;
    private Checker checker = new Checker();

    public AppCore() {
        this.settings = initSettings();
        this.database = new DatabaseImplementation(new MYSQLrepository(this.settings));
        this.tableModel = new TableModel();
        this.tree = new TreeImplementation();

    }

    private Settings initSettings() {
        Settings settingsImplementation = new SettingsImplementation();
        settingsImplementation.addParameter("mysql_ip", Constants.MYSQL_IP);
        settingsImplementation.addParameter("mysql_database", Constants.MYSQL_DATABASE);
        settingsImplementation.addParameter("mysql_username", Constants.MYSQL_USERNAME);
        settingsImplementation.addParameter("mysql_password", Constants.MYSQL_PASSWORD);
        return settingsImplementation;
    }


    public DefaultTreeModel loadResource() throws IOException, ParseException {
        InformationResource ir = (InformationResource) this.database.loadResource();
        Description description = new Description();
        description.loadJSON();
        return this.tree.generateTree(ir);
    }

    public void readDataFromTable(String fromTable) {
        lastQuery = "SELECT * FROM " + fromTable;
        tableModel.setRows(this.database.readDataFromTable(fromTable));
        MainFrame.getInstance().getJTable().setModel(tableModel);


        //Zasto ova linija moze da ostane zakomentarisana?
        this.notifySubscribers(new Notification(NotificationCode.DATA_UPDATED, this.getTableModel()));
    }

    public boolean proveriQuery(String[] textArea) throws SQLException {
        return checker.check(textArea);
    }

    public void baciQuery(String text) throws SQLException {
        String[] check = text.split(" ");
        List<Row> rows = new ArrayList<>();
        if (check[0].equalsIgnoreCase("select")) {
            lastQuery = text;
            String query = text;
            Connection conn;

            conn = DriverManager.getConnection("jdbc:mysql://" + Constants.MYSQL_IP + "/" + Constants.MYSQL_DATABASE, Constants.MYSQL_USERNAME, Constants.MYSQL_PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();

            while (rs.next()) {
                Row row = new Row();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                }
                rows.add(row);
            }

            tableModel.setRows(rows);
            MainFrame.getInstance().getJTable().setModel(tableModel);

            try{
                conn.close();
            }
            catch (SQLException e1){
                e1.printStackTrace();
            }
            finally {
                conn = null;
            }
        }else{
            String query = text;
            Connection conn;

            conn = DriverManager.getConnection("jdbc:mysql://" + Constants.MYSQL_IP + "/" + Constants.MYSQL_DATABASE, Constants.MYSQL_USERNAME, Constants.MYSQL_PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.addBatch();
            preparedStatement.executeBatch();
            JOptionPane.showMessageDialog(null, "Query: " + query + " successfuly pushed");
        }
        return;
    }


    public String getLastQuery() {
        return lastQuery;
    }
}
