package NewVersion;

public class Generacion {
    int num = 4; //Usamos un numero multiplo de 4 (4, 8, 12, 16, 20, ...) para la reproducccion cada pareja tiene 4 hijos por eso es multiplo de 4
    Individuo[] poblacion = new Individuo[num];
    
    public Generacion(String[] entrada){//la cadena de string se convierte en una matriz de caracteres para poder crear los individuos
        char[][] x = new char[entrada.length][];
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < entrada.length; j++) {
                x[j]=entrada[j].toCharArray();
            }
            poblacion[i] = new Individuo(x);//por cada i se crea un nuevo individuo
        }
    }
    
    public Generacion(int num, char[][] entrada) {
        this.num = num;
        poblacion = new Individuo[num];

        for (int i = 0; i < num; i++) {
            poblacion[i] = new Individuo(entrada);
        }
    }
    // Cruza y aqui se valida si cambio la secuencia
    public void realizarCruza(Generacion otraGeneracion) {
        for (int i = 0; i < num; i++) {
            Individuo padre1 = poblacion[i];
            Individuo padre2 = otraGeneracion.poblacion[i];
            Individuo descendiente = padre1.cutAndSpliceCrossover(padre2);
            
            String cambios = identificarCambios(padre1.secuencias, descendiente.secuencias);
            
            if (!cambios.isEmpty()) {
               //System.out.println("En el individuo " + i + " la secuencia ha cambiado después de la cruza:");
               System.out.println(cambios);
            } else {
               //System.out.println("En el individuo " + i + " la secuencia no ha cambiado después de la cruza.");
            }
            
            poblacion[i] = descendiente;
        }
    }
    
public String identificarCambios(char[][] secuenciaOriginal, char[][] secuenciaNueva) {
    // Comparar las secuencias y determinar qué partes han cambiado
    // Devolver una cadena que muestra los cambios
    StringBuilder cambios = new StringBuilder();

    int filas = secuenciaOriginal.length;
    int columnas = secuenciaOriginal[0].length;  // Suponiendo que todas las filas tienen la misma longitud

    if (filas != secuenciaNueva.length || columnas != secuenciaNueva[0].length) {
        cambios.append("");
    } else {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (secuenciaOriginal[i][j] != secuenciaNueva[i][j]) {
                    cambios.append("Cambio en la posición [" + i + "][" + j + "]: ");
                    cambios.append(secuenciaOriginal[i][j] + " -> " + secuenciaNueva[i][j]);
                    cambios.append("\n");
                }
            }
        }
    }

    return cambios.toString();
}

    public void Ordenar(){//ordeno la poblacion dependiendo su calificacion
        for (int i = 0; i < num; i++) {
            int x = i;
            for (int j = i-1; j >= 0; j--) {
                if(poblacion[x].Calificar()>poblacion[j].Calificar()){
                    Individuo temp = poblacion[x];
                    poblacion[x] = poblacion[j];
                    poblacion[j] = temp;
                    x = j;
                }
            }
        }
    }
    
    private void DesordenarParejas(){
        for (int i = 0; i < num/2; i++) {
            int x = i;
            for (int j = i-1; j >= 0; j--) {
                if(Math.random() < 0.5){
                    Individuo temp = poblacion[x];
                    poblacion[x] = poblacion[j];
                    poblacion[j] = temp;
                    x = j;
                }
            }
        }
    }
    
    public int Letras(int i){//cuenta las letras, se dividen las secuencias de los individuos en la misma letra, ignorar gaps
        int cont = 0;
        for (char j : poblacion[0].secuencias[i]) {
            if(Character.isLetter(j))
                cont++;
        }
        return cont;
    }
    
    public Individuo hijo(int izq, int medio, int der){//el hijo hereda 3 partes de los padres
        int letras = 0, n = 0;
        String[] secHijo  = new String[poblacion[0].secuencias.length];
        boolean x;
        //Con sus letras dividimos en tres partes la secuencia
        int[][] particiones = new int[2][poblacion[0].secuencias.length]; // El numero 2 es del numero de particiones que tiene cada secuencia
        for (int i = 0; i < poblacion[0].secuencias.length; i++) {
            particiones[0][i]=(int)(Letras(i)/3);
            particiones[1][i]=(int)(2*Letras(i)/3);
        }
        
        for (int i = 0; i < 3; i++) {
        //Es de la primera parte
            letras=0;
            x=true;
            String secuencia="";
            for (int j = 0; j < poblacion[izq].secuencias[i].length; j++) {
                if(x){
                    char letra = poblacion[izq].secuencias[i][j];
                    secuencia += letra;
                    if(Character.isLetter(letra)){
                        letras++;
                    }
                    if(letras==particiones[0][i]){//en el momento en que encuentra la particion deja de añadir
                        x=false;
                    }
                }
            }
            //Empieza la segunda
            boolean y=false;
            x=true;
            letras=0;
            for (int j = 0; j < poblacion[medio].secuencias[i].length; j++) {
                char letra=poblacion[medio].secuencias[i][j];
                if(Character.isLetter(letra)){
                    letras++;
                }
                if(x && y){
                    secuencia+=letra;
                    if(letras==particiones[1][i]){
                        x=false;
                    }
                }
                if(letras==particiones[0][i]){
                    y=true;
                }
            }
            //Empieza la tercera
            y=false;
            letras=0;
            for (int j = 0; j < poblacion[der].secuencias[i].length; j++) {
                char letra=poblacion[der].secuencias[i][j];
                if(Character.isLetter(letra)){
                    letras++;
                }
                if(y){
                    secuencia+=letra;
                }
                if(letras==particiones[1][i]){
                    y=true;
                }
            }
            secHijo[n]=secuencia;
            n++;
        }
        //la secuencia se hace una matriz de caracteres porque crea al nuevo individuo
        char[][] y = new char[secHijo.length][];
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < secHijo.length; j++) {
                y[j] = secHijo[j].toCharArray();
            }
        }
        return new Individuo(y);
    }
    
    public void Reproducir(){//Primero los ordeno para reproducirlos
        Ordenar();
        DesordenarParejas();//para que sea aleatoria
        Individuo[] hijos = new Individuo[num];
        int n = 0;
        for (int i = 0; i < num/2; i+=2) {
            //padre, padre, madre
            hijos[n] = hijo(i, i, i+1);
            n++;
            hijos[n] = hijo(i, i+1, i);
            n++;
            hijos[n] = hijo(i, i+1, i+1);
            n++;
            hijos[n] = hijo(i+1, i, i);
            n++;
        }
        poblacion = hijos;//La nueva poblacion es de los hijos
    }
}