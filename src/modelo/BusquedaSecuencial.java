package modelo;

import java.awt.desktop.ScreenSleepEvent; //Importo la libreria para realizar una pausa

public class BusquedaSecuencial { //Creo la clase
    public static int busquedaSecuencialBinaria(int [] array, int objetivo){ // Créo la función que me devuelve el entero, le envio por parametro en array y el numero buscado
        // Variables para guardar el tiempo de inicio y el tiempo de búsqueda
        double tiempoInicial, tiempoFinal, conversion, segundos;

        tiempoInicial = System.nanoTime(); // Guardo el tiempo en que inicia busqueda , lo hago en el momento justo previo a la busqueda

        int low=0; //Inicio una variable entera y le asigno 0
        int high=array.length -1; //Inicio una variable entera y le asigno la ultima parte del array (resto 1 ya que cuenta el valor de la posicion 0)

        while (low <=high) { // Comparo y repito el siguien código mientras el valor en low sea menor o igual que high

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            int mid = (low + high) / 2; //Inicio una variable entera y le asigno la posicion de la mitad entre low y high
            if (array[mid] == objetivo){ // Comparo el valor de esa posicion en el array con el valor buscado
                //Cálculo del tiempo de la busqueda
                tiempoFinal = System.nanoTime()-tiempoInicial; //Si el valor es el buscado, guardo el tiempo actual menos el inicial en la variable TiempoFinal
                System.out.print("El programa secuencial, demoró: "+ tiempoFinal +" nanoSegundos\n\n"); // Imprimo el tiempo en nanosegundos

                conversion = Math.pow(10,9); //Guarda en la variable conversion la base 10, elevado a 9
                segundos = tiempoFinal/conversion; //Guardo en la variable segundos la conversion de el tiempo final a segundos
                System.out.print("El programa, demoró: "+ segundos +" Segundos\n\n"); //Imprimo el tiempo demorado de busqueda en segundos

                return mid; // devuelvo la posicion donde se encontró
            }
            else if(array[mid] < objetivo) { //Si no lo encontró, comparo el valor de esa posicion, con el buscado,Si el valor buscado es mas grande va a realizar lo siguiente
                low = mid + 1; // Guardo en la variable low la posicion en la que me encuentro y le sumo 1
            } else { //De lo contrario, si el valor es mas chico que la posicion que me encuentro
                high = mid - 1; // Guardo en la variable high la posicion en la que me encuentro y le resto 1

            }
        }
        //Si el valor no se encuentra, calculo el tiempo final cuando termina la busqueda total
        tiempoFinal = System.nanoTime()-tiempoInicial; //Si el valor es el buscado, guardo el tiempo actual menos el inicial en la variable TiempoFinal
        System.out.print("El programa secuencial, demoró: "+ tiempoFinal +" nanoSegundos\n\n"); // Imprimo el tiempo en nanosegundos

        conversion = Math.pow(10,9); //Guarda en la variable conversion la base 10, elevado a 9
        segundos = tiempoFinal/conversion; //Guardo en la variable segundos la conversion de el tiempo final a segundos
        System.out.print("El programa, demoró: "+ segundos +" Segundos\n\n"); //Imprimo el tiempo demorado de busqueda en segundos

        return -1; //Devuelvo el valor Standard , no se encontró
    }
}
