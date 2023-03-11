import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

public class Admin {
	int id;
	static JMenuBar mb;
	static JMenuItem logOff, info;
	static JFrame f;
	static Butone[] users = new Butone[10000];
	static int isSupera;
	static ResultSet rs,rs2;
	static JPanel userPanel;
	static Connection connection;
	static int n, n1, n2;
	static int userid;
	static String []role= new String[1000];
	static String []nicks= new String[1000];
	static String []parole= new String[1000];

	public Admin(int id) {
		this.id = id;
	}

	public int return_user_id(String nick)
	{
		try {
			
			Statement st = connection.createStatement();
			rs2=st.executeQuery("Select* from users where username='"+nick+"'");
			rs2.next();
			return  Integer.parseInt(rs2.getString("idusers"));
			
		}catch(Exception e) {};
		return -1;
	}
	
	public void AfisUseri(int m) {
		String []role= new String[1000];
		for(int i=0;i<1000;i++)
			role[i]= new String();
		String []nicks= new String[1000];
		for(int i=0;i<1000;i++)
			nicks[i]= new String();
		String []parole= new String[1000];
		for(int i=0;i<1000;i++)
			parole[i]= new String();
		for (int i = 0; i < 10000; i++) {
			users[i] = new Butone();
		}
		getConnToSql(connection, rs);
		n = 0;
		n1 = 0;
		n2 = 0;
		try {
			Statement st = connection.createStatement();
			if (m == 0) {
				rs = st.executeQuery("Select* from users where idusers<>" + userid);
			}
			if (m == 1) {
				rs = st.executeQuery("Select* from users where role='student'");
			}
			if (m == 2) {
				rs = st.executeQuery("Select* from users where role='profesor'");
			}
			if (m == 3) {
				rs = st.executeQuery("Select* from users where role='admin' and idusers <>" + userid);
			}
			while (rs.next()) {
				
				users[n].setVisible(true);
				users[n].setOpaque(false);
				users[n].setContentAreaFilled(false);
				users[n].setBorderPainted(false);
				users[n].setFont(new Font("Arial", Font.PLAIN, 14));
				userPanel.add(users[n]);
				users[n].setBounds(n2, n1, 200, 50);
				users[n].setText(rs.getString("username") + ", " + rs.getString("role"));
				users[n].i=return_user_id(rs.getString("username"));
				role[n]=rs.getString("role");
				nicks[n]=rs.getString("username");
				parole[n]=rs.getString("password");
				int nr=n;
				n++;
				n1 += 30;
				if (n % 20 == 0) {
					n2 += 200;
					n1 = 0;
					
				}
				
				users[nr].addActionListener(new ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						isSupera=IsSuper();
						if(role[nr].equals("admin")&&(isSupera==0))
						{
							
						JOptionPane.showMessageDialog(f, "Nu puteti accesa acest utilizator");
						}
						else
						{
						
							AfUserPtAdmin afu=new AfUserPtAdmin(users[nr].i,nicks[nr],role[nr],parole[nr],isSupera);
							afu.afisare();
						}
						}
					
					});
				
			}
			SwingUtilities.updateComponentTreeUI(f);
			
			
			
			
		}

		catch (Exception ex) {
			
		}
	}

	public static void getConnToSql(Connection conection, ResultSet rs) {
		rs = null;
		try {

			connection = DriverManager
					.getConnection("jdbc:mysql://localhost/u?user=root&password=12345&serverTimezone=UTC");
		}

		catch (Exception ex) {
			
		}

	}

	public int IsSuper() {
		ResultSet rs2 = null;
		Connection connection2 = null;
		try {

			connection2 = DriverManager
					.getConnection("jdbc:mysql://localhost/u?user=root&password=12345&serverTimezone=UTC");
			Statement st = connection2.createStatement();
			rs2 = st.executeQuery("select* from admins where idadmins=" + id);
			rs2.next();
			
			return Integer.parseInt(rs2.getString("super"));
		}

		catch (Exception ex) {
			
		}
		return -1;

	}

	public void Afisare(JFrame popUpLogIn, String userName, String userPass, int userId) {
		userid = userId;
		f = new JFrame("Admin - " + userName);
		f.setBounds(0, 0, 1500, 800);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);

		mb = new JMenuBar();
		logOff = new JMenuItem(userName + " - Log off");
		info = new JMenuItem("Date personale");
		mb.add(logOff);
		mb.add(info);
		f.setJMenuBar(mb);

		logOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showConfirmDialog(popUpLogIn, "Are u sure?");
				if (n == JOptionPane.OK_OPTION) {
					popUpLogIn.show();
					f.dispose();
				}

			}
		});

		info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Afisare_inf_admin a = new Afisare_inf_admin();
				isSupera = IsSuper();
				a.afisare_date(id, userName, userPass, userid, f, popUpLogIn, isSupera);

			}
		});
		userPanel = new JPanel();
		userPanel.setBounds(0, 100, 1500, 800);
		userPanel.setVisible(true);
		userPanel.setLayout(null);
		f.getContentPane().add(userPanel);
		JRadioButton r1 = new JRadioButton(" Studenti");
		JRadioButton r2 = new JRadioButton(" Profesori");
		JRadioButton r3 = new JRadioButton(" Admini");
		JRadioButton r4 = new JRadioButton(" Toti");
		r1.setBounds(120, 50, 100, 30);
		r2.setBounds(220, 50, 100, 30);
		r3.setBounds(320, 50, 100, 30);
		r4.setBounds(20, 50, 100, 30);
		ButtonGroup bg = new ButtonGroup();
		bg.add(r1);
		bg.add(r2);
		bg.add(r3);
		bg.add(r4);
		f.getContentPane().add(r4);
		f.getContentPane().add(r1);
		f.getContentPane().add(r2);
		f.getContentPane().add(r3);

		r1.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				userPanel.removeAll();
				AfisUseri(1);
				for(int i=0;i<1000;i++)
					role[i]= new String();
				for(int i=0;i<1000;i++)
					nicks[i]= new String();
				for(int i=0;i<1000;i++)
					parole[i]= new String();
				SwingUtilities.updateComponentTreeUI(f);
			}

		});
		r2.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				userPanel.removeAll();
				AfisUseri(2);
				for(int i=0;i<1000;i++)
					role[i]= new String();
				for(int i=0;i<1000;i++)
					nicks[i]= new String();
				for(int i=0;i<1000;i++)
					parole[i]= new String();
				SwingUtilities.updateComponentTreeUI(f);
			}

		});
		r3.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				userPanel.removeAll();
				AfisUseri(3);
				for(int i=0;i<1000;i++)
					role[i]= new String();
				for(int i=0;i<1000;i++)
					nicks[i]= new String();
				for(int i=0;i<1000;i++)
					parole[i]= new String();
				SwingUtilities.updateComponentTreeUI(f);
			}

		});
		r4.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				userPanel.removeAll();
				AfisUseri(0);
				for(int i=0;i<1000;i++)
					role[i]= new String();
				for(int i=0;i<1000;i++)
					nicks[i]= new String();
				for(int i=0;i<1000;i++)
					parole[i]= new String();
				SwingUtilities.updateComponentTreeUI(f);
			}

		});
		

		f.setLayout(null);
		f.setVisible(true);
	}

}
