/**
 * Computer Science 2210 Assignment 2
 * @author Yimin Xu 
 * Student Number: 250876566
 * Achi
 */

public class Achi 
{
	private int size;
	private char[][] gameBoard;
	
	/**
	 * Constructor
	 * @param board_size
	 * @param max_levels
	 */
	public Achi(int board_size, int max_levels)
	{
		size = board_size;
		gameBoard = new char[size][size];
		for (int i = 0; i < size; i ++)
		{
			for (int j = 0; j < size; j ++)
			{
				gameBoard[i][j] = " ".charAt(0);
			}
		}
	}
	
	/**
	 * A private method which return the string value of the game board configuration
	 * @return
	 */
	private String gameConfig()
	{
		String result = "";
		for (int i = 0; i < size; i ++)
			for (int j = 0; j < size; j ++)
				result += gameBoard[i][j];
		return result;
	}
	
	/**
	 * Create a dictionary
	 * @return
	 */
	public Dictionary createDictionary()
	{
		Dictionary dict = new Dictionary(size);
		return dict;
	}
	
	/**
	 * 
	 * @param configurations
	 * @return
	 */
	public int repeatedConfig(Dictionary configurations)
	{
		return configurations.find(gameConfig());
	}

	/**
	 * Insert the configuration with the socore into the dictionary
	 * @param configurations
	 * @param score
	 */
	public void insertConfig(Dictionary configurations, int score) 
	{
		String config = gameConfig();
		ConfigData data = new ConfigData(config,score);
		try
		{
			configurations.insert(data);
		}
		catch(DictionaryException e)
		{
			System.out.println("Exception: " + e);
		}
	}
	
	/**
	 * Change the configuration of the game board
	 * @param row
	 * @param col
	 * @param symbol
	 */
	public void storePlay(int row, int col, char symbol)
	{
		gameBoard[row][col] = symbol;
	}
	
	/**
	 * Check if the tile is empty
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean tileIsEmpty (int row, int col)
	{
		if (gameBoard[row][col] == " ".charAt(0))
			return true;
		else
			return false;
	}
	
	/**
	 * Check if the tile is computer
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean tileIsComputer (int row, int col)
	{
		if (gameBoard[row][col] == "O".charAt(0))
			return true;
		else
			return false;
	}
	
	/**
	 * Check if the tile is human
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean tileIsHuman (int row, int col)
	{
		if (gameBoard[row][col] == "X".charAt(0))
			return true;
		else
			return false;
	}
	
	/**
	 * Check if someone has won
	 * @param symbol
	 * @return
	 */
	public boolean wins (char symbol)
	{
		boolean result = false;
		
		//Check the rows
		int i = 0;
		int j = 0;
		while (result == false)
		{
			if (i == size)
				break;
			if (gameBoard[i][j] == symbol)
			{
				if (j == size - 1)
					return result = true;
				else
					j ++;
			}else
			{
				j = 0; 
				i ++;
			}
		}
		
		//Check the columns 
		i = 0;
		j = 0;
		while (result == false)
		{
			if (j == size)
				break;
			if (gameBoard[i][j] == symbol)
			{
				if (i == size - 1)
					return result = true;
				else
					i ++;
			}else
			{
				i = 0;
				j ++;
			}
		}
		
		//Check the two diagonals
		i = 0;
		j = 0;
		while (result == false)
		{
			if (i == size && j == size)
				break;
			if (gameBoard[i][j] == symbol)
			{
				if (i == size - 1 && j == size - 1)
					return result = true;
				else
					i ++; 
					j ++;
			}else
			{
				break;
			}
		}
		
		i = size - 1;
		j = 0;
		while (result == false)
		{
			if (i == -1 && j == size)
				break;
			if (gameBoard[i][j] == symbol)
			{
				if (i == 0 && j == size - 1)
					return result = true;
				else
					i --;
					j ++;
			}else
			{
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * Private method copied from PlayAchi class to identify the tiles next to the target tile
	 * @param row1
	 * @param col1
	 * @param row2
	 * @param col2
	 * @return
	 */
	private boolean adjacent (int row1, int col1, int row2, int col2) 
	{
	       /* ----------------------------------------------------------------- */
		    if (Math.abs(row1 - row2) <= 1 && Math.abs(col1 - col2) <= 1)
			return true;
		    else return false;
	}
	
	/**
	 * Check if the player cannot make any move 
	 * @param symbol
	 * @return
	 */
	public boolean isDraw(char symbol)
	{
		//Count the number of blank tiles
		int blankNum = 0;
		int bRow = 0;
		int bCol = 0;
		for (int i = 0; i < size; i ++)
		{
			for (int j = 0; j < size; j ++)
			{
				if (tileIsEmpty(i,j))
					blankNum ++;
					bRow = i;
					bCol = j;
			}
		}
		
		//If there is only one tile empty, check every tile besides it
		if (blankNum != 1)
		{			
			return false;
		}
		else 
		{
			char[] surrounded = new char[9];
			int sSize = 0;
			for (int i = 0; i < size; i ++)
			{
				for (int j = 0; j < size; j ++)
				{
					if (adjacent(bRow,bCol,i,j))
					{
						surrounded[sSize] = gameBoard[i][j];
						sSize ++;
					}
				}
			}
			
			//If there is any tile with target symbol, the game can still continue
			for (char elem : surrounded)
			{
				if (elem == symbol)
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns the score of every configuration
	 * @param symbol
	 * @return
	 */
	public int evalBoard(char symbol)
	{
		if (wins("X".charAt(0)))
			return 0;
		else if (wins("O".charAt(0)))
			return 3;
		else if (isDraw(symbol))
			return 2;
		else
			return 1;
	}
}
