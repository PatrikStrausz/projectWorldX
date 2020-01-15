package databaseApp;

import java.sql.*;
import java.util.Scanner;

public class Database implements DatabaseMethods {


    private final String JDBC = "com.mysql.cj.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/world_x?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private Connection connection;

    @Override
    public Connection getConnection() throws Exception {
        Class.forName(JDBC);
        connection = DriverManager.getConnection(URL, "root", "");
        return connection;
    }

    @Override
    public void updateSlovakCity() throws Exception {
        Class.forName(JDBC);
        connection = DriverManager.getConnection(URL, "root", "");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter City to update: ");
        String city = scanner.nextLine();
        System.out.println("Enter Country Code: ");
        String countryCode = scanner.nextLine();
        System.out.println("Enter district: ");
        String district = scanner.nextLine();
        System.out.println("Enter population: ");
        String population = scanner.nextLine();

        final String select = "SELECT Name from city WHERE name = ? ";

        PreparedStatement check = getConnection().prepareStatement(select);
        check.setString(1, city);
        ResultSet resultSet = check.executeQuery();
        if (!resultSet.next()) {
            System.out.println(city + " is not in database");
        } else {


            final String update = "UPDATE City SET CountryCode = ?, district = ?, Info = ? WHERE Name = ? ;";
            PreparedStatement statement = getConnection().prepareStatement(update);

            statement.setString(1, countryCode);
            statement.setString(2, district);
            statement.setString(3, "{\"Population\": " + population + "}");
            statement.setString(4, city);


            statement.execute();
            System.out.println(city + " successfully updated");

            getConnection().close();
        }
    }

    @Override
    public void deleteSlovakCity() throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter City to delete: ");
        String city = scanner.nextLine();
        System.out.println("Are you sure? ");
        String answer = scanner.nextLine();
        int count = 0;
        if (answer.equals("yes") || answer.equals("y")) {
            final String delete = " DELETE FROM City WHERE Name = ?";
            final String INSERTPP = "SELECT * FROM city where name = ? ";

            PreparedStatement statement = getConnection().prepareStatement(INSERTPP);
            statement.setString(1, city);

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                System.out.println(city + " is not in database");

            } else {
                count++;
                if (count > 0) {
                    PreparedStatement deletes = getConnection().prepareStatement(delete);


                    deletes.setString(1, city);

                    deletes.execute();
                    System.out.println(city + " deleted.");

                }
            }
        } else {
            System.out.println(city + " Not removed.");
        }
        getConnection().close();
    }

    @Override
    public void insertSLovakCity() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter city to add: ");
        String city = scanner.nextLine();
        System.out.println("Enter Country Code: ");
        String countryCode = scanner.nextLine();
        System.out.println("Enter district: ");
        String district = scanner.nextLine();
        System.out.println("Enter population: ");
        String population = scanner.nextLine();


        final String select = "SELECT Name from city WHERE name = ? ";

        PreparedStatement check = getConnection().prepareStatement(select);
        check.setString(1, city);
        ResultSet resultSet = check.executeQuery();
        if (resultSet.next()) {
            System.out.println(city + " is already in database.");
        } else {


            final String insert = "INSERT INTO city(Name, countryCode, District, Info) VALUES(?, ?, ?, ?)";
            PreparedStatement statement = getConnection().prepareStatement(insert);
            statement.setString(1, city);
            statement.setString(2, countryCode);
            statement.setString(3, district);
            statement.setString(4, "{\"Population\": " + population + "}");
            statement.execute();
            System.out.println(city + " successfully added.");

        }

        getConnection().close();

    }

    @Override
    public void selectSlovakCities() throws Exception {
        Class.forName(JDBC);
        connection = DriverManager.getConnection(URL, "root", "");
        final String select = "SELECT Name, countryCode, district, json_extract(info,'$.Population')as Population FROM city WHERE countryCode LIKE 'SVK' ";
        PreparedStatement statement = connection.prepareStatement(select);
        ResultSet resultSet = statement.executeQuery();
        System.out.println("List of Slovak cities: ");
        int count = 0;
        while (resultSet.next()) {

            System.out.println(count + "  " + resultSet.getString("Name") + "  " + resultSet.getString("countryCode") + "  "
                    + resultSet.getString("district") + "  " + resultSet.getInt(4));

            count++;


        }
        System.out.println();


    }


    public void showPopulationOfCountry() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter name of Country to show population: ");
        String name = sc.nextLine();


        PreparedStatement statement = getConnection().prepareStatement("SELECT json_extract(doc, '$.Name'),json_extract(doc, '$.demographics.Population') FROM countryinfo WHERE json_extract(doc, '$.Name') like ?");
        statement.setString(1, "\"" + name + "\"");

        ResultSet rs = statement.executeQuery();
        System.out.println("\nPopulation of this country: ");
        if (rs.next()) {
            System.out.println(rs.getString(1) + "     "
                    + rs.getString(2) + "     ");
        }else {
            System.out.println(name + " is not in database");
            connection.close();
        }
    }
}
