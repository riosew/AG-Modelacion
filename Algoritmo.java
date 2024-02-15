package NewVersion;

public class Algoritmo {

    public static void main(String[] args) throws InterruptedException {
        long inicio = System.currentTimeMillis();
        Archivos a = new Archivos();
        int totalEvaluaciones = 0;

        String s1 = a.leertxt("C:\\Users\\abere\\OneDrive\\Documentos\\Fasta\\monkeyPoxVirus-.fasta");
        String s2 = a.leertxt("C:\\Users\\abere\\OneDrive\\Documentos\\Fasta\\Rhabdoviridae.fasta");
        String s3 = a.leertxt("C:\\Users\\abere\\OneDrive\\Documentos\\Fasta\\coronaVirus.fasta");

        String[] secuencias = { s1, s2, s3 };
        Generacion obj = new Generacion(secuencias);
        obj.Ordenar();
        // Inicializar el mejor individuo con el primero de la población
        Individuo mejorIndividuo = obj.poblacion[0];
        
        for (int z = 1; z < 11; z++) {
            System.out.println("\u001B[32m╔═══════════════════╗\u001B[30m");
            System.out.println("\u001B[32m║         GENERACIÓN " + z + "         ║\u001B[30m");
            System.out.println("\u001B[32m╚═══════════════════╝\u001B[30m");
            
            for (Individuo x : obj.poblacion) {
                for (char[] y : x.secuencias) {
                    for (int i = 0; i < y.length; i++) {
                        System.out.print(y[i] + " ");
                    }
                    System.out.println("");
                }
                System.out.println(x.Calificar());
                System.out.println("\u001B[36mSe evaluó este individuo " + x.contEvaluacion + " veces\u001B[30m\n");

                // Actualizar el mejor individuo si es necesario
                if (x.Calificar() > mejorIndividuo.Calificar()) {
                    mejorIndividuo = x;
                }
                totalEvaluaciones += x.contEvaluacion;
            }
        
        
            System.out.println("╔═════════════════════════╗");
            System.out.println("    Mejor Individuo en la Generación " + z + ":");
            System.out.println("╚═════════════════════════╝");
            for (char[] y : mejorIndividuo.secuencias) {
                for (int i = 0; i < y.length; i++) {
                    System.out.print(y[i] + " ");
                }
                System.out.println("");
            }
            
            System.out.println(mejorIndividuo.Calificar());
            System.out.println("\u001B[36mSe evaluó este individuo " + mejorIndividuo.contEvaluacion + " veces\u001B[30m\n");
            
            obj.Reproducir();
            obj.Ordenar();
            totalEvaluaciones += mejorIndividuo.contEvaluacion;
        }
        long fin = System.currentTimeMillis();
        double tiempo = ((double) (fin - inicio)) / 1000;
        System.out.println("\u001B[32m╔═══════════════════╗╔═══════════════════╗\u001B[30m");
        System.out.println("\u001B[32m║    Total de evaluaciones      ║║ Tiempo que tarda el programa  ║\u001B[30m");
        System.out.println("\u001B[32m║             " + totalEvaluaciones + "             ║║               " + tiempo + "           ║\u001B[30m");
        System.out.println("\u001B[32m╚═══════════════════╝╚═══════════════════╝\u001B[30m");
 
        for (int z = 1; z < 11; z++) {
        obj.realizarCruza(obj); // Llamando a realizarCruza con otraGeneracion igual a la misma generación
        for (Individuo x : obj.poblacion) {
            String cambios = obj.identificarCambios(mejorIndividuo.secuencias, x.secuencias);
            if (!cambios.isEmpty()) {

                }
            }
        }
    }
}
