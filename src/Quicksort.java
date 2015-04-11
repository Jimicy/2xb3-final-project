
public class Quicksort {
	public static void Sort(HuffmanNode theArray[], int low, int high){
		int i = low;
		int j = high;
		int middle = low+(high-low)/2;
		//Get pivot which is the middle element of the list
		HuffmanNode pivot = theArray[middle];

		
		//divide 2 list
		while (i <= j){
			
			while(theArray[i].getFrequency() < pivot.getFrequency()){
				i++;
			}
			
			while(theArray[j].getFrequency() > pivot.getFrequency()){
				j--;
			}
		
			if ( i <= j ){
				exchange(theArray,i,j);
				i++;
				j--;
			}
		}
	
		if(low < j){
			Sort(theArray,low,j);
		}
		if(i < high){
			Sort(theArray,i,high);
		}
	}
	
	private static void exchange(HuffmanNode theArray[],int i, int j){
		HuffmanNode temp = theArray[i];
		theArray[i] = theArray[j];
		theArray[j] = temp;
	}
	
	//-----------------------------------------------------------------------------
	// SortArray - Takes the HuffmanNode array and its size as inputs.
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
