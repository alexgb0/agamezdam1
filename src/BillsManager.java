import java.util.ArrayList;

public class BillsManager
{
	private final ArrayList<Bill> bills;
	private final Auth auth;

	BillsManager(Auth auth)
	{
		this.auth = auth;
		this.bills = auth.fetch_bills();
	}

	public Bill get_last()
	{
		return bills.get(bills.size() - 1);
	}

	public ArrayList<Bill> from_user(Client client)
	{
		ArrayList<Bill> out = new ArrayList<>();
		for (Bill bill : bills)
			if (bill.get_client().get_dni().compareToIgnoreCase(client.get_dni()) == 0)
				out.add(bill);

		return out;
	}

	public ArrayList<Bill> from_user(String dni)
	{
		ArrayList<Bill> out = new ArrayList<>();
		for (Bill bill : bills)
			if (bill.get_client().get_dni().compareToIgnoreCase(dni) == 0)
				out.add(bill);
	
		return out;
	}


}
