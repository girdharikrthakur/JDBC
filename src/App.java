import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

//POJO class for database columns

class Employee {
    private String name;
    private String phone;
    private String email;
    private String password;

    // Getter and Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}

public class App {

    public static void main(String[] args) {

        String tableName = "employee";
        String createTable = "create table employee(id int primary key, name varchar(255), phone varchar(20), email varchar(255), password varchar(20))";
        String query = "select * from employee;";
        String insertBase = "insert into employee(id,name,phone,email,password) values(%d, '%s', '%s', '%s', '%s')";

        int startId = 1; // Starting ID to check
        Employee employee = new Employee();
        Connection connection = null;
        Statement stmt = null;
        Scanner scn = null;
        final String url = "jdbc:mysql://localhost:3306/hibernate";
        final String user = "root";
        final String password = "admin";

        try {

            // Set up the database connection

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(url, user, password);

            stmt = connection.createStatement();
            DatabaseMetaData dbMetaData = connection.getMetaData();

            // Check if the table exists
            ResultSet tables = dbMetaData.getTables(null, null, tableName, null);
            if (tables.next()) {
                System.out.println("Table " + tableName + " exists in the database.");
            } else {
                stmt.execute(createTable);
                System.out.println("Table " + tableName + " does not exist in the database.");
            }

            // Insert data into the table from user input;
            scn = new Scanner(System.in);
            System.out.println("Enter your name:");
            employee.setName(scn.nextLine());
            System.out.println("Enter your phone:");
            employee.setPhone(scn.nextLine());
            System.out.println("Enter your email:");
            employee.setEmail(scn.nextLine());
            System.out.println("Enter your password:");
            employee.setPassword(scn.nextLine());

            // Find a unique ID
            int uniqueId = getUniqueId(stmt, startId);

            // Insert the data with the unique ID
            String insertQuery = String.format(insertBase, uniqueId, employee.getName(), employee.getPhone(),
                    employee.getEmail(),
                    employee.getPassword());
            stmt.executeUpdate(insertQuery);
            System.out.println("Inserted employee with ID: " + uniqueId);

            // Query the database and display results
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                System.out.println("\t" + result.getInt("id") + "\t" + result.getString("name") + "\t"
                        + result.getString("phone"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (stmt != null)
                    stmt.close();
                if (connection != null)
                    connection.close();
                if (scn != null)
                    scn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Function to get a unique ID by checking existing IDs and incrementing until
    // found

    private static int getUniqueId(Statement stmt, int startId) throws SQLException {
        int idToCheck = startId;

        // Loop to check if the ID exists in the database
        while (true) {
            String checkIdQuery = "SELECT COUNT(*) FROM employee WHERE id = " + idToCheck;
            ResultSet rs = stmt.executeQuery(checkIdQuery);
            rs.next();
            int count = rs.getInt(1);

            if (count == 0) {
                // ID is unique, exit the loop
                return idToCheck;
            } else {
                // ID exists, increment and check again
                idToCheck++;
            }
        }
    }
}
