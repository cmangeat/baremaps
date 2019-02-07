package io.gazetteer.osm.postgis;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PostgisSchemaTest {

  public static final String url = "jdbc:postgresql://localhost:5432/osm?user=osm&password=osm";

  @Test
  @Tag("integration")
  public void resetDatabase() throws SQLException {
    try (Connection connection = DriverManager.getConnection(url)) {
      PostgisSchema.createExtensions(connection);

      PostgisSchema.dropTables(connection);
      assertFalse(tableExists("osm_info"));
      assertFalse(tableExists("osm_users"));
      assertFalse(tableExists("osm_nodes"));
      assertFalse(tableExists("osm_ways"));
      assertFalse(tableExists("osm_relations"));

      PostgisSchema.createTables(connection);
      assertTrue(tableExists("osm_info"));
      assertTrue(tableExists("osm_users"));
      assertTrue(tableExists("osm_nodes"));
      assertTrue(tableExists("osm_ways"));
      assertTrue(tableExists("osm_relations"));
    }
  }

  public boolean tableExists(String table) throws SQLException {
    try (Connection connection = DriverManager.getConnection(url)) {
      DatabaseMetaData metadata = connection.getMetaData();
      ResultSet tables = metadata.getTables(null, null, table, null);
      return tables.next();
    }
  }
}
