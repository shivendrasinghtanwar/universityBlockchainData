package blockchain;

import java.io.IOException;
import java.io.*;
import java.net.*;
import java.util.*;

class ClientThread extends Thread
{
	Socket skt;
	
	Scanner sc;
	PrintWriter pw;

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
			}
			 
		}
		
		
		System.out.println("Client disconnected");
		System.out.println("Total Connected: " +(--chainserver.count));
	}	
	

}



public class chainserver 
{

	static int count=0;
	
	public static void main(String[] args) throws IOException 
	{
		ServerSocket ss=new ServerSocket(1234);
		System.out.println("			** SERVER **");
		System.out.println("waiting for client....\n\n\n\n");
		
		while(true)
		{
			Socket skt=ss.accept();
			System.out.println("Connection Establised");
			System.out.println("Total Connected: " +(++count));
			ClientThread ct=new ClientThread(skt);
			ct.start();
		}

	}

}
