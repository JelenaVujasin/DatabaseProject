package controller;

import app.AppCore;
import app.Main;
import gui.MainFrame;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import utils.Constants;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class ExportAction extends AbstractAction{

    private AppCore appCore = new AppCore();

    public ExportAction() {
        putValue(NAME,"Export");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        BufferedWriter writer = null;

        try{
            if(appCore.getLastQuery() == null || appCore.getLastQuery().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nothing to export");
                return;
            }
            //Get mysql database connection
            conn = DriverManager.getConnection("jdbc:mysql://"+ Constants.MYSQL_IP+"/"+Constants.MYSQL_DATABASE,Constants.MYSQL_USERNAME,Constants.MYSQL_PASSWORD);
            stmt = conn.prepareStatement(appCore.getLastQuery());


            //Execute query and retrieve data
            rs = stmt.executeQuery();

            JFrame parentFrame = new JFrame();

            JFileChooser fileChooser = new JFileChooser();
            FileFilter fnef1 = new FileNameExtensionFilter("CSV Document", "csv");
            FileFilter fnef2 = new FileNameExtensionFilter("PDF Document", "pdf");
            fileChooser.addChoosableFileFilter(fnef1);
            fileChooser.addChoosableFileFilter(fnef2);
            fileChooser.setDialogTitle("Specify a file to save");

            int userSelection = fileChooser.showSaveDialog(parentFrame);


            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                if(fileChooser.getFileFilter().getDescription() == "CSV Document")
                    writer = new BufferedWriter(new FileWriter(fileToSave.getAbsolutePath() + ".csv"));
                else if(fileChooser.getFileFilter().getDescription() == "PDF Document")
                    writer = new BufferedWriter(new FileWriter(fileToSave.getAbsolutePath() + ".pdf"));
                else
                    writer = new BufferedWriter(new FileWriter(fileToSave.getAbsolutePath()));

                CSVPrinter printer = new CSVPrinter(writer, CSVFormat.EXCEL.withDelimiter(',').withQuote('"').withQuoteMode(QuoteMode.ALL).withHeader(rs));
                printer.printRecords(rs);
                printer.close(true);
                JOptionPane.showMessageDialog(null, "CSV file exported to: " + fileToSave.getPath());

            }
        }catch(IOException | SQLException ex){
            System.err.print(ex.getMessage());
        }finally{
            //Close all resources
            try {
                if(writer != null)
                    writer.close();

                if(rs != null)
                    rs.close();

                if(stmt != null)
                    stmt.close();

                if(conn != null)
                    conn.close();
            } catch (SQLException | IOException ex) {
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
