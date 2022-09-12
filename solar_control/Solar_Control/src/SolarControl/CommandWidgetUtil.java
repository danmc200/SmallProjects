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
	private JButton buttonSelected;
	
	private static CommandWidgetUtil cwu = new CommandWidgetUtil();
	
	private CommandWidgetUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public static CommandWidgetUtil getCommandWidgetUtilInstance()
	{
		return cwu;
	}
	
	public int [] buildButton(String label, JFrame frame, int x, int y)
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
					System.out.println("The button: " + b.getText() + " was pressed.");
					relay.executeCommand(ToggleOption.ON);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		return new int[] {x, (y + heightInc)};
	}
	
	public JButton getButtonSelected()
	{
		return buttonSelected;
	}
	public void setButtonSelected(JButton jb)
	{
		buttonSelected = jb;
	}
	
	public JButton getButton(JFrame frame, Relay relay)
	{
		getComponents(frame.getComponents(), relay);
		JButton jb = getButtonSelected();
		setButtonSelected(null);
		return jb;
	}
	
	private void getComponents(Component [] components, Relay relay)
	{
		for(Component c : components)
		{
			if (c instanceof JPanel)
			{
				getComponents(((JPanel)c).getComponents(), relay);
				System.out.println("1");
			}
			else if (c instanceof JLayeredPane)
			{
				getComponents(((JLayeredPane)c).getComponents(), relay);
				System.out.println("2");
			}
			else if (c instanceof JRootPane)
			{
				int count = ((JRootPane)c).getContentPane().getComponentCount();
				getComponents(((JRootPane)c).getContentPane().getComponents(), relay);
				System.out.println("3" + " " + count + ((JRootPane)c).getContentPane().getComponents().length);
			}
			else if (c instanceof JButton)
			{
				JButton jb = (JButton) c;
				if(jb.getText().equals(relay.getDisplayText()))
				{
					System.out.println("Found button:" + c);
					buttonSelected = jb;
					return;
				}
			}
		}
	}
}