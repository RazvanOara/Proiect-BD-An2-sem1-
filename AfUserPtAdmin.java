import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class AfUserPtAdmin {

	static ResultSet rs,rs2,rs3;
	static Connection connection;
	static JFrame f;
	int idUser;
	String nick,role,password;
	static JTextField textnume,textprenume,textadresa,textcnp,year,parola,username,departament,superad;
	static JButton editi;
	static JLabel nu,pn,ad,cnp,y,pa,us;
	//static String minHour;
	static boolean edit;
	static int idStudent,idprof,minHour,maxHour,isEditorAdminSuper,idAdmin;
	
	static JButton cursuri;
	
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
	
	public int getIdStudent(int idUser)
	{
		try {
		 Statement   selectStatement = connection.createStatement();
         rs=selectStatement.executeQuery("select* from students where idusers="+ idUser);
         rs.next();
         return Integer.parseInt(rs.getString("idstudents"));
		}catch(Exception e) {};
		return -1;
	}
	public int getIdAdmin(int idUser)
	{
		try {
		 Statement   selectStatement = connection.createStatement();
         rs=selectStatement.executeQuery("select* from admins where idusers="+ idUser);
         rs.next();
         return Integer.parseInt(rs.getString("idadmins"));
		}catch(Exception e) {};
		return -1;
	}
	public int getIdProf(int idUser)
	{
		try {
		 Statement   selectStatement = connection.createStatement();
         rs=selectStatement.executeQuery("select* from professors where idusers="+ idUser);
         rs.next();
         return Integer.parseInt(rs.getString("idprofessors"));
		}catch(Exception e) {};
		return -1;
	}
	public String getRole(int idUser)
	{
		try {
		 Statement   selectStatement = connection.createStatement();
         rs=selectStatement.executeQuery("select* from users where idusers="+ idUser);
         rs.next();
         return rs.getString("role");
		}catch(Exception e) {};
		return null;
	}
	public AfUserPtAdmin(int idUser,String username,String role,String password,int isEditorAdminSuper) {
		this.idUser=idUser;
		this.nick=username;
		this.role=role;
		this.password=password;
		this.isEditorAdminSuper=isEditorAdminSuper;
	}

	public void afisare()
	{
		getConnToSql();
		idStudent=getIdStudent(idUser);
		idprof=getIdProf(idUser);  
		idAdmin=getIdAdmin(idUser);
		f=new JFrame("Utilizatorul "+nick);
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		
		
		if(role.equals("student")) //afisare pt un student
		{
			cursuri= new JButton("Vizualizare cursuri");
			cursuri.setVisible(true);
			cursuri.setBounds(100,500,200,50);
			f.getContentPane().add(cursuri);			
			
			
			cursuri.addActionListener(new ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					 AfisareCursuri nou=new  AfisareCursuri();
		        	 nou.afisare(idStudent,1);
					 
					}
				
				});
			
			
			
			
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
						
					int ok=JOptionPane.showConfirmDialog(f, "Continuati modificarfile?");
					if(ok==JOptionPane.YES_OPTION)
					{
					try {
						Statement selectStatement= connection.createStatement();
						Statement selectStatement2= connection.createStatement();
						rs2=selectStatement.executeQuery("call u.update_data_student("+idStudent+",'"+textnume.getText()+"','"+
								textprenume.getText()+"',"+textcnp.getText()+",'"+textadresa.getText()+"',"+
								 year.getText()+","+Integer.parseInt("0")+");"//schimba din null in 0  min hours
								 );
						
						rs3=selectStatement2.executeQuery("call u.update_data_user ("+idUser+",'"+username.getText()+"','"+
						parola.getText()+"','student')");
						
					} catch (SQLException e1) {}
					
				
					
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
						parola.setText(password);
					username.setText(nick);
					minHour=Integer.parseInt(rs.getString("min_hours"));
					maxHour=Integer.parseInt(rs.getString("max_hours"));
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
				parola.setText(password);
			username.setText(nick);
			minHour=Integer.parseInt(rs.getString("min_hours"));
			maxHour=Integer.parseInt(rs.getString("max_hours"));
			}catch(Exception e) {};
					
			
		}
		else
			if(role.equals("profesor"))
			{
				cursuri= new JButton("Vizualizare cursuri");
				cursuri.setVisible(true);
				cursuri.setBounds(100,500,200,50);
				f.getContentPane().add(cursuri);			
				
				
				cursuri.addActionListener(new ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						CursuriProfPtAdmin nou=new   CursuriProfPtAdmin();
			        	 nou.afisare(idprof);
						 
						}
					
					});
				
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
				
				departament=new JTextField();
				departament.setVisible(true);
				departament.setBounds(200,221,200,20);
				f.getContentPane().add(	departament);
				departament.setEnabled(false);
				JLabel y=new JLabel("Departament:");
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
							departament.setEnabled(false);
							parola.setEnabled(false);
							
						int ok=JOptionPane.showConfirmDialog(f, "Continuati modificarfile?");
						if(ok==JOptionPane.YES_OPTION)
						{
						try {
							
							Statement selectStatement= connection.createStatement();
							Statement selectStatement2= connection.createStatement();
							rs2=selectStatement.executeQuery("call u.update_data_professor("+idprof+",'"+textnume.getText()+"','"+
									textprenume.getText()+"','"+textcnp.getText()+"','"+textadresa.getText()+"','"+
									 departament.getText()+"',"+Integer.parseInt(rs.getString("min_hours"))+","+Integer.parseInt(rs.getString("max_hours"))+");");//schimba din null in 0  min hours
							
							rs3=selectStatement2.executeQuery("call u.update_data_user ("+idUser+",'"+username.getText()+"','"+
							parola.getText()+"','profesor')");
							
						} catch (SQLException e1) {}
						
					
						
						}
						else
						{
							
							try {
							Statement   selectStatement = connection.createStatement();
							 rs=selectStatement.executeQuery("select* from professors where idprofessors="+idprof);
							 rs.next();	
							textnume.setText(rs.getString("first_name"));
							textprenume.setText(rs.getString("last_name"));
							textcnp.setText(rs.getString("cnp"));
							textadresa.setText(rs.getString("adress"));
							departament.setText(rs.getString("department"));
							parola.setText(password);
						username.setText(nick);
						minHour=Integer.parseInt(rs.getString("min_hours"));
						maxHour=Integer.parseInt(rs.getString("max_hours"));
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
						departament.setEnabled(true);
						}
					}
					
				});
				try {
					Statement   selectStatement = connection.createStatement();
					 rs=selectStatement.executeQuery("select* from professors where idprofessors="+idprof);
					 rs.next();	
					textnume.setText(rs.getString("first_name"));
					textprenume.setText(rs.getString("last_name"));
					textcnp.setText(rs.getString("cnp"));
					textadresa.setText(rs.getString("adress"));
					departament.setText(rs.getString("department"));
					parola.setText(password);
				username.setText(nick);
				minHour=Integer.parseInt(rs.getString("min_hours"));
				maxHour=Integer.parseInt(rs.getString("max_hours"));
				}catch(Exception e) {};
				
				
			}
			else if(role.equals("admin"))
			{
				
				if(isEditorAdminSuper==0)
				{
				
					JOptionPane.showMessageDialog(f, "Nu puteti accesa datele acestui utilizator!");
					
				}
				else
				{
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
					
					superad=new JTextField();
					superad.setVisible(true);
					superad.setBounds(200,221,200,20);
					f.getContentPane().add(superad);
					superad.setEnabled(false);
					JLabel y=new JLabel("Super admin ");
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
								superad.setEnabled(false);
								
							int ok=JOptionPane.showConfirmDialog(f, "Continuati modificarfile?");
							if(ok==JOptionPane.YES_OPTION)
							{
							try {
								Statement selectStatement= connection.createStatement();
								Statement selectStatement2= connection.createStatement();
							
								rs2=selectStatement.executeQuery("call u.update_data_admin("+idAdmin+",'"+textnume.getText()+"','"+
										textprenume.getText()+"','"+textcnp.getText()+"','"+textadresa.getText()+"',"+
									Integer.parseInt(superad.getText())+");");
								
								rs3=selectStatement2.executeQuery("call u.update_data_user ("+idUser+",'"+username.getText()+"','"+
								parola.getText()+"','admin')");
								
							} catch (SQLException e1) {}
							
						
							
							}
							else
							{
								
								
								try {
									
								Statement   selectStatement = connection.createStatement();
								 rs=selectStatement.executeQuery("select* from admins where idadmins="+idAdmin);
								 rs.next();	
								textnume.setText(rs.getString("first_name"));
								textprenume.setText(rs.getString("last_name"));
								textcnp.setText(rs.getString("cnp"));
								textadresa.setText(rs.getString("adress"));
								superad.setText(rs.getString("super"));
								parola.setText(password);
							    username.setText(nick);
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
							superad.setEnabled(true);
							}
						}
						
					});
					try {
						
						Statement   selectStatement = connection.createStatement();
						 rs=selectStatement.executeQuery("select* from admins where idadmins="+idAdmin);
						 rs.next();	
						textnume.setText(rs.getString("first_name"));
						textprenume.setText(rs.getString("last_name"));
						textcnp.setText(rs.getString("cnp"));
						textadresa.setText(rs.getString("adress"));
						superad.setText(rs.getString("super"));
						parola.setText(password);
						username.setText(nick);
					
					}catch(Exception e) {};
							
					
					
				}
				
				
			}
		
		
		
		
		f.setLayout(null);
		f.setVisible(true);
	}
}
