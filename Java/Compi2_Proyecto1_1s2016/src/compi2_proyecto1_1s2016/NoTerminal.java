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
public class NoTerminal {

    ArrayList<ArrayList<Object>> derivaciones;
    int tipoReturn;
    Object valor;
    String nombre;
    ArrayList<ArrayList<nodoAST>> acciones;

    public NoTerminal(String nombre) {
        this.nombre = nombre;
        this.tipoReturn = -98;
        this.valor = null;
        this.derivaciones = null;
    }

    public void addDerivacion() {
        if (derivaciones == null) {
            derivaciones = new ArrayList<>();
            acciones = new ArrayList<>();
            derivaciones.add(new ArrayList<>());
            acciones.add(new ArrayList<nodoAST>());
        } else {
            derivaciones.add(new ArrayList<>());
            acciones.add(new ArrayList<nodoAST>());
        }
    }

    public void addHijo(Object hijo, nodoAST accion) {
        ArrayList<Object> hijos = derivaciones.get(derivaciones.size() - 1);
        ArrayList<nodoAST> accionesTemp = acciones.get(derivaciones.size() - 1);
        hijos.add(hijo);
        accionesTemp.add(accion);
    }

    boolean isNonTerminal(Object testSubject) {
        try {
            NoTerminal test = (NoTerminal) testSubject;
            int a = test.tipoReturn;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*
    
    
    
     GET's Y SET's
    
    
    
    
     */
    public String getNombre() {
        return nombre;
    }

    public void setTipoReturn(int tipoReturn) {
        this.tipoReturn = tipoReturn;
    }

    public int getTipoReturn() {
        return tipoReturn;
    }
}
