package SolarControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

import SolarControl.Relay.ToggleOption;

public interface CommandWidgetLayoutManager {

	public static int [] buildButton(String label, JFrame frame, int x, int y)
	{
		//button width/height defined....
		int 
			height = 40,
			width = 100,		
			heightInc = 50;
		
		JButton b = new JButton(label);
		b.setBounds(x,y,width,height);//x, y, width, height
		frame.add(b);
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = b.getText();
				Relay relay = Relay.getRelay(text);
				try {
					relay.executeCommand(ToggleOption.ON);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		return new int[] {x, (y + heightInc)};
	}
}
