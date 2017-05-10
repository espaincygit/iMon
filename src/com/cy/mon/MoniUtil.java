package com.cy.mon;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.apple.eawt.*;

public class MoniUtil {

	public static void makeTrans(JFrame frmDisks, float f){

		com.sun.awt.AWTUtilities.setWindowOpacity(frmDisks, f); 
		 
	}
//	
//	public static void setMacIcon(){
//		Application.getApplication().setDockIconImage(new ImageIcon(
//				PicIcon.class.getClass().getResource("/com/cy/mon/icon/imon.png")).getImage());
//	}
}
