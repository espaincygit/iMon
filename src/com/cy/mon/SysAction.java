package com.cy.mon;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JColorChooser;
import javax.swing.JFrame;

public final class SysAction {

	private static SysAction instance = new SysAction();
	
//	private boolean isshow = false;
//	private boolean isshowW = false;
//	private boolean isshowNetIn = false;
//	private boolean isshowNetOut = false;
 
	public static int POLL_INTER = 3000;
	private MainMon mainWin;

	private String curReads = "";
	private String curWrites = "";

	private String curNetIn = "0";
	private String curNetOut = "0";

	private static int INTERVAL_LED = 150;
	public static int flashTimes = POLL_INTER / INTERVAL_LED - 2;
//	private int maxReadsFlash = flashTimes;
	
//	private boolean isStartFlash = false;
//	private boolean isStartFlashW = false;
//	private boolean isStartFlashNetIn = false;
//	private boolean isStartFlashNetOut = false;

	private Timer timerFlash = null;
	private Timer timerFlashW = null;
 
	private Timer timerNetInFlash = null;
	private Timer timerNetOutFlash = null;

//	private int maxReadsFlashW;
//	private int maxNetInFlash;
//	private int maxNetOutFlash;


//	String command = "echo -e \"\n$(top -l 1 | awk '/PhysMem/';)\n\"";
//	String command = "top -l 1 | awk '/PhysMem/'";
//	String command = "top -l 1 | awk '/PhysMem/'";
//	private static String command = "top -l 1 | awk '/Disks/'";
	private static String command = "top -l 1 -n 1";

	private static String commandCPU = "top -l 1 | awk '/CPU usage:/'";
//	private static String commandCPU = "top -ocpu";

	private static String commandRAM = "top -l 1 | awk '/PhysMem/'";
	
	private static String keyNet = "Networks: packets:";
	private static String commandNet = "sar -n DEV 2 1 | awk '/Average:   en/'";//"top -l 1 | awk '/" + keyNet + "/'";

	
//	String [] cmd={"/bin/sh","-c","ln -s exe1 exe2"};
	private static String [] cmd={"/bin/sh","-c", command};

	private static String [] cmdCPU = {"/bin/sh","-c", commandCPU};
	private static String [] cmdRAM = {"/bin/sh","-c", commandRAM};
	
	private static String [] cmdNet = {"/bin/sh","-c", commandNet};
	
	private String keycpu = "CPU usage:";

//	private String textNet = "";

//	private FlashLED flashRead;
//	private FlashLED flashWrite;
//	private FlashLED flashIn;
//	private FlashLED flashOut;
	
//	Disks: 147870/3164M read, 175553/3168M written
	
	private static String key = "Disks";
	
	public static float TRANS = 0.5f;

	public MainMon getMainWin() {
		return mainWin;
	}

	public void setMainWin(MainMon mainWin) {
		this.mainWin = mainWin;
	}

	public static SysAction getInstance() {
		return instance;
	}

	public static void setInstance(SysAction instance) {
		SysAction.instance = instance;
	}
 
	public void doNextDisk() {
		
		polldisk();
	}

	private void pollCPU() {
		
		try {
			Process proc = Runtime.getRuntime().exec(cmdCPU );
			
			BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String str;
			while ((str = br.readLine()) != null){ 
				  
				setCpuLabel(str);
				
			}
			 
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	private void setCpuLabel(String str) {

		String cpu = str.substring(str.indexOf(keycpu) + keycpu.length() + 1, str.indexOf("%") + 1);
		mainWin.getLblNewLabel_12().setText(cpu);
		
	}

	private void polldisk() {

		try {
			Process proc = Runtime.getRuntime().exec(cmd );
			
			BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String str;
			while ((str = br.readLine()) != null){
//				System.out.println(str);
				if (str.indexOf(key) >= 0){
//				/**
					String curReadsNew = str.substring(str.indexOf(key) + (key.length() + 2), str.indexOf("/"));
					
					
					String curWritesNew = str.substring(str.indexOf(",") + 1, str.lastIndexOf("/"));
	//				System.out.println(this.curReads + "   " + this.curWrites);
					int offset = 0;
					if (!curReads.equals(curReadsNew)){
						
						offset = calcuTimes(curReads, curReadsNew);
						
	//					if (!isStartFlash) {
							timerFlash = new Timer();
	//						isStartFlash = true; 
							
							FlashLED flashRead = new FlashLED(mainWin.getPanel_11(), Color.GREEN, timerFlash, offset);
							
							timerFlash.schedule(flashRead, 0, INTERVAL_LED);
	//						timerFlash.schedule(new FlashDiskLED(), 0, 100);
							
	//					}
							
//							System.out.println(Integer.valueOf(
//									curReads = curReads.equals("") ? "0" : curReads
//									) - Integer.valueOf(
//											curReadsNew = curReadsNew.equals("") ? "0" : curReadsNew
//											));
					}
					curReads = curReadsNew;
					if (!curWrites.equals(curWritesNew)){
						
						offset = calcuTimes(curWrites, curWritesNew);
						
	//					if (!isStartFlashW) {
							timerFlashW = new Timer();
	//						isStartFlashW = true; 
							FlashLED flashWrite = new FlashLED(mainWin.getPanel_12(), Color.RED, timerFlashW, offset);
							 
							timerFlashW.schedule(flashWrite, 0, INTERVAL_LED);
	//						timerFlashW.schedule(new FlashDiskLEDW(), 0, 100);
	//					}
					}
					curWrites = curWritesNew;
					break;
	//				**/
				} else if (str.indexOf(keycpu) >= 0){
					setCpuLabel(str);
				} else if (str.indexOf("PhysMem") >= 0){
					setRamLabel(str);
				}
				
			}
			 
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	private int calcuTimes(String curReads2, String curReadsNew2) {
		curReads2 = curReads2.trim();
		curReadsNew2 = curReadsNew2.trim();
		int offset = Integer.valueOf(
						curReadsNew2.equals("") ? "0" : curReadsNew2
						) - Integer.valueOf(
								curReads2.equals("") ? "0" : curReads2
							);
//		System.out.println("offset 1   " + offset);	
		offset /= 10;

//		System.out.println("offset  2   " + offset);
		offset = offset > SysAction.flashTimes ? SysAction.flashTimes : 
			offset == 0 ? 4 : offset;
		
//		offset = offset < 4 ? 4 : offset;

		return offset;
	}

	/*
	public void flashRead() {

		if (maxReadsFlash > 0){
			if (isshow){
				mainWin.getPanel_11().setBackground(Color.GREEN);//.getProgressBar_1().setValue(90);
				isshow = false;
			} else {
				mainWin.getPanel_11().setBackground(Color.BLACK);
				isshow = true;
				
			}
			maxReadsFlash--;
		} else {
			maxReadsFlash = flashTimes;
			if (isStartFlash){
				if (timerFlash != null) timerFlash.cancel();
				timerFlash = null;
				mainWin.getPanel_11().setBackground(Color.BLACK);
				isStartFlash = false;
			}
		}
	}

	public void flashWrt() {
		if (maxReadsFlashW > 0){
			if (isshowW){
				mainWin.getPanel_12().setBackground(Color.RED);//.getProgressBar_1().setValue(90);
				isshowW = false;
			} else {
				mainWin.getPanel_12().setBackground(Color.BLACK);
				isshowW = true;
				
			}
			maxReadsFlashW--;
		} else {
			maxReadsFlashW = flashTimes;
			if (isStartFlashW ){
				if (timerFlashW != null) timerFlashW.cancel();
				timerFlashW = null;
				mainWin.getPanel_12().setBackground(Color.BLACK);
				isStartFlashW = false;
			}
		}
	}
*/
	public void doNextCpu() {
		

		pollCPU(); 
	}

	public void doNextRam() {

		pollRam(); 
	}

	private void pollRam() {
		try {
			Process proc = Runtime.getRuntime().exec(cmdRAM );
			
			BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String str;
			while ((str = br.readLine()) != null){ 
				
				setRamLabel(str);
			}
			 
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	private void setRamLabel(String str) {

		String cpu = str.substring(str.indexOf("used, ") + 6).replace(" free.", "");
		mainWin.getLblNewLabel_13().setText(cpu);
	
	}

	public void doNextNet() {
		try {
			Process proc = Runtime.getRuntime().exec(cmdNet );
			
			BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String str;
			int inreads = 0;
			int outreads = 0;
			while ((str = br.readLine()) != null){
//Networks: packets: 3316740/3342M in, 2619963/606M out.
//Average:   en1           15          6795          15           990
//				System.out.println(str);
//				String[] substr = str.split(" ");
//				int itemIndex = 0;
//				String curInNew = "0";//str.substring(str.indexOf(this.keyNet) + (keyNet.length() + 1), str.indexOf("/"));
				
//				String curOutNew = "0";//str.substring(str.indexOf(",") + 2, str.lastIndexOf("/"));

				/*for (String item : substr){
					if (!item.equals("")){
						if (itemIndex == 3){
							curInNew = item;
//							System.out.println(item);
						} else if (itemIndex == 5){
							curOutNew = item;
//							System.out.println(item);
						}
						itemIndex++;
						
					}
					
				}
				*/
				String curInNew = str.substring(27, 41).trim();
				String curOutNew = str.substring(53).trim();
//				System.out.println("-      -" +curInNew +"-    " + "     --" + curOutNew);
				//

//				int inreads = (Integer.valueOf(curInNew) - Integer.valueOf(curNetIn)) / 1024;
//				int outreads = (Integer.valueOf(curOutNew) - Integer.valueOf(curNetOut)) / 1024;
				inreads += (Integer.valueOf(curInNew)) / 1024 ;
				outreads += (Integer.valueOf(curOutNew)) / 1024;
				String textNet = String.valueOf(outreads) + "kb/s";
				mainWin.label_net.setText(textNet);
				mainWin.label_net_in.setText(String.valueOf(inreads) + "kb/s ");
				
//				mainWin.label_net.setText(text );
		
//				if (!"0".equals(curInNew)){
//					  
//						timerNetInFlash = new Timer();
//						 
//						FlashLED flashIn = new FlashLED(mainWin.panel_netIn, Color.GREEN, timerNetInFlash);
//						 
//						timerNetInFlash.schedule(flashIn, 0, INTERVAL_LED);
//
//				}
//				curNetIn = curInNew;
//				if (!"0".equals(curOutNew)){
//					  
//						timerNetOutFlash = new Timer(); 
//						
//						timerNetOutFlash.schedule(new FlashLED(mainWin.panel_netOut, Color.RED, timerNetOutFlash), 0, INTERVAL_LED);
//
//				} 
//				curNetOut = curOutNew;
			}
			 
			br.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
/*
	public void flashNetIn() {
		if (maxNetInFlash > 0){
			if (isshowNetIn){
				mainWin.panel_netIn.setBackground(Color.GREEN);//.getProgressBar_1().setValue(90);
				isshowNetIn = false;
			} else {
				mainWin.panel_netIn.setBackground(Color.BLACK);
				isshowNetIn = true;
				
			}
			maxNetInFlash--;
		} else {
			maxNetInFlash = flashTimes;
			if (isStartFlashNetIn ){
				if (this.timerNetInFlash != null) timerNetInFlash.cancel();
				timerNetInFlash = null;
				mainWin.panel_netIn.setBackground(Color.BLACK);
				isStartFlashNetIn = false;
			}
		}
	}

	public void flashNetOut() {
		if (maxNetOutFlash > 0){
			if (isshowNetOut){
				mainWin.panel_netOut.setBackground(Color.RED);//.getProgressBar_1().setValue(90);
				isshowNetOut = false;
			} else {
				mainWin.panel_netOut.setBackground(Color.BLACK);
				isshowNetOut = true;
				
			}
			maxNetOutFlash--;
		} else {
			maxNetOutFlash = flashTimes;
			if (isStartFlashNetOut ){
				if (this.timerNetOutFlash != null) timerNetOutFlash.cancel();
				timerNetOutFlash = null;
				mainWin.panel_netOut.setBackground(Color.BLACK);
				isStartFlashNetOut = false;
			}
		}
	}
*/

	public static void resetTrans(boolean b, JFrame frmDisks) {
		if (SysAction.TRANS <= 0.2f){
			SysAction.TRANS = 0.2f;
		}
		if (SysAction.TRANS >= 0.9f){
			SysAction.TRANS = 0.9f;
		}
		if (b) {

			SysAction.TRANS += 0.1f;
		} else {	
			SysAction.TRANS -= 0.1f;
		} 
		MoniUtil.makeTrans(frmDisks, SysAction.TRANS);
	}

	public void setAllColor() {
		Color newColor = JColorChooser.showDialog(
				mainWin.frmDisks,
                 "Choose Background Color",
                 mainWin.frmDisks.getBackground());
		
		if (newColor != null){
			mainWin.lblNewLabel_11.setForeground(newColor);
			mainWin.lblNewLabel_12.setForeground(newColor);
			mainWin.lblNewLabel_13.setForeground(newColor);
			mainWin.lblNewLabel_14.setForeground(newColor);
			mainWin.lblNewLabel.setForeground(newColor);
			mainWin.lblNewLabel_2.setForeground(newColor);
			mainWin.lblNetworks.setForeground(newColor);
			mainWin.label_net_in.setForeground(newColor);
			mainWin.lblNewLabel_1.setForeground(newColor);
			mainWin.label_net.setForeground(newColor);
			
		}
	}

	 
}
class PollData extends TimerTask{
	public void run() {
	   
		SysAction instance = SysAction.getInstance();
		instance.doNextDisk();
//		instance.doNextCpu();
//		instance.doNextRam();

	}
}
class PollDisk extends TimerTask{
	public void run() {
	   
		SysAction.getInstance().doNextDisk();
	}
}
class PollCpu extends TimerTask{
	public void run() {
	   
		SysAction.getInstance().doNextCpu();
	}
}
class PollRam extends TimerTask{
	public void run() {
	   
		SysAction.getInstance().doNextRam();
	}
}
/*
class FlashDiskLEDW extends TimerTask{
	public void run() {
	   
		SysAction.getInstance().flashWrt();
	}
}
class FlashDiskLED extends TimerTask{
 
	public void run() {
	   
		SysAction.getInstance().flashRead();
	 
	}
}*/
class PollNet extends TimerTask{
	public void run() {
	   
		SysAction.getInstance().doNextNet();
	}
}
/*
class FlashNetInLED extends TimerTask{
	public void run() {
	   
		SysAction.getInstance().flashNetIn();
	}
}
class FlashNetOutLED extends TimerTask{
	public void run() {
	   
		SysAction.getInstance().flashNetOut();
	}
}*/