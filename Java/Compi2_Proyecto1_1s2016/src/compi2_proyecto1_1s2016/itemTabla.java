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
public class itemTabla {
    int numeroEstado;
    int tipo; //NoTerminal = 0; Terminal = 1; Reduccion = 2;
    Object simboloConElQueSeDesplaza;
    int DestinoReduccionDesplazamiento;
    nodoAST accion;
    int numeroItem;

    public itemTabla(int numeroEstado, int tipo, Object simboloConElQueSeDesplaza, int DestinoReduccionDesplazamiento, nodoAST accion, int numeroItem) {
        this.numeroItem = numeroItem;
        this.numeroEstado = numeroEstado;
        this.accion = accion;
        this.tipo = tipo;
        this.simboloConElQueSeDesplaza = simboloConElQueSeDesplaza;
        this.DestinoReduccionDesplazamiento = DestinoReduccionDesplazamiento;
    }
}
