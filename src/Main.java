public class Main {

    public static void main(String[] args)
    {
        if (args.length == 0 || args[0].equalsIgnoreCase("--client"))
            Logger.main();
        else if (args[0].equalsIgnoreCase("--admin"))
            Admin.main(args[1]);
        else
            System.out.println(
                    """
                    usage: ./store [mode] [password]
                    default (no arguments:\t opens the program in client mode.
                    modes:
                    \t--client: opens the program as a client.
                    \t--admin: opens the program as an administrator, a valid password is required.
                    examples:
                    \tclient mode: ./store
                    \tadmin mode: ./store --admin admin
                    """
            );
    }
}
