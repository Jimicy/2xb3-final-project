import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


public class Output {
	public static void encodeToFile(String towrite){
		PrintWriter writer;
		try {
			writer = new PrintWriter("encode.txt", "UTF-8");
			writer.println(towrite);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void decodeToFile(String towrite){
		PrintWriter writer;
		try {
			writer = new PrintWriter("decode.txt", "UTF-8");
			writer.println(towrite);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void tableToFile(HuffmanNode theArray[], int Size){
		PrintWriter writer;
		try {
			writer = new PrintWriter("table.txt", "UTF-8");
			writer.println("\n	SYMBOL		FREQUENCY	ENCODING");
			writer.println("	------		---------	--------");
			for(int i = 0; i < Size; i++)
			{
				if (theArray[i].getCharacter().equals("\n"))
					
					writer.println("	 BREAK		   " + theArray[i].getFrequency() + "		" + HuffmanTree.EncodeCharacter("\n", theArray, Size));
				else if (theArray[i].getCharacter().equals(" "))
					writer.println("	 SPACE		   " + theArray[i].getFrequency() + "		" + HuffmanTree.EncodeCharacter(" ", theArray, Size));
				else
					writer.println("	   " + theArray[i].getCharacter() + "		   " + theArray[i].getFrequency() + "		" + HuffmanTree.EncodeCharacter(theArray[i].getCharacter(), theArray, Size));
			}
			//writer.println(towrite);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
