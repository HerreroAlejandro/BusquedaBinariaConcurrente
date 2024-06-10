package modelo;

//librerias
import java.util.List;   // Importo la libreria para el uso de Listas
import java.util.ArrayList; // Importo la libreria para el uso de Listas de Array
import java.util.concurrent.CountDownLatch;  // Importo la libreria para el uso de la funcionalidad de esperarse entre hilos

public class BusquedaConcurrente { // Creo la clase
    private static volatile boolean objetivoEncontrado = false; // Variable compartida para indicar si se ha encontrado el objetivo (numero en busqueda)

    public static int concurrentBinarySearch(int[] array, int target, int numThreads) { //Creo la funcion que me devuelve un entero,le envio por parametro el array,el numero buscado y la cantidad de hilos

        // Variables para guardar el tiempo de inicio y el tiempo de búsqueda de los hilos
        double conversion, segundos, tiempoInicial, tiempoFinal;

        // Crear listas para hilos y tareas
        List<Thread> threads = new ArrayList<>(); // Lista Hilos
        List<SearchTask> tasks = new ArrayList<>(); // Lista Tareas
        int result = -1; //Resultado Standard si el objetivo no se encuentra

        // Crear un CountDownLatch para esperar a que todos los hilos completen su búsqueda
        CountDownLatch latch = new CountDownLatch(numThreads); // Le envio la cantidad de hilos para que pueda descontarlos

        // Calcular el tamaño del segmento
        int segmentLength = array.length / numThreads; // Cada segmento es 1 hilo

        // Crear hilos y tareas con segmentos calculados
        for (int i = 0; i < numThreads; i++) {
            int start = i * segmentLength; //Le asigno el primer valor al segmento
            int end = (i == numThreads - 1) ? array.length - 1 : (start + segmentLength - 1); //le envio el ultimo valor al segmento

            // Crear y ejecutar el hilo con el segmento calculado
            SearchTask task = new SearchTask(array, target, start, end, latch, objetivoEncontrado); //Instancio la clase SearchTask, guardando los datos de busqueda de cada segmento y la sincronizacion del hilo
            Thread thread = new Thread(task); // Creo hilo
            threads.add(thread); // Agrego en la lista de hilos
            tasks.add(task);   // Agrego en la lista de tareas
        }

        tiempoInicial = System.nanoTime(); // Guardo el tiempo en que inicia la primer busqueda , lo hago en el momento justo previo a la primer busqueda del primer hilo

        // Iniciar los hilos después de calcular los segmentos
        for (Thread thread : threads) { // Recorro todos los hilos

            thread.start(); // Inicio la busqueda del hilo
        }

        // Esperar a que todos los hilos completen su búsqueda
        try {
            latch.await();  // bloqueo la tarea de el hilo actual hasta que terminen todos los hilos
        } catch (InterruptedException e) { // Esta excepcion se lanza si otro hilo interrumpe un hilo que este esperando
            e.printStackTrace();
        }

        // Buscar el tiempo en el que el primer hilo encontró el objetivo
        for (SearchTask task : tasks) { //Recorro la lista de tareas
            int pos = task.getResult(); //Guarda en la variable pos el resultado de la busqueda (-1 valor standard, otro valor si se encontró)
            if (pos != -1) { // Chequéo si la variable pos es distinta a -1
                result = pos; //Si encuentra el valor, lo guarda en resultado
                break; //Utilizo break para salir del bucle
            }
        }

        // Calcular el tiempo transcurrido desde el inicio de la búsqueda del primer hilo hasta que termina la búsqueda
        tiempoFinal = System.nanoTime() - tiempoInicial; //Calculo el tiempo ahora y le resto cuando el tiempo cuando empezé la busqueda, eso lo guardo en la variable Tiempo final
        System.out.println("Tiempo transcurrido por busqueda concurrente: " + tiempoFinal + " nanosegundos"); //Imprimo en pantalla el tiempo que tardó

        //Convertir nanosegundos en segundos
        conversion = Math.pow(10, 9); //Guarda en la variable conversion la base 10, elevado a 9
        segundos = tiempoFinal / conversion; //Guardo en la variable segundos la conversion de el tiempo final a segundos
        System.out.print("El programa, demoró: " + segundos + " Segundos\n\n"); // Imprimo el tiempo en segundos

        return result; // Devuelvo el resultado
    }

    private static class SearchTask implements Runnable { // Creo la clase que representa la taréa de búsqueda de cada hilo, extiende de la clase Hilo
        private int[] array; //El arreglo donde se hará la búsqueda
        private int target; //El valor que se busca
        private int start; // El índice inicial del segmento de cada hilo
        private int end;  // El índice final del segmento de cada hilo
        private int result = -1; // La posicion del arreglo donde encuentro el valor, Standard -1 = "No se encontró"
        private CountDownLatch latch; // Una instancia de la clase CountDownLatch para sincronizar el inicio de hilos
        private volatile boolean objetivoEncontrado; //Indica si el valor fue o no encontrado


        public SearchTask(int[] array, int target, int start, int end, CountDownLatch latch, boolean objetivoEncontrado) { // Créo el constructor, este inicia las variables de instancia con los valores proporcionados cuando se créa la instancia SearchTask
            this.array = array;   //Guardo en el atributo array, el array que envio en el constructor
            this.target = target; //Guardo en el atributo target, el target que envio en el constructor
            this.start = start;   //Guardo en el atributo start, el start que envio en el constructor
            this.end = end;       //Guardo en el atributo end, el end que envio en el constructor
            this.latch = latch;   //Guardo en el atributo latch, el latch que envio en el constructor
            this.objetivoEncontrado = objetivoEncontrado; // Guardo en el atributo objetivoEncontrado, el atributo del constructor
        }

        @Override
        public void run() { //Funcion que ejecutan los hilos
            // Realizar la búsqueda binaria
            int low = start; //Guardo en la variable low el primer valor de la busqueda del segmento
            int high = end; //Guardo en la variable high el ultimo valor de la busqueda del segmento

            while (low <= high && !objetivoEncontrado) { // Mientras low sea menor o igual a high , esto se va a repetir la cantidad de veces necesarias hasta que la busqueda recorra el array o lo encuentre antes

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                int mid = (low + high) / 2; //Posiciono la variable mid a la mitad de las variables low y high
                if (array[mid] == target) { //Comparo esa posicion del array con el numero buscado
                    result = mid; // Si lo encuentra, guardo esa posicion en el resultado
                    objetivoEncontrado = true; // Guardo que el numero se encontró
                    break; //Realizo un corte en la funcion para finalizarla ya que fue encontrado
                } else if (array[mid] < target) { //Si no se encontró, comparo si el valor en la mitad de low y high, es mas grande al valor buscado
                    low = mid + 1; //Si es mas grande, posiciono low en la posicion siguiente al del medio
                } else { // Si el valor es mas chico al buscado, realizo otra accion
                    high = mid - 1; // //si es mas chico,posiciono high en la posicion anterior al medio (el medio de low y high)
                }
            }
            latch.countDown(); // Contar que este hilo ha completado su búsqueda, descuento un valor de latch, por cada hilo que finaliza, cuando esta en 0 significa que terminó
        }

        public int getResult() { //Creo un get para obtener el resultado

            return result; // Devuelvo la variable result
        }
    }}