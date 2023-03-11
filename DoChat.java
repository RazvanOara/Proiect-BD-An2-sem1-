import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;

import com.mysql.cj.xdevapi.Statement;

public class DoChat {
	static ResultSet rs,rs2,rs3,rs4;
	static Connection connection;
	static JPanel chat;
	static JTextField textChat;
	static ArrayList<JLabel> messages = new ArrayList<JLabel>();
	static JTextField scris;
	static JButton send,refresh;
	static JScrollPane jsp;
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
	public void trimite_msg(String text,int idUser, int idGrup)
	{
		try {	
			java.sql.Statement   selectStatement = connection.createStatement();
		 rs=selectStatement.executeQuery("call u.new_message("+idUser+","+idGrup+",'"+text+"')");
		 rs.next();
		 
	}catch(Exception ex) {};
	}
	public boolean isProf(int idUser)
	{
		try {	
			java.sql.Statement   selectStatement = connection.createStatement();
		 rs4=selectStatement.executeQuery("select* from users where idusers="+idUser);
		 rs4.next();
		if(rs4.getString("role").equals("student")) return false;
		 
	}catch(Exception ex) {};
		return true;
	}
	
	public String get_username(int idUser)
	{
		if(isProf(idUser))
		{try {	
			java.sql.Statement   selectStatement = connection.createStatement();
		 rs3=selectStatement.executeQuery("select* from professors where idusers="+idUser);
		 rs3.next();
		 return rs3.getString("first_name")+" "+rs3.getString("last_name");
		 
	}catch(Exception ex) {};}
	else
	{
		try {	
			java.sql.Statement   selectStatement = connection.createStatement();
		 rs3=selectStatement.executeQuery("select* from students where idusers="+idUser);
		 rs3.next();
		 return rs3.getString("first_name")+" "+rs3.getString("last_name");
		 
	}catch(Exception ex) {};
	}		
return null;
	
	}
public ArrayList<JLabel> get_mes(int idGrup,ArrayList<JLabel> mesaje,int idUser)
{
	mesaje=new ArrayList<JLabel>();
	try {
		java.sql.Statement s = connection.createStatement();
		rs = s.executeQuery("Select* from messages where idgroup=" + idGrup + " order by date");
		int i = 0;

		while (rs.next()) {

			JLabel a = new JLabel(rs.getString("date")+
					" " +get_username( Integer.parseInt( rs.getString("idusers"))) + ":" + rs.getString("text"));
			a.setBounds(10, 10 + i * 40, 2000, 40);
			a.setVisible(true);
			a.setFont(new Font("Arial", Font.PLAIN,20));
			mesaje.add(a);
			i++;

		}

	} catch (Exception e) {
	}
	return mesaje;
}
	
	
	public void afisare(JFrame f,int idUser, int idGrup)
	{
		getConnToSql(connection,rs);
		
		scris=new JTextField();
		scris.setBounds(580,700,900,50);
		scris.setVisible(true);
		f.getContentPane().add(scris);
		
		send= new JButton("Trimte");
		send.setVisible(true);
		f.getContentPane().add(send);
		send.setBounds(500,700,80,50);
		
		refresh=new JButton("Refreș");
		refresh.setVisible(true);
		refresh.setBounds(500,670,80,30);
		f.getContentPane().add(refresh);
		
		messages.removeAll(messages);	
		chat = new JPanel();
		chat.setBounds(0, 0, 1920, 1080);
		chat.setVisible(true);
		chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));
		
		messages=get_mes(idGrup,messages,idUser);
			
		for (int i = 0; i < messages.size(); i++) {
			chat.add(messages.get(i));
		}
		
		
		
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
		jsp = new JScrollPane(chat, v, h);
		jsp.setPreferredSize(new Dimension(600, 600));
		jsp.setBounds(500, 20, 1000, 650);
		f.add(jsp);
		f.setLayout(null);
		SwingUtilities.updateComponentTreeUI(f);
		
	
		
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		
				f.remove(jsp);
				
				scris.setText(null);
				
				chat = new JPanel();
				chat.setBounds(0, 0, 1920, 1080);
				chat.setVisible(true);
				chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));
				
				messages=get_mes(idGrup,messages,idUser);
				
				for (int i = 0; i < messages.size(); i++) {
					chat.add(messages.get(i));
				}
				
				chat.revalidate();
				chat.repaint();
				
				
				int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
				int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
				 jsp = new JScrollPane(chat, v, h);
				jsp.setPreferredSize(new Dimension(600, 600));
				jsp.setBounds(500, 20, 1000, 650);
				f.add(jsp);
				f.setLayout(null);
				SwingUtilities.updateComponentTreeUI(f);
				
			
				
			}
		
		});
		
		send.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				f.remove(jsp);
				
				trimite_msg(scris.getText(),idUser,idGrup);
				scris.setText(null);
				
				chat = new JPanel();
				chat.setBounds(0, 0, 1920, 1080);
				chat.setVisible(true);
				chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));
				
				messages=get_mes(idGrup,messages,idUser);
				
				for (int i = 0; i < messages.size(); i++) {
					chat.add(messages.get(i));
				}
				
				chat.revalidate();
				chat.repaint();
				
				
				int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
				int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
				 jsp = new JScrollPane(chat, v, h);
				jsp.setPreferredSize(new Dimension(600, 600));
				jsp.setBounds(500, 20, 1000, 650);
				f.add(jsp);
				f.setLayout(null);
				SwingUtilities.updateComponentTreeUI(f);
				
			
			}
		
		});
		
		
		
		f.setLayout(null);
		
		
	}

}
