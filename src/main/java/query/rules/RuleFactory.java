package query.rules;

import query.Description;

import java.sql.SQLException;
import java.util.Stack;

public class RuleFactory {
    RuleAlias ruleAlias = new RuleAlias();
    RuleColumn ruleColumn = new RuleColumn();
    RuleForeignKey ruleForeignKey = new RuleForeignKey();
    RuleGroupBy ruleGroupBy = new RuleGroupBy();
    RuleMandatoryQuery ruleMandatoryQuery = new RuleMandatoryQuery();
    RuleOrderQuery ruleOrderQuery = new RuleOrderQuery();
    RuleSameDataType ruleSameDataType = new RuleSameDataType();
    RuleTable ruleTable = new RuleTable();
    RuleUsageOfVariable ruleUsageOfVariable = new RuleUsageOfVariable();
    RuleWhere ruleWhere = new RuleWhere();

    public boolean choseRule(String name, String [] textArea) throws SQLException {
        switch (name){
            case "RuleAlias":
                return ruleAlias.check(textArea);
            case "RuleColumn":
                return ruleColumn.check(textArea);
            case "RuleForeignKey":
                return ruleForeignKey.check(textArea);
            case "RuleGroupBy":
                return ruleGroupBy.check(textArea);
            case "RuleMandatoryQuery":
                return ruleMandatoryQuery.check(textArea);
            case "RuleOrderQuery":
                return ruleOrderQuery.check(textArea);
            case "RuleSameDataType":
                return ruleSameDataType.check(textArea);
            case "RuleTable":
                return ruleTable.check(textArea);
            case "RuleUsageOfVariable":
                return ruleUsageOfVariable.check(textArea);
            case "RuleWhere":
                return ruleWhere.check(textArea);
            default:
                return true;
        }
    }
}