package model.data_structures;

public interface IRedBlackBST<K,V> extends Iterable<K> 
{
	public int size();

	public boolean isEmpty();

	public V get(K key);

	public int getHeight(K key);

	public boolean contains(K key);

	public void put(K key, V val);

	public int height();

	public K min();

	public K max();

	public boolean check();
}
