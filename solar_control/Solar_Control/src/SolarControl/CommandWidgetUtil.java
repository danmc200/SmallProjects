package SolarControl;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import SolarControl.Relay.ToggleOption;

public class CommandWidgetUtil 
{
	//placeholder variable. clear after 
	private JButton buttonSelected;
	
	private static CommandWidgetUtil cwu = new CommandWidgetUtil();
	
	/**
	 * singleton
	 */
	private CommandWidgetUtil() {
	}
	
	public static CommandWidgetUtil getCommandWidgetUtilInstance()
	{
		return cwu;
	}
	
	/**
	 * 
	 * @param label text to cover button
	 * @param frame frame to place button into
	 * @param x axis to place
	 * @param y axis to place
	 * @return the next x/y axis to use
	 */
	public int [] buildButton(String label, JFrame frame, int x, int y, ActionListener actionListener)
	{
		//button width/height defined....
		int 
			height = 40,
			width = 100,
			buttonPadding = 10,
			heightInc = height + buttonPadding;
		JButton b = new JButton(label);
		b.setBounds(x,y,width,height);//x, y, width, height
		frame.add(b);
		b.addActionListener(actionListener);
		
		return new int[] {x, (y + heightInc)};
	}

	/**
	 * 
	 * @param label text to cover button
	 * @param frame frame to place button into
	 * @param x axis to place
	 * @param y axis to place
	 * @return the next x/y axis to use
	 */
	public int [] buildButtonRelay(String label, JFrame frame, int x, int y)
	{
		return buildButton(label, frame, x, y, getRelayActionListener());
	}

	public int [] buildButtonReadRelay(String label, JFrame frame, int x, int y)
	{
		return buildButton(label, frame, x, y, getReadRelayActionListener());
	}

	public ActionListner getRelayActionListener()
	{

		ActionListener relayActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = b.getText();
				Relay relay = Relay.getRelay(text);
				try {
					System.out.println("The button: " + b.getText() + " was pressed.");
					relay.executeCommand(ToggleOption.ON);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};
		return relayActionListener;
	}

	public ActionListner getReadRelayActionListener()
	{

		ActionListener readRelayActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = b.getText();
				Relay relay = Relay.getRelay(text);
				try {
					System.out.println("The button: " + b.getText() + " was pressed.");
					//TODO: executeCommand("c/codebase/SmallProjects/solar_control" + "/readRelayToFile.sh");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};
		return readRelayActionListener;
	}

	public void executeCommand(String command) throws IOException
	{
		Runtime rt = Runtime.getRuntime();
		Process p = null;
		
		p = rt.exec(command);
		ConsolePrinter.printOutput(p);
	}
	
	public JButton getButtonSelected()
	{
		return buttonSelected;
	}
	public void setButtonSelected(JButton jb)
	{
		buttonSelected = jb;
	}
	/**
	 * call after using placeholder buttonSelected variable
	 **/
	public void clearButtonSelected()
	{
		setButtonSelected(null);
	}
	
	public JButton getButton(JFrame frame, Relay relay)
	{
		getComponents(frame.getComponents(), relay);
		JButton jb = getButtonSelected();
		clearButtonSelected();
		return jb;
	}
	
	private void getComponents(Component [] components, Relay relay)
	{
		getComponents(components, relay.getDisplayText());
	}

	private void getComponents(Component [] components, String displayText)
	{
		for(Component c : components)
		{
			if (c instanceof JPanel)
			{
				getComponents(((JPanel)c).getComponents(), displayText);
				System.out.println("1");
			}
			else if (c instanceof JLayeredPane)
			{
				getComponents(((JLayeredPane)c).getComponents(), displayText);
				System.out.println("2");
			}
			else if (c instanceof JRootPane)
			{
				getComponents(((JRootPane)c).getContentPane().getComponents(), displayText);
				int count = ((JRootPane)c).getContentPane().getComponentCount();
				System.out.println("3" + " " + count + ((JRootPane)c).getContentPane().getComponents().length);
			}
			else if (c instanceof JButton)
			{
				JButton jb = (JButton) c;
				if(jb.getText().equals(displayText))
				{
					System.out.println("Found button:" + c);
					setButtonSelected(jb);
					return;
				}
			}
		}
	}
}
