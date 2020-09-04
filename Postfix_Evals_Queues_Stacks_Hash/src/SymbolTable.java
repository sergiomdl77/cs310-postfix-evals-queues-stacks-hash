/**
 * This is a class that manages that will manage the storage of variables and their
 * value in a Hash Table structure
 *
 * @version 1.0
 * @author Sergio Delgado.
 * 
 * @param <T> type of the array's element
 */
public class SymbolTable<T> 
{
	private TableEntry<String,T>[] storage;   // Array of of elements for the hash table
	private int capacity;                     // Total capacity of the hash table of variables
	private int size;                         // Total of elements currently active on the table
	
	
	/**
	 * Constructor with initial capacity received as parameter
	 * @param s  int requested initial size for the hash talbe
	 */
	@SuppressWarnings("unchecked")
	public SymbolTable(int s)
	{
		storage = new TableEntry[s];		
		capacity = s;
		size = 0;
	}
	
	/**
	 * Returns how big the storage is
	 * @return int with current table's capacity
	 */
	public int getCapacity()
	{
		return capacity;
	}
	
	/**
	 * Returns the number of elements in the table
	 * @return int with number of elements
	 */
	public int size() 
	{
		return size;
	}
	
	/**
	* This method returns the position in which a new element with a key could be
	* inserted into the table after getting the hash code on that key and linearly
	* probing until finding a tombstone or a null place on the table.
	* @param key Identifier of an element of the table of generic type Object.
	* @return int with position on the table were insertion of an element can be made.
	*/
	private int getFirstFreeSpace(Object key)
	{
		int position = Math.abs(key.hashCode()) % capacity;   //  Getting hash code of the key
		
		while (storage[position] != null && !isTombstone(position) )  // traversing table
				position = (position + 1) % capacity;
		
		return position;
	}
		
	
	/**
	 * Returns the position on the table at which a searched key was found.
	 * If the key wans't found, it returns -1 which is not a valid index for a hash table.
	 * @param key generic type identifier of the element searched in the table.
	 * @return int with position on the table where element searched was found.
	 */
	private int getPosition(Object key)
	{
		int position = Math.abs(key.hashCode()) % capacity;
		boolean found = false;
		
		while (storage[position] != null && !found )
		{
			if (storage[position].getKey().equals(key) && !isTombstone(position))
				found = true;
			else
				position = (position + 1) % capacity;
		}
		
		if (storage[position] == null)  // if it never found the key element in the table
			position = -1;
		
		return position;
	}
	
	/**
	 * Puts an element on the location (hash code) of the key k. Uses linear probing
	 * to handle collisions. If the key already exists in the table replace the 
	 * current value with v. If the key isn't found in the table and the table is greater or equal to 80% full
	 * after the current element addition, the table is expanded to twice its size and rehashed.
	 * @param k String with key of the element of the table.
	 * @param v generic type value of the element of the table.
	 */
	public void put(String k, T v) 
	{
		int position = getPosition(k);
		
		if (position == -1)   // if the new element didn't already exist in the table
		{
			position = getFirstFreeSpace(k);    // find available place in table to add element
			size++;                             // and increase the size of table
		}
		
		storage[position] = new TableEntry<>(k,v);  // insert the element
		
		float load = (float)(size)/capacity; //
		if ( load >= 0.8 )                   //  check for size over capacity
			rehash(capacity * 2);
		
		return;
	}

	
	/**
	 * Removes the given key (and associated value) from the table. It uses tombstones to mark
	 * removed elements (turning the table space inactive).
	 * Worst case: O(n), Average case: O(1)
	 * @param k String with the key of the element to remove. 
	 * @return generic type (T) with the value of the element removed from table, and 
	 * returns null if the element is not in the table.
	 */
	public T remove(String k) 
	{
		T value = null;

		int position = getPosition(k);
		
		if (position > -1)  // if the element to remove was found in the table
		{
			value = storage[position].getValue();
			storage[position] = new TableEntry<String,T>("Tombstone",null);
			size--;
		}
		
		return value;
	}
	

	/**
	 * Returns the value if the element of the table whose key equal parameter k.
	 * Worst case: O(n), Average case: O(1)
	 * @param k Key of an element of the table.
	 * @return generic type (T) with the value of element from table.  It returns null
	 * if elements wasn't found in the table.
	 */
	public T get(String k) 
	{
		T value = null;
		
		int position = getPosition(k);
		
		if (position > -1)     // if the searched element was found in the table
			value = storage[position].getValue();
		
		return value;
	}

	/**
	 * Returns true if the element of the table at position index is a tombstone. O(1).
	 * @param index Position on the table to be evaluated.
	 * @return Boolean value, with whether or not the element in positon index
	 * is a tombstone.
	 */
	public boolean isTombstone(int index) 
	{
		boolean success = false;
		
		if (storage[index] != null)
			if (storage[index].getKey().equals("Tombstone"))
				success = true;
		
		return success;
	}
	
	
	/**
	 * Increase or decrease the size of the storage, rehashing all values.
	 * If the new size won't fit all the elements, return false and do not rehash.
	 * Return true if you were able to rehash.
	 * @param newSize int with the new capacity of the table (storage).
	 * @return Boolean value with whether or not the rehashing was executed.
	 */
	@SuppressWarnings("unchecked")
	public boolean rehash(int newSize) 
	{
		boolean success = true;
		
		if (newSize < size)
			success = false;

		else    // if new size of table fits all its current elements then ...
		{
			TableEntry<String,T>[] oldStorage = storage;  // save pointer to the old storage
	
			storage =  new TableEntry[newSize];    // create new storage with twice the size
			size = 0;
			capacity = newSize;

			for(int i=0; i<oldStorage.length; i++)    //  insert elements in new storage (one by one)
			{
				if (oldStorage[i] != null)                 // if current element is not an empty space
					if (!oldStorage[i].getKey().equals("Tombstone"))  // if current element is not a tombstone
						put(oldStorage[i].getKey(), oldStorage[i].getValue());  // add current element.
			}
			
		}

		return success;	
	}
	
	public static void main(String[] args) {
		//main method for testing, edit as much as you want
		SymbolTable<String> st1 = new SymbolTable<>(10);
		SymbolTable<Integer> st2 = new SymbolTable<>(5);
		
		if(st1.getCapacity() == 10 && st2.getCapacity() == 5 && st1.size() == 0 && st2.size() == 0) {
			System.out.println("Yay 1");
		}
		
		st1.put("a","apple");
		st1.put("b","banana");
		st1.put("banana","b");
		st1.put("b","butter");
		
		if(st1.toString().equals("a:apple\nb:butter\nbanana:b") && st1.toStringDebug().equals("[0]: null\n[1]: null\n[2]: null\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: a:apple\n[8]: b:butter\n[9]: banana:b")) {
			System.out.println("Yay 2");
		}
		
		if(st1.getCapacity() == 10 && st1.size() == 3 && st1.get("a").equals("apple") && st1.get("b").equals("butter") && st1.get("banana").equals("b")) {
			System.out.println("Yay 3");
		}
		
		st2.put("a",1);
		st2.put("b",2);
		st2.put("e",3);
		st2.put("y",4);

		if(st2.toString().equals("e:3\ny:4\na:1\nb:2") && st2.toStringDebug().equals("[0]: null\n[1]: e:3\n[2]: y:4\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: a:1\n[8]: b:2\n[9]: null")) {
			System.out.println("Yay 4");
		}
		
		if(st2.getCapacity() == 10 && st2.size() == 4 && st2.get("a").equals(1) && st2.get("b").equals(2) && st2.get("e").equals(3) && st2.get("y").equals(4)) {
			System.out.println("Yay 5");
		}
		
		if(st2.remove("e").equals(3) && st2.getCapacity() == 10 && st2.size() == 3 && st2.get("e") == null  &&  st2.get("y").equals(4)) {
			System.out.println("Yay 6");
		}
		
		if(st2.toString().equals("y:4\na:1\nb:2") && st2.toStringDebug().equals("[0]: null\n[1]: tombstone\n[2]: y:4\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: a:1\n[8]: b:2\n[9]: null")) {
			System.out.println("Yay 7");
		}

		if(st2.rehash(2) == false && st2.size() == 3 && st2.getCapacity() == 10) {
			System.out.println("Yay 8");
		}
		
		if(st2.rehash(4) == true && st2.size() == 3 && st2.getCapacity() == 4) {
			System.out.println("Yay 9");
		}
		
		if(st2.toString().equals("y:4\na:1\nb:2") && st2.toStringDebug().equals("[0]: null\n[1]: y:4\n[2]: a:1\n[3]: b:2")) {
			System.out.println("Yay 10");
		}
		
		SymbolTable<String> st3 = new SymbolTable<>(2);
		st3.put("a","a");
		st3.remove("a");
		
		if(st3.toString().equals("") && st3.toStringDebug().equals("[0]: null\n[1]: tombstone")) {
			st3.put("a","a");
			if(st3.toString().equals("a:a") && st3.toStringDebug().equals("[0]: null\n[1]: a:a") && st3.toStringDebug().equals("[0]: null\n[1]: a:a")) {
				System.out.println("Yay 11");
			}
		}
	}
	
	//--------------Provided methods below this line--------------
	//Add JavaDocs, but do not change the methods.
	
	public String toString() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < storage.length; i++) {
			if(storage[i] != null && !isTombstone(i)) {
				s.append(storage[i] + "\n");
			}
		}
		return s.toString().trim();
	}
	
	/**
	 * Returns a string with the key and value of every active element of the table
	 * @return String with all active elements of table.
	 */
	public String toStringDebug() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < storage.length; i++) {
			if(!isTombstone(i)) {
				s.append("[" + i + "]: " + storage[i] + "\n");
			}
			else {
				s.append("[" + i + "]: tombstone\n");
			}
			
		}
		return s.toString().trim();
	}
}