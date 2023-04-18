import java.util.Random;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class Main {
    public static void main(String[] args) throws SQLException {
        Random random = new Random();
        //Class.forName("org.sqlite.JDBC");
        Connection conn = null;
        Statement statement = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:simulation.db");
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            // Do something with the Connection
            statement.executeUpdate("""
                    DROP TABLE IF EXISTS simulations
                    """);
            statement.executeUpdate("""
                    CREATE TABLE simulations (
                    id INTEGER PRIMARY KEY,
                    period INTEGER NOT NULL,
                    clan INTEGER NOT NULL,
                    family INTEGER NOT NULL,
                    generation INTEGER NOT NULL,
                    mean_altruism DOUBLE NOT NULL,
                    mean_patience DOUBLE NOT NULL,
                    std DOUBLE NOT NULL,
                    age INTEGER NOT NULL,
                    children INTEGER NOT NULL,
                    prev_children INTEGER NOT NULL,
                    altruism INTEGER NOT NULL,
                    patience INTEGER NOT NULL,
                    charity INTEGER NOT NULL,
                    goods INTEGER NOT NULL,
                    future_goods INTEGER NOT NULL,
                    self_goods INTEGER NOT NULL,
                    pref INTEGER NOT NULL,
                    utility DOUBLE NOT NULL
                    )
                    """);
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        double sd = 0.1;
        double altruism = 0.1;
        double patience = 0.25;
        int maxA = 10;
        int maxB = 4;
        int maxC = 3;
        for (int a = 0; a < maxA + 1; a++) {
            for (int b = 0; b < maxB + 1; b++) {
                for (int c = 0; c < maxC + 1; c++) {
                    Economy economy = new Economy(1000, random, statement, altruism * a, patience * b, sd * c);
                    for (int i = 0; i < 1; i++) {
                        economy.period();
                        //economy.print();
                        //System.out.println(altruism * a + " " + patience * b + " " + sd * c);
                        //int percent = (c * maxA * maxB) + (b * maxA) + a;
                    }
                    conn.commit();
                }
                System.out.println(a + " " + b);
            }
        }
    }
}