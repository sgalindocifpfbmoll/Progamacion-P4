/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionvideoclub;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author chiqui
 */
public class GestionVideoclub {
    //
    private static ArrayList<Pelicula> peliculas=new ArrayList<Pelicula>();
    private static BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
    
    public static void main(String[] args){
        boolean continuar=true;
        System.out.println("BIENVENIDO AL VIDEOCLUB.");
        while(continuar){
            try{
                System.out.println("\n======= MENÚ =======");
                System.out.println("1. Añadir");
                System.out.println("2. Buscar");
                System.out.println("3. Listar");
                System.out.println("4. Reservar");
                System.out.println("0. Salir");
                System.out.println("\nEscoge tu opción");
                int opcion=Integer.parseInt(teclado.readLine());
                switch(opcion){
                    case 0:
                        continuar=false;
                        break;
                    case 1:
                        Pelicula.añadirPelicula();
                        break;
                    case 2:
                        Pelicula.buscarPelicula(peliculas);
                        break;
                    case 3:
                        Pelicula.listarPeliculas(peliculas);
                        break;
                    case 4:
                        Pelicula.reservarPelicula(peliculas);
                        break;
                    default:
                        throw new Exception();
                }
            }catch(Pelicula.Error e){
                System.out.println(e.getMessage());
            }catch(Exception e){
                System.out.println("Introduce un caracter válido.");
            }
        }
    }
    
    public static ArrayList<Pelicula> getPeliculas(){
        return peliculas;
    }
    
    public static void añadirPelicula(Pelicula pelicula){
        peliculas.add(pelicula);
    }
    
}
