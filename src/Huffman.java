import java.io.*;

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
		File theFile = new File("input/sample.txt");
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
		SortArray( CharacterArray, size );
		Head = BuildTree( CharacterArray, size );
		DisplayTable( CharacterArray, size );

		// Encode the original text to its binary equivalent
		String EncodedInput = EncodeText( theText, CharacterArray, size );
		System.out.println("\n\n" + EncodedInput + "\n\n	Length of encoded text: " + EncodedInput.length());
		//System.out.println("\n\n	Length of encoded text: " + EncodedInput.length());

		// Decode the encoded text
		String DecodedInput = DecodeText( EncodedInput, CharacterArray, Head );
		System.out.println("\n\n" + DecodedInput + "\n\n	Length of decoded text: " + DecodedInput.length());
		//System.out.println("\n\n	Length of decoded text: " + DecodedInput.length());

		// exit program
		System.exit(0);
	}

	//----------------------------------------------------------------------------
	// BuildTree - Takes the array of HuffmanNodes as input along with the size of
	//             the array.  Initializes all LeftChild, RightChild, and Parent
	//		   pointers for each node and then returns a pointer to the Head.
	//----------------------------------------------------------------------------
	public static HuffmanNode BuildTree( HuffmanNode theArray[], int Size )
	{
		// Create a copy of the original array to build the tree
		HuffmanNode tempCharacterArray[] = new HuffmanNode[255];
		for(int i = 0; i < Size; i++)
		{
			tempCharacterArray[i] = theArray[i];
		}

		// While the temp array has more than one node, pop off the smallest two items
		// and create a new node with the frequency of freq(N1) + freq(N2).  Make this
		// new node the parent node for each of the two items and put it back on the array.
		int tempSize = Size;
		HuffmanNode tempChar;
		while (tempSize != 1)
		{
			int Frequency = tempCharacterArray[tempSize-1].getFrequency() + tempCharacterArray[tempSize-2].getFrequency();
			tempChar = new HuffmanNode( "NON-LEAF", Frequency );
			tempChar.LeftChild = tempCharacterArray[tempSize-1];
			tempChar.RightChild = tempCharacterArray[tempSize-2];
			tempChar.LeftChild.Parent = tempChar;
			tempChar.RightChild.Parent = tempChar;
			tempCharacterArray[tempSize-2] = tempChar;
			tempSize--;
			SortArray( tempCharacterArray, tempSize );
		}

		// Return the last node left in the array as the Head pointer
		return (tempCharacterArray[0]);
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
				System.out.println("	 BREAK		   " + theArray[i].getFrequency() + "		" + EncodeCharacter("\n", theArray, Size));
			else if (theArray[i].getCharacter().equals(" "))
				System.out.println("	 SPACE		   " + theArray[i].getFrequency() + "		" + EncodeCharacter(" ", theArray, Size));
			else
				System.out.println("	   " + theArray[i].getCharacter() + "		   " + theArray[i].getFrequency() + "		" + EncodeCharacter(theArray[i].getCharacter(), theArray, Size));
		}
	}

	//----------------------------------------------------------------------------
	// EncodeText - Takes a string, the HuffmanNode array, and the array size as
	//              inputs.  Outputs the encoded value of every character in the
	//		    string in its huffman code binary equivalent.
	//----------------------------------------------------------------------------
	public static String EncodeText( String Text, HuffmanNode theArray[], int Size )
	{
		String HuffmanCode = new String("");
		int Length = Text.length();
		for(int i = 0; i < Length; i++)
		{
			String temp = Text.substring(i, i + 1);
			HuffmanCode += EncodeCharacter(temp, theArray, Size);
		}
		return HuffmanCode;
	}

	//----------------------------------------------------------------------------
	// EncodeCharacter - Takes a character, the HuffmanNode array, and the array
	//                   size as inputs.  Finds the node associated with the given
	//		         character and returns the BackwardsTraverse function with
	//			   the given node as input.
	//----------------------------------------------------------------------------
	public static String EncodeCharacter( String Input, HuffmanNode theArray[], int Size )
	{
		HuffmanNode theChar = theArray[0];
		for(int i = 0; i < Size; i++)
		{
			if (theArray[i].getCharacter().equals(Input))
			{
				theChar = theArray[i];
				break;
			}
 		}
		return BackwardsTraverse(theChar);
	}

	//----------------------------------------------------------------------------
	// BackwardsTraverse - Takes a leaf of the HuffmanNode tree as input and traverses
	//                     in reverse back to the parent.  The binary encoding is
	//		           build in reverse.
	//----------------------------------------------------------------------------
	public static String BackwardsTraverse( HuffmanNode Node )
	{
		String Code = new String("");
		while(Node.Parent != null)
		{
			if (Node.Parent.LeftChild == Node)  // This is a left child of its parent
			{
				Code = "0" + Code;
			}
			else  					// This is a right child of its parent
			{
				Code = "1" + Code;
			}
			Node = Node.Parent;
		}
		return Code;
	}

	//----------------------------------------------------------------------------
	// DecodeText - Takes a binary encoded string, the HuffmanNode array, and the
	//              Head pointer as inputs.  Traverses the tree starting from the
	//		    head all the way to a leaf and outputs the leaf's character.
	//		    This process is done until the entire binary string is decoded.
	//----------------------------------------------------------------------------
	public static String DecodeText( String Input, HuffmanNode theArray[], HuffmanNode Head )
	{
		String NormalText = new String("");
		int Length = Input.length();
		HuffmanNode Node = Head;
		for(int i = 0; i < Length; i++)
		{
			// Go left for "0" value, go right for "1" value
			String temp = Input.substring(i, i + 1);
			if (temp.equals("0"))
				Node = Node.LeftChild;
			else
				Node = Node.RightChild;

			if (Node == null)
			{
				NormalText = "\n\nERROR: Incorrect Encoded Input\n\n";
				break;
			}
			else
			{
				if ((Node.LeftChild == null) && (Node.RightChild == null))
				{
					NormalText += Node.getCharacter();
					Node = Head;
				}
			}
		}
		return NormalText;
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
