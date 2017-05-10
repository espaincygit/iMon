package com.cy.mon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Timer;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;

import com.cy.mon.icon.PicIcon;

public final class MainMon {

	public JFrame frmDisks;
	private JPanel panel_11;
	private JPanel panel_12;
	public JLabel lblNewLabel_12;
	public JLabel lblNewLabel_13;
//	public JPanel panel_netIn;
//	public JPanel panel_netOut;
	public JLabel label_net;
	public JLabel label_net_in;
	public JLabel lblNewLabel_11;
	public JLabel lblNewLabel_14;
	public JLabel lblNewLabel;
	public JLabel lblNewLabel_2;
	public JLabel lblNetworks;
	public JLabel lblNewLabel_1; 
	
	public JLabel getLblNewLabel_13() {
		return lblNewLabel_13;
	}

	public JLabel getLblNewLabel_12() {
		return lblNewLabel_12;
	}

	public JPanel getPanel_12() {
		return panel_12;
	}

	public JPanel getPanel_11() {
		return panel_11;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMon window = new MainMon();
					window.frmDisks.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainMon() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDisks = new JFrame();
		frmDisks.setTitle("iDisk");
		int width = 176;
		frmDisks.setBounds(1440 - width, 22, width, 75);
		frmDisks.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		frmDisks.getContentPane().add(panel, BorderLayout.CENTER);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(panel, popupMenu);
		
		JMenuItem menuTrans = new JMenuItem("Transparent -");
		menuTrans.setFont(new Font("Tahoma", Font.PLAIN, 14));
		menuTrans.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SysAction.resetTrans(true, frmDisks);
			}
		}); 
		popupMenu.add(menuTrans);
		
		JMenuItem menuTrans_1 = new JMenuItem("Transparent +");
		menuTrans_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		menuTrans_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SysAction.resetTrans(false, frmDisks);
			}
		});  
		popupMenu.add(menuTrans_1);
		 
		JMenuItem mntmColor = new JMenuItem("Color");
		mntmColor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		mntmColor.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				SysAction.getInstance().setAllColor();
				
			}
		});  
		popupMenu.add(mntmColor);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
	    lblNewLabel = new JLabel("Reads");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel_1.add(lblNewLabel);
		
		panel_11 = new JPanel();
		panel_11.setBackground(Color.BLACK);
		panel_1.add(panel_11);
		
	    lblNewLabel_2 = new JLabel("Writes");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel_1.add(lblNewLabel_2);
		
	    panel_12 = new JPanel();
	    panel_12.setBackground(Color.BLACK);
	    panel_1.add(panel_12);
		
		JPanel panel_13 = new JPanel();
		panel_13.setBackground(Color.BLACK);
		frmDisks.getContentPane().add(panel_13, BorderLayout.NORTH);
		panel_13.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_14 = new JPanel();
		panel_13.add(panel_14, BorderLayout.WEST);
		
	    lblNewLabel_11 = new JLabel("CPU");
		lblNewLabel_11.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel_14.add(lblNewLabel_11);
		
	    lblNewLabel_12 = new JLabel("");
	    lblNewLabel_12.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    panel_14.add(lblNewLabel_12);
	    
	    lblNewLabel_14 = new JLabel("RAM");
	    lblNewLabel_14.setFont(new Font("Tahoma", Font.BOLD, 12));
	    panel_14.add(lblNewLabel_14);
	    
	    lblNewLabel_13 = new JLabel("");
	    lblNewLabel_13.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    panel_14.add(lblNewLabel_13);

		SysAction inst = SysAction.getInstance();
//		inst.setWinStyle();
		
		inst.setMainWin(this);
		Timer timer = new Timer();

		timer.schedule(new PollData(), 10, SysAction.POLL_INTER);
//		timer.schedule(new PollDisk(), 10, 1000);
		/*
		Timer timer1 = new Timer();
		   
		timer1.schedule(new PollCpu(), 10, 5000);
		Timer timer2 = new Timer();
		   
		timer2.schedule(new PollRam(), 10, 60000 * 2);
		*/
		Timer timer3 = new Timer();
		   
		timer3.schedule(new PollNet(), 10, 4000);
		
		frmDisks.setAlwaysOnTop(true);

		MoniUtil.makeTrans(frmDisks, SysAction.TRANS); 
		PicIcon.setMacIcon();
		
		//added by cy 0130
		Color white = Color.WHITE;
		Color black = Color.BLACK;
		panel_1.setBackground(black);
		lblNewLabel_2.setForeground(white);
		lblNewLabel.setForeground(white);
		panel_14.setBackground(black);
		lblNewLabel_11.setForeground(white);
		lblNewLabel_12.setForeground(white);
		lblNewLabel_14.setForeground(white);
		lblNewLabel_13.setForeground(white);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.BLACK);
		frmDisks.getContentPane().add(panel_3, BorderLayout.SOUTH);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.BLACK);
		panel_3.add(panel_4, BorderLayout.WEST);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.BLACK);
		panel_4.add(panel_6, BorderLayout.NORTH);
		
	    lblNetworks = new JLabel("In");
		panel_6.add(lblNetworks);
		lblNetworks.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNetworks.setForeground(Color.WHITE);
		
//		panel_netIn = new JPanel();
//		panel_6.add(panel_netIn);
//		panel_netIn.setBackground(Color.BLACK);
		
		label_net_in = new JLabel("");
		panel_6.add(label_net_in);
		label_net_in.setForeground(Color.WHITE);
		label_net_in.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
	    lblNewLabel_1 = new JLabel("Out");
		panel_6.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setForeground(Color.WHITE);
		
//		panel_netOut = new JPanel();
//		panel_6.add(panel_netOut);
//		panel_netOut.setBackground(Color.BLACK);
		
		label_net = new JLabel("");
		panel_6.add(label_net);
		label_net.setForeground(Color.WHITE);
		label_net.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		frmDisks.setUndecorated(true);

	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
