package databaseApp;

import java.sql.Connection;

public interface DatabaseMethods {

    Connection getConnection () throws Exception;
    void updateSlovakCity() throws Exception;
    void deleteSlovakCity() throws Exception;
    void insertSLovakCity()throws Exception;
    void selectSlovakCities() throws Exception;
}
