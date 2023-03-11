import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;

public class Schimbare_procent {

	static JFrame f;
	static Connection connection;
	static ResultSet rs,rs2;
	static JLabel[] bs;
	static JTextField[] t;
	static JButton edit;
	static int n;
	public int get(JTextField[] t,int n,String type,JLabel[] b)
	{

		int j=-1;
		for(int i=0;i<n;i++)
		{
		
			if(b[i].getText().equals(type))
				j=i;	
		}
		
		try{return Integer.parseInt(t[j].getText());}
		catch( Exception ex){ return 0;
		}
		}
	
	public String get_procent(String name,String type)
	{
		try {
			
			Statement   selectStatement = connection.createStatement();
			rs2=selectStatement.executeQuery("SELECT * FROM u.percentagesgrades where course_name='"+name+"'");
			rs2.next();
			if(type.equals("Curs"))
					 return rs2.getString("course_p");
			if(type.equals("Laborator"))
				 return rs2.getString("laboratory_p");
			if(type.equals("Seminar"))
				 return rs2.getString("seminar_p");
			
			
			
		}catch(Exception ex) {}
		return null;
	}
	
	public static void getConnToSql()
	{
		  rs = null;
			try {
				
				 connection=DriverManager.getConnection("jdbc:mysql://localhost/u?user=root&password=12345&serverTimezone=UTC");
		          }
				
			
			catch(Exception ex)	{
				ex.printStackTrace();
			}
			
		}
	public void afisare(String name)
	{
		edit= new JButton("Schimba");
		edit.setBounds(200,40,100,50);
		edit.setVisible(true);
		
		bs=new JLabel[3];
		for(int i =0;i<3;i++)
			bs[i]=new JLabel();
		
		t=new JTextField[3];
		for(int i=0;i<3;i++)
			{
			t[i]=new JTextField();
			t[i].setText("0");
			
			}
		
		getConnToSql();
		f=new JFrame(name);
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setBounds(0,0,1500,800);
		f.getContentPane().add(edit);
		 n=0;
		int nr=0;
try {
			
			Statement   selectStatement = connection.createStatement();
			rs=selectStatement.executeQuery("Select* from courses where name='"+name+"'");
			while(rs.next())
			{
				
				bs[n].setText(rs.getString("type"));
				bs[n].setBounds(0,nr,100,50);
				bs[n].setVisible(true);
				
				t[n].setText(get_procent(name,bs[n].getText()));
				t[n].setVisible(true);
				t[n].setBounds(105,nr+20,50,20);
				
				f.getContentPane().add(t[n]);
				f.getContentPane().add(bs[n]);
				nr+=30;
				n++;
			}
			
			
		
}catch(Exception ex) {};
		
edit.addActionListener(new ActionListener() {
	public void actionPerformed(java.awt.event.ActionEvent evt) {
	try {
		
		Statement   selectStatement = connection.createStatement();
		int curs=get(t,n,"Curs",bs);
		int seminar=get(t,n,"Seminar",bs);
		int lab=get(t,n,"Laborator",bs);
		int con=JOptionPane.showConfirmDialog(f, "Doriti sa schimbati?");
		if(con==JOptionPane.YES_OPTION) {
		rs=selectStatement.executeQuery("call u.update_percentagesgrades('"+name+"',"+curs+","+ seminar+","+ lab+");");
		
		JOptionPane.showMessageDialog(f, "A-ti schimbat cu succes!");}
	}catch(Exception ex) {};
	
	}

});
		
		
		f.setLayout(null);
		f.setVisible(true);
	}
}
