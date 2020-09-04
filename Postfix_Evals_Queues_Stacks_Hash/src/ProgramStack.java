import java.util.Iterator;

/**
 * This class will provide all the basic methods to handle operations on a stack data
 * structure which will utilize doubly linked nodes.
 * 
 * @author Sergio Delgado
 *
 * @param <T> Generic type that will be used to define the type of value the nodes of the
 * stack will hold.
 */
public class ProgramStack<T> implements Iterable<T> 
{
	private Node<T>topOfStack;
	private int size;
	
	/**
	 * Constructor that will initialize the pointer to top of the stack to null, and the
	 * size of the stack (number of elements in the stack) to 0;
	 */
	public ProgramStack()
	{
		topOfStack = null;
		size = 0;
	}
	
	/**
	 * Inserts a new element into the stack at the top of it. This stack class defines
	 * next as the next node towards bottom of stack and prev as next node towards the top
	 * of the stack. Complexity O(1).
	 * @param item Generic type value of the new element that will be pushed into stack.
	 */
	public void push(T item)
	{
		Node<T> newItem = new Node<T>(item);
				
		if (topOfStack != null)           // If the stack is empty...
			topOfStack.setPrev(newItem);

		newItem.setNext(topOfStack);
		newItem.setPrev(null);
		topOfStack = newItem;
		size++;
	}
	
	/**
	 * Pops an item off the stack
	 * This stack class defines next as the next node towards bottom of stack
	 * and prev as next node towards the top of the stack. Complexity O(1).
	 * @return Generic type value which was held by the node removed. It returns
	 * null if there are no items in the stack.
	 */
	public T pop() 
	{
		T removed = null;	
		// if the stack is not empty
		if (topOfStack != null)
		{
			removed = topOfStack.getValue();
			
			topOfStack = topOfStack.getNext(); // move the top of stack towards the bottom
			size--;                            //
			
			// if there is still items left in the stack
			if (topOfStack != null) 
				topOfStack.setPrev(null);
		}
		return removed;
	}
	
	/**
	 * Returns the element at the top of the stack (but doesn't remove it). Complexity O(1).
	 * @return Generic type value held by the node at top of stack. It returns null
	 * if there are no elements on the stack.
	 */
	public T peek()
	{
		T removed = null;		
		if (topOfStack != null)
		{
			removed = topOfStack.getValue();
		}
		return removed;		
	}

	/** 
	 * Overrides the Object.toString() method to suit the representation of the stack.
	 * Creates a string of the stack where each item is separated by a space. The top of the stack
	 * is shown to the right and the bottom of the stack on the left. Complexity O(n).
	 * @return String with the representation of the stack.
	 */
	public String toString() 
	{
		String result = "";
		
		// since listToStringBackward does not check for top of stack being null
		// we call the method only if top of stack is not null
		if (topOfStack != null)
			result = Node.listToStringBackward(topOfStack);

		return result;
	}
	
	/**
	 * Removes everything from the stack. Complexity O(1).
	 */
	public void clear() 
	{
		topOfStack = null;
		size = 0;
	}

	/**
	 * Returns the number of items on the stack. Complexity O(1).
	 * @return int value with the number of elements in the stack.
	 */
	public int size() 
	{
		return size;
	}

	/**
	 * Returns whether or not the stack is empty. Complexity O(1).
	 * @return Boolean value that indicates if the stack is empty.
	 */
	public boolean isEmpty() 
	{
		boolean result = true;
		if (size > 0)
			result = false;
		
		return result;
	}
	
	/**
	 * Returns an array representation of the stack. The top of the stack is element 0 of the array.
	 * Complexity O(n).	
	 * @return Object array type which holds in each element of the array, a representation of an 
	 * element of the stack.
	 */
	@SuppressWarnings("unchecked")
	public Object[] toArray() 
	{
		T[] array = (T[]) new Object[size];
		int i=0;
		Node<T> current = topOfStack;
		
		while (current != null)
		{
			array[i] = current.getValue();
			current = current.getNext();
			i++;
		}
		
		return array;
	}
	
	/**
	 * Return an iterator that traverses from the top of the stack to the bottom of
	 * the stack. The iterator's hasNext() and next() methods are both complexity O(1).
	 * The next() method throws a NullPointerException if you try to use next when 
	 * there are no more items.
	 */
	public Iterator<T> iterator()
	{
		/**
		 * Nested declaration of the class Iterator which provides the which provides the 
		 * methods to traverse the stack.
		 * @param <T> Generic type that specifies the type of value held in every node of stack. 
		 */
		return new Iterator<T>()
		{
			Node<T> current = topOfStack;   // pointer to the top of the stack.
			
			/**
			 * Returns true if the stack has a next element.
			 * @return Boolean value which indicates if there is a next element in stack.
			 */
			public boolean hasNext()
			{
				return (current != null);
			}
	
			/**
			 * Returns value held by the next node on the stack.
			 * @return T Generic type value held by the next node on the stack.	
			 */
			public T next()
			{
				if (current == null)
					throw new NullPointerException("There was no next item on stack");
				
				T value =  current.getValue();
				current = current.getNext();
				return value;
			}
		};
	}
	
	public static void main(String[] args)
	{
		
		ProgramStack<String> s1 = new ProgramStack<>();
		s1.push("a");
		s1.push("b");
		
		ProgramStack<Integer> s2 = new ProgramStack<>();
		s2.push(1);
		s2.push(2);
		s2.push(3);
   		
		if(s1.toString().equals("a b") && s1.toArray()[0].equals("b") && s1.toArray()[1].equals("a") && s1.toArray().length == 2) {
			System.out.println("Yay 1");
		}
		
		if(s1.peek().equals("b") && s2.peek().equals(3) && s1.size() == 2 && s2.size() == 3) {
			System.out.println("Yay 2");
		}
		
		if(s1.pop().equals("b") && s2.pop().equals(3) && s1.size() == 1 && s2.size() == 2) {
			System.out.println("Yay 3");
		}
		
		if(s1.toString().equals("a") && s1.peek().equals("a") && s2.peek().equals(2) && s1.pop().equals("a") && s2.pop().equals(2) && s1.size() == 0 && s2.size() == 1) {
			System.out.println("Yay 4");
		}
		
		if(s1.toString().equals("") && s1.peek() == null && s2.peek().equals(1) && s1.pop() == null && s2.pop().equals(1) && s1.size() == 0 && s2.size() == 0) {
			System.out.println("Yay 5");
		}
		
		s2.push(10);
		s2.push(20);
		s2.push(30);
		if(s1.isEmpty() && s1.toArray().length == 0 && !s2.isEmpty()) {
			s2.clear();
			if(s2.isEmpty()) {
				System.out.println("Yay 6");
			}
		}
		
		ProgramStack<Integer> s3 = new ProgramStack<>();
		s3.push(3);
		s3.push(2);
		s3.push(1);
		
		int i = 1;
		// enhanced for loop, which can be used for arrays or linked lists
		// it traverses the list, one item per loop-cycle automatically
		// it seems to use the Iterator.next() method to retrieve the value of the list
		// item has to be declared as the type that will be returned from Iterator.next()
		for(Integer item : s3) 
		{	
			if(i == item) System.out.println("Yay " + (6+i));
			else
				System.out.println(item);
			i++;
		}
		
	}
}