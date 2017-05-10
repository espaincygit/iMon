package com.cy.mon.icon;

import javax.swing.ImageIcon;
import com.apple.eawt.*;

public class PicIcon {
	
	public static void setMacIcon(){
		Application.getApplication().setDockIconImage(new ImageIcon(
				PicIcon.class.getClass().getResource("/com/cy/mon/icon/imon.png")).getImage());
	}

}
