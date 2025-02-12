import java.sql.*;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        String tableName = "employee";
        String createTable = "CREATE TABLE employee (id INT PRIMARY KEY, name VARCHAR(255), phone VARCHAR(20), email VARCHAR(255), password VARCHAR(20))";
        String query = "SELECT * FROM employee";
        String insertBase = "INSERT INTO employee (id, name, phone, email, password) VALUES (%d, '%s', '%s', '%s', '%s')";

        final String url = "jdbc:mysql://localhost:3306/hibernate";
        final String user = "root";
        final String password = "admin";

        try (Connection connection = DriverManager.getConnection(url, user, password);
                Statement stmt = connection.createStatement();
                Scanner scn = new Scanner(System.in)) {

            Class.forName("com.mysql.cj.jdbc.Driver");

            // Check if the table exists
            DatabaseMetaData dbMetaData = connection.getMetaData();
            ResultSet tables = dbMetaData.getTables(null, null, tableName, null);
            if (!tables.next()) {
                stmt.execute(createTable);
                System.out.println("Table '" + tableName + "' was created.");
            } else {
                System.out.println("Table '" + tableName + "' already exists.");
            }

            while (true) {
                System.out.println("\nEnter your Option:");
                System.out.println("1 - Insert Data");
                System.out.println("2 - View All Employees");
                System.out.println("3 - Exit");
                System.out.print("Enter choice: ");

                int option = scn.nextInt();
                scn.nextLine(); // Consume newline

                switch (option) {
                    case 1:
                        Employee employee = new Employee();
                        System.out.print("Enter your name: ");
                        employee.setName(scn.nextLine());
                        System.out.print("Enter your phone: ");
                        employee.setPhone(scn.nextLine());
                        System.out.print("Enter your email: ");
                        employee.setEmail(scn.nextLine());
                        System.out.print("Enter your password: ");
                        employee.setPassword(scn.nextLine());

                        int uniqueId = getUniqueId(stmt, 1); // Get next available ID
                        String insertQuery = String.format(insertBase, uniqueId, employee.getName(),
                                employee.getPhone(),
                                employee.getEmail(), employee.getPassword());
                        stmt.executeUpdate(insertQuery);
                        System.out.println("Inserted employee with ID: " + uniqueId);
                        break;

                    case 2:
                        ResultSet result = stmt.executeQuery(query);
                        System.out.println("\nEmployee Records:");
                        System.out.println("ID\tName\tPhone");
                        while (result.next()) {
                            System.out.println(result.getInt("id") + "\t" + result.getString("name") + "\t"
                                    + result.getString("phone"));
                        }
                        break;

                    case 3:
                        System.out.println("Exiting...");
                        System.exit(0);

                    default:
                        System.out.println("Invalid option, please try again.");
                        break;
                }
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
            if (rs.getInt(1) == 0) {
                return idToCheck;
            }
            idToCheck++;
        }
    }
}
