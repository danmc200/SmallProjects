import java.io.*;
import java.util.*;

/*
    Exercise from: http://codingdojo.org/cgi-bin/index.pl?KataBankOCR
*/
public class KataBankParser
{
	public static final int 
		ACCOUNT_NUM_OF_LINES = 4,
		CHARACTERS_PER_LINE = 27,
		COLUMNS_PER_NUMBER = 3;

	protected BufferedReader 
		acctNumsReader;

	/* representation is:
	 * " _ "
	 * "| |"
	 * "|_|"
	 */
	private static final Map<Integer, List<Integer>> columnAndRows0 = new HashMap<Integer, List<Integer>>();
	static
	{
		columnAndRows0.put(0, Arrays.asList(new Integer[]{makePipe(1),makePipe(2)}));
		columnAndRows0.put(1, Arrays.asList(new Integer[]{0,2}));
		columnAndRows0.put(2, Arrays.asList(new Integer[]{makePipe(1),makePipe(2)}));
	}
	/* representation is:
	 * "   "
	 * "  |"
	 * "  |"
	 */
	private static final Map<Integer, List<Integer>> columnAndRows1 = new HashMap<Integer, List<Integer>>();
	static
	{
		columnAndRows1.put(2, Arrays.asList(new Integer[]{makePipe(1),makePipe(2)}));
	}
	/* representation is:
	 * " _ "
	 * " _|"
	 * "|_ "
	 */
	private static final Map<Integer, List<Integer>> columnAndRows2 = new HashMap<Integer, List<Integer>>();
	static
	{
		columnAndRows2.put(0, Arrays.asList(new Integer[]{makePipe(2)}));
		columnAndRows2.put(1, Arrays.asList(new Integer[]{0,1,2}));
		columnAndRows2.put(2, Arrays.asList(new Integer[]{makePipe(1)}));
	}
	/* representation is:
	 * " _ "
	 * " _|"
	 * " _|"
	 */
	private static final Map<Integer, List<Integer>> columnAndRows3 = new HashMap<Integer, List<Integer>>();
	static
	{
		columnAndRows3.put(1, Arrays.asList(new Integer[]{0,1,2}));
		columnAndRows3.put(2, Arrays.asList(new Integer[]{makePipe(1),makePipe(2)}));
	}
	/* representation is:
	 * "   "
	 * "|_|"
	 * "  |"
	 */
	private static final Map<Integer, List<Integer>> columnAndRows4 = new HashMap<Integer, List<Integer>>();
	static
	{
		columnAndRows4.put(0, Arrays.asList(new Integer[]{makePipe(1)}));
		columnAndRows4.put(1, Arrays.asList(new Integer[]{1}));
		columnAndRows4.put(2, Arrays.asList(new Integer[]{makePipe(1),makePipe(2)}));
	}
	/* representation is:
	 * " _ "
	 * "|_ "
	 * " _|"
	 */
	private static final Map<Integer, List<Integer>> columnAndRows5 = new HashMap<Integer, List<Integer>>();
	static
	{
		columnAndRows5.put(0, Arrays.asList(new Integer[]{makePipe(1)}));
		columnAndRows5.put(1, Arrays.asList(new Integer[]{0,1,2}));
		columnAndRows5.put(2, Arrays.asList(new Integer[]{makePipe(2)}));
	}
	/* representation is:
	 * " _ "
	 * "|_ "
	 * "|_|"
	 */
	private static final Map<Integer, List<Integer>> columnAndRows6 = new HashMap<Integer, List<Integer>>();
	static
	{
		columnAndRows6.put(0, Arrays.asList(new Integer[]{makePipe(1),makePipe(2)}));
		columnAndRows6.put(1, Arrays.asList(new Integer[]{0,1,2}));
		columnAndRows6.put(2, Arrays.asList(new Integer[]{makePipe(2)}));
	}
	/* representation is:
	 * " _ "
	 * "  |"
	 * "  |"
	 */
	private static final Map<Integer, List<Integer>> columnAndRows7 = new HashMap<Integer, List<Integer>>();
	static
	{
		columnAndRows7.put(1, Arrays.asList(new Integer[]{0}));
		columnAndRows7.put(2, Arrays.asList(new Integer[]{makePipe(1),makePipe(2)}));
	}
	/* representation is:
	 * " _ "
	 * "|_|"
	 * "|_|"
	 */
	private static final Map<Integer, List<Integer>> columnAndRows8 = new HashMap<Integer, List<Integer>>();
	static
	{
		columnAndRows8.put(0, Arrays.asList(new Integer[]{makePipe(1),makePipe(2)}));
		columnAndRows8.put(1, Arrays.asList(new Integer[]{0,1,2}));
		columnAndRows8.put(2, Arrays.asList(new Integer[]{makePipe(1),makePipe(2)}));
	}
	/* representation is:
	 * " _ "
	 * "|_|"
	 * " _|"
	 */
	private static final Map<Integer, List<Integer>> columnAndRows9 = new HashMap<Integer, List<Integer>>();
	static
	{
		columnAndRows9.put(0, Arrays.asList(new Integer[]{makePipe(1)}));
		columnAndRows9.put(1, Arrays.asList(new Integer[]{0,1,2}));
		columnAndRows9.put(2, Arrays.asList(new Integer[]{makePipe(1),makePipe(2)}));
	}
	
	private static final Map<Integer, Map<Integer, List<Integer>>> NumberAndColumnRowsRepresentation = 
			new TreeMap<Integer, Map<Integer, List<Integer>>>();
	static
	{
		NumberAndColumnRowsRepresentation.put(0, columnAndRows0);
		NumberAndColumnRowsRepresentation.put(1, columnAndRows1);
		NumberAndColumnRowsRepresentation.put(2, columnAndRows2);
		NumberAndColumnRowsRepresentation.put(3, columnAndRows3);
		NumberAndColumnRowsRepresentation.put(4, columnAndRows4);
		NumberAndColumnRowsRepresentation.put(5, columnAndRows5);
		NumberAndColumnRowsRepresentation.put(6, columnAndRows6);
		NumberAndColumnRowsRepresentation.put(7, columnAndRows7);
		NumberAndColumnRowsRepresentation.put(8, columnAndRows8);
		NumberAndColumnRowsRepresentation.put(9, columnAndRows9);
	}

	private static int makePipe(int num)
	{
		return num + 10;
	}

	public KataBankParser(String fileLocation)
	{
		try {
			acctNumsReader = new BufferedReader(new FileReader(fileLocation));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static String accountImageToNumber(String [] accountImage)
	{
		Map<Integer, List<Integer>> columnAndRowsRead = new HashMap<Integer, List<Integer>>();
		StringBuilder accountNumber = new StringBuilder();

		for(int i = 0; i < CHARACTERS_PER_LINE; i++)
		{
			List<Integer> rows = new ArrayList<Integer>();
			for(int j = 0; j < (accountImage.length-1); j++)
			{
				//validate length of image row
				if(i >= accountImage[j].length())
					continue;

				switch(accountImage[j].charAt(i))
				{
				case '|':
					rows.add(makePipe(j));
					break;
				case '_':
					rows.add(j);
					break;
				}
			}
			if(!rows.isEmpty())
				columnAndRowsRead.put(new Integer(i % (COLUMNS_PER_NUMBER)), rows);
			if((i+1) % COLUMNS_PER_NUMBER == 0)
			{
				int num = findMatchingNumber(columnAndRowsRead);
				accountNumber.append((num == -1) ? "? ILL" : num);
				columnAndRowsRead.clear();
			}
		}
		return accountNumber.toString();
	}
	
	private static int findMatchingNumber(Map<Integer, List<Integer>> columnAndRowsRead)
	{
		outer:
		for(Integer ikey : NumberAndColumnRowsRepresentation.keySet())
		{
			Map<Integer, List<Integer>> columnAndRowsDefined = NumberAndColumnRowsRepresentation.get(ikey);
			Integer [] 
				columnDefArray = columnAndRowsDefined.keySet().toArray(new Integer[0]),
				columnReadArray = columnAndRowsRead.keySet().toArray(new Integer[0]);

			if(Arrays.asList(columnDefArray).toString().equals(Arrays.asList(columnReadArray).toString()))
			{
				for(Integer key : columnAndRowsRead.keySet())
				{
					if(!columnAndRowsDefined.get(key).toString().equals(columnAndRowsRead.get(key).toString()))
					{
						continue outer;
					}
				}
				return ikey;
			}
		}
		return -1;
	}
	

	public static String aggregateString(String [] strs)
	{
		StringBuilder sb = new StringBuilder();
		for(String s : strs)
			if(s != null)
				sb.append(s);
		return sb.toString();
	}
	
	public String [] readNextAccountNumberImage()
	{
		String [] accountNumImage = new String[ACCOUNT_NUM_OF_LINES];
		try {
			for(int i = 0; i < ACCOUNT_NUM_OF_LINES; accountNumImage[i++] = acctNumsReader.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return accountNumImage;
	}
	
	public static void main(String [] args)
	{
		if(args.length != 1)
		{
			System.out.println("useage: command (image file)");
            System.exit(0);
		}
		String filename = args[0];
		KataBankParser parser = new KataBankParser(filename);
		
		for(
				String [] accntImg = parser.readNextAccountNumberImage(); 
				(accntImg != null && !aggregateString(accntImg).isEmpty()); 
				accntImg = parser.readNextAccountNumberImage())
		{
			String accountNumber = KataBankParser.accountImageToNumber(accntImg);
			
			for(String str : accntImg)
				System.out.println(str);
			System.out.println(accountNumber);
		}
	}
}
