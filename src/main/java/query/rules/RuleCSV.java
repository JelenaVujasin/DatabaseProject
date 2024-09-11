package query.rules;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RuleCSV{

    public boolean check(String [] columnNames, ResultSet columns) throws SQLException {

        int i =0;
        while(columns.next()){
            String columnName = columns.getString("COLUMN_NAME");
            for(var x : columnNames){
                if(x.equalsIgnoreCase(columnName))
                    i++;
            }
        }

        if(columnNames.length == i)
            return true;
        return false;
    }


}
