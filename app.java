import java.sql.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class app {

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
	
	
	public app() {
	
	}
	public static void play_music(String musicLocation)
	{
		try {
			
			File musicPath=new File( musicLocation);
			if(musicPath.exists())
			{
				AudioInputStream audioInput=AudioSystem.getAudioInputStream(musicPath);	
				clip=AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();
				clip.loop(clip.LOOP_CONTINUOUSLY);
				
			}
			else
			{
				System.out.println("fail music path");
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	static Clip clip;
	static JButton  reg;
	static Connection connection;
	static ResultSet rs;
	static JFrame logInPopUp;
	static JButton logIn;
	static JLabel logInLabelPass;
	static JLabel logInLabelNick;
	static JTextField nicusor;
	static  JPasswordField logInPass;
	static String userName;
	static String userPassword;
	static JCheckBox af_parola;
	static char defaultt;
	static boolean af_p;
	static int userid;
	public static void main(String[] args) {
		
		getConnToSql();
		
		logInPopUp=new JFrame("Log in");
		logInPopUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		logInPopUp.setBounds(540, 300, 400, 200);
		
		af_parola=new JCheckBox("<html>Show <br/>password<html>");
		af_parola.setVisible(true);
		af_parola.setBounds(290, 30,100, 50);
		logInPopUp.getContentPane().add(af_parola);
		
		logInLabelPass=new JLabel("Username ");
		logInLabelPass.setBounds(50,0,100,50);
		logInLabelPass.setVisible(true);
		logInPopUp.getContentPane().add(logInLabelPass);
		
		nicusor= new JTextField("momentousmallard");//username 
		nicusor.setBounds(120,18,150,20);
		nicusor.setVisible(true);
		logInPopUp.getContentPane().add(nicusor);
		
		logInLabelNick=new JLabel("Password");
		logInLabelNick.setBounds(50,30,100,50);
		logInLabelNick.setVisible(true);
		logInPopUp.getContentPane().add(logInLabelNick);
		
		logInPass= new  JPasswordField("abcd1234");//parola
		logInPass.setEchoChar('\u25CF');
		logInPass.setBounds(120,50,150,20);
		logInPass.setVisible(true);
		logInPopUp.getContentPane().add(logInPass);
		
		 logIn=new JButton("Log in");
		 logIn.setVisible(true);
		 logIn.setBounds(140,90,100,50);
		 logInPopUp.getContentPane().add(logIn);
		 
		 reg=new JButton("Inregistreaza-te");
		 reg.setVisible(true);
		 reg.setBounds(-10,110,120,50);
		 reg.setOpaque(false);
		 reg.setContentAreaFilled(false);
		 reg.setBorderPainted(false);
		 reg.setFont(new Font("Arial", Font.PLAIN,10));
		 logInPopUp.getContentPane().add( reg);
		 
		 defaultt = logInPass.getEchoChar();
		 
		SwingUtilities.updateComponentTreeUI(logInPopUp);
		
		af_p=true;
		af_parola.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
			if(af_p)
			{logInPass.setEchoChar((char) 0 );
			af_p=false;
			}
			else
				{
				logInPass.setEchoChar('\u25CF');
				af_p=true;
				
				}
			}
		
		});
		reg.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
			Register r=new Register();
			r.register();
			}
		
		});
		logIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

				 Statement   selectStatement = connection.createStatement();
		          rs=selectStatement.executeQuery("select* from users");
		          boolean gasit_user=false;
	     		  boolean gasit_parola=false;
		          while(rs.next()) {
		        	  gasit_user=false;
		        	  gasit_parola=false;
		        	  userName=nicusor.getText();
		        	  userPassword=logInPass.getText();
		     		  
		        	 if(userName.equals(rs.getString("username")))
		        		 gasit_user=true;
		        	 if(userPassword.equals(rs.getString("password")))
		        		 gasit_parola=true;
		        
		        	 if(gasit_user&&gasit_parola) break;
		        	 
		        	 
		        	 
		        }
		        	
		          if(gasit_user&&gasit_parola)
		          { 
		        	  userid=Integer.parseInt(rs.getString("idusers"));
		        	  userName=rs.getString("username");
		        	  userPassword=rs.getString("password");
		        	//  f.dispose();
		        	  logInPopUp.dispose();
		        	 // play_music("C:\\Users\\razva\\Downloads\\Moai-sound.wav");
		        	  if(rs.getString("role").equals("student"))// II STUDENT
		        	  {
		        		  
		        		  ResultSet rsIdS=selectStatement.executeQuery("select* from students where idusers="+rs.getString("idusers"));
		        		 rsIdS.next();  
		        	  student st=new student(Integer.parseInt(rsIdS.getString("idstudents")));
		        	  st.do_student(logInPopUp,userName,userPassword,userid);
		        	  
		        	  }
		        	  else
		        		  if(rs.getString("role").equals("profesor"))
		        		  {
		        			 
		        			  ResultSet rsIdT=selectStatement.executeQuery("select* from professors where idusers="+rs.getString("idusers"));
				        		 rsIdT.next();  
				        		 Profesor p=new Profesor(Integer.parseInt(rsIdT.getString("idprofessors")));
				        		 p.do_profesor( logInPopUp, userName, userPassword, userid);
		        		  }
		        		  else
		        			  if(rs.getString("role").equals("admin"))
		        			  {
		        				  ResultSet rsIdA = selectStatement
											.executeQuery("Select* from admins where idusers=" + userid);
									rsIdA.next();
									Admin a = new Admin(Integer.parseInt(rsIdA.getString("idadmins")));
									a.Afisare(logInPopUp, userName, userPassword, userid);
		        			  }
		        	  
		        	  }
		          else
		          {
		        	  JOptionPane.showMessageDialog(logInPopUp, "Incorect data");
		        	  nicusor.setText("");
		        	  logInPass.setText("");
		          }
		        	  
		          
		          }
		          
		        
		        	 
		          
				
				catch(Exception ex)
		          {
		        	 // ex.printStackTrace();
		        	  
		          }
				
		                                                }
				
			
		});
		
		logInPopUp.setLayout(null);
		logInPopUp.setVisible(true);
	}

}
