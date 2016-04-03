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
public class Simbolo {

    public String nombre;
    public int tipoDato;
    public int ambito;
    public int posicion;
    public int numeroDimensiones;
    public int numeroParametros;
    public Object valor;
    public ArrayList<Parametro> parametros;
    public Object[] valoresArray;
    public ArrayList<Integer> sizes;
    public nodoAST mainNodo;

    public Simbolo(String nombre, Object valor, int ambito, int tipoDato) {
        this.nombre = nombre.trim();
        this.valor = valor;
        this.ambito = ambito;
        this.tipoDato = tipoDato;
        this.parametros = null;
        this.sizes = null;
        this.valoresArray = null;
    }

    public void makeMetodo() {
        this.parametros = new ArrayList<>();
    }

    public void makeArray() {
        this.sizes = new ArrayList<>();
    }

    public boolean isMetodo() {
        return this.parametros != null;
    }

    public boolean isArreglo() {
        return this.sizes != null;
    }

    

    public void inicializarValores() {
        int size = 0;
        for (int i = 0; i < sizes.size(); i++) {
            if (i == 0) {
                size = sizes.get(i);
            } else {
                size = size * sizes.get(i);
            }
        }
        this.valoresArray = new Object[size];
    }

    public Object getObjectArreglo(int... indices) {
        System.out.println("ind:" + indices.length);
        int index = getIndex(indices);
        if (index == -1 || valoresArray.length <= index) {
            nodoAST n = new nodoAST(nombre, nombre, valor);
            n.addError("Index fuera del rango en el array " + this.nombre);
            return null;
        }
        return valoresArray[index];
    }

    public void setObjectArreglo(Object valor, int... indices) {
        int index = getIndex(indices);
        if (index != -1) {
            valoresArray[index] = valor;
        } else {
            nodoAST n = new nodoAST(nombre, nombre, valor);
            n.addError("Index fuera del rango en el array " + this.nombre);
        }
    }

    public int getIndex(int... indices) {
        if (indices.length == numeroDimensiones) {
            int index = 0, i, n;
            for (int p = 0; p < sizes.size(); p++) {
                if (indices[p] >= sizes.get(p)) {
                    return -1;
                }
                if (p == 0) {
                    index = indices[p];
                } else {
                    i = indices[p];
                    n = sizes.get(p);
                    index = index * n + i;
                }
            }
            return index;
        } else {
            return -1;
        }
    }

    public void addDimension(int size) {
        numeroDimensiones++;
        this.sizes.add(size);
    }

    public void addParametro(String nombre, int tipoDato) {
        numeroParametros++;
        Parametro temp = new Parametro(nombre, tipoDato);
        parametros.add(temp);
    }
}
