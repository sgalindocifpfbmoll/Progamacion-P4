/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionvideoclub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 *
 * @author chiqui
 */
public class Pelicula{
    //Variables estáticas
    private static int id=0;
    private static BufferedReader teclado = new BufferedReader
        (new InputStreamReader(System.in));
    //Variables no estáticas 
    private int duracion, año, copiasTotal, copiasReserva;
    private String titulo, director, genero;
    private boolean disponible;
    
    //CONSTRUCTOR
    public Pelicula(int id, String titulo, String director, int duracion, 
            String genero, int año, boolean disponible, int copiasTotal, 
            int copiasReserva){
        setId(id);
        setTitulo(titulo);
        setDirector(director);
        setDuracion(duracion);
        setGenero(genero);
        setAño(año);
        setDisponible(disponible);
        setCopiasTotal(copiasTotal);
        setCopiasReserva(copiasReserva);
    }
    
    public static void listarPeliculas(ArrayList<Pelicula> peliculas) throws Error{
        if(peliculas.isEmpty()){
            throw new Error("no hay películas disponibles a mostrar.");
        }else{
            for(Pelicula pelicula : peliculas){
                System.out.println(pelicula);
            }
        }
    }
    
    public static void añadirPelicula() throws IOException{
        try{
            //Comprobamos si id es mayor a 3000
            if(id>3000){
                throw new Error("el límite máximo de peliculas es de 3000.");
            }
            //Comenzamos a insertar la película
            else{
                String inicio="¿Cuál es ";
                //Pedimos todos los datos de Pelicula
                System.out.println(inicio+"el título de la película?");
                String titulo=teclado.readLine();
                //Comprobamos la película
                if(!comprobarPelicula(titulo)){
                    throw new Error("la pelicula ya está insertada.");
                }
                //
                else{
                    System.out.println(inicio+"el director?");
                    String director=teclado.readLine();
                    System.out.println(inicio+"la duración?");
                    int duracion=Integer.parseInt(teclado.readLine());
                    System.out.println(inicio+"el género?");
                    String genero=teclado.readLine();
                    System.out.println(inicio+"el año?");
                    int año=Integer.parseInt(teclado.readLine());
                    System.out.println("¿Cúantas copias desear insertar?");
                    int copias=Integer.parseInt(teclado.readLine());
                    //Se añade a la lista de peliculas
                    GestionVideoclub.añadirPelicula(new Pelicula(id, titulo, director, duracion, 
                            genero, año, true, copias, 0));
                    System.out.println("La película "+titulo+" "
                            + "ha sido añadida con éxito.");
                    //Sumamos +1 el id
                    setId(getId()+1);
                }
            }
        }
        catch(Error e){
            System.out.println(e.getMessage());
        }
        pressEnter();
    }
    
    public static void buscarPelicula(ArrayList<Pelicula> peliculas) throws 
            IOException, NoSuchMethodException, IllegalAccessException, 
            IllegalArgumentException, InvocationTargetException{
        //Declaraciones locales
        String texto="";
        String[] metodos={"getId","getTitulo","getDirector","getDuracion",
            "getGenero","getAño","isDisponible"};
        String[] frases={"el id","el título","el director","la duración",
            "el género","el año","la disponiblilidad"};
        boolean encontrado=false;
        //
        System.out.println("¿Qué deseas buscar?");
        System.out.println("1. Por id\n2. Por título.\n3. Por director\n"
                + "4. Por duración\n5. Por género\n6. Por año\n"
                + "7. Por disponiblidad");
        int opcion=Integer.parseInt(teclado.readLine());
        //
        System.out.println("\nIntroduce "+frases[opcion-1]+":");
        texto=teclado.readLine();
        //
        try{
            for (Pelicula pelicula: peliculas){
                //Obtenemos el método según la opción
                    Method metodo=pelicula.getClass().getMethod(metodos[opcion-1]);
                    //Invocamos al método y obtenemos su valor en String. En este
                    //valor convertido se observa si contiene el texto.
                    if(String.valueOf(metodo.invoke(pelicula)).contains(texto)){
                        System.out.println(pelicula);
                        encontrado=true;
                    }
                }
            if(!encontrado){
                throw new Error("Ninguna película coincide con "
                        +frases[opcion-1]+" introducido.");
            }
        }catch(Error e){
            System.out.println(e.getMessage());
        }
        pressEnter();
    }
    
    public static void reservarPelicula(ArrayList<Pelicula> peliculas) throws IOException, Error{
        try{
            //Listamos las peliculas
            listarPeliculas(peliculas);
            //Si no lanza error, insertamos el id
            System.out.println("¿Qué película deseas reservar? (Escoge por ID)");
            int id=Integer.parseInt(teclado.readLine());
            //Buscamos la película
            for(Pelicula pelicula : peliculas){
                //Si los id coinciden
                    if(pelicula.getId()==id){
                        //Y la película está disponible
                        if(pelicula.isDisponible()){
                            //Aumentamos el número de reservas
                            pelicula.setCopiasReserva(pelicula.getCopiasReserva()+1);
                            System.out.println("\nSe ha alquilado la película.");
                            //Si copiasReserva=copiastotal
                            if(pelicula.getCopiasReserva()==pelicula.getCopiasTotal()){
                                //Se setea falso la disponibilidad
                                pelicula.setDisponible(false);
                                System.out.println("Ya no está disponible \""
                                        +pelicula.getTitulo()+"\" en el videoclub.");
                            }
                        }
                        //Lanzamos error copias.
                        else{
                            throw new Error(" no quedan copias de la película.");
                        }
                    }
                    //Lanzamos error no existe.
                    else{
                        throw new Error(" la película no existe.");
                    }
                }
        }catch(Error e){
                System.out.println(e.getMessage());
            }
        pressEnter();
    }
    
    private static boolean comprobarPelicula(String titulo){
        for (Pelicula pelicula : GestionVideoclub.getPeliculas()) {
            if (titulo.equals(pelicula.getTitulo())) {
                return false;
            }
        }
        return true;
    }
    
    private static void pressEnter() throws IOException{
        System.out.println("\nPulsa enter para volver al menú...");
        teclado.readLine();
    }
    
    //-----------------------------SETTERS--------------------------------------
    public static void setId(int id2) {
        id = id2;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public void setCopiasTotal(int copiasTotal) {
        this.copiasTotal = copiasTotal;
    }

    public void setCopiasReserva(int copiasReserva) {
        this.copiasReserva = copiasReserva;
    }

    public void setTitulo(String titulo) {
        String output = titulo.substring(0, 1).toUpperCase() + titulo.substring(1);
        this.titulo = output;
    }

    public void setDirector(String director) {
        String output = director.substring(0, 1).toUpperCase() + director.substring(1);
        this.director = output;
    }

    public void setGenero(String genero) {
        String output = genero.substring(0, 1).toUpperCase() + genero.substring(1);
        this.genero = output;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    
    //-----------------------------GETTERS--------------------------------------
    public static int getId() {
        return id;
    }

    public int getDuracion() {
        return duracion;
    }

    public int getAño() {
        return año;
    }

    public int getCopiasTotal() {
        return copiasTotal;
    }

    public int getCopiasReserva() {
        return copiasReserva;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDirector() {
        return director;
    }

    public String getGenero() {
        return genero;
    }

    public boolean isDisponible() {
        return disponible;
    }

    @Override
    public String toString() {
        return "\nID: "+this.getId()+"\nTítulo: "+this.getTitulo()+"\nDirector: "+this.getDirector()+
                "\nDuración: "+this.getDuracion()+"\nAño: "+this.getAño()+
                "\nGénero: "+this.getGenero()+"\nDisponibilidad: "
                +this.isDisponible()+"\nCopias totales: "+this.getCopiasTotal()
                +"\nCopias reservadas: "+this.getCopiasReserva();
    }
    
    public static class Error extends Exception { 
        public Error(String s){
            super("\nError: "+s);
        }
    }
}
