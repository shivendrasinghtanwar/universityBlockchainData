package blockchain;


import java.security.*;
import org.json.*;

public class User{
	private String UserID,Username,UserPassword;
	private static int UserCount = 0;
	private int UserBlockCount = 0;
	protected Block genBlock,nextBlock,nextBlock2,endBlock;

	public User(String Username,String UserData,String UserID)
	{
		this.Username = Username;
		this.UserID = UserID;
		UserCount++;
		genBlock = new Block(Username,UserData,UserID,null);
		endBlock = genBlock;
		UserBlockCount++;
	}
	
	public Block getGen()
	{
		return genBlock;
	}

	protected String getUsername()
	{
		return Username;
	}
	
	public void createBlock(String UserData)
	{
		if(UserBlockCount==1)
		{
			nextBlock = new Block(genBlock.getHash(),UserData,UserID,null);
			genBlock.setLink(nextBlock);
			endBlock = nextBlock;
			UserBlockCount++;
		}
		else
		{
			endBlock = new Block(nextBlock.getHash(),UserData,UserID,null);
			nextBlock.setLink(endBlock);
			nextBlock = endBlock;
			UserBlockCount++;
		}
	}
	
	public JSONArray printUserBlockchainJson()
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		int i = 1;
		if(UserBlockCount==1)
		{
			jobj.put("Block_no", i);
			jobj.put("Data", genBlock.getData());
			jobj.put("Authenticated", genBlock.getAuthentication());
			jobj.put("HashID", genBlock.getHash());
			
			jarr.put(jobj);
		}
		else
		{
			jobj.put("Block_no", i);
			jobj.put("Data", genBlock.getData());
			jobj.put("Authenticated", genBlock.getAuthentication());
			jobj.put("HashID", genBlock.getHash());
			nextBlock = genBlock.getLink();
			
			while(nextBlock.getLink()!=endBlock.getLink())
			{
				System.out.println("Here"+i);
				jobj.put("Block_no", i);
				jobj.put("Data", nextBlock.getData());
				jobj.put("Authenticated", nextBlock.getAuthentication());
				jobj.put("HashID", nextBlock.getHash());
				i++;
				jarr.put(jobj);
				jobj = new JSONObject();
				nextBlock=nextBlock.getLink();
			}
			jobj.put("Block_no", i);
			jobj.put("Data", nextBlock.getData());
			jobj.put("Authenticated", nextBlock.getAuthentication());
			jobj.put("HashID", nextBlock.getHash());
			
			jarr.put(jobj);
		}
		return jarr;
	}

	public void printUserBlockchain()
	{
		
		int i = 1;
		System.out.println(Username+"'s Blockchain = ");
		if (UserBlockCount==1) 
		{
			System.out.println("Block No = " + genBlock.noOfBlocks());
			System.out.println("Data = " + genBlock.getData());
		}
		else
		{
        	System.out.println("\n\nBlock No = "+i);
        	i++;
        	System.out.println("Data = "+genBlock.getData());
        	System.out.println("HashID = "+genBlock.getHash());
        	System.out.println("Prev HashID = "+genBlock.getPrevHash());
        	System.out.print("\n");
        	
        	nextBlock = genBlock.getLink();
        	// if (nextBlock.getPrevHash() == genBlock.getHash())
        	// {
        	// 	System.out.println("\n\n\t\t Yeah !!!\n\n");
        	// }
        	while (nextBlock.getLink() != null)
        	{
         	   	System.out.println("Block No = "+i);
        		i++;
        		System.out.println("Data = "+nextBlock.getData());
        		System.out.println("HashID = "+nextBlock.getHash());
        		System.out.println("Prev HashID = "+nextBlock.getPrevHash());
        		System.out.print("\n");
        		// nextBlock2 = nextBlock;
          	   	nextBlock = nextBlock.getLink();
          		//if (nextBlock.getPrevHash() == nextBlock2.getHash())
        		// {
        		// 	System.out.println("\n\n\t\t Yeah !!!\n\n");
        		// }
        	}
        	System.out.println("Block No = "+i);
        	i++;
        	System.out.println("Data = "+nextBlock.getData());
        	System.out.println("HashID = "+nextBlock.getHash());
        	System.out.print("\n");
		}
	}
	
	public JSONObject printAuthenticatedBlocksJson()
	{
		JSONObject jobj = new JSONObject();
		jobj.put("Name",this.Username);
		int i = 1;
		jobj.put("Gen_Data", genBlock.getData());
		nextBlock = genBlock;
		while(nextBlock.getLink()!=null)
		{
			nextBlock=nextBlock.getLink();
			if(nextBlock.getAuthentication())
			{
				jobj.put("Data"+i,nextBlock.getData());
			}
			i++;
		}
		return jobj;
	}

	public void printAuthenticatedBlocks()
	{
		int i = 1;
		System.out.println(Username+"'s Authenticated Blockchain = ");
		nextBlock = genBlock;
        while (nextBlock.getLink() != null)
        {
      	   	nextBlock = nextBlock.getLink();
        	if(nextBlock.getAuthentication())
        	{
        		System.out.println("Block No = "+i);
        		System.out.println(" Data = "+nextBlock.getData()+
        				"\n Authenticated = "+nextBlock.getAuthentication()+"\n");
        	}
        	i++;
        }
	}

	public void setBlockAuthentication(User one,int blockNo,boolean Authenticated)
	{
		Block nptr = new Block(null,null,null,null);
		int i;
		if(blockNo==1)
		{
			nptr = one.genBlock;
		}
		else
		{
			blockNo--;
			nptr = one.genBlock.getLink();
			for (i=1;i<blockNo;i++) 
			{
				nptr = nptr.getLink();
			}
		}
		nptr.setAuthentication(Authenticated);
	}
	
	public boolean returnGenBlock(String Username)
	{
		if(this.Username.equals(Username))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}