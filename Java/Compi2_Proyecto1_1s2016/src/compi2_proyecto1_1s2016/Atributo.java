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
public class Atributo {

    public Object valor;
    public int tipo; // Los mismos, 1 = int, 2 = double...
    public Object TerminalNoTerminal;

    public Atributo(Object valor, int tipo, Object TNT) {
        this.valor = valor;
        this.tipo = tipo;
        this.TerminalNoTerminal = TNT;
    }

    public Atributo(Object valor, int tipo, Object TNT, String valorT, String tipoToken) {
        if (tipoToken != null) {
            switch (tipoToken.trim()) {
                case "int":
                    this.valor = Integer.parseInt(valorT);
                    break;
                case "float":
                    this.valor = Double.parseDouble(valorT);
                    break;
                case "double":
                    this.valor = Double.parseDouble(valorT);
                    break;
                case "string":
                    this.valor = valorT;
                    break;
                case "char":
                    this.valor = valorT.charAt(0);
                    break;
                case "bool":
                    this.valor = Boolean.parseBoolean(valorT);
                    break;
            }
        }
        this.tipo = tipo;
        this.TerminalNoTerminal = TNT;
    }
}
