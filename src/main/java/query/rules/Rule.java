package query.rules;

import java.sql.SQLException;

public interface Rule {
    public boolean check(String [] textArea) throws SQLException;
}


