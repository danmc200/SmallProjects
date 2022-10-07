package SolarControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;

import SolarControl.Relay.ToggleOption;

public class ReadRelayActionListener implements ActionListener
{
	private JButton button;
	
	public void setButton(JButton button)
	{
		this.button = button;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String text = this.button.getText();
		System.out.println("The button: " + this.button.getText() + " was pressed.");
	}

}
