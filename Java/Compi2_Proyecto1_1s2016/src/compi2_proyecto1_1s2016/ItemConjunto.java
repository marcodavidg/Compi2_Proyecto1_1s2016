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
public class ItemConjunto {

    NoTerminal nombreIzq;
    ArrayList<Object> hijos;
    int puntero;
    ArrayList<Terminal> simbolos;
    ArrayList<nodoAST> acciones;

    public ItemConjunto(String simboloIzq, int puntero) {
        nombreIzq = (NoTerminal) Compi2_Proyecto1_1s2016.getObject(simboloIzq);
        this.puntero = puntero;
        hijos = new ArrayList<>();
        simbolos = new ArrayList<>();
        acciones = new ArrayList<>();
    }

    public void addHijo(Object hijo, nodoAST accion) {
        this.hijos.add(hijo);
        this.acciones.add(accion);

    }

    public void addSimbolo(Terminal simbolo) {
        this.simbolos.add(simbolo);
    }
}
