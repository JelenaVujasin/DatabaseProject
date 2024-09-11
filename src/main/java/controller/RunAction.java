package controller;

import app.AppCore;
import gui.MainFrame;
import lombok.SneakyThrows;
import query.Checker;
import resource.enums.SQLCommand;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RunAction extends AbstractAction {
    public RunAction() {
        putValue(NAME, "Run");
    }

    AppCore appCore = new AppCore();

    @SneakyThrows
    @Override
    public void actionPerformed(ActionEvent e) {
        Checker checker = new Checker();
        QueryChecker queryChecker = new QueryChecker();
        JTextPane textPane = MainFrame.getInstance().getTextPane();
        boolean flag = false;
        boolean flagAS = false;
        String text = new String();
        text = textPane.getText();
        textPane.setText("");
        SQLCommand[] sqlCommands = SQLCommand.values();
        String[] delovi = text.split("[\r\n ]");
        if(appCore.proveriQuery(delovi))
            appCore.baciQuery(text);
    }
}