package SolarControl;

import java.io.IOException;

public enum Relay 
{
	relay1("Relay 1", new String [] {"a1f>", "a1fo"}),
	relay2("Relay 2", new String [] {"a2f>", "a2fo"}),
	relay3("Relay 3", new String [] {"a3f>", "a3fo"}),
	relay4("Relay 4", new String [] {"a8fo", "a8fo"}),
	relay5("Relay 5", new String [] {"a0f>", "a0fo"}),
	relay6("Relay 6", new String [] {"a9fo", "a9fo"}),
	relay7("Relay 7", new String [] {"a7f>", "a7fo"});
	
	private static final String 
		SOLAR_HOME_DIR = "/home/daniel/Solar/vim",
		COMMAND_PREFIX = SOLAR_HOME_DIR + "/" + "relay_command.sh ";
	
	private String relayDisplayText;
	private String [] relayCodeValues;
	
	private Relay(String relayDisplayText, String [] relayCodeValues)
	{
		this.relayDisplayText = relayDisplayText;
		this.relayCodeValues = relayCodeValues;
	}
	
	/**
	 * associated with the 2 commands available in Relay enum
	 **/
	public enum ToggleOption {
		ON,
		OFF
	}
	
	public String getDisplayText()
	{
		return this.relayDisplayText;
	}
	
	public String [] getRelayCode()
	{
		return this.relayCodeValues;
	}
	
	public static Relay getRelay(String text)
	{
		for (Relay r : Relay.values())
		{
			if(r.relayDisplayText.equals(text))
				return r;
		}
		return null;
	}
	
	/**
	 * 
	 * @param option 0 or 1 for ON/OFF
	 * @throws IOException 
	 */
	public void executeCommand(ToggleOption optionOnOrOff) throws IOException
	{
		Runtime rt = Runtime.getRuntime();
		Process p = null;
		String command = COMMAND_PREFIX + this.getRelayCode()[optionOnOrOff.ordinal()];
		
		p = rt.exec(command);
		ConsolePrinter.printOutput(p);
	}
}
