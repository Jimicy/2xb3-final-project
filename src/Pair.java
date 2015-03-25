public class Pair
{
	private char value;
	private int freq;

	public Pair(char v, int f)
	{
		value = v;
		freq = f;
	}
	public void setValue(char v)
	{
		value = v;
	}
	public void setFreq(int f)
	{
		freq = f;
	}
	public char getValue()
	{
		return value;
	}
	public int getFreq()
	{
		return freq;
	}
	public String toString()
	{
		return value + "\t" + freq + "\n";
	}
}