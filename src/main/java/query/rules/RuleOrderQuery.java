package query.rules;

import resource.enums.SQLCommand;

import java.util.Locale;

public class RuleOrderQuery implements Rule{

    @Override
    public boolean check(String [] textArea) {
        int jacina =0;
        SQLCommand[] sqlCommands = SQLCommand.values();
        for(int i = 0; i < textArea.length; i++){
            String xy = textArea[i].toUpperCase();
            for(var x : sqlCommands){
                if(xy.equals(x.toString())){
                    if(x.getPrednost() >= jacina){
                        jacina = x.getPrednost();
                    }else
                        return false;
                }
            }
        }
        return true;
    }


}
