package SolarControl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public interface ConsolePrinter {
	
	public static void printOutput(Process p) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		BufferedReader br2 = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		String s = null;

		while((s = br.readLine()) != null)
		{
			System.out.println(s);
		}
		while((s = br2.readLine()) != null)
		{
			System.out.println(s);
		}
	}

}
