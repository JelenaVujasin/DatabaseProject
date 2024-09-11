package resource.enums;

public enum SQLCommand {
    SELECT (1),
    CREATE (1),
    ALTER (1),
    DROP (1),
    TRUNCATE (1),
    TABLE (2),
    INSERT (1),
    UPDATE (1),
    DELETE (1),
    FROM (2),
    WHERE (3),
    INTO (2),
    BETWEEN (4),
    IN (4),
    NOT (4),
    EXIST (4),
    LIKE (4),
    HAVING (5),
    VALUES (3),
    SET (2),
    JOIN (3),
    RIGHT (3),
    INNER (3),
    LEFT (3),
    OUTER (3),
    GROUP (5),
    BY (5),
    ORDER (5),
    ASC (6),
    DESC (6),
    DISTINCT (2),
    ON (4);

    private final int prednost;

    SQLCommand(int i) {
        this.prednost=i;
    }

    public int getPrednost(){
        return this.prednost;
    }
}
