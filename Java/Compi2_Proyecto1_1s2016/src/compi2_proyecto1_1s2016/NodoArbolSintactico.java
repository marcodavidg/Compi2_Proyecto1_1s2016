/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compi2_proyecto1_1s2016;

import java.util.ArrayList;

/**
 *
 * @author Mac
 */
public class NodoArbolSintactico {
    String nombre;
    nodoAST accion;
    ArrayList<NodoArbolSintactico> hijos;
    int tipo; //0 = NoTerminal, 1 = Terminal
    
    public NodoArbolSintactico(String nombre, int tipo) {
        hijos = new ArrayList<>();
        this.nombre = nombre;
        accion = null;
        this.tipo = tipo;
    }
    
    public void addHijo(NodoArbolSintactico hijo){
        this.hijos.add(hijo);
    }
    
    public void mostrar(){
        Graphviz g = new Graphviz();
        g.graficarSintactico(this);
    }
    
    void addAccion(nodoAST accion){
        this.accion = accion;
    }
}
