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
public class TokenCadenaEntrada {
    String nombre;
    int line;
    int row;
    String tipo;
    String valor;

    public TokenCadenaEntrada(String nombre, int line, int row, String tipo, String valor) {
        this.nombre = nombre;
        this.line = line;
        this.row = row;
        this.tipo = tipo;
        this.valor = valor;
    }
    
    public TokenCadenaEntrada(String nombre, int line, int row) {
        this.nombre = nombre;
        this.line = line;
        this.row = row;
        tipo = null;
        valor = null;
    }
    
    public boolean hasValor(){
        return !(tipo == null || valor == null);
    }
}
