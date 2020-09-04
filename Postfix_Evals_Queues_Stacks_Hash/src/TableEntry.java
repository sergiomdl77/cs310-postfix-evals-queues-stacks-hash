//Do not edit this class, just add JavaDocs
/** 
 * This class holds the attributes of one element of a hash table. 
 * 
 * @author CS 310 Teaching Staff
 *
 * @param <K> Type parameter assigned to the Key of the entry.
 * @param <V> TYpe parameter assigned to the value of the entry.
 */
public class TableEntry<K,V> {
	private K key;
	private V value;
	
	/**
	 * Constructor of the class (the only one) which requires the key and value
	 * of the element of the table to be received as parameters.
	 * @param key Generic type key that will be used to obtain the hashing code of the element.
	 * @param value Generic type value associated with this element in the table.
	 */
	public TableEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Returns the key of the this element of the hash table.
	 * @return Generic type key of this element.
	 */
	public K getKey() {
		return key;
	}
	
	/**
	 * Returns the value associated with this element of the hash table
	 * @return Generic type of the value of the this element.
	 */
	public V getValue() {
		return value;
	}
	
	/**
	 * Override of the Object.toString() method that suits this particular class. 
	 * @return String that represents the attributes of this hash table element.
	 */
	public String toString() {
		return key.toString()+":"+value.toString();
	}
}