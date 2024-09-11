package controller;

import app.AppCore;
import database.Database;
import database.MYSQLrepository;
import gui.MainFrame;
import gui.table.TableModel;
import query.Checker;
import query.Description;
import query.rules.RuleCSV;
import utils.Constants;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class BulkAction extends AbstractAction{

    public BulkAction() {
        putValue(NAME,"Bulk");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean flag = false;
        String insertQuery = "";
        String insq = "";
        String x;
        RuleCSV ruleCSV = new RuleCSV();
        Checker checker = new Checker();

        try{
            String imeTabele = MainFrame.getInstance().getJTree().getLastSelectedPathComponent().toString();
            conn = DriverManager.getConnection("jdbc:mysql://" + Constants.MYSQL_IP + "/" + Constants.MYSQL_DATABASE, Constants.MYSQL_USERNAME, Constants.MYSQL_PASSWORD);

            JFrame parentFrame = new JFrame();

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Open CSV File");
            FileFilter fnef1 = new FileNameExtensionFilter("CSV Document", "csv");
            fileChooser.addChoosableFileFilter(fnef1);
            DatabaseMetaData metaData = conn.getMetaData();

            ResultSet columns = metaData.getColumns(conn.getCatalog(), null, imeTabele, null);


            int userSelection = fileChooser.showOpenDialog(parentFrame);
            BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                while ((x = reader.readLine()) != null) {
                    if (!flag) {
                        x = x.replaceAll("\"", "");
                        String [] parse = x.split(",");
                        if(!ruleCSV.check(parse, columns)) {
                            JOptionPane.showMessageDialog(null, "Selected CSV file is not compatible with "+ imeTabele+ " table" + "\n" + "Try to select another table with same structure as selected CSV file or create table with same structure." + "\n");
                            try{
                                conn.close();
                            }
                            catch (SQLException e1){
                                e1.printStackTrace();
                            }
                            finally {
                                conn = null;
                            }
                            reader.close();
                            try {
                                if(stmt != null)
                                    stmt.close();

                                if(conn != null)
                                    conn.close();
                            } catch (SQLException ex) {
                                System.err.print(ex.getMessage());
                            }

                            return;
                        }
                        insertQuery = insertQuery + "INSERT INTO " + imeTabele + " ("+ x + ") VALUES(";
                        flag = true;
                    }else {
                        insq = insertQuery;
                        insq = insq + x +")";
                        stmt = conn.prepareStatement(insq);
                        stmt.addBatch();
                        stmt.executeBatch();
                    }
                }
            }
            MainFrame.getInstance().getAppCore().readDataFromTable(imeTabele);


            reader.close();
        }catch(IOException | SQLException ex){
            System.err.print(ex.getMessage());
        }finally{
            try {
                if(stmt != null)
                    stmt.close();

                if(conn != null)
                    conn.close();
            } catch (SQLException ex) {
                System.err.print(ex.getMessage());
            }
        }
        try{
            conn.close();
        }
        catch (SQLException e1){
            e1.printStackTrace();
        }
        finally {
            conn = null;
        }

    }
}
