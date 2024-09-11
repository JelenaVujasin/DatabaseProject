package resource.enums;

import java.util.ArrayList;
import java.util.List;

public enum SQLOdgovara {
    SELECT (),
    CREATE (),
    ALTER (),
    DROP (),
    TRUNCATE (),
    TABLE ("ALTER", "CREATE", "DROP", "SELECT", "UPDATE"),
    INSERT (),
    UPDATE (),
    DELETE (),
    FROM ("DELETE", "SELECT"),
    WHERE ("FROM", "UPDATE"),
    INTO ("INSERT"),
    BETWEEN ("WHERE"),
    IN ("WHERE"),
    NOT ("WHERE"),
    EXIST ("WHERE", "FROM"),
    LIKE ("NOT", "WHERE"),
    HAVING ("GROUP", "WHERE"),
    VALUES ("INTO", "TABLE"),
    JOIN ("RIGHT", "INNER", "LEFT", "ALTER", "OUTER", "FROM"),
    RIGHT ("FROM"),
    INNER ("FROM"),
    LEFT ("FROM"),
    OUTER ("FROM"),
    GROUP ("WHERE", "FROM"),
    BY ("GROUP", "ORDER"),
    ORDER ("WHERE", "BY"),
    ASC ("HAVING", "ORDER", "BY"),
    DESC ("HAVING", "ORDER", "BY"),
    DISTINCT ("TABLE"),
    ON ("LEFT", "RIGHT", "INNER", "OUTER", "JOIN");

    private final List <String> odgovara;

    SQLOdgovara(String st1, String st2, String st3, String st4, String st5) {
        odgovara = new ArrayList<>();
        this.odgovara.add(st1);
        this.odgovara.add(st2);
        this.odgovara.add(st3);
        this.odgovara.add(st4);
        this.odgovara.add(st5);
    }

    SQLOdgovara() {
        odgovara = new ArrayList<>();
    }

    SQLOdgovara(String st1) {
        odgovara = new ArrayList<>();
        this.odgovara.add(st1);
    }

    SQLOdgovara(String st1, String st2) {
        odgovara = new ArrayList<>();
        this.odgovara.add(st1);
        this.odgovara.add(st2);
    }

    SQLOdgovara(String st1, String st2, String st3, String st4, String st5, String st6) {
        odgovara = new ArrayList<>();
        this.odgovara.add(st1);
        this.odgovara.add(st2);
        this.odgovara.add(st3);
        this.odgovara.add(st4);
        this.odgovara.add(st5);
        this.odgovara.add(st6);
    }

    SQLOdgovara(String st1, String st2, String st3) {
        odgovara = new ArrayList<>();
        this.odgovara.add(st1);
        this.odgovara.add(st2);
        this.odgovara.add(st3);
    }

    public List<String> getOdgovara() {
        return odgovara;
    }
}
