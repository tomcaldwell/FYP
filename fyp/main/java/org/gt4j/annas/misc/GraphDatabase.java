package org.gt4j.annas.misc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class provides a means of persistence, providing the ability to
 * push/pull a graph to a SQL (Sqlite) database.
 * 
 * 
 * @see java.sql.Connection
 * 
 * @author Sam Wilson
 * @version v1.0
 */
public class GraphDatabase {

	/**
	 * Connection to the database server
	 */
	private Connection conn;

	/**
	 * Statement to execute at the server.
	 */
	private Statement stmt;

	/**
	 * Table name
	 */
	private static final String table_name = "annas_graph";

	private String filename;

	/**
	 * Statement to create a table suitable of storing graphs.
	 */
	private static final String create_tbl = "CREATE TABLE IF NOT EXISTS "
			+ GraphDatabase.table_name
			+ " (id INTEGER auto_increment PRIMARY KEY , name TEXT, sgraph LONGBLOB, num_vertices INT, num_edges INT, encoding TINYTEXT);";

	/**
	 * 
	 * @param filename Filename of the SQLite database
	 * @return GraphDatabase connected using SQLite
	 */
	public static GraphDatabase getSQLiteDatabase(String filename) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:"
					+ filename);
			return new GraphDatabase(conn, filename);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param filename Filename of the SQLite database
	 * @return GraphDatabase connected using H2
	 */
	public static GraphDatabase getH2Database(String filename) {
		try {
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection("jdbc:h2:" + filename
					+ ";MODE=MySQL");
			return new GraphDatabase(conn, filename);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Constructs a GraphDatase with a given connection
	 * 
	 * @param conn
	 *            Connection to database
	 * @param filename
	 *            database filename
	 */
	public GraphDatabase(Connection conn, String filename) {
		super();
		this.conn = conn;
		this.filename = filename;
		try {
			this.stmt = this.conn.createStatement();
			this.stmt.setQueryTimeout(30);
			this.setupTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Inserts a graph into the graph database
	 * 
	 * @param name
	 *            Name of the graph
	 * @param sgraph
	 *            Encoded graph
	 * @param order
	 *            Order of the graph
	 * @param size
	 *            Size of the graph
	 * @return true if the graph was added successfully
	 * @throws SQLException Caused if the SQL statement fails to execute on the server
	 */
	public boolean insert(String name, String sgraph, int order, int size)
			throws SQLException {
		PreparedStatement pstmt = this.conn
				.prepareStatement("INSERT INTO "
						+ GraphDatabase.table_name
						+ " (name,sgraph,num_vertices,num_edges,encoding) VALUES (?,?,?,?,?);");

		pstmt.setString(1, name);
		//pstmt.setBlob(2, new SerialBlob(sgraph.getBytes()));
		pstmt.setBytes(2, sgraph.getBytes());
		pstmt.setInt(3, order);
		pstmt.setInt(4, size);
		pstmt.setString(5, "g6");

		return pstmt.execute();
	}

	/**
	 * Gets all graphs from the graph database
	 * 
	 * @return A {@link org.gt4j.annas.misc.GraphIterator} of all graphs in the database
	 * @throws SQLException Caused if the SQL statement fails to execute on the server
	 */
	public GraphIterator getAllGraphs() throws SQLException {
		return new GraphIterator(this.stmt.executeQuery("SELECT * FROM "
				+ GraphDatabase.table_name + ';'));
	}

	/**
	 * Gets all graphs from the graph database of the given order
	 * 
	 * @param n
	 *            Order of the graph
	 * @return A {@link org.gt4j.annas.misc.GraphIterator} of all graphs of order n
	 * @throws SQLException Caused if the SQL statement fails to execute on the server
	 */
	public GraphIterator getGraphOfOrder(int n) throws SQLException {
		return new GraphIterator(this.stmt.executeQuery("SELECT * FROM "
				+ GraphDatabase.table_name + " WHERE num_vertices = " + n
				+ " ;"));
	}

	/**
	 * Gets all graphs from the graph database of order greater than n
	 * 
	 * @param n
	 *            Order
	 * @return A {@link org.gt4j.annas.misc.GraphIterator} of all graphs of order greater
	 *         than n
	 * @throws SQLException Caused if the SQL statement fails to execute on the server
	 */
	public GraphIterator getGraphOfOrderGreaterThan(int n) throws SQLException {
		return new GraphIterator(this.stmt.executeQuery("SELECT * FROM "
				+ GraphDatabase.table_name + " WHERE num_vertices > " + n
				+ " ;"));

	}

	/**
	 * Gets all graphs from the graph database of order less than n
	 * 
	 * @param n
	 *            Order
	 * @return A {@link org.gt4j.annas.misc.GraphIterator} of all graphs of order less
	 *         than n
	 * @throws SQLException Caused if the SQL statement fails to execute on the server
	 */
	public GraphIterator getGraphOfOrderLessThan(int n) throws SQLException {
		return new GraphIterator(this.stmt.executeQuery("SELECT * FROM "
				+ GraphDatabase.table_name + " WHERE num_vertices < " + n
				+ " ;"));

	}

	/**
	 * Gets all graphs from the graph database of size n
	 * 
	 * @param n
	 *            Size
	 * @return A {@link org.gt4j.annas.misc.GraphIterator} of all graphs of size n
	 * @throws SQLException Caused if the SQL statement fails to execute on the server
	 */
	public GraphIterator getGraphOfSize(int n) throws SQLException {
		return new GraphIterator(this.stmt.executeQuery("SELECT * FROM "
				+ GraphDatabase.table_name + " WHERE num_edges = " + n + " ;"));
	}

	/**
	 * Gets all graphs from the graph database of size greater than n
	 * 
	 * @param n
	 *            Size
	 * @return A {@link org.gt4j.annas.misc.GraphIterator} of all graphs of size greater
	 *         than n
	 * @throws SQLException Caused if the SQL statement fails to execute on the server
	 */
	public GraphIterator getGraphOfSizeGreaterThan(int n) throws SQLException {
		return new GraphIterator(this.stmt.executeQuery("SELECT * FROM "
				+ GraphDatabase.table_name + " WHERE num_edges > " + n + " ;"));

	}

	/**
	 * Gets all graphs from the graph database of order less than n
	 * 
	 * @param n
	 *            Size
	 * @return A {@link org.gt4j.annas.misc.GraphIterator} of all graphs of order less
	 *         than n
	 * @throws SQLException Caused if the SQL statement fails to execute on the server
	 */
	public GraphIterator getGraphOfSizeLessThan(int n) throws SQLException {
		return new GraphIterator(this.stmt.executeQuery("SELECT * FROM "
				+ GraphDatabase.table_name + " WHERE num_edges < " + n + " ;"));

	}

	/**
	 * Sets up the table in the graph database
	 * 
	 * @return true if the database was setup successfully
	 * @throws SQLException
	 */
	private boolean setupTable() throws SQLException {
		return this.stmt.execute(GraphDatabase.create_tbl);
	}

	@Override
	public String toString() {
		return "GraphDatabase: Connected to " + this.filename;
	}

}
