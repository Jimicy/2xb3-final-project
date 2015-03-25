import java.util.ArrayList;
import java.util.Collections;

public class Nodes {
	
	public ArrayList<Integer> freq(String string){
		ArrayList<Integer> occurances = new ArrayList<Integer>();
		int count=0;
		for (int i = 0; i < string.length(); i++) {
			char ch = string.charAt(i);
			// if character is already
			// present then skip
			if (string.indexOf(ch) < i)
				continue;
			for (int j = i; j < string.length(); j++) {
				if (string.charAt(j) == ch)
					count++;
			}
			occurances.add(count);
			// reset count to 0
			count = 0;
		}
		Collections.sort(occurances);
		return(occurances);
	}
	
	public void topNode(ArrayList <Integer>occurances){
		while(occurances.size()!=1){
			occurances.add(occurances.get(0)+occurances.get(1));
			Collections.sort(occurances);
			occurances.remove(0);
			occurances.remove(0);
			System.out.println(occurances);
		}
	}
}
