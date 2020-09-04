import java.util.Scanner;
import java.io.File;
import java.io.IOException;

/**
 * This class coordinates all the necessary procedures to read a file and load its content
 * (which is the postfix representation of a math expression) into a queue, which will be 
 * read element by element and executed, with the assistance of a hash table structure to 
 * store the variables and its values, and a stack to push and pop terms and operators of the 
 * expression as necessary.   
 * @author Sergio Delgado.
 *
 */
public class Computer {
	
	public Computer()
	{
	}
	
	
	/**
	 * Given a file name, it opens that file in a scanner and creates a queue of nodes.
 	 * The head of the queue of nodes is the start of the queue. The values in the nodes are the
 	 * strings read in each time next() is called on the scanner
	 * @param filename String that holds the name of the file to read from.
	 * @return Node of the String type that points to the head of the queue of elements read from file.
	 * @throws IOException Handles the exceptions related to a file not found.
	 */
	public static Node<String> fileToNodeQueue(String filename) throws IOException 
	{
		String value;
		Scanner inFile;
		// Pointers to the head and tail of our Queue
		Node<String> head = null;
		Node<String> tail = null;
		
		try
		{
			inFile = new Scanner(new File(filename));
			Node<String> word;
			
			// while there is a token to read from file
			while (inFile.hasNext())
			{
				value = inFile.next();	

				// Enqueue word (new node created with a value to link to our Queue)
				word = new Node<String>(value);
				
				if (head == null)
					head = word;

				if (tail != null)
					tail.setNext(word);

				word.setPrev(tail);
				word.setNext(null);
				tail = word;
				// End of enqueueing.
			}
			
			inFile.close();
		}
		catch(IOException e)
		{
			System.out.println(e.toString());
			e.printStackTrace();			

		}
		
		return head;
	}
	
	
	/**
	 * Takes an operand (of the Object class) and if such operand is an instance of Integer it will
	 * cast it to an integer.  If it is not an integer, it will assume that the operand is variable name,
	 * in which case it will obtain the value assigned to that variable and cast it to an integer.
	 * @param operand Object type value of the operand that we are trying to extract an integer from.
	 * @return
	 */
	private Integer getOperand(Object operand)
	{
		Integer number;
		
		if (operand instanceof Integer)
			number = (Integer)operand;
		else
			number = (Integer)symbols.get((String)operand);  
		
		return number;
	}
	
	
	/**
	 * Given 2 operands and one operator, this method executes a mathematical operation according 
	 * to the three parameters received.
	 * @param operand1 int value of the first operand.
	 * @param operand2 int value of the second operand.
	 * @param operator String value, which is the symbol that dictates what math operation to execute.
	 * @return int value with the result of the operation on the two operands.
	 */
	private int operate(int operand1, int operand2, String operator)
	{
		int result = 0;
		
		switch(operator)
		{
			case "+":
				result = operand1 + operand2;
			break;	
			case "-":
				result = operand1 - operand2;
			break;	
			case "*":
				result = operand1 * operand2;
			break;	
			case "/":
				result = operand1 / operand2;
			break;

		}
		
		return result;
	}
	
	
	/**
	 * Given a symbol as a parameter, this method decides whether such symbol should 
	 * start the execution of an operation, a value assignment to a variable, push the symbol onto the stack,
	 * or just print the status of the stack.
	 * @param symbol String value which is a symbol that will be analyzed. 
	 */
	private void analyze(String symbol)
	{
		int result = 0, operand1 = 0, operand2 = 0;
		String variable = "";
		char firstChar = symbol.charAt(0);
		
		switch (firstChar)
		{	// if it is an integer
			case '0': case '1': case '2': case '3': case '4':
			case '5': case '6': case '7': case '8': case '9':			
				progStack.push(Integer.parseInt(symbol)); // pushes the Integer value of symbol
			break;
			
			// if it is an operator	
			case '+': case '-': case '*': case '/':
				operand1 = getOperand(progStack.pop());
				operand2 = getOperand(progStack.pop());
				result = operate(operand1,operand2,symbol);
				progStack.push(result);
			break;
				
			// if it is an assignment symbol
			case '=':
				operand1 = getOperand(progStack.pop());
				variable = progStack.pop().toString();
				symbols.put(variable,operand1);
			break;
			
			// if it is an identifier
			default:  
			{
				if (symbol.equals("print"))
					System.out.println(progStack.pop());

				else
					progStack.push(symbol);
			}	
			
		
		}
		
		return;
	}
	
	
	/**
	 * Given an input queue of symbols, it processes the number of symbols specified (numSymbols)
	 * and updates the progStack and symbols variables appropriately to reflect the state of the 
	 * "computer". Returns the remaining queue items.
	 * @param input Node that holds string values. This node is the head of the queue to be processed.
	 * @param numSymbols int value of number of symbols to process.
	 * @return Node of String values, which is the top of the stack.
	 */
	public Node<String> process(Node<String> input, int numSymbols) 
	{
		String symbol;
		int i = 0;
		Node<String> current = input;   // initializing current to point to head of program queue

		while(i<numSymbols && current != null)
		{
			symbol = current.getValue();
			analyze(symbol);
			
			i++;
			current = current.getNext();
		}
		
		return current;
	}
	
	/**
	 * Tests the class Computer with calls to its methods.	
	 * @throws IOException Exception handling in case the file used in this Method is not found.
	 */
	public void testMain() throws IOException
	{

//		String fileName = "c:/users/sergio/desktop/sample1.txt";
		String fileName = "/home/sergio/eclipse-workspace/CS310-project2/src/sample2.txt";
		boolean debug = false;
    	Node<String> inputQueue = fileToNodeQueue(fileName);

		System.out.println(Node.listToString(inputQueue));
		
		if(!debug) {
			while(inputQueue != null) {
				inputQueue = process(inputQueue, 10);
			}
		}
		else {
			Scanner s = new Scanner(System.in);
			for(int i = 1; inputQueue != null; i++) {
				System.out.println("\n######### Step " + i + " ###############\n");
				System.out.println("----------Step Output----------");
				inputQueue = process(inputQueue, 1);
				System.out.println("----------Symbol Table---------");
				System.out.println(symbols);
				System.out.println("----------Program Stack--------");
				System.out.println(progStack);
				if(inputQueue != null) {
					System.out.println("----------Program Remaining----");
					System.out.println(Node.listToString(inputQueue));
				}
				System.out.println("\nPress Enter to Continue");
				s.nextLine();
			}
			s.close();
		}		
		
	}
	
	//--------------------DON'T EDIT BELOW THIS LINE--------------------
	//----------------------EXCEPT TO ADD JAVADOCS----------------------
	
	//don't edit these...
	public static final String[] INT_OPS = {"+","-","*","/"};
	public static final String[] ASSIGN_OPS = {"=","+=","-=","*=","/="};
	
	//or these...
	public ProgramStack<Object> progStack = new ProgramStack<>();
	public SymbolTable<Integer> symbols = new SymbolTable<>(5);
	
	public static void main(String[] args) throws IOException {
		//this is not a testing main method, so don't edit this
		//edit testMain() instead!
		
		if(args.length == 0) {
			(new Computer()).testMain();
			return;
		}
		
		if(args.length != 2 || !(args[1].equals("false") || args[1].equals("true"))) {
			System.out.println("Usage: java Computer [filename] [true|false]");
			System.exit(0);
		}
		
		try {
			(new Computer()).runProgram(args[0], args[1].equals("true"));
		}
		catch(IOException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	//provided, don't change this
	public void runProgram(String filename, boolean debug) throws IOException {
		Node<String> input = fileToNodeQueue(filename);
		System.out.println("\nProgram: " + Node.listToString(input));
		
		if(!debug) {
			while(input != null) {
				input = process(input, 10);
			}
		}
		else {
			Scanner s = new Scanner(System.in);
			for(int i = 1; input != null; i++) {
				System.out.println("\n######### Step " + i + " ###############\n");
				System.out.println("----------Step Output----------");
				input = process(input, 1);
				System.out.println("----------Symbol Table---------");
				System.out.println(symbols);
				System.out.println("----------Program Stack--------");
				System.out.println(progStack);
				if(input != null) {
					System.out.println("----------Program Remaining----");
					System.out.println(Node.listToString(input));
				}
				System.out.println("\nPress Enter to Continue");
				s.nextLine();
			}
			s.close();
		}
	}
}