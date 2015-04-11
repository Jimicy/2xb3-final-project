
public class HuffmanTree {
	/**
	 * @param args
	 */
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
				Quicksort.SortArray( tempCharacterArray, tempSize );
			}

			// Return the last node left in the array as the Head pointer
			return (tempCharacterArray[0]);
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
;			return BackwardsTraverse(theChar);
		}

		//----------------------------------------------------------------------------
		// BackwardsTraverse - Takes a leaf of the HuffmanNode tree as input and traverses
		//                     in reverse back to the parent.  The binary encoding is
		//		           build in reverse.
		//----------------------------------------------------------------------------
		private static String BackwardsTraverse( HuffmanNode Node )
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

}
