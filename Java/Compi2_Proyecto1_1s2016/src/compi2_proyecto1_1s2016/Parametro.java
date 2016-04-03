/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compi2_proyecto1_1s2016;

/**
 *
 * @author Mac
 */
public class Parametro {

    public String nombre;
    public int tipoDato;
    public Object valor;
    
    public Parametro(String nombre, int tipoDato) {
        this.nombre = nombre;
        this.tipoDato = tipoDato;
        this.valor = null;
    }
}
