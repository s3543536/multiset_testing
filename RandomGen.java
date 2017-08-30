import java.io.*;
import java.util.*;


/**
 * Framework to test the multiset implementations.
 * 
 * @author jkcchan
 * Modified by:
 * @author Angel English s3543536
 */
public class RandomGen
{
	/** Name of class, used in error messages. */
	protected static final String progName = "RandomGen";
	
	/** Standard outstream. */
	protected static final PrintStream outStream = System.out;

	/**
	 * Print help/usage message.
	 */
	public static void usage(String progName) {
		System.err.println(progName + ": <implementation> [fileName to output search results to]");
		System.err.println("<implementation> = <linkedlist | sortedlinkedlist | bst| hash | baltree>");
		System.exit(1);
	} // end of usage

	public static int index = -1;
	public static final String abc = "abcdefghijklmnopqrstuvwxyz";
	public static final int max = abc.length() * abc.length() * abc.length();

	public static String getPrev() {
		if(index == -1) { index = max; }
		if(!(index >= 0)) {
			index = -1;
			return "";
		}
		char fst = abc.charAt((index / (abc.length() * abc.length())) % abc.length());
		char snd = abc.charAt((index / abc.length()) % abc.length());
		char trd = abc.charAt(index % abc.length());
		--index;
		return "" + fst + snd + trd;
	}


	public static String getNext() {
		if(index == -1) { index = 0; }
		if(index >= max) {
			index = -1;
			return "";
		}
		char fst = abc.charAt((index / (abc.length() * abc.length())) % abc.length());
		char snd = abc.charAt((index / abc.length()) % abc.length());
		char trd = abc.charAt(index % abc.length());
		index++;
		return "" + fst + snd + trd;
	}

	public static final int ForSEQA = 0;//forward sequential add
	public static final int RevSEQA = 1;//reverse sequential add
	public static final int ForSEQR = 2;//forward sequential remove
	public static final int RevSEQR = 3;//reverse sequential remove
	public static final int RanARS = 4;//random add, remove, search
	public static int mode = RanARS;

	public static int count = 0;

	//random distribution for add, search, and remove... 50 50 50 means each one
	//will have a 1/3 chance, 50 50 0 means the first two will have a 1/2 chance
	//and the third will have 0 chance
	public static final int addPercent = 50;
	public static final int srcPercent = 50;
	public static final int remPercent = 50;

	public static String getCommand() {
		if(count > max) {
			return "q";
		}

		String returnval;
		switch(mode) {
			case ForSEQA:
				returnval = getNext();
				if(returnval.equals("")) { return "q"; }
				return "a " + returnval;
			case ForSEQR:
				returnval = getNext();
				if(returnval.equals("")) { return "q"; }
				return "ro " + returnval;
			case RevSEQA:
				returnval = getPrev();
				if(returnval.equals("")) { return "q"; }
				return "a " + returnval;
			case RevSEQR:
				returnval = getPrev();
				if(returnval.equals("")) { return "q"; }
				return "ro " + returnval;
			default:
			case RanARS:
				if(count > max) { return "q"; }
				Random rand = new Random();
				int fst = (int)(rand.nextDouble() * 26);
				int snd = (int)(rand.nextDouble() * 26);
				int trd = (int)(rand.nextDouble() * 26);
				returnval = " " + abc.charAt(fst) + abc.charAt(snd) + abc.charAt(trd);

				int total = addPercent + remPercent + srcPercent;
				int opt = (int)(rand.nextDouble() * total);
				if(opt < addPercent) {
					//add
					returnval = "a" + returnval;
				} else if(opt < addPercent + remPercent) {
					//remove
					returnval = "ro" + returnval;
				} else {
					//search
					returnval = "s" + returnval;
				}
				count++;
				return returnval;
		}
	}

	/**
	 * Process the operation commands coming from inReader, and updates the multiset according to the operations.
	 * 
	 * @param inReader Input reader where the operation commands are coming from.
	 * @param searchOutWriter Where to output the results of search.
	 * @param multiset The multiset which the operations are executed on.
	 * 
	 * @throws IOException If there is an exception to do with I/O.
	 */
	public static void processOperations(/*BufferedReader inReader, */PrintWriter searchOutWriter, Multiset<String> multiset) 
		throws IOException
	{
		String line;
		int lineNum = 1;
		boolean bQuit = false;
		
		// continue reading in commands until we either receive the quit signal or there are no more input commands
		while (!bQuit) {
			line = getCommand();
			if(line.equals("q")) {
				//no more things
				//line = "P";
				bQuit = true;
				continue;
			}
			String[] tokens = line.split(" ");

			// check if there is at least an operation command
			if (tokens.length < 1) {
				System.err.println(lineNum + ": not enough tokens.");
				lineNum++;
				continue;
			}

			String command = tokens[0];
			// determine which operation to execute
			switch (command.toUpperCase()) {
				// add
				case "A":
					if (tokens.length == 2) {
						multiset.add(tokens[1]);
					}
					else {
						System.err.println(lineNum + ": not enough tokens.");
					}
					break;
				// search
				case "S":
					if (tokens.length == 2) {
						int foundNumber = multiset.search(tokens[1]);
						searchOutWriter.println(tokens[1] + " " + foundNumber);
					}
					else {
						// we print -1 to indicate error for automated testing
						searchOutWriter.println(-1);
						System.err.println(lineNum + ": not enough tokens.");
					}
					break;
				// remove one instance
				case "RO":
					if (tokens.length == 2) {
						multiset.removeOne(tokens[1]);
					}
					else {
						System.err.println(lineNum + ": not enough tokens.");
					}
					break;
				// remove all instances
				case "RA":
					if (tokens.length == 2) {
						multiset.removeAll(tokens[1]);
					}
					else {
						System.err.println(lineNum + ": not enough tokens.");
					}
					break;		
				// print
				case "P":
					multiset.print(outStream);
					break;
				// quit
				case "Q":
					bQuit = true;
					break;
				default:
					System.err.println(lineNum + ": Unknown command. " + line);
			}

			lineNum++;
		}

	} // end of processOperations() 


	/**
	 * Main method.  Determines which implementation to test.
	 */
	public static void main(String[] args) {

		// check number of command line arguments
		if (args.length > 2 || args.length < 1) {
			System.err.println("Incorrect number of arguments.");
			usage(progName);
		}

		String implementationType = args[0];
		
		String searchOutFilename = null;
		if (args.length == 2) {
			searchOutFilename = args[1];
		}
		
		
		// determine which implementation to test
		Multiset<String> multiset = null;
		switch(implementationType) {
			case "linkedlist":
				multiset = new LinkedListMultiset<String>();
				break;
			case "sortedlinkedlist":
				multiset = new SortedLinkedListMultiset<String>();
				break;
			case "bst":
				multiset = new BstMultiset<String>();
				break;
			case "hash":
				multiset = new HashMultiset<String>();
				break;
			case "baltree":
				multiset = new BalTreeMultiset<String>();
				break;
			default:
				System.err.println("Unknown implmementation type.");
				usage(progName);
		}


		// construct in and output streams/writers/readers, then process each operation.
		try {
			//BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter searchOutWriter = new PrintWriter(System.out, true);
			
			if (searchOutFilename != null) {
				searchOutWriter = new PrintWriter(new FileWriter(searchOutFilename), true);
			}
			// process the operations
			processOperations(/*inReader, */searchOutWriter, multiset);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	} // end of main()

} // end of class RandomGen
