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

	public boolean register_user(Client client, String password)
	{
		if (client == null || password.isEmpty()) return true;
		String hashed = Password.hashPassword(password);

		try
		{
			//"INSERT INTO store.clients (dni, nom, email, phone, address) VALUES ('sdfins', 'efison', 'oinsf', 23423423, 'sdfsdfsdf');"
			PreparedStatement query = conn.prepareStatement(
					"INSERT INTO store.clients (dni, nom, email, phone, address) VALUES (?, ?, ?, ?, ?);"
			);
			query
		} catch (SQLException e)
		{
			if (debug) e.printStackTrace();
			return true;
		}
	}
}
