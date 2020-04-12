package model.data_structures;

public class NodoRedBlackBST<K,V> 
{
	private K llave;           
    private V valor;         
    private NodoRedBlackBST<K,V> derecha; 
    private NodoRedBlackBST<K,V> izquierda;  
    private boolean color;     
    private int tamano;          
	
	
	public NodoRedBlackBST(K key, V val, boolean pColor, int pTamano)
	{
		llave = key;
		valor = val;
		color = pColor;
		tamano = pTamano;
	}
	
	public K darLLave()
	{
		return llave;
	}
	
	public V darValor()
	{
		return valor;
	}
	
	public NodoRedBlackBST<K,V> darDerecho()
	{
		return derecha;
	}
	
	public NodoRedBlackBST<K,V> darIzquierdo()
	{
		return izquierda;
	}
	
	public boolean darColor()
	{
		return color;
	}
	
	public int size()
	{
		return tamano;
	}
	
	public void cambiarTamano(int pTamano)
	{
		tamano = pTamano;
	}
	
	public void cambiarValor(V pValor)
	{
		valor = pValor;
	}
	
	public void cambiarDerecha(NodoRedBlackBST<K,V> pDerecha)
	{
		derecha = pDerecha;
	}
	
	public void cambiarIzquierda(NodoRedBlackBST<K,V> pIzquierda)
	{
		izquierda = pIzquierda;
	}
	
	public void cambiarColor(boolean pColor)
	{
		color = pColor;
	}
	
	public boolean esHoja()
	{
		return (derecha == null && izquierda == null);
	}

}
