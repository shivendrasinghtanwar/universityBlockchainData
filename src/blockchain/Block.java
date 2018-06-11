package blockchain;


import java.security.*;
import java.util.Date;

public class Block
{
	private boolean Authenticated;
	private String UserID,PrevHashID,HashID,Data;
	private static int count=0;
	protected Block link;

	public Block(String PrevHashID,String Data,String UserID,Block blk)
	{
		this.PrevHashID = PrevHashID;
		this.Data = Data;
		this.UserID = UserID;
		this.link = blk;
		HashID = Block.calculateHash(this);
		Authenticated = false;
		count++;
	}

	public void setAuthentication(boolean Authenticated)
	{
		this.Authenticated = Authenticated;
	}

	public boolean getAuthentication()
	{
		return Authenticated;
	}

	public String getHash()
	{
		return HashID;
	}

	public String getData()
	{
		return Data;
	}

	public String getPrevHash()
	{
		return PrevHashID;
	}

	public static String calculateHash(Block block)
	{
		String all = StringUtil.applySHA(block.PrevHashID+block.Data);
		return all;
	}

	public static int noOfBlocks()
	{
		return count;
	}

	public void setLink(Block nlink)
	{
		this.link = nlink;
	}

	public Block getLink()
	{
		return link;
	}

	public void deleteBlock()
	{
		count--;
		this.Data = "";
		this.HashID = "";
		this.PrevHashID = "";
		this.UserID = "";
		link = null;
	}
}
