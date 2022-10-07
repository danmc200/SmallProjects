package SolarControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;

import SolarControl.Relay.ToggleOption;

public class RelayActionListener implements ActionListener, RelaysActionListenerProperties
{
	private JButton button;
	
	@Override
	public void setButton(JButton button)
	{
		this.button = button;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String text = this.button.getText();
		Relay relay = Relay.getRelay(text);
		try {
			System.out.println("The button: " + this.button.getText() + " was pressed.");
			relay.executeCommand(ToggleOption.ON);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
