package sk.itsovy.strausz.projectWorldX;

import java.sql.*;

public class Main {

    public static final String QUERYCOUNTRIES= "SELECT Name FROM country";
    public static final String CITIES= "SELECT Name FROM city WHERE countryCode LIKE ? ORDER BY Name";
    public static final String INSERTCITY= "INSERT INTO city(Name, countryCode, District, Info)" +
            " VALUES(?,?,?,?)";

    public static void main(String[] args) {
        try{
         Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/world_x?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root", "");

            PreparedStatement statement = connection.prepareStatement(QUERYCOUNTRIES);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("List of countries: " );
            int count = 0;
            while (resultSet.next()){
                String data = resultSet.getString("Name");
                System.out.print(data + "  ");
                count++;
                if(count%5==0){
                    System.out.println();
                }

            }
            System.out.println();

           statement = connection.prepareStatement(CITIES);
            statement.setString(1, "RUS"); // nahradi 1. otaznik v cities hodnotou RUS
            resultSet = statement.executeQuery();
            System.out.println("List of cites: " );

            while (resultSet.next()){
                String data = resultSet.getString("Name");
                System.out.print(data + "  ");
                count++;
                if(count%5==0){
                    System.out.println();
                }
            }


            statement = connection.prepareStatement(INSERTCITY);
            statement.setString(1, "Poprad");
            statement.setString(2, "SVK");
            statement.setString(3, "Tatry");
            statement.setString(4, "{\"Population\":51992}"); // zapis jsonu

            statement.executeUpdate();

           connection.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
