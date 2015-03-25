class HuffmanNode
{
	private String theCharacter;
	private int Frequency;
	public HuffmanNode LeftChild;
	public HuffmanNode RightChild;
	public HuffmanNode Parent;

	public HuffmanNode( String theChar, int theFreq )
	{
		theCharacter = theChar;
		Frequency = theFreq;
	}

	public void addFrequency()
	{
		Frequency++;
	}

	public int getFrequency()
	{
		return Frequency;
	}

	public String getCharacter()
	{
		return theCharacter;
	}
}

