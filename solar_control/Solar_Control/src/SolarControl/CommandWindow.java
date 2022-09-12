package SolarControl;

import javax.swing.JFrame;

public class CommandWindow 
{
	private static final int
		COMMAND_FRAME_WIDTH = 400,
		COMMAND_FRAME_HEIGHT = 500;
	
	private int 
		xAxis = 100,
		yAxis = 50;
	
	private JFrame commandFrame = new JFrame();
	
	public CommandWindow() 
	{
		CommandWidgetUtil cwu = CommandWidgetUtil.getCommandWidgetUtilInstance();
		for(Relay r : Relay.values())
		{
			String dispText = r.getDisplayText();
			int [] axis = cwu.buildButton(dispText, commandFrame, xAxis, yAxis);
			xAxis = axis[0];
			yAxis = axis[1];
		}
		
		commandFrame.setSize(COMMAND_FRAME_WIDTH,COMMAND_FRAME_HEIGHT);
		commandFrame.setLayout(null);
		commandFrame.setVisible(true);
	}
}
