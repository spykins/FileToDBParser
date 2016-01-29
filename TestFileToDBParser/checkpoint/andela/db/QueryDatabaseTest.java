package checkpoint.andela.db;

import checkpoint.andela.model.DatabaseConfig;
import checkpoint.andela.model.InputFieldModel;
import checkpoint.andela.parser.ReadData;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by Spykins on 29/01/2016.
 */
public class QueryDatabaseTest extends DatabaseConfig{
  QueryDatabase queryDatabase;
  Connection databaseConnection;
  String tableName = "reactions";
  ReadData readData = new ReadData("/Users/Spykins/IdeaProjects/FileToDBParser/res/reactions.dat");

  @Test
  public void testCreateTable() throws Exception {
    initialize();
    assertFalse(queryDatabase.hasTable(tableName));
    assertTrue(queryDatabase.createTable(tableName));
    assertTrue(queryDatabase.hasTable(tableName));
    queryDatabase.deleteTableFromDatabase(tableName);
  }

  @Test
  public void testInsertIntoDatabase() throws Exception{
    initialize();
    HashMap<String, String> returnedValue = readData.getData();
    assertFalse(returnedValue.isEmpty());

    if(!queryDatabase.hasTable(tableName)){
      queryDatabase.createTable(tableName);
    }

    assertTrue(queryDatabase.insertIntoDatabase(tableName,returnedValue));

    HashMap<String, String> returnedValue2 = readData.getData();
    assertTrue(queryDatabase.insertIntoDatabase(tableName,returnedValue2));
    queryDatabase.deleteTableFromDatabase(tableName);
  }

  private void initialize(){
    try {
        databaseConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" +
          DATABASE_NAME, DATABASE_USER_NAME, DATABASE_PASSWORD);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    queryDatabase = new QueryDatabase(databaseConnection);
  }
}