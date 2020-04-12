package test.data_structures;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.data_structures.NodoRedBlackBST;
import model.data_structures.Queue;
import model.data_structures.RedBlackBST;

public class TestRedBlackBST 
{
	private RedBlackBST<Integer,Integer> estructura;
	private int tamano;

	@Before
	public void setUp1()
	{
		tamano = 0;
		estructura = new RedBlackBST<Integer,Integer>();
	}

	@Before
	public void setUp2()
	{
		estructura = null;
		estructura = new RedBlackBST<Integer,Integer>();
		tamano = 0;
		for(int i = 0; i<100;i++)
		{
			estructura.put(i,i);
			tamano++;
		}
	}

	//----------------------------------------------------------------
	//Metodos --------------------------------------------------------
	//----------------------------------------------------------------


	@Test
	public void testSize()
	{
		setUp1();

		assertEquals(estructura.size(), tamano);
		assertEquals(tamano, 0);

		setUp2();
		assertEquals(estructura.size(), tamano);
		assertEquals(tamano, 100);
	}

	@Test
	public void testIsEmpty()
	{
		setUp1();
		assertEquals(estructura.isEmpty(), true);
		assertEquals(estructura.size(), tamano);
		assertEquals(tamano, 0);

		setUp2();
		assertEquals(estructura.isEmpty(), false);
		assertEquals(estructura.size(), tamano);
		assertEquals(tamano, 100);
	}

	@Test
	public void testGet()
	{
		setUp1();
		assertEquals(estructura.get(0), null);

		setUp2();
		Integer valor = 0;
		for(int i = 0; i<100;i++)
		{
			valor = i;
			assertEquals(estructura.get(i), valor);
		}
	}

	@Test
	public void testGetHeight()
	{
		setUp2();
		assertEquals(estructura.getHeight(63), 0);
		assertEquals(estructura.getHeight(0), 6);
		assertEquals(estructura.getHeight(99), 5);
	}

	@Test
	public void testcContains()
	{
		setUp1();
		assertEquals(estructura.contains(0), false);

		setUp2();
		for(int i = 0; i<100; i++)
		{
			assertEquals(estructura.contains(i), true);
		}
	}

	@Test
	public void testPut()
	{
		setUp1();

		assertEquals(estructura.size(), tamano);
		assertEquals(tamano, 0);
		for(int i = 0; i<50; i++)
		{
			estructura.put(i, i);
			tamano++;
		}
		assertEquals(estructura.size(), tamano);
		assertEquals(tamano, 50);
		assertEquals(estructura.check(), true);

		setUp2();
		assertEquals(estructura.size(), tamano);
		assertEquals(tamano, 100);
		for(int i = 100; i<200;i++)
		{
			estructura.put(i, i);	
		}
		assertEquals(estructura.check(), true);
	}

	@Test
	public void testHeight()
	{
		setUp1();
		assertEquals(estructura.height(), -1);
		estructura.put(1, 1);
		assertEquals(estructura.height(), 0);

		setUp2();
		assertEquals(estructura.height(), 6);
	}

	@Test
	public void testMin()
	{
		setUp2();
		Integer valor = 0;
		assertEquals(estructura.min(), valor);
		valor = -2;
		estructura.put(-2, -2);
		assertEquals(estructura.min(), valor);

		for(int i = -10; i>-100;i--)
		{
			estructura.put(i,i);
			valor = i;
			assertEquals(estructura.min(), valor);
		}
	}

	@Test
	public void testMax()
	{
		setUp2();
		Integer valor = 99;
		assertEquals(estructura.max(), valor);
		valor = 100;
		estructura.put(100, 100);
		assertEquals(estructura.max(), valor);

		for(int i = 1000; i<1500;i++)
		{
			estructura.put(i,i);
			valor = i;
			assertEquals(estructura.max(), valor);
		}
	}

	@Test
	public void testCheck()
	{
		setUp2();
		assertEquals(estructura.check(), true);
	}

	@Test
	public void testKeys()
	{
		setUp2();
		Queue<Integer> cola = estructura.keys();

		for(int i = 0; i<100;i++)
		{
			Integer com = i;
			assertEquals(cola.dequeue(), com);
		}
	}

	@Test
	public void testValuesInRange()
	{
		setUp2();
		Integer min = 0;
		Integer max = 99;
		Queue<Integer> cola = estructura.valuesInRange(min, max);



		for(int i = 0; i<100;i++)
		{
			Integer com = i;
			assertEquals(cola.dequeue(), com);
		}

		min = 49;
		max = 80;
		cola = null;
		cola = estructura.valuesInRange(min, max);

		for(int i = 49; i<81;i++)
		{
			Integer com = i;
			assertEquals(cola.dequeue(), com);
		}

		ArrayList<NodoRedBlackBST<Integer,Integer>> cosa = estructura.dar();

		for(int i = 0; i<100;i++)
		{
//						System.out.println(cosa.get(i).darLLave() + " - "+ cosa.get(i).darValor());
			assertEquals(cosa.get(i).darLLave(), cosa.get(i).darValor());
		}

	}

	@Test
	public void testKeysInRange()
	{
		setUp2();
		Integer min = 0;
		Integer max = 99;
		Queue<Integer> cola = estructura.keysInRange(min, max);

		for(int i = 0; i<100;i++)
		{
			Integer com = i;
			assertEquals(cola.dequeue(), com);
		}

		min = 49;
		max = 80;
		cola = null;
		cola = estructura.keysInRange(min, max);

		for(int i = 49; i<81;i++)
		{
			Integer com = i;
			assertEquals(cola.dequeue(), com);
		}

	}
}
