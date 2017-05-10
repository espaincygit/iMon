package com.cy.mon;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public final class FlashLED extends TimerTask{
	private int maxReadsFlash = 0;//SysAction.flashTimes;
	private boolean isshow = true;
	private JPanel panel;

//	private boolean isStartFlash = true;

	private Timer timerFlash = null;
	
	private Color col;
	
	public FlashLED (JPanel panel, Color col, Timer timerFlash, int maxtime){
		this.panel = panel;
		this.col = col;
		this.timerFlash = timerFlash;
		this.maxReadsFlash = maxtime;
	}
	
	public void init(){
		
		maxReadsFlash = SysAction.flashTimes;
		isshow = true;
	}
	public void run() {
	   
//		SysAction.getInstance().flashRead();
		if (maxReadsFlash > 0){
			if (isshow){
				panel.setBackground(col);//.getProgressBar_1().setValue(90);
				isshow = false;
			} else {
				panel.setBackground(Color.BLACK);
				isshow = true;
				
			}
			maxReadsFlash--;
		} else {
//			maxReadsFlash = SysAction.flashTimes;
//			if (isStartFlash){
				if (timerFlash != null) timerFlash.cancel();
				timerFlash = null;
				panel.setBackground(Color.BLACK);
//				isStartFlash = false;
//			}
		}
	}
}
