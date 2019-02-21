package blockchain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import org.json.*;

import com.google.zxing.WriterException;

import java.net.*;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class AdminFrame extends JFrame implements ActionListener, Runnable
{
	 JLabel ltitle;
	 JButton bshow;
	 JTextArea jta;
	 
	 Admin adm = new Admin();
	 
   	 static int count=0;
   	 
   	 Thread sthd;
   	 ServerSocket ss;
   	 
 	static int i=0;
 	static User userarr[]=new User[30];
	 
	 AdminFrame()
		{	 
			  super("create block");
			  setSize(550,600);
			  setLocation(200,25);
			  
			  jta=new JTextArea();
			  jta.setFont(new Font("lucida console",Font.PLAIN,18));
			  JScrollPane jsp=new JScrollPane(jta);
			  jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

			  ltitle=new JLabel("Admin Panel");
			  ltitle.setFont(new Font("lucida consol",Font.PLAIN,40));
			  ltitle.setForeground(Color.lightGray);
			  		  
			  bshow=new JButton("Show Blocks");
			  bshow.setFont(new Font("lucida consol",Font.PLAIN,18));
			  bshow.setBackground(new Color(100,100,150));
			  bshow.setForeground(Color.white);
		  
			  AdminPanel p=new AdminPanel(5,5,5,5);
			  p.setLayout(new BorderLayout(5,5));
			  
			  	AdminPanel p1=new AdminPanel(5,150,5,100);
			  	p1.setLayout(new GridLayout(2,1,5,5));
			  	p1.add(ltitle);
			  	p1.add(new JLabel());
			  				 
			  	AdminPanel p2=new AdminPanel(5,5,5,5);
			  	p2.setLayout(new BorderLayout(5,5));
			  	p2.add(bshow, BorderLayout.NORTH);
			  	p2.add(jsp, BorderLayout.CENTER);			  	
			  	
			  p.add(p1,BorderLayout.NORTH);
			  p.add(p2,BorderLayout.CENTER);
  
			  add(p);
			  
			  bshow.addActionListener(this);	
			  
			  
			 try 
			 	{
					ss=new ServerSocket(1234);
					
					System.out.println("			** SERVER **");
					System.out.println("waiting for client....\n\n\n\n");
					
					sthd=new Thread(this);
					sthd.start();
					  
				} 
			 	catch (IOException e) 
			 	{
					e.printStackTrace();
				}
	
		}
		
		public void actionPerformed(ActionEvent ae)
		{
			if(ae.getSource()==bshow)
			{
				int j=0;
				while(AdminFrame.userarr[j]!=null)
				{
					adm.authenticateUserChain(AdminFrame.userarr[j]);
					j++;
				}
				adm.printAdminBlockchainConsole();
				adm.printAdminBlockchain(jta);
			}			
		}
		
		public void run()
		 {
		  try
		  {
			  while(true)
				{
					Socket skt=ss.accept();
					System.out.println("Connection Establised");
					System.out.println("Total Connected: " +(++count));
					ClientThread ct=new ClientThread(skt);
					ct.start();
				}
		  } 
		  catch (IOException e) 
		  {
		   e.printStackTrace();
		  }
		 }

}

 
 class AdminPanel extends JPanel
 {
 	private int top,left,bottom,right;
 	
 	AdminPanel(int top, int left, int bottom, int right)
 	{
 		this.top=top;
 		this.left=left;
 		this.bottom=bottom;
 		this.right=right;	
 	}

 	public void paintComponent(Graphics g)
 	{
 		super.paintComponent(g);
 		setBackground(new Color(50,50,50));
 	}

 	public Insets getInsets()
 	{
 		return new Insets(top,left,bottom,right);
 	}
 }


class ClientThread extends Thread
{
	Socket skt;
	
	Scanner sc;
	PrintWriter pw;
	
	String username;
	String name;
	
	QRCode qrc;

	int usercount=0;
	
	ClientThread(Socket skt)
	{
		this.skt=skt;
		
		try
		{
			sc=new Scanner(skt.getInputStream());
			pw=new PrintWriter(skt.getOutputStream(),true);
		
			//pw.println("Server connected");	
		}
		catch(IOException e)
		{
			e.printStackTrace();
		
		}
	}
	
	@Override
	public void run() 
	{
		while(sc.hasNextLine())
		{
			String str=sc.nextLine();
			String[] msg=str.split(":");
			String head=msg[0];
			
			switch(head)
			{
				case "Create"  : 
			    {
			    	try
			    	{
			    		String rec_name=msg[1];
						String rec_mail=msg[2];
						String rec_username=msg[3];
						String rec_password=msg[4];
			    		
						FileReader fr=new FileReader("data.txt");
						BufferedReader br=new BufferedReader(fr);
					  
						String data=br.readLine();
						int flag=0;
						System.out.println(data +"arinvd");
						while(data!=null)
						{
							String[] data_msg=data.split(":");
							
							if(rec_mail.equalsIgnoreCase(data_msg[1]) || rec_username.equals(data_msg[2]))
							{
								if(rec_mail.equalsIgnoreCase(data_msg[1]))
								{	
									pw.println("mail_exist");
								}
								else
								if(rec_username.equalsIgnoreCase(data_msg[2]))
								{
									pw.println("username_exist");
								}
							
								flag=1;
								break;
							}
							
							data=br.readLine();
						}
						
						br.close();
						
						if(flag==0)
						{
							FileWriter fw=new FileWriter("data.txt",true);
							BufferedWriter bw=new BufferedWriter(fw);
						
							String record=rec_name +":" +rec_mail +":" +rec_username +":" +rec_password;
							bw.write(record);
							bw.newLine();
							
							bw.close();
							
							username=rec_username;
							name=rec_name;
							
							AdminFrame.userarr[AdminFrame.i]=new User(rec_username, Integer.toString(AdminFrame.i), rec_name);
							AdminFrame.i++;
							
							qrc=new QRCode(username);
							
							pw.println("created");
						}
						
			    	}
			    	catch(FileNotFoundException e)
			    	{
			    		System.out.println("File Read Alert - " +e.getMessage());
			    	} 
			    	catch (IOException e) 
			    	{
						System.out.println("File Read Alert - " +e.getMessage());
					}
			    	
			    	
			    	break;
			    }
			    
				case "Login" :
				{
					String rec_username=msg[1];
					String rec_password=msg[2];
					
					try
					{
						FileReader fr=new FileReader("data.txt");
						BufferedReader br=new BufferedReader(fr);
				  
						String data=br.readLine();
						int flag=0;
						String[] data_msg=null;
						
						while(data!=null)
						{
							data_msg=data.split(":");
							
							if(data_msg[2].equals(rec_username) && data_msg[3].equals(rec_password))
							{
								flag=1;
								break;
							}
							
							data=br.readLine();
						}
						
						if(flag==1)
						{
							pw.println("success"); 
							pw.println(data_msg[0] +":" +data_msg[1] +":" +data_msg[2]);
							
							username=rec_username;
							name=data_msg[0];
							
							qrc=new QRCode(username);
						
						}
						else
						{
							pw.println("fail");
						}
						
					}
					catch(FileNotFoundException e)
					{
						System.out.println("File Read Alert - " +e.getMessage());
					} 
					catch (IOException e) 
					{
						System.out.println("File Read Alert - " +e.getMessage());
					}
					
					break;
				}
				
				case "Create Block" :
				{
					//String rec_username=msg[1];
					String rec_data=msg[2];
					
					int i=0;
					while(AdminFrame.userarr[i]!=null)
					{
						System.out.println(" if if if if");
						if(AdminFrame.userarr[i].returnGenBlock(username))
						{
							System.out.println(" if testif if if");
							
							usercount = i;
							System.out.println(i);
							break;
						}
						
						i++;
					}
					System.out.println(rec_data +"arivnd" + usercount);
					AdminFrame.userarr[usercount].createBlock(rec_data);
					
					pw.println("done");
					
					break;
				}
				
				case "Print" :
				{
//					pw.println(chainserver.userarr +":" +usercount);
					
					int i=0;
					while(AdminFrame.userarr[i]!=null)
					{
						System.out.println(" if if if if");
						if(AdminFrame.userarr[i].returnGenBlock(username))
						{
							System.out.println(" if testif if if");
							
							usercount = i;
							System.out.println(i);
							break;
						}
						
						i++;
					}
					
					JSONArray jarrserver = AdminFrame.userarr[usercount].printUserBlockchainJson();
					
					pw.println(jarrserver.toString());
					
					break;
				}
				
				case "QRCode" :
				{
					int i=0;
					while(AdminFrame.userarr[i]!=null)
					{
						if(AdminFrame.userarr[i].returnGenBlock(username))
						{
							System.out.println(" QR Code test ");
							System.out.println(i);
							break;
						}
						
						i++;
					}
					
					JSONObject jobj=AdminFrame.userarr[usercount].printAuthenticatedBlocksJson();
					AdminFrame.userarr[usercount].printAuthenticatedBlocks();
					
					System.out.println(jobj.toString());
					try 
					{
						qrc.generateQRCodeImage(jobj.toString());
					} 
					catch (WriterException e) 
					{
						e.printStackTrace();
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
					
					//pw.println(jobj);
					
					break;
				}
				
			}
			 
		}
		
		
		System.out.println("Client disconnected");
		System.out.println("Total Connected: " +(--AdminFrame.count));
	}	
	

}



public class chainserver 
{		
	public static void main(String[] args) throws IOException 
	{	
		AdminFrame af=new AdminFrame();
    	af.setVisible(true);
    	af.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
