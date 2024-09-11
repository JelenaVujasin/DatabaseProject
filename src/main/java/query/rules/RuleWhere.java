package query.rules;

import resource.enums.SQLCommand;

public class RuleWhere implements Rule{

    @Override
    public boolean check(String [] textArea) {
        String [] parse = new String[0];
        SQLCommand[] sqlCommands = SQLCommand.values();
        for(int i = 0; i<textArea.length; i++){
            if(textArea[i].equalsIgnoreCase("where")){
                while(i<textArea.length){
                    i++;
                    for(var x : sqlCommands){
                        if(i<textArea.length && textArea[i].equalsIgnoreCase(String.valueOf(x))){
                            return true;
                        }
                    }if(i < textArea.length)
                        parse = textArea[i].split("[(]");
                    if(parse.length == 2) {
                        if (parse[0].equalsIgnoreCase("max") || parse[0].equalsIgnoreCase("min")
                                || parse[0].equalsIgnoreCase("count") || parse[0].equalsIgnoreCase("avg")
                                || parse[0].equalsIgnoreCase("sum")) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
