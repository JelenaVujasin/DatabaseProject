package query.rules;

import resource.enums.SQLCommand;
import resource.enums.SQLOdgovara;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RuleMandatoryQuery implements Rule {

    @Override
    public boolean check(String [] textArea) {
        boolean flag = false;
        SQLOdgovara[] sqlOdgovaras = SQLOdgovara.values();
        List<String> moguce = new ArrayList<>();
        for(int i = textArea.length-1; i>=0; i--){
            String xy = textArea[i].toUpperCase();
            for (var x : sqlOdgovaras){
                if(xy.equals(x.toString())){
                    if(!flag){
                        for(var b: x.getOdgovara())
                            moguce.add(b);
                        flag =true;
                    }else{
                        if(!moguce.contains(xy)){
                            return false;
                        }else{
                            moguce.clear();
                            for(var b : x.getOdgovara())
                                moguce.add(b);
                        }
                    }if(moguce.isEmpty())
                        return true;
                }
            }
        }
        return true;
    }

}
