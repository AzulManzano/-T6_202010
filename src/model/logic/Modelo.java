package model.logic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import model.data_structures.RedBlackBST;
import model.data_structures.Comparendo;
import model.data_structures.Nodo;
import model.data_structures.Queue;
/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo 
{
	// PORFAVOR LEER !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//Ya que mi computador no tiene la capacidad de leer los 500000 datos del archivo grande no pude hacer las tablas de datos con esa informacion, 
	//por ese motivo tuve que trabajar con este archivo que solo tiene 50000. Gracias por tu comprension

	public static String PATH = "./data/Comparendos_DEI_2018_Bogot�_D.C_50000_.geojson";
	// PORFAVOR LEER !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	private RedBlackBST<Integer,Comparendo> arbol;


	public Modelo()
	{
		arbol = new  RedBlackBST<Integer,Comparendo>();
	}


	// Retorno de estructuras -----------------------

	public  RedBlackBST<Integer,Comparendo> darArbol()
	{
		return arbol;
	}
	//---------------------------------------------


	//Tama�o ----------------------------------------------

	public int darTotalComparendos()
	{
		return arbol.size();
	}

	//---------------------------------------------------

	public void cargarDatos() 
	{
		JsonReader reader;
		try {
			reader = new JsonReader(new FileReader(PATH));
			JsonElement elem = JsonParser.parseReader(reader); 
			JsonArray e2 = elem.getAsJsonObject().get("features").getAsJsonArray();


			SimpleDateFormat parser=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");


			for(JsonElement e: e2) {
				int OBJECTID = e.getAsJsonObject().get("properties").getAsJsonObject().get("OBJECTID").getAsInt();

				String s = e.getAsJsonObject().get("properties").getAsJsonObject().get("FECHA_HORA").getAsString();	
				Date FECHA_HORA = parser.parse(s); 

				String MEDIO_DETE = e.getAsJsonObject().get("properties").getAsJsonObject().get("MEDIO_DETECCION").getAsString();
				String CLASE_VEHI = e.getAsJsonObject().get("properties").getAsJsonObject().get("CLASE_VEHICULO").getAsString();
				String TIPO_SERVI = e.getAsJsonObject().get("properties").getAsJsonObject().get("TIPO_SERVICIO").getAsString();
				String INFRACCION = e.getAsJsonObject().get("properties").getAsJsonObject().get("INFRACCION").getAsString();
				String DES_INFRAC = e.getAsJsonObject().get("properties").getAsJsonObject().get("DES_INFRACCION").getAsString();	
				String LOCALIDAD = e.getAsJsonObject().get("properties").getAsJsonObject().get("LOCALIDAD").getAsString();

				double longitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray()
						.get(0).getAsDouble();

				double latitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray()
						.get(1).getAsDouble();


				Comparendo c = new Comparendo(OBJECTID, FECHA_HORA, DES_INFRAC, MEDIO_DETE, CLASE_VEHI, TIPO_SERVI, INFRACCION, LOCALIDAD, longitud, latitud);

				arbol.put(OBJECTID, c);
			}

		} catch (FileNotFoundException | ParseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	public Comparendo darMinimo()
	{
		return arbol.get(arbol.min());
	}

	public Comparendo darMaximo()
	{
		return arbol.get(arbol.max());
	}

	public Comparendo requerimiento2(int pId)
	{
		return arbol.get(pId);
	}

	public Queue<Comparendo> requerimiento3(int valorInf, int valorSup)
	{
		return arbol.valuesInRange(valorInf, valorSup);
	}

	public int darAltura()
	{
		return arbol.height();
	}

	public double promediHojas()
	{
		Queue<Integer> cola = arbol.darHojas();
		double promedio = 0;
		int tam = cola.getSize();

		for(int i = 0; i<tam; i++)
		{
			Integer cosa = cola.dequeue();
			promedio = promedio + arbol.getHeight(cosa);
		}
		promedio = promedio/tam;

		return promedio;
	}

	public String indicarMes(int inicial)
	{
		if(inicial == 0)
			return "Enero";
		else if(inicial == 1)
			return "Febrero";
		else if(inicial == 2)
			return "Marzo";
		else if(inicial == 3)
			return "Abril";
		else if(inicial == 4)
			return "Mayo";
		else if(inicial == 5)
			return "Junio";
		else if(inicial == 6)
			return "Julio";
		else if(inicial == 7)
			return "Agosto";
		else if(inicial == 8)
			return "Septiembre";
		else if(inicial == 9)
			return "Octubre";
		else if(inicial == 10)
			return "Noviembre";
		else if(inicial == 11)
			return "Diciembre";

		return "";
	}

	public int indicarDia(String inicial)
	{
		if(inicial.equalsIgnoreCase("L"))
			return 1;
		else if(inicial.equalsIgnoreCase("M"))
			return 2;
		else if(inicial.equalsIgnoreCase("I"))
			return 3;
		else if(inicial.equalsIgnoreCase("J"))
			return 4;
		else if(inicial.equalsIgnoreCase("V"))
			return 5;
		else if(inicial.equalsIgnoreCase("S"))
			return 6;
		else if(inicial.equalsIgnoreCase("D"))
			return 0;
		return -1;
	}

	public String indicarDia2(String inicial)
	{
		if(inicial.equalsIgnoreCase("L"))
			return "Lunes";
		else if(inicial.equalsIgnoreCase("M"))
			return "Martes";
		else if(inicial.equalsIgnoreCase("I"))
			return "Miercoles";
		else if(inicial.equalsIgnoreCase("J"))
			return "Jueves";
		else if(inicial.equalsIgnoreCase("V"))
			return "Viernes";
		else if(inicial.equalsIgnoreCase("S"))
			return "Sabado";
		else if(inicial.equalsIgnoreCase("D"))
			return "Domingo";
		return "";
	}

	public String tranformarTipoVeculo(String lista)
	{
		String listica = "";

		if(lista.equals("1"))
		{
			listica = "AUTOMÓVIL";
		} 
		else if(lista.equals("2"))
		{
			listica = "BICICLETA";
		}
		else if(lista.equals("3"))
		{
			listica = "BUS";
		}
		else if(lista.equals("4"))
		{
			listica = "BUSETA";
		}
		else if(lista.equals("5"))
		{
			listica = "CAMIONETA";
		}
		else if(lista.equals("6"))
		{
			listica = "CAMPERO";
		}
		else if(lista.equals("7"))
		{
			listica =  "MOTOCICLETA";
		}

		return listica;
	}

	public String tranformarMedioDeteccion(String lista)
	{
		String listica = "";

		if(lista.equals("1"))
		{
			listica = "AUTOMÓVIL";
		} 
		else if(lista.equals("2"))
		{
			listica = "DEAP";
		}


		return listica;
	}

	public String tranformarTipoServicio(String lista)
	{
		String listica = "";

		if(lista.equals("1"))
		{
			listica = "Público";
		} 
		else if(lista.equals("2"))
		{
			listica = "Oficial";
		}
		else if(lista.equals("3"))
		{
			listica = "Particular";
		}


		return listica;
	}

	public String transformarLocalidad(String lista)
	{
		String listica = "";

		if(lista.equals("1"))
		{
			listica = "PUENTE ARANDA";
		} 
		else if(lista.equals("2"))
		{
			listica = "FONTIBON";
		}
		else if(lista.equals("3"))
		{
			listica = "ENGATIVA";
		}
		else if(lista.equals("4"))
		{
			listica = "SUBA";
		}
		else if(lista.equals("5"))
		{
			listica = "USME";
		}
		else if(lista.equals("6"))
		{
			listica = "CIUDAD BOLIVAR";
		}
		else if(lista.equals("7"))
		{
			listica =  "USAQUEN";
		}
		else if(lista.equals("8"))
		{
			listica = "BOSA";
		}
		else if(lista.equals("9"))
		{
			listica = "SAN CRISTOBAL";
		}
		else if(lista.equals("10"))
		{
			listica = "KENNEDY";
		}
		else if(lista.equals("11"))
		{
			listica = "CHAPINERO";
		}
		else if(lista.equals("12"))
		{
			listica = "MARTIRES";
		}
		else if(lista.equals("13"))
		{
			listica =  "BARRIOS UNIDOS";
		}
		else if(lista.equals("14"))
		{
			listica =  "TUNJUELITO";
		}
		else if(lista.equals("15"))
		{
			listica =  "SANTA FE";
		}
		else if(lista.equals("16"))
		{
			listica =  "ANTONIO NARIÑO";
		}

		return listica;
	}
}


