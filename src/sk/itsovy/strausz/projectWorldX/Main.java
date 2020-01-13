package sk.itsovy.strausz.projectWorldX;

import java.sql.*;

public class Main {

    public static final String QUERYCOUNTRIES = "SELECT Name FROM country";
    public static final String CITIES = "SELECT Name FROM city WHERE countryCode LIKE ? ORDER BY Name";
    public static final String INSERTCITY = "INSERT INTO city(Name, countryCode, District, Info)" +
            " VALUES(?,?,?,?)";
    public static final String INSERTPP = "SELECT * FROM city where name LIKE 'Poprad' ";
    public static final String CITYINFO = "SELECT city.Name, country.Name as Country, json_extract(info,'$.Population')as Population FROM country " +
            "INNER JOIN city ON country.code= city.countryCode WHERE city.Name LIKE ?";
    public static final String CITYTOP20 = "SELECT city.Name, country.Name as Country, json_extract(info,'$.Population')as Population FROM country " +
            "INNER JOIN city ON country.code= city.countryCode Order BY Population DESC LIMIT 20 ";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/world_x?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root", "");

            PreparedStatement statement = connection.prepareStatement(QUERYCOUNTRIES);
            ResultSet resultSet = statement.executeQuery();
//            System.out.println("List of countries: " );
//            int count = 0;
//            while (resultSet.next()){
//                String data = resultSet.getString("Name");
//                System.out.print(data + "  ");
//                count++;
//                if(count%5==0){
//                    System.out.println();
//                }
//
//            }
//            System.out.println();
//
//           statement = connection.prepareStatement(CITIES);
//            statement.setString(1, "RUS"); // nahradi 1. otaznik v cities hodnotou RUS
//            resultSet = statement.executeQuery();
//            System.out.println("List of cites: " );
//
//            while (resultSet.next()){
//                String data = resultSet.getString("Name");
//                System.out.print(data + "  ");
//                count++;
//                if(count%5==0){
//                    System.out.println();
//                }
//            }
//            String city = "Poprad";
//            statement = connection.prepareStatement(INSERTPP);
//            resultSet = statement.executeQuery(INSERTPP);
//
//
//            if (resultSet.next()) {
//
//                System.out.println(city + " is already in database");
//                connection.close();
//                return;
//            } else {
//                statement = connection.prepareStatement(INSERTCITY);
//                statement.setString(1, "Poprad");
//                statement.setString(2, "SVK");
//                statement.setString(3, "Tatry");
//                statement.setString(4, "{\"Population\":51992}"); // zapis jsonu
//                System.out.println(city + " successfully added");
//
//                statement.executeUpdate();
////
//            }

//            statement = connection.prepareStatement(INSERTCITY);
//            statement.setString(1, "Čadca");
//            statement.setString(2, "SVK");
//            statement.setString(3, "Orava");
//            statement.setString(4, "{\"Population\":24550}"); // zapis jsonu
//
//            statement.executeUpdate();
//
//

            statement = connection.prepareStatement(CITYINFO);
            statement.setString(1, "Utrecht");
            resultSet = statement.executeQuery();
            System.out.println("City info: ");
            if (resultSet.next()) {

                System.out.print(resultSet.getString("Name") + "  " + resultSet.getString("Country") + "  " + resultSet.getInt(3));
            } else {
                System.out.println("City not in database");
            }

            System.out.println();
            System.out.println();

            statement = connection.prepareStatement(CITYTOP20);
            resultSet = statement.executeQuery();
            int count = 1;
            System.out.println("Top 20 by population:");
            while (resultSet.next()) {

                System.out.print(count + ".  " + resultSet.getString("Name") + "  " + resultSet.getString("Country") + "  " + resultSet.getInt(3) + "\n");
                count++;
            }


            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
