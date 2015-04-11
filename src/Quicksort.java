
public class Quicksort {
	
	//-----------------------------------------------------------------------------
	// SortArray - Takes the HuffmanNode array and its size as inputs.  Uses Bubble
	//             Sort to arrange the characters in decreasing order.
	//-----------------------------------------------------------------------------
	public static void SortArray( HuffmanNode theArray[], int Size )
	{
		int compares = 1;
		while (compares != 0)
		{
			compares = 0;
			for(int j = 0; j < Size - 1; j++)
			{
				if (theArray[j].getFrequency() < theArray[j+1].getFrequency())
				{
					HuffmanNode temp = theArray[j];
					theArray[j] = theArray[j+1];
					theArray[j+1] = temp;
					compares = 1;
				}
			}
		}
	}
}
