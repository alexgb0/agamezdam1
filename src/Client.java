import java.util.regex.Pattern;

public class Client
{
	private String dni;
	private String nom;
	private String email;
	private int phone;
	private String address;

	public Client(String dni, String nom, String email, int phone, String address) {
		if (set_dni(dni)) this.dni = "";
		if (set_email(email)) this.email = "";
		if (set_phone(phone)) this.phone = 0;
		this.nom = nom;
		this.address = address;
	}

	public boolean set_dni(String dni)
	{
		//credits: https://gist.github.com/afgomez/5691823
		String dni_letters = "TRWAGMYFPDXBNJZSQVHLCKE";
		char letter = dni_letters.charAt(Integer.parseInt(dni) % 23);

		if (!(letter == dni.charAt(8)))
			return true;

		this.dni = dni;
		return false;
	}

	public boolean set_email(String email)
	{
		//credits: https://emailregex.com/
		String email_regrex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

		if (!Pattern.matches(email_regrex, email))
			return true;

		this.email = email;
		return false;
	}

	public boolean set_phone(int phone)
	{
		double digits = Math.log10(phone) + 1;
		if (digits != 9) return true;
		this.phone = phone;
		return  false;
	}

	public void set_nom(String nom) { this.nom = nom; }
	public void set_address(String address) { this.address = address; }

	public String get_dni() { return dni; }
	public String get_nom() { return nom; }
	public String get_email() { return email; }
	public int get_phone() { return phone; }
	public String get_address() { return address; }

	@Override
	public String toString()
	{
		return dni + "\t" + nom + "\t" + email + "\t" + phone + "\t" + address;
	}
}
