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
public class Precedencia {

    int ponderacion;
    int asociatividad; //0 = izq; 1 = der;
    ArrayList<Terminal> TerminalesAfectados;

    public Precedencia(int ponderacion, int asociatividad, ArrayList<String> hijos) {
        this.ponderacion = ponderacion;
        this.asociatividad = asociatividad;
        this.TerminalesAfectados = new ArrayList<>();
        for (String hijo : hijos) {
            Object o = Compi2_Proyecto1_1s2016.getObject(hijo);
            if (o == null) {
                nodoAST.addError("Valor invalido en declaracion de precedencia.");
            } else {
                try {
                    TerminalesAfectados.add((Terminal) o);
                } catch (Exception e) {
                    nodoAST.addError("No se le puede aplicar precedencia a un No Terminal.");
                }
            }
        }
        Compi2_Proyecto1_1s2016.Precedencias.add(this);
    }

    public int getValor(String nombre){
        for (int i = 0; i < TerminalesAfectados.size(); i++) {
            if (TerminalesAfectados.get(i).nombre.equalsIgnoreCase(nombre)) {
                String x = "" + ponderacion + "00000" + i;
                return Integer.parseInt(x);
            }
        }
        return -1;
    }
    
}
