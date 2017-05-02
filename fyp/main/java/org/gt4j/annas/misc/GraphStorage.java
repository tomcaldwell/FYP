package org.gt4j.annas.misc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.gt4j.annas.graph.GraphInterface;

/**
 * This class provides a means of persistence, providing the ability to
 * push/pull a graph to a SQL (MySQL) database.
 * 
 * This class requires a SQL compatible server and suitable java database
 * connectors to be available on the classpath. A suitable connector should
 * implement the java.sql.Connection interface.
 * 
 * @see java.sql.Connection
 * 
 * @author Sam Wilson
 * @version v1.0
 */
public class GraphStorage {

	/**
	 * Connection to the database server
	 */
	private Connection conn;

	/**
	 * Statement to execute at the server.
	 */
	private Statement stmt;

	/**
	 * Schema name
	 */
	private static final String schema = "annas";

	/**
	 * Table name
	 */
	private static final String table_name = "annas_graph";
	/**
	 * Statement to create a database.
	 */
	private static final String create_db = "CREATE DATABASE IF NOT EXISTS "
			+ GraphStorage.schema + "; ";

	/**
	 * Statement to drop the database and subsequently the tables in it.
	 */
	private static final String drop_db = "DROP DATABASE IF EXISTS "
			+ GraphStorage.schema + "; ";

	/**
	 * Statement to select the database
	 */
	private static final String use = "USE " + GraphStorage.schema + "; ";

	/**
	 * Statement to create a table suitable of storing graphs.
	 */
	private static final String create_tbl = "CREATE TABLE IF NOT EXISTS "
			+ GraphStorage.table_name
			+ " (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name TINYTEXT,class TEXT, Des TEXT,sgraph LONGBLOB); ";

	/**
	 * Statement to check if the database exists
	 */
	private static final String check_db = "SELECT COUNT(*) as COUNT FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '"
			+ GraphStorage.schema + "'; ";

	private static final String check_table = "SELECT COUNT(*) as COUNT FROM information_schema.tables \n WHERE table_schema = '"
			+ GraphStorage.schema
			+ "' AND table_name = '"
			+ GraphStorage.table_name + "';";

	/**
	 * Statement to insert a graph into the database.
	 */
	private static final String insert_tbl = "INSERT INTO "
			+ GraphStorage.table_name
			+ " (name,class,Des,sgraph) VALUES (?,?,?,?); ";

	/**
	 * Statement to list the names of the graph in the database.
	 */
	private static final String select_graph = "SELECT name FROM "
			+ GraphStorage.table_name + "; ";

	/**
	 * Statement to select a graph by name.
	 */
	private static final String select_graph_by_name = "SELECT * FROM "
			+ GraphStorage.table_name + " WHERE name = ";

	/**
	 * Statement to select a graph by id.
	 */
	private static final String select_graph_by_ID = "SELECT * FROM "
			+ GraphStorage.table_name + " WHERE id = ";

	private static final String get_size = "SELECT count(*) TABLES, concat(round(sum(data_length+index_length)/(1024*1024),2),'') total_size FROM information_schema.TABLES WHERE  table_name LIKE \""
			+ GraphStorage.table_name + "\";";

	/**
	 * Constructor
	 * 
	 * @param connection
	 *            Connection to the database server
	 * @throws SQLException
	 *             if the database can not be set up for use
	 */
	public GraphStorage(Connection connection) throws SQLException {
		super();
		this.conn = connection;
		this.stmt = this.conn.createStatement();
		if (!this.isSetup()) {
			this.SetupDatabase(this.conn);
		}
		this.stmt.execute(GraphStorage.use);
	}

	/**
	 * Gets the size in Megabytes of the data in the table
	 * 
	 * @return gets the size of the table
	 * @throws SQLException
	 *             if the database can not be set up for use
	 */
	public double getTableSize() throws SQLException {
		ResultSet rs;

		rs = this.stmt.executeQuery(GraphStorage.get_size);
		rs.next();
		return rs.getDouble("total_size");
	}

	/**
	 * Stores a graph in the database.
	 * 
	 * @param name
	 *            Name of the graph
	 * @param Des
	 *            Description of the graph
	 * @param graph
	 *            Graph object
	 * @return true if the graph was successfully stored.
	 * @throws SQLException
	 *             if the database can not be set up for use
	 * @throws IOException
	 *             Caused if the object can not be serialised
	 * @throws ClassNotFoundException
	 *             Caused when checking if the graph is already contained in the
	 *             database
	 */
	public int store(String name, String Des, GraphInterface<?, ?> graph)
			throws SQLException, IOException, ClassNotFoundException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = new ObjectOutputStream(bos);
		out.writeObject(graph);
		out.close();

		byte[] buf = bos.toByteArray();

		if (this.get(name) == null) {

			PreparedStatement pstmt = this.conn
					.prepareStatement(GraphStorage.insert_tbl);

			pstmt.setString(1, name);
			pstmt.setString(2, graph.getClass().getCanonicalName());
			pstmt.setString(3, Des);
			Blob blob = this.conn.createBlob();
			blob.setBytes(1, buf);
			pstmt.setBlob(4, blob);
			pstmt.execute();

			ResultSet rs = this.stmt
					.executeQuery(GraphStorage.select_graph_by_name + " '"
							+ name + "' ;");

			if (rs.next()) {

				int id = rs.getInt("id");

				return id;
			} else {
				return -1;
			}

		} else {
			return -1;
		}

	}

	/**
	 * Gets a graph from the database.
	 * 
	 * @param name
	 *            Name of the graph to retrieve.
	 * @return GraphInterface of the graph (casting may be required).
	 * @throws SQLException
	 *             if the database can not be set up for use
	 * @throws IOException
	 *             Caused if the object can not be serialised
	 * @throws ClassNotFoundException
	 *             Caused if the object can not be reconstructed due to not
	 *             being able to find the class
	 */
	public GraphInterface<?, ?> get(String name) throws SQLException,
			ClassNotFoundException, IOException {
		ResultSet rs = this.stmt.executeQuery(GraphStorage.select_graph_by_name
				+ " '" + name + "' ;");

		if (rs.next()) {

			ByteArrayInputStream bos = new ByteArrayInputStream(
					rs.getBytes("sgraph"));

			ObjectInput in = new ObjectInputStream(bos);
			Object f = in.readObject();

			// Object f = new Object();

			GraphInterface<?, ?> gi = (GraphInterface<?, ?>) Class.forName(
					rs.getString("class")).cast(f);
			return gi;
		} else {
			return null;
		}
	}

	/**
	 * Gets a graph from the database.
	 * 
	 * @param id
	 *            id of the graph to retrieve.
	 * @return GraphInterface of the graph (casting may be required).
	 * @throws SQLException
	 *             if the SQL command fails to execute on the server
	 * @throws ClassNotFoundException
	 *             if the class can not be found on the classpath
	 * @throws IOException
	 *             if the Object fails to be reconstructed from the serialized
	 *             stream
	 */
	public GraphInterface<?, ?> get(int id) throws SQLException,
			ClassNotFoundException, IOException {
		ResultSet rs = this.stmt.executeQuery(GraphStorage.select_graph_by_ID
				+ " '" + id + "' ;");

		if (rs.next()) {

			ByteArrayInputStream bos = new ByteArrayInputStream(
					rs.getBytes("sgraph"));

			ObjectInput in = new ObjectInputStream(bos);
			Object f = in.readObject();

			GraphInterface<?, ?> gi = (GraphInterface<?, ?>) Class.forName(
					rs.getString("class")).cast(f);
			return gi;
		} else {
			return null;
		}
	}

	/**
	 * Lists all of the graph stored in the database
	 * 
	 * @return Array of string containing the names of the graph.
	 * @throws SQLException
	 *             if the SQL command fails to execute on the server
	 */
	public String[] getName() throws SQLException {
		ArrayList<String> names = new ArrayList<String>();

		ResultSet rs = this.stmt.executeQuery(GraphStorage.select_graph);

		while (rs.next()) {
			names.add(rs.getString("name"));
		}

		return names.toArray(new String[names.size()]);
	}

	/**
	 * Removes the database from the database server. #### This will loose all
	 * graph stored in the database ####
	 * 
	 * @return True if the operation is successful.
	 * @throws SQLException
	 *             if the SQL command fails to execute on the server
	 */
	public boolean drop() throws SQLException {
		return this.Drop(this.conn);
	}

	/**
	 * Creates the database and tables ready for graphs to be stored.
	 * 
	 * @param conn
	 *            Connection to the database
	 * @return true if the database was set up successfully
	 * @throws SQLException
	 *             if the SQL command fails to execute on the server
	 */
	private boolean SetupDatabase(Connection conn) throws SQLException {

		this.stmt.execute(GraphStorage.create_db);
		this.stmt.execute(GraphStorage.use);
		this.stmt.execute(GraphStorage.create_tbl);

		ResultSet rs = this.stmt.executeQuery(GraphStorage.check_db);
		rs.next();
		return rs.getInt("COUNT") == 1;
	}

	/**
	 * Drops the database from the server.
	 * 
	 * @param conn
	 *            Connection to the database
	 * @return true if the connection was successfully dropped
	 * @throws SQLException
	 *             if the SQL command fails to execute on the server
	 */
	private boolean Drop(Connection conn) throws SQLException {

		this.stmt.execute(GraphStorage.drop_db);
		ResultSet rs = this.stmt.executeQuery(GraphStorage.check_db);
		rs.next();
		return rs.getInt("COUNT") == 0;

	}

	/**
	 * Check if the database is setup to store graph objects.
	 * 
	 * @return True if the database is setup to store graph objects.
	 * @throws SQLException
	 *             if the SQL command fails to execute on the server
	 */
	private boolean isSetup() throws SQLException {

		ResultSet rs = this.stmt.executeQuery(GraphStorage.check_db);
		rs.next();
		if (rs.getInt("COUNT") == 0)
			return false;
		else
			rs = this.stmt.executeQuery(GraphStorage.check_table);
		rs.next();
		if (rs.getInt("COUNT") == 0)
			return false;
		return true;
	}

}
