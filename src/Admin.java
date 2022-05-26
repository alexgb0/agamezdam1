public class Admin
{
	private static final String PASSWORD = "admin";
	static void main(String passwd)
	{
		if (passwd.compareTo(PASSWORD) != 0)
			return;
	}

}
