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
public class Terminal {
    int tipoReturn;
    Object valor;
    String nombre;

    public Terminal(String nombre) {
        this.nombre = nombre;
        this.tipoReturn = 0;
        this.valor = null;
    }

    public void setTipoReturn(int tipoReturn) {
        this.tipoReturn = tipoReturn;
    }
    
    
    
}
