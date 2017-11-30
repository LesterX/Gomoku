/**
 * Computer Science 2210 Assignment 2
 * @author Yimin Xu 
 * Student Number: 250876566
 * Dictionary
 */

public class Dictionary implements DictionaryADT
{
	private int size;
	private LinkedNode[] table;

	
	/**
	 * Constructor 
	 */
	public Dictionary(int newSize)
	{
		size = newSize;
		table = new LinkedNode[size];

	}
	
	/**
	 * LinkedNode class will be used to create separate chaining structure
	 * 
	 */
	public class LinkedNode
	{
		LinkedNode next;
		ConfigData element;
		
		/**
		 * Constructor
		 */
		public LinkedNode()
		{
			next = null;
			element = null;
		}
		
		/**
		 * Constructor override
		 * @param newConfig
		 * @param newScore
		 */
		public LinkedNode(String newConfig, int newScore)
		{
			next = null;
			element = new ConfigData(newConfig, newScore);
		}
		
		public void setElement(String config, int score)
		{
			element = new ConfigData(config,score);
		}
		
		public ConfigData getElement()
		{
			return element;
		}
		
		/**
		 * Set the next node
		 * @param node
		 */
		public void setNext(LinkedNode node)
		{
			next = node;
		}
		
		/**
		 * Set the configuration and score of the node
		 * @param dataConfig
		 * @param dataScore
		 */
		public void setData(String dataConfig, int dataScore)
		{
			element = new ConfigData(dataConfig, dataScore);
		}
		
		public String getConfig()
		{
			return element.getConfig();
		}
		
		/**
		 * return the next node
		 * @return
		 */
		public LinkedNode getNext()
		{
			return next;
		}
	}
	
	/**
	 * This is the method used as hash code
	 * @param s
	 * @return
	 */
	private static int getHash(String s)
	{
		byte[] ss = s.getBytes();
		int result = 0;
		for (int i = 1; i < ss.length; i ++)
		{
			int t = (int) Math.pow(73, i);  //33 is chosen to be the constant
			result = result + ss[i] * t;
		}
		
		if (result < 0)
			result = -result;
		
		return result;
	}
	
	/**
	 * This method will throw an exception if there is no empty cell in the table.
	 * If there is any space available, the data will be put into the table
	 * and return 1 if collision happens, otherwise return 0
	 * @param pair
	 * @return
	 * @throws DictionaryException
	 */
	public int insert(ConfigData pair) throws DictionaryException
	{
		int newKey = getHash(pair.getConfig());
		int hash = newKey % size;
		LinkedNode newNode = new LinkedNode(pair.getConfig(), pair.getScore());
		
		if (table[hash] == null)
		{
			table[hash] = newNode;
			return 0;
		}else
		{
			LinkedNode node = table[hash];
			while(node.getNext() != null)
			{
				if (node.getConfig().equals(newNode.getConfig()))
					throw new DictionaryException("a");
				node = node.getNext();
			}
			if (node.getConfig().equals(newNode.getConfig()))
				throw new DictionaryException("b");
			
			node.setNext(newNode);
			
			return 1;
		}
	}
	
	/**
	 * This method remove the data with target configuration
	 * It will throw an exception if the target is not found
	 * @param targetConfig
	 * @throws DictionaryException
	 */
	public void remove(String targetConfig) throws DictionaryException
	{
		// If the cell is empty, throw the exception
		int hash = getHash(targetConfig) % size;
		if (table[hash] == null)
			throw new DictionaryException("Target not found.");
		
		// Remove the node from different position. 
		// When the target node is the head of the list.
		LinkedNode node = table[hash];
		if (node.getConfig().equals(targetConfig))
		{
			if (node.getNext() == null)
			{
				table[hash] = null;
				return;
			}else
			{
				table[hash] = node.getNext();
				return;
			}
		}else
		{
			// When the node is not the target but it's the only node in the cell. Throw the exception.
			if (node.getNext() == null)
				throw new DictionaryException("Target not found.");
			
			LinkedNode next = node.getNext();
			
			// When the target node is not the head of the list
			while(next != null)
			{
				if (next.getConfig().equals(targetConfig))
				{
					if (next.getNext() == null)
					{
						node.setNext(null);
					}else
					{
						node.setNext(next.getNext());
					}
					return;
				}
				
				node = next;
				next = next.getNext();
			}
			
			// When all the nodes in this cell have been searched but target is not found, throw the exception
			if (next == null)
				throw new DictionaryException("Target not found.");
		}
	}
	
	/**
	 * This method returns the score with target configuration or -1 if target is not found
	 * @param targetConfig
	 * @return
	 */
	public int find(String targetConfig)
	{
		int hash = getHash(targetConfig) % size;
		if (table[hash] == null)
		{
			return -1;
		}else
		{
			LinkedNode node = table[hash];
			while (node.getNext() != null)
			{
				if (node.getConfig().equals(targetConfig))
				{
					return node.getElement().getScore(); 
				}
				
				node = node.getNext();
			}
			
			if (node.getConfig().equals(targetConfig))
			{
				return node.getElement().getScore();
			}else
			{
				return -1;
			}
		}
	}
	

}


