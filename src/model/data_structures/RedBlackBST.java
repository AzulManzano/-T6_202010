package model.data_structures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RedBlackBST<K extends Comparable<K>,V extends Comparable<V>> implements IRedBlackBST<K,V> 
{
	private static final boolean RED   = true;
	private static final boolean BLACK = false;

	private NodoRedBlackBST<K,V> raiz;

	public RedBlackBST()
	{
		raiz = null;
	}

	public int size() 
	{	
		return size(raiz);
	}

	private int size(NodoRedBlackBST<K,V> local)
	{
		if(local == null)
		{
			return 0;
		}
		return local.size();
	}

	public int size(K izquierdo, K derecho)
	{
		if(izquierdo == null)
			throw new IllegalArgumentException("El primer argumento de size() es null");
		if(derecho == null)
			throw new IllegalArgumentException("El segundo argumento de size() es null");

		if(izquierdo.compareTo(derecho)>0)
			return 0;
		if(contains(derecho))
			return rank(derecho)-rank(izquierdo)+1;
		else
			return rank(derecho)-rank(izquierdo);
	}

	public boolean isEmpty() 
	{
		return raiz == null;
	}


	public V get(K key) 
	{		
		if(key == null)
			throw new IllegalArgumentException("El argumento de get() es null");
		return get(raiz,key);
	}

	private V get(NodoRedBlackBST<K,V> nodo, K key)
	{
		while(nodo != null)
		{
			int cmp = key.compareTo(nodo.darLLave());
			if(cmp < 0)
				nodo = nodo.darIzquierdo();
			else if(cmp > 0)
				nodo = nodo.darDerecho();
			else 
				return nodo.darValor();
		}
		return null;

	}
	
	public boolean esHoja(K key)
	{
		NodoRedBlackBST<K,V> nodo = raiz;
		while(nodo != null)
		{
			int cmp = key.compareTo(nodo.darLLave());
			if(cmp < 0)
				nodo = nodo.darIzquierdo();
			else if(cmp > 0)
				nodo = nodo.darDerecho();
			else 
				return nodo.esHoja();
		}
		return false;
	}

	@Override
	public int getHeight(K key) 
	{
		if(key == null)
			throw new IllegalArgumentException("El argumento de getHeight() es null");
		return getHeight(raiz,key);
	}

	private int getHeight(NodoRedBlackBST<K,V> nodo, K key)
	{
		int tam = 0;
		while(nodo != null)
		{
			int cmp = key.compareTo(nodo.darLLave());
			if(cmp < 0)
			{
				nodo = nodo.darIzquierdo();
				tam++;
			}
			else if(cmp > 0)
			{
				nodo = nodo.darDerecho();
				tam++;
			}
			else 
				return tam;
		}
		return -1;
	}

	public boolean contains(K key) 
	{
		return get(key) != null;
	}

	@Override
	public void put(K key, V val) 
	{
		if(key == null)
			throw new IllegalArgumentException("El primer argumento de put() es null");
		if(val == null)
			throw new IllegalArgumentException("El segundo argumento de put() es null");

		raiz = put(raiz, key, val);
		raiz.cambiarColor(BLACK);		
	}

	private NodoRedBlackBST<K,V> put(NodoRedBlackBST<K,V> nodo, K key, V val)
	{
		if(nodo == null)
			return new NodoRedBlackBST<K, V>(key, val, RED, 1);

		int cmp = key.compareTo(nodo.darLLave());
		if(cmp < 0)
			nodo.cambiarIzquierda(put(nodo.darIzquierdo(),key, val));
		else if(cmp > 0)
			nodo.cambiarDerecha(put(nodo.darDerecho(),key, val));
		else
			nodo.cambiarValor(val);

		if(isRed(nodo.darDerecho()) && !isRed(nodo.darIzquierdo()))
			nodo = rotateLeft(nodo);
		if(isRed(nodo.darIzquierdo()) && isRed(nodo.darIzquierdo().darIzquierdo()))
			nodo = rotateRight(nodo);
		if(isRed(nodo.darIzquierdo()) && isRed(nodo.darDerecho()))
			flipColors(nodo);
		nodo.cambiarTamano(size(nodo.darIzquierdo())+size(nodo.darDerecho())+1);

		return nodo;
	}

	public int height() 
	{
		return height(raiz);
	}

	private int height(NodoRedBlackBST<K,V> nodo)
	{
		if(nodo == null)
			return -1;
		return 1 + Math.max(height(nodo.darIzquierdo()) , height(nodo.darDerecho()));
	}

	public K min() 
	{
		if(isEmpty())
			throw new NoSuchElementException("Llamo a min() con el arbol vacio");
		return min(raiz).darLLave();
	}

	private NodoRedBlackBST<K,V> min(NodoRedBlackBST<K,V> nodo)
	{
		if(nodo.darIzquierdo() == null)
			return nodo;
		else
			return min(nodo.darIzquierdo());
	}

	public K max() {
		if(isEmpty())
			throw new NoSuchElementException("Llamo a max() con el arbol vacio");
		return max(raiz).darLLave();
	}

	private NodoRedBlackBST<K,V> max(NodoRedBlackBST<K,V> nodo)
	{
		if(nodo.darDerecho() == null)
			return nodo;
		else
			return max(nodo.darDerecho());
	}

	public boolean check() 
	{
		if(!isBST())
			System.out.println("No posee orden simetrico");
		if(!isSizeConsistent())
			System.out.println("Los tamaños no son consistentes");
		if(!isRankConsistent())
			System.out.println("Los rangos no son consistentes");
		if(!is23())
			System.out.println("No es un arbol 2-3");
		if(!isBalanced())
			System.out.println("No esta balanciado");
		return isBST() && isSizeConsistent() && isRankConsistent() && is23() && isBalanced();
	}

	private boolean isRed(NodoRedBlackBST<K,V> nodo)
	{
		if(nodo == null)
		{
			return false;
		}
		return nodo.darColor() == RED;
	}

	private boolean isBST()
	{
		return isBST(raiz, null, null);
	}

	private boolean isBST(NodoRedBlackBST<K,V> nodo, K minimo, K maximo)
	{
		if(nodo == null)
			return true;
		if(minimo != null && nodo.darLLave().compareTo(minimo) <=0)
			return false;
		if(maximo != null && nodo.darLLave().compareTo(maximo) >=0)
			return false;
		return isBST(nodo.darIzquierdo(), minimo, nodo.darLLave()) && isBST(nodo.darDerecho(), nodo.darLLave(), maximo);
	}

	private boolean isSizeConsistent()
	{
		return isSizeConsistent(raiz);
	}

	private boolean isSizeConsistent(NodoRedBlackBST<K,V> nodo)
	{
		if(nodo == null)
			return true;
		if(nodo.size() != size(nodo.darIzquierdo())+size(nodo.darDerecho())+1)
			return false;
		return isSizeConsistent(nodo.darIzquierdo()) && isSizeConsistent(nodo.darDerecho());
	}

	private boolean isRankConsistent()
	{
		for(int i = 0; i<size(); i++)
		{
			if(i != rank(select(i)))
				return false;
		}

		Queue<K> cola = keys();
		int tam = cola.getSize();
		for(int i = 0; i<tam; i++)
		{
			K llave = cola.dequeue();
			if(llave.compareTo(select(rank(llave))) != 0)
				return false;
		}
		return true;


	}

	private boolean is23()
	{
		return is23(raiz);
	}

	private boolean is23(NodoRedBlackBST<K,V> nodo)
	{
		if(nodo == null)
			return true;
		if(isRed(nodo.darDerecho()))
			return false;
		if(nodo != raiz && isRed(nodo) && isRed(nodo.darIzquierdo()))
			return false;
		return is23(nodo.darIzquierdo()) && is23(nodo.darDerecho());
	}

	private boolean isBalanced()
	{
		int black = 0;
		NodoRedBlackBST<K,V> nodo = raiz;
		while(nodo != null)
		{
			if(!isRed(nodo))
				black++;
			nodo =nodo.darIzquierdo();
		}

		return isBalanced(raiz, black);
	}

	private boolean isBalanced(NodoRedBlackBST<K,V> nodo, int pBlack)
	{
		if(nodo == null)
			return pBlack == 0;
		if(!isRed(nodo))
			pBlack--;
		return isBalanced(nodo.darIzquierdo(), pBlack) && isBalanced(nodo.darDerecho(),pBlack);
	}

	public K floor(K llave)
	{
		if(llave == null)
			return null;
		if(isEmpty())
			return null;

		NodoRedBlackBST<K,V> nodo = floor(raiz, llave);
		if(nodo == null)
			return null;
		else
			return nodo.darLLave();
	}

	private NodoRedBlackBST<K,V> floor(NodoRedBlackBST<K,V> nodo, K llave)
	{
		if(nodo == null)
			return null;
		int compar = llave.compareTo(nodo.darLLave());
		if(compar == 0)
			return nodo;
		if(compar<0)
			return floor(nodo.darIzquierdo(), llave);
		NodoRedBlackBST<K,V> nodo2 = floor(nodo.darDerecho(), llave);
		if(nodo2 != null)
			return nodo2;
		else
			return nodo;
	}

	public K ceiling(K llave)
	{
		if(llave == null)
			throw new IllegalArgumentException("el argumento de ceiling() es null");
		if(isEmpty())
			throw new NoSuchElementException("llamo a ceiling() con el arbol vacio");
		NodoRedBlackBST<K,V> nodo = ceiling(raiz, llave);
		if(nodo == null)
			throw new NoSuchElementException("El argumento de ceiling() es null");
		else
			return nodo.darLLave();
	}

	private NodoRedBlackBST<K,V> ceiling(NodoRedBlackBST<K,V> nodo, K llave)
	{
		if(nodo == null)
			return null;
		int comp = llave.compareTo(nodo.darLLave());
		if(comp == 0)
			return nodo;
		if(comp>0)
			return ceiling(nodo.darDerecho(), llave);
		NodoRedBlackBST<K,V> nodo2 = ceiling(nodo.darIzquierdo(), llave);
		if(nodo2 != null)
			return nodo2;
		else 
			return nodo;
	}

	public K select(int rango)
	{
		if(rango < 0 || rango >= size())
			throw new IllegalArgumentException("El argumento de select() es invalido: " + rango);
		return select(raiz, rango);
	}

	private K select(NodoRedBlackBST<K,V> nodo, int rango)
	{
		if(nodo == null)
			return null;
		int izquierdaTamano = size(nodo.darIzquierdo());
		if(izquierdaTamano > rango)
			return select(nodo.darIzquierdo(), rango);
		else if(izquierdaTamano< rango)
			return select(nodo.darDerecho(),rango-izquierdaTamano-1);
		else 
			return nodo.darLLave();
	}

	public int rank(K llave)
	{
		if(llave == null)
			throw new IllegalArgumentException("El argumento de rank() es null");
		return rank(llave, raiz);
	}

	public int rank(K llave,NodoRedBlackBST<K,V> nodo)
	{
		if(nodo == null)
			return 0;
		int comp = llave.compareTo(nodo.darLLave());
		if(comp<0)
			return rank(llave, nodo.darIzquierdo());
		else if(comp > 0)
			return 1 + size(nodo.darIzquierdo()) + rank(llave,nodo.darDerecho());
		else
			return size(nodo.darIzquierdo());
	}

	private NodoRedBlackBST<K,V> rotateRight(NodoRedBlackBST<K,V> pNodo)
	{
		NodoRedBlackBST<K,V> nodo = pNodo.darIzquierdo();
		pNodo.cambiarIzquierda(nodo.darDerecho());
		nodo.cambiarDerecha(pNodo);
		nodo.cambiarColor(nodo.darDerecho().darColor());
		nodo.darDerecho().cambiarColor(RED);
		nodo.cambiarTamano(pNodo.size());
		pNodo.cambiarTamano(size(pNodo.darIzquierdo())+size(pNodo.darDerecho())+1);
		return nodo;
	}

	private NodoRedBlackBST<K,V> rotateLeft(NodoRedBlackBST<K,V> pNodo)
	{
		NodoRedBlackBST<K,V> nodo = pNodo.darDerecho();
		pNodo.cambiarDerecha(nodo.darIzquierdo());
		nodo.cambiarIzquierda(pNodo);
		nodo.cambiarColor(nodo.darIzquierdo().darColor());
		nodo.darIzquierdo().cambiarColor(RED);
		nodo.cambiarTamano(pNodo.size());
		pNodo.cambiarTamano(size(pNodo.darIzquierdo())+size(pNodo.darDerecho())+1);
		return nodo;
	}

	private void flipColors(NodoRedBlackBST<K,V> nodo)
	{
		nodo.cambiarColor(!nodo.darColor());
		nodo.darIzquierdo().cambiarColor(!nodo.darIzquierdo().darColor());
		nodo.darDerecho().cambiarColor(!nodo.darDerecho().darColor());
	}



	public Queue<K> keys()
	{
		if(isEmpty())
			return new Queue<K>();
		return keysInRange(min(), max());
	}

	public Queue<V> valuesInRange(K init, K end)
	{
		if( init == null)
			throw new IllegalArgumentException("El primer argumento de keys() es null");
		if(end == null)
			throw new IllegalArgumentException("El segundo argumento de keys() es null");

		Queue<V> cola = new Queue<V>();
		vals(raiz, cola, init, end);
		return cola;
	}

	public ArrayList<NodoRedBlackBST<K,V>> dar()
	{
		ArrayList<NodoRedBlackBST<K,V>> cosa = new ArrayList<NodoRedBlackBST<K,V>>();
		cosita(raiz, cosa, min(),max());
		return cosa;


	}

	private void cosita(NodoRedBlackBST<K,V> nodo, ArrayList<NodoRedBlackBST<K,V>> cola, K init, K end)
	{
		if(nodo == null)
			return;
		int cmplo = init.compareTo(nodo.darLLave());
		int cmphi = end.compareTo(nodo.darLLave());
		if(cmplo < 0)
			cosita(nodo.darIzquierdo(),cola,init,end);
		if(cmplo <= 0 && cmphi >= 0)
			cola.add(nodo);
		if(cmphi > 0)
			cosita(nodo.darDerecho(),cola,init,end);
	}

	private void vals(NodoRedBlackBST<K,V> nodo, Queue<V> cola, K init, K end)
	{
		if(nodo == null)
			return;
		int cmplo = init.compareTo(nodo.darLLave());
		int cmphi = end.compareTo(nodo.darLLave());
		if(cmplo < 0)
			vals(nodo.darIzquierdo(),cola,init,end);
		if(cmplo <= 0 && cmphi >= 0)
			cola.enqueue(nodo.darValor());
		if(cmphi > 0)
			vals(nodo.darDerecho(),cola,init,end);
	}

	public Queue<K> keysInRange(K init, K end)
	{
		if( init == null)
			throw new IllegalArgumentException("El primer argumento de keys() es null");
		if(end == null)
			throw new IllegalArgumentException("El segundo argumento de keys() es null");

		Queue<K> cola = new Queue<K>();
		keys(raiz, cola, init, end);
		return cola;

	}

	private void keys(NodoRedBlackBST<K,V> nodo, Queue<K> cola, K init, K end)
	{
		if(nodo == null)
			return;
		int cmplo = init.compareTo(nodo.darLLave());
		int cmphi = end.compareTo(nodo.darLLave());
		if(cmplo < 0)
			keys(nodo.darIzquierdo(),cola,init,end);
		if(cmplo <= 0 && cmphi >= 0)
			cola.enqueue(nodo.darLLave());
		if(cmphi > 0)
			keys(nodo.darDerecho(),cola,init,end);
	}

	public Queue<K> darHojas()
	{
		if( min() == null)
			throw new IllegalArgumentException("El primer argumento de keys() es null");
		if(max() == null)
			throw new IllegalArgumentException("El segundo argumento de keys() es null");

		Queue<K> cola = new Queue<K>();
		hojas(raiz, cola, min(), max());
		return cola;
	}

	private void hojas(NodoRedBlackBST<K,V> nodo, Queue<K> cola, K init, K end)
	{
		if(nodo == null)
			return;
		int cmplo = init.compareTo(nodo.darLLave());
		int cmphi = end.compareTo(nodo.darLLave());
		if(cmplo < 0)
			hojas(nodo.darIzquierdo(),cola,init,end);
		if(cmplo <= 0 && cmphi >= 0 && nodo.esHoja() == true)
		{
				cola.enqueue(nodo.darLLave());
		}
		if(cmphi > 0)
			hojas(nodo.darDerecho(),cola,init,end);
	}


	public Iterator<K> iterator() 
	{
		return keys().iterator();
	}
}

