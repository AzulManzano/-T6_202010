package controller;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import model.data_structures.Comparendo;
import model.data_structures.Nodo;
import model.data_structures.Queue;
import model.logic.Modelo;
import view.View;

public class Controller 
{
	// Solucion de carga de datos publicada al curso Estructuras de Datos 2020-10

	/* Instancia del Modelo*/
	private Modelo modelo;

	/* Instancia de la Vista*/
	private View view;

	/**
	 * Crear la vista y el modelo del proyecto
	 * @param capacidad tamaNo inicial del arreglo
	 */
	public Controller ()
	{
		view = new View();
		modelo = new Modelo();
	}

	public void run() throws ParseException 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;


		view.bienvenida();

		while( !fin ){
			view.printMenu();

			int option = lector.nextInt();
			if(option == 1)
			{
				modelo.cargarDatos();
			}
			switch(option)
			{
			case 1:	
				view.printMessage("");
				view.printMessage("El total de comparendos es de: "+ modelo.darTotalComparendos());
				view.printMessage("");
				view.printMessage("-La informacion del comparendo con menor id es");
				view.printMessage("  "+modelo.darMinimo().darDatos());
				view.printMessage("-La informacion del comparendo con mayor id es");
				view.printMessage("  "+modelo.darMaximo().darDatos());
				view.printMessage("");
				view.printMessage("");
				break;

			case 2:
				view.printMessage("");
				view.printMessage("Ingrese el id del comparendo que quiere consultar:");
				int idComparendo = lector.nextInt();
				Comparendo busacado = modelo.requerimiento2(idComparendo);
				if(busacado == null)
					view.printMessage("No hay comparendo con ese identificador");
				else
				{
					view.printMessage("-La informacion del comparendo con el identificador "+ idComparendo+ " es:");
					view.printMessage(busacado.darDatos());	
				}
				view.printMessage("");
				view.printMessage("");
				break;

			case 3:
				view.printMessage("");
				view.printMessage("Ingrese el id que servira como limite inferior:");
				int inf = lector.nextInt();
				view.printMessage("");
				view.printMessage("Ingrese el id que servira como limite superior:");
				int sup = lector.nextInt();
				Queue<Comparendo> cola = modelo.requerimiento3(inf, sup);
				int tam = cola.getSize();
				if(tam == 0)
					view.printMessage("No hay comparendo entre los identificadores dados");
				else
				{
					view.printMessage("-Se pudieron encontrar "+ tam + " comparendos en los limites dados, y la informacion es la siguiente");
					for(int i = 0; i<tam;i++)
					{
						view.printMessage("  "+cola.dequeue().darDatos());
					}
				}
				view.printMessage("");
				view.printMessage("");
				break;

			case 4:
				DecimalFormat df = new DecimalFormat("#.00");
				view.printMessage("");
				view.printMessage("Despues de realizar la carga de los comparendos se puede realizar el siguiente analisis");
				view.printMessage("");
				view.printMessage("-----------------------------------------------------------------------------------------|");
				view.printMessage("|Total nodos en el árbol Red-Black                   |            "+modelo.darTotalComparendos());
				view.printMessage("|----------------------------------------------------|-----------------------------------|");
				view.printMessage("|Altura (real) del árbol Red-Black                   |              "+modelo.darAltura());
				view.printMessage("|----------------------------------------------------|-----------------------------------|");
				view.printMessage("|Altura promedio de las hojas del árbol Red-Black    |            "+df.format(modelo.promediHojas()));
				view.printMessage("-----------------------------------------------------------------------------------------|");
				view.printMessage("");
				view.printMessage("");
				break;

			case 5:			
				view.printMessage("--------- \n Hasta pronto !! \n---------"); 
				lector.close();
				fin = true;
				break;

			default: 
				view.printMessage("--------- \n Opcion Invalida !! \n---------");
				break;
			}
		}

	}	
}
