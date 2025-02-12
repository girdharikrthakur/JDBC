import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        String tableName = "employee";
        String createTable = "create table employee(id int primary key, name varchar(255), phone varchar(20), email varchar(255), password varchar(20))";
        String query = "select * from employee;";
        String insertBase = "insert into employee(id,name,phone,email,password) values(%d, '%s', '%s', '%s', '%s')";

        int startId = 1; // Starting ID to check

        Employee employee = new Employee();
        final String url = "jdbc:mysql://localhost:3306/hibernate";
        final String user = "root";
        final String password = "admin";

        try (Connection connection = DriverManager.getConnection(url, user, password);
                Statement stmt = connection.createStatement();
                Scanner scn = new Scanner(System.in);) {

            Class.forName("com.mysql.cj.jdbc.Driver");
            DatabaseMetaData dbMetaData = connection.getMetaData();
            ResultSet tables = dbMetaData.getTables(null, null, tableName, null);

            if (tables.next()) {
                System.out.println("Table " + tableName + " exists in the database.");
            } else {
                stmt.execute(createTable);
                System.out.println("Table " + tableName + " does not exist in the database.");
            }

            System.out.println("Enter your name:");
            employee.setName(scn.nextLine());
            System.out.println("Enter your phone:");
            employee.setPhone(scn.nextLine());
            System.out.println("Enter your email:");
            employee.setEmail(scn.nextLine());
            System.out.println("Enter your password:");
            employee.setPassword(scn.nextLine());

            int uniqueId = getUniqueId(stmt, startId);

            String insertQuery = String.format(insertBase, uniqueId, employee.getName(), employee.getPhone(),
                    employee.getEmail(),
                    employee.getPassword());
            stmt.executeUpdate(insertQuery);
            System.out.println("Inserted employee with ID: " + uniqueId);

            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                System.out.println("\t" + result.getInt("id") + "\t" + result.getString("name") + "\t"
                        + result.getString("phone"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static int getUniqueId(Statement stmt, int startId) throws SQLException {
        int idToCheck = startId;

        while (true) {
            String checkIdQuery = "SELECT COUNT(*) FROM employee WHERE id = " + idToCheck;
            ResultSet rs = stmt.executeQuery(checkIdQuery);
            rs.next();
            int count = rs.getInt(1);

            if (count == 0) {
                return idToCheck;
            } else {
                idToCheck++;
            }
        }
    }
}
