import java.sql.*;


public class Auth
{
	static final String CONNECTION = "jdbc:mysql://localhost:3306/store";
	static final String USERNAME = "root";
	static final String PASSWD = "";

	private Connection conn;
	private Statement stmt;

	private boolean debug;


	Auth(boolean debug)
	{
		this.debug = debug;
	}

	public boolean connect()
	{
		try
		{
			conn = DriverManager.getConnection(CONNECTION, "root", PASSWD);
			stmt = conn.createStatement(0, ResultSet.CONCUR_UPDATABLE);
			return false;

		} catch (SQLException e) {
			if (debug) e.printStackTrace();
			return true;
		}
	}

}
