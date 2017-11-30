
public class SelfTest 
{	
	
	
	public static void main(String[] args)
	{
		Achi a = new Achi(3,3);
		Dictionary b = a.createDictionary();
		for (int i = 0; i < 3; i ++)
		{
			for (int j = 0; j < 3; j ++)
			{
				if ((i % 2 == 0 && j % 2 == 1) || (i % 2 == 1 && j % 2 == 0))
					a.storePlay(i, j, "O".charAt(0));
				else
					a.storePlay(i, j, "X".charAt(0));
			}
		}
		
		System.out.println(a.tileIsComputer(0, 1));
	}
}