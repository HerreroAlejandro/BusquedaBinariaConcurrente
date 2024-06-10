package test;

import modelo.BusquedaConcurrente; // Importo la clase BusquedaConcurrente
import modelo.BusquedaSecuencial;  // Importo la clase BusquedaSecuencial

import java.util.Arrays;
import java.util.Scanner; //  Importo la libreria para ingresar datos por pantalla

public class Main { public static void main(String[] args) {
/*

        Scanner scanner = new Scanner(System.in); //Creo un objeto Scanner que lee la entrada de dato que voy a ingresar
        System.out.print("Ingrese el tamaño del arreglo (Tam): "); // Imprimo el mensaje que me pide el ingreso del tam
        int Tam = scanner.nextInt(); //El scanner espera un entero, despues de ingresarlo la funcion nextInt lo lée, y lo guardo en la variable tam



        int[] array = generarArray(Tam); // Generar y ordenar el arreglo

        System.out.print("Ingrese el valor a buscar: "); // Imprimo un mensaje que me pide el valor buscado
        int target = scanner.nextInt(); //El scanner espera un entero, despues de ingresarlo la funcion nextInt lo lée, y lo guardo en la variable target

        System.out.print("Ingrese el número de hilos: "); //Imprimo un mensaje que me pide el numero de hilos para la busqueda concurrente
        int numThreads = scanner.nextInt(); //El scanner espera un entero, despues de ingresarlo la funcion nextInt lo lée, y lo guardo en la variable numThreads
*/
        int Tam = 1000000;
        int[] array = generarArray(Tam);
        int target = 116845;
        int numThreads =100;

        // Búsqueda secuencial
        Arrays.sort(array); // Ordeno el array
        int resultSequential = BusquedaSecuencial.busquedaSecuencialBinaria(array, target); //Realizo la busqueda binaria de forma secuencial, le envio por parametro un array y el numero buscado, guardo el resultado en una variable
        System.out.println("Resultado secuencial: " + (resultSequential == -1 ? "Elemento no encontrado" : "Elemento encontrado en el índice " + resultSequential)); // Imprimo el resultado de la busqueda (-1 si no encuentra, si encuentra envia la posicion del array)

        // Búsqueda concurrente
        Arrays.sort(array); // Ordeno el Array
        int resultConcurrent = BusquedaConcurrente.concurrentBinarySearch(array, target, numThreads); //Realizo la busqueda binaria de forma concurrente, le envio por parametro el array, el numero buscado y la cantidad de hilos, y guarda el resultado en una variable
        System.out.println("Resultado concurrente: " + (resultConcurrent == -1 ? "Elemento no encontrado" : "Elemento encontrado en el índice " + resultConcurrent)); // Imprimo el resultado de la busqueda (-1 si no encuentra, si encuentra envia la posicion del array)
}
        private static int[] generarArray(int Tam) { //Genero el array
                int[] array = new int[Tam]; // Creo un array del tamaño ingresado por pantalla
                for (int i = 0; i < Tam; i++) { // Recorro todo el array
                        array[i] = i + 1; // Llena el arreglo con números
                }
                return array; //Devuelvo el array
        }
}
