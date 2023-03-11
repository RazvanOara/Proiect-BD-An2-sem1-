import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Afisare_inf_student {

	public Afisare_inf_student() {
		
	}
	public static void getConnToSql(Connection conection,ResultSet rs)
	{
		  rs = null;
			try {
				
				 connection=DriverManager.getConnection("jdbc:mysql://localhost/u?user=root&password=12345&serverTimezone=UTC");
		          }
				
			
			catch(Exception ex)	{
				ex.printStackTrace();
			}
			
		}
	static Connection connection;
	static ResultSet rs,rs2,rs3;
	static JFrame f;
	static JTextField textnume,textprenume,textadresa,textcnp,year,parola,username;
	static JButton editi;
	static JLabel nu,pn,ad,cnp,y,pa,us;
	static String minHour;
	static boolean edit;
	public void afisare_date(int idStudent,String userName, String userPassword,int userid,JFrame mare,JFrame logIn)
	{
		f=new JFrame("Proiect_bd");
		f.setBounds(0,0,900,600);
		f.setLayout(null);
		f.setVisible(false);
		getConnToSql(connection,rs);
		
		editi=new JButton("Editare");
		editi.setVisible(true);
		editi.setBounds(500, 50, 200, 200);
		editi.setOpaque(false);
		editi.setContentAreaFilled(false);
		editi.setBorderPainted(false);
		editi.setFont(new Font("Arial", Font.PLAIN,20));
		f.getContentPane().add(editi);
		
		parola=new JTextField();
		parola.setVisible(true);
		parola.setBounds(200,272,200,20);
		f.getContentPane().add(parola);
		parola.setEnabled(false);
		JLabel pa=new JLabel("Parola:");
		pa.setBounds(100,272,200,20);
		pa.setVisible(true);
		f.getContentPane().add(pa);
		
		username=new JTextField();
		username.setVisible(true);
		username.setBounds(200,323,200,20);
		f.getContentPane().add(username);
		username.setEnabled(false);
		JLabel us=new JLabel("Username:");
		us.setBounds(100,323,200,20);
		us.setVisible(true);
		f.getContentPane().add(us);
	
		textnume=new JTextField();
		textnume.setVisible(true);
		textnume.setBounds(200,17,200,20);
		f.getContentPane().add(textnume);
		textnume.setEnabled(false);
		JLabel nu=new JLabel("Nume:");
		nu.setBounds(100,17,200,20);
		nu.setVisible(true);
		f.getContentPane().add(nu);
		
		textprenume=new JTextField();
		textprenume.setVisible(true);
		textprenume.setBounds(200,68,200,20);
		f.getContentPane().add(textprenume);
		textprenume.setEnabled(false);
		JLabel pn=new JLabel("Prenume:");
		pn.setBounds(100,68,200,20);
		pn.setVisible(true);
		f.getContentPane().add(pn);
		
		textcnp=new JTextField();
		textcnp.setVisible(true);
		textcnp.setBounds(200,119,200,20);
		f.getContentPane().add(textcnp);
		textcnp.setEnabled(false);
		JLabel cnp=new JLabel("CNP: ");
		cnp.setBounds(100,119,200,20);
		cnp.setVisible(true);
		f.getContentPane().add(cnp);
		
		textadresa=new JTextField();
		textadresa.setVisible(true);
		textadresa.setBounds(200,170,200,20);
		f.getContentPane().add(textadresa);
		textadresa.setEnabled(false);
		JLabel a=new JLabel("Adresa: ");
		a.setBounds(100,170,200,20);
		a.setVisible(true);
		f.getContentPane().add(a);
		
		year=new JTextField();
		year.setVisible(true);
		year.setBounds(200,221,200,20);
		f.getContentPane().add(year);
		year.setEnabled(false);
		JLabel y=new JLabel("An de studiu:");
		y.setBounds(100,221,200,20);
		y.setVisible(true);
		f.getContentPane().add(y);
		
		editi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
				if(edit)
				{
					edit=false;
					
					editi.setText("Editare");
					
					textnume.setEnabled(false);
					textprenume.setEnabled(false);
					textcnp.setEnabled(false);
					textadresa.setEnabled(false);
					username.setEnabled(false);
					parola.setEnabled(false);
					
				int ok=JOptionPane.showConfirmDialog(f, "Va trebui sa va reconectati. Continuati modificarfile?");
				if(ok==JOptionPane.YES_OPTION)
				{
				try {
					Statement selectStatement= connection.createStatement();
					Statement selectStatement2= connection.createStatement();
					rs2=selectStatement.executeQuery("call u.update_data_student("+idStudent+",'"+textnume.getText()+"','"+
							textprenume.getText()+"','"+textcnp.getText()+"','"+textadresa.getText()+"',"+
							 year.getText()+","+Integer.parseInt("0")+");"//schimba din null in 0  min hours
							 );
					
					rs3=selectStatement2.executeQuery("call u.update_data_user ("+userid+",'"+username.getText()+"','"+
					parola.getText()+"','student')");
					
				} catch (SQLException e1) {}
				
				f.dispose();
				mare.dispose();
				logIn.show();
				
				}
				else
				{
					
					try {
					Statement   selectStatement = connection.createStatement();
					 rs=selectStatement.executeQuery("select* from students where idstudents="+idStudent);
					 rs.next();	
					textnume.setText(rs.getString("first_name"));
					textprenume.setText(rs.getString("last_name"));
					textcnp.setText(rs.getString("cnp"));
					textadresa.setText(rs.getString("adress"));
					year.setText(rs.getString("year_of_study"));
					parola.setText(userPassword);
				username.setText(userName);
				minHour=rs.getString("min_hours");
				}catch(Exception ex) {};
					
					
				}
				
				}
			else
				{
				edit=true;
				editi.setText("Salvare");
				textnume.setEnabled(true);
				textprenume.setEnabled(true);
				textcnp.setEnabled(true);
				textadresa.setEnabled(true);
				username.setEnabled(true);
				parola.setEnabled(true);
				}
			}
			
		});
		try {
			Statement   selectStatement = connection.createStatement();
			 rs=selectStatement.executeQuery("select* from students where idstudents="+idStudent);
			 rs.next();	
			textnume.setText(rs.getString("first_name"));
			textprenume.setText(rs.getString("last_name"));
			textcnp.setText(rs.getString("cnp"));
			textadresa.setText(rs.getString("adress"));
			year.setText(rs.getString("year_of_study"));
			parola.setText(userPassword);
		username.setText(userName);
		minHour=rs.getString("min_hours");
		}catch(Exception e) {};
		
		f.setLayout(null);
		f.setVisible(true);
	}
}
