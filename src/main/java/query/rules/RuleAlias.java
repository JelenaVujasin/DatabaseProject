package query.rules;

import resource.enums.SQLCommand;

import java.util.ArrayList;

public class RuleAlias implements Rule {

    @Override
    public boolean check(String[] textArea) {
        int h = 0;
        SQLCommand[] sqlCommands = SQLCommand.values();
        for (int i = 0; i < textArea.length; i++) {
            if(i+1 < textArea.length && textArea[i+1].equalsIgnoreCase("=")){
                continue;
            }
            if ( i + 2 < textArea.length && textArea[i].equalsIgnoreCase("as")) {
                if (textArea[i + 1].charAt(0) != '"' && textArea[i + 1].charAt(textArea[i + 1].length() - 1) != ',') {
                    for (var x : sqlCommands) {
                        if (textArea[i + 2].equalsIgnoreCase(String.valueOf(x))) {
                            h++;
                        }
                    }
                    if(h==0){
                        return false;
                    }else
                        h = 0;
                }
            } /*else if (i+2 < textArea.length && textArea[i+1].charAt(textArea[i+1].length() - 1) != ',' && textArea[i+1].charAt(0) != '"') {
                for (var x : sqlCommands) {
                    if (textArea[i + 2].equalsIgnoreCase(String.valueOf(x)) || textArea[i].equalsIgnoreCase(String.valueOf(x)) || textArea[i+1].equalsIgnoreCase(String.valueOf(x))) {
                        h++;
                    }
                }
                if(h==0){
                    return false;
                }else
                    h = 0;
            }*/
        }
        return true;
    }
}
