package gui;

import app.AppCore;
import controller.ActionManager;
import database.MYSQLrepository;
import lombok.Data;
import lombok.Getter;
import observer.Notification;
import observer.Subscriber;
import org.json.simple.parser.ParseException;
import tree.implementation.SelectionListener;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.IOException;

@Data
@Getter
public class MainFrame extends JFrame implements Subscriber {

    private static MainFrame instance = null;
    //jsp - JScrollPane
    private AppCore appCore;
    private JTable jTable;
    private JScrollPane jsp;
    private JScrollPane jspHorizontal;
    private JScrollPane jspTable;
    private JPanel buttonPanel;
    private JTree jTree;
    private JPanel treePanel;
    private JPanel desniGlavniPanel;
    private JPanel gorePanel;
    private JPanel tablePanel;
    private JPanel textPanel;
    private JTextPane textPane;
    private JSplitPane splitPane;
    private JButton run;
    private JButton pretty;
    private JButton bulk;
    private JButton export;
    private ActionManager actionManager;

    private MainFrame() {
    }

    public static MainFrame getInstance(){
        if (instance==null){
            instance=new MainFrame();
            instance.initialise();
        }
        return instance;
    }


    private void initialise() {

        //Podesavanje glavnog prozora
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(700,700));
        this.setMinimumSize(new Dimension(300,300));


        //Podesavanje izgleda tabele
        jTable = new JTable();
        jTable.setPreferredScrollableViewportSize(new Dimension(250, 300));
        jTable.setFillsViewportHeight(true);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        initActionManager();
        initialiseGUI();

    }
    private void initActionManager(){
        actionManager = new ActionManager();
    }

    private void initialiseGUI(){
        initButtons();
        initPanels();
        //dodavanje horizontalnog scrollpane
        textPane = new JTextPane();
        jspHorizontal = new JScrollPane(textPane,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        textPanel.add(jspHorizontal);
        jspHorizontal.setMinimumSize(new Dimension(150, 0));
        //dodavanje scrolla za tabelu
        tablePanel.add(jTable,BorderLayout.CENTER);
        jspTable = new JScrollPane(jTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //SplitPane
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jspHorizontal, buttonPanel);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(1);
        addButtons();
        //Dodavanje panela
        gorePanel.add(splitPane,BorderLayout.CENTER);
        tablePanel.add(jspTable);

        desniGlavniPanel.add(gorePanel,BorderLayout.NORTH);
        desniGlavniPanel.add(tablePanel,BorderLayout.CENTER);

        add(treePanel, BorderLayout.WEST);
        add(desniGlavniPanel,BorderLayout.CENTER);

    }

    private void initButtons(){
        //Inicijalizacija
        run = new JButton("Run");
        bulk = new JButton("Bulk");
        export = new JButton("Export");
        pretty = new JButton("Pretty");
        //Alignment
        run.setAlignmentX(Component.CENTER_ALIGNMENT);
        bulk.setAlignmentX(Component.CENTER_ALIGNMENT);
        export.setAlignmentX(Component.CENTER_ALIGNMENT);
        pretty.setAlignmentX(Component.CENTER_ALIGNMENT);
        //ActionListener
        pretty.addActionListener(actionManager.getPrettyAction());
        export.addActionListener(actionManager.getExportAction());
        bulk.addActionListener(actionManager.getBulkAction());
        run.addActionListener(actionManager.getRunAction());
    }

    private void rigidArea(){
        buttonPanel.add(Box.createRigidArea(new Dimension(20,10)));
    }

    private void initPanels(){
        treePanel = new JPanel(new BorderLayout());
        desniGlavniPanel = new JPanel(new BorderLayout());
        gorePanel = new JPanel(new BorderLayout());
        tablePanel = new JPanel(new BorderLayout());
        textPanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setMinimumSize(new Dimension(120,50));
    }

    private void addButtons(){
        rigidArea();
        buttonPanel.add(run);
        rigidArea();
        buttonPanel.add(bulk);
        rigidArea();
        buttonPanel.add(export);
        rigidArea();
        buttonPanel.add(pretty);
        rigidArea();
    }

    public void setAppCore(AppCore appCore) throws IOException, ParseException {
        this.appCore = appCore;
        this.appCore.addSubscriber(this);
        this.jTable.setModel(appCore.getTableModel());
        initialiseTree();
    }

    private void initialiseTree() throws IOException, ParseException {
        //Tree inicijalizacija
        DefaultTreeModel defaultTreeModel = appCore.loadResource();
        jTree = new JTree(defaultTreeModel);
        jTree.addTreeSelectionListener(new SelectionListener());
        jsp = new JScrollPane(jTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        treePanel.add(jsp, BorderLayout.CENTER);

        pack();
    }

    public void errorWindow(){

    }
    @Override
    public void update(Notification notification) {
    }

}
