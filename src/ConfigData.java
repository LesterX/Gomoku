/**
 * Computer Science 2210 Assignment 2
 * @author Yimin Xu 
 * Student Number: 250876566
 * ConfigData
 */
public class ConfigData 
{
	private String Dconfig;
	private int Dscore;
	
	
	public ConfigData(String config, int score)
	{
		Dconfig = config;
		Dscore = score;
	}
	
	public String getConfig()
	{
		return Dconfig;
	}
	
	public int getScore()
	{
		return Dscore;
	}

}
