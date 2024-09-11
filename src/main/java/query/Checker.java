package query;

import query.rules.Rule;
import query.rules.RuleAlias;
import query.rules.RuleColumn;
import query.rules.RuleFactory;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Stack;

public class Checker implements Rule{

    private Stack<Description> errors = new Stack<>();
    private ArrayList<String> Classrules = new ArrayList<>();
    private Description description = new Description();
    private RuleFactory ruleFactory = new RuleFactory();

    public void addToList(){
        Classrules.add("RuleAlias");
        Classrules.add("RuleColumn");
        Classrules.add("RuleCSV");
        Classrules.add("RuleForeignKey");
        Classrules.add("RuleGroupBy");
        Classrules.add("RuleMandatoryQuery");
        Classrules.add("RuleOrderQuery");
        Classrules.add("RuleSameDataType");
        Classrules.add("RuleTable");
        Classrules.add("RuleUsageOfVariable");
        Classrules.add("RuleWhere");
    }

    public boolean check(String [] textArea) throws SQLException {
        addToList();
        for(Description rule : description.getRules()){
            String ime = rule.getName();
            if(!ruleFactory.choseRule(ime, textArea)){
                errors.add(rule);
                }
        }
        if(errors.isEmpty()){
            return true;
        }else{
            for(int i = 0;i<errors.size();i++) {
                JOptionPane.showMessageDialog(null, errors.get(i).getDesc() + "\n" + errors.get(i).getSugg());
            }
            errors.clear();
            return false;
        }
    }

}
