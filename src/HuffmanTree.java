import java.util.ArrayList;

public class HuffmanTree {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Sample Chun code for testing/understanding
		String string = "Row, Row, Row Your Boat";
		Nodes nodes = new Nodes();
		ArrayList<Integer> freq = new ArrayList<Integer>();
		freq = nodes.freq(string);
		System.out.println(freq);
	}
}
