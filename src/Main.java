import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            System.out.println("Running InpCtrl UDP server...");

            Server server = new Server();
            server.start();

            System.out.println("InpCrtl UDP server run peacefully.");

            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            String consoleInputStr;

            while((consoleInputStr = consoleReader.readLine()) != null)
            {
                if (consoleInputStr.equals("exit"))
                {
                    System.out.println("Exit signal got.");
                    System.out.println("Shutting down...");
                    server.closeSocket();
                    return;
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
