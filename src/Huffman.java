import java.io.*;
import java.util.Arrays;

public class Huffman
{
	//------------------------------------------------------------------------
	// MAIN
	//------------------------------------------------------------------------
	public static void main( String args[] )
	{
		// Initialize variables for the Huffman Node array, the Head pointer, and the size of the array
		HuffmanNode CharacterArray[] = new HuffmanNode[255];  // only 256 ascii characters possible
		HuffmanNode Head;
		int size = 0;

		// Read in file sample.txt and pass to the getText() function to convert to a string
		File theFile = new File("C:\\Users\\Richard\\Documents\\Java Stuff\\Huffman Compression\\src\\sample.txt");
		String theText = GetText(theFile);
		int Length = theText.length();

		// Read through each character of the text string, if it is not already in the array add it.
		// If it is already in the array add one value to the frequency of that Huffman Node.
		for(int i = 0; i < Length; i++)
		{
			String temp = theText.substring(i, i + 1);  // read each substring of the string
			if (!temp.equals("\r"))  			  // ignore carriage returns
			{
				// CheckArray function returns -1 for a new character or the position in the array
				// for which this Huffman Node already exists
				int check = CheckArray(CharacterArray, temp, size);
				if (check == -1)
				{
					// If new character, add to end of array and increase the size variable
					CharacterArray[size] = new HuffmanNode(temp, 1);
					size++;
				}
				else
				{
					CharacterArray[check].addFrequency();
				}
			}
		}

		// After the array is built, sort the array, build a tree with it, and then display the table of characters.
		Quicksort.SortArray( CharacterArray, size );
		
		Head = HuffmanTree.BuildTree( CharacterArray, size );
		DisplayTable( CharacterArray, size );
		Output.tableToFile(CharacterArray, size);

		// Encode the original text to its binary equivalent
		String EncodedInput = HuffmanTree.EncodeText( theText, CharacterArray, size );
		System.out.println("\n\n" + "Encoded input:" + EncodedInput + "\n\n	Length of encoded text: " + EncodedInput.length());
		//System.out.println("\n\n	Length of encoded text: " + EncodedInput.length());

		// Decode the encoded text
		String DecodedInput = HuffmanTree.DecodeText( EncodedInput, CharacterArray, Head );
		Output.decodeToFile(DecodedInput);
		System.out.println("\n\n" + DecodedInput + "\n\n	Length of decoded text: " + DecodedInput.length());
		//System.out.println("\n\n	Length of decoded text: " + DecodedInput.length());

		// exit program
		System.exit(0);
	}

	

	//----------------------------------------------------------------------------
	// CheckArray - Takes the HuffmanNode array, a character, and the size of the
	//              array as inputs.  Outputs a -1 if the character does not already
	//		    exist in the array.  Outputs the position of the array where
	//		    the character exists if it is already placed.
	//----------------------------------------------------------------------------
	public static int CheckArray( HuffmanNode theArray[], String Input, int Size )
	{
		int Value = -1;
		for(int i = 0; i < Size; i++)
		{
			if (theArray[i].getCharacter().equals(Input))
			{
				Value = i;
				break;
			}
		}
		return Value;
	}

	
	
	//----------------------------------------------------------------------------
	// DisplayTable - Take the HuffmanNode array and its size as inputs.  Outputs
	//                a table displaying each character, its frequency, and the
	//		      huffman encoded value in binary.
	//----------------------------------------------------------------------------
	public static void DisplayTable( HuffmanNode theArray[], int Size )
	{
		System.out.println("\n	SYMBOL		FREQUENCY	ENCODING");
		System.out.println("	------		---------	--------");

		for(int i = 0; i < Size; i++)
		{
			if (theArray[i].getCharacter().equals("\n"))
				
				System.out.println("	 BREAK		   " + theArray[i].getFrequency() + "		" + HuffmanTree.EncodeCharacter("\n", theArray, Size));
			else if (theArray[i].getCharacter().equals(" "))
				System.out.println("	 SPACE		   " + theArray[i].getFrequency() + "		" + HuffmanTree.EncodeCharacter(" ", theArray, Size));
			else
				System.out.println("	   " + theArray[i].getCharacter() + "		   " + theArray[i].getFrequency() + "		" + HuffmanTree.EncodeCharacter(theArray[i].getCharacter(), theArray, Size));
		}
	}


	//----------------------------------------------------------------------------
	// GetText - Takes a file as input.  The file is parsed line by line and each
	//           line's text is appended to the end of a string.  When the last
	//		 line of the file is read, the entire string is returned.
	//----------------------------------------------------------------------------
	public static String GetText(File aFile)
	{
		StringBuffer contents = new StringBuffer();
		BufferedReader input = null;

		try
		{
			input = new BufferedReader( new FileReader(aFile) );
			String line = null;

			// Read one line at a time and append to buffer reader
			while (( line = input.readLine()) != null)
			{
				contents.append(line);
				contents.append(System.getProperty("line.separator"));
			}
		}
		// print stack trace if the file is not found
		catch (FileNotFoundException ex)
		{
			ex.printStackTrace();
		}
		catch (IOException ex){
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				if (input!= null)
				{
					// Close the open connection to the file reader
					input.close();
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}

		return contents.toString();
	}

}
