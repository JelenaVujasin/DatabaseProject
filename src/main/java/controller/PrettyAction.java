package controller;

import gui.MainFrame;
import resource.enums.SQLCommand;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PrettyAction extends AbstractAction{

    public PrettyAction() {
        putValue(NAME,"Pretty");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTextPane textPane = MainFrame.getInstance().getTextPane();
        boolean flag = false;
        String text;
        text = textPane.getText();
        textPane.setText("");
        String [] delovi =text.split("[\r\n ]");
        SQLCommand[] sqlCommands = SQLCommand.values();
        String print = "";
        for(var x : delovi){
            String xy = x.toUpperCase();
            for(var y : sqlCommands){
                if(xy.equals(y.toString())){
                    flag = true;
                    print = print + xy + " ";
                    break;
                }
                flag = false;
            }
            if(!flag){
                if(!print.isEmpty()) {
                    if(textPane.getText().equals("")) {
                        appendToPane(textPane, print, Color.BLUE);
                    }
                    else
                        appendToPane(textPane, "\n" + print, Color.BLUE);
                    print = "";
                }
                if(print.isEmpty()){
                    appendToPane(textPane, x+" ", Color.BLACK);
                }
            }
        }
        textPane.removeStyle("  ");
    }
    private void appendToPane(JTextPane tp, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }
}
