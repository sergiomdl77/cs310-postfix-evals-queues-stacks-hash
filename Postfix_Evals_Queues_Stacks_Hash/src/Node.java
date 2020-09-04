/**
 * This is a class that manages all basic Node operations.
 * This node is designed to handle double linked lists.

 * @version 1.0
 * @author Provided by CS 310 (teaching staff)
 * 
 * @param <T> type of the list element
 * 
 */

public class Node<T> {
	private T value;  
	private Node<T> next;
	private Node<T> prev;
	
	/**
	 * Constructor of the Node that expects the value of the element of the list.
	 * @param value Generic type value of the Node of the list.
	 */
	public Node(T value) {
		this.value = value;
	}
	
	/**
	 * Returns the value of this element of the list.
	 * @return Generic type value of this node in the list.
	 */
	public T getValue() {
		return value;
	}
	
	/**
	 * Sets the value of the Node.
	 * @param value Generic type value to set the node with.
	 */
	public void setValue(T value) {
		this.value = value;
	}
	
	/**
	 * Returns the memory address (link) to the next node of the list.
	 * @return Pointer to a node of Generic type value to the next Node in the list.
	 */
	public Node<T> getNext() {
		return this.next;
	}
	
	/**
	 * Sets the Pointer to the next element (Node) in the list.
	 * @param next Node of generic type value that the Node will be set to.
	 */
	public void setNext(Node<T> next) {
		this.next = next;
	}
		
	/**
	 * Returns the memory address (link) to the previous node of the list.
	 * @return Pointer to a node of Generic type value to the previous Node in the list.
	 */
	public Node<T> getPrev() {
		return this.prev;
	}
	
	/**
	 * Sets the Pointer to the previous element (Node) in the list.
	 * @param prev Node of generic type value that the Node will be set to.
	 */
	public void setPrev(Node<T> prev) {
		this.prev = prev;
	}
	
	/**
	 * Traverses the list that this node is a head of and saves the attributes of each
	 * node into a String that will be returned with a representation of the whole list.
	 * @param head Node of generic type which is the head of a list to make a string of.
	 * @return String with the representation of every node of the list in it.
	 */
	public static String listToString(Node<?> head) {
		StringBuilder ret = new StringBuilder();
		Node<?> current = head;
		while(current != null) {
			ret.append(current.value);
			ret.append(" ");
			current = current.getNext();
		}
		return ret.toString().trim();
	}
	
	/**
	 * Traverses the list that this node is a head of, but it first moves to the end of the list
	 * and then it traverses it backwards, saving the attributes of each node into a String that
	 * will be returned with a representation of the whole list, starting from end of the list.
	 * @param head Node of generic type which is the head of a list to make a string of.
	 * @return String with the representation of every node of the list in it.
	 */
	public static String listToStringBackward(Node<?> head) {
		Node<?> current = head;
		while(current.getNext() != null) {
			current = current.getNext();
		}
		
		StringBuilder ret = new StringBuilder();
		while(current != null) {
			ret.append(current.value);
			ret.append(" ");
			current = current.getPrev();
		}
		return ret.toString().trim();
	}
	
	/**
	 * Main method created just to test the performance of the class Node.
	 * @param args Does not expect any arguments.
	 */
	public static void main(String[] args) {
		//main method for testing, edit as much as you want
		
		//make nodes
		Node<String> n1 = new Node<>("A");
		Node<String> n2 = new Node<>("B");
		Node<String> n3 = new Node<>("C");
		
		//connect forward references
		n1.setNext(n2);
		n2.setNext(n3);
		
		//connect backward references
		n3.setPrev(n2);
		n2.setPrev(n1);
		
		//print forward and backward
		System.out.println(Node.listToString(n1));
		System.out.println(Node.listToStringBackward(n1));
	}
}