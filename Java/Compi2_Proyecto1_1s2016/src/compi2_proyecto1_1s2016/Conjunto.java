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
public class Conjunto {

    ArrayList<ItemConjunto> items;
    int numeroConjunto;

    public Conjunto(int numero) {
        numeroConjunto = numero;
        items = new ArrayList<>();
    }

    public void addItem(ItemConjunto nuevo) {
        items.add(nuevo);
    }

    public void addOtrosHijos() {
//        System.out.println("thisSim:" + this.items.get(0).simbolos.get(0).nombre);
        int elQueToca = items.size() - 1;
        int limite = elQueToca + 1;
        ItemConjunto last;
        ArrayList<String> aux = Compi2_Proyecto1_1s2016.auxConjuntos;
        while (elQueToca < limite) {
//            System.out.println(elQueToca + "-limite is: " + limite);
            last = items.get(elQueToca);
            if (last.puntero != last.hijos.size()) {
//                System.out.println(">>" + last.hijos.get(last.puntero));
                if (Compi2_Proyecto1_1s2016.isNonTerminal(last.hijos.get(last.puntero))) {
                    String simbolosA = "";
                    if (last.puntero + 1 != last.hijos.size()) {
                        ArrayList<Terminal> auxTemp = Compi2_Proyecto1_1s2016.primero(last.hijos.get(last.puntero + 1));
                        for (Terminal simbolo : auxTemp) {
                            simbolosA += simbolo.nombre;
                        }
                    } else {
                        for (Terminal simbolo : last.simbolos) {
                            simbolosA += simbolo.nombre;
                        }
                    }
                    if (aux.contains(((NoTerminal) last.hijos.get(last.puntero)).nombre + simbolosA)) {
//                        System.out.println("NoAdd:" + ((NoTerminal) last.hijos.get(last.puntero)).nombre + simbolosA);

                    } else {
                        aux.add(((NoTerminal) last.hijos.get(last.puntero)).nombre + simbolosA);
//                        System.out.println("add:" + ((NoTerminal) last.hijos.get(last.puntero)).nombre + simbolosA);
                        NoTerminal target = (NoTerminal) last.hijos.get(last.puntero);
//                        System.out.println("target:" + target.nombre + ".size: " + target.derivaciones.size());
                        for (int i = 0; i < target.derivaciones.size(); i++) {
                            ItemConjunto otro = new ItemConjunto(target.nombre, 0);
                            for (int j = 0; j < target.derivaciones.get(i).size(); j++) {
                                otro.addHijo(target.derivaciones.get(i).get(j), target.acciones.get(i).get(j));
                            }
                            ArrayList<Terminal> simbolos;
                            if (last.puntero + 1 == last.hijos.size()) { //Se copian los simbolos
//                                System.out.println("No se usa primero:");
//                                System.out.println("last.puntero: " + last.puntero + ". last.hijos.size(): " + last.hijos.size());
//                                System.out.println(otro.nombreIzq.nombre);
//                                System.out.println(this.numeroConjunto);
                                for (int j = 0; j < last.simbolos.size(); j++) {
//                                    System.out.println("1se agrega simbolo: " + last.simbolos.get(j).nombre);
                                    otro.addSimbolo(last.simbolos.get(j));
                                }
                            } else { //Se usa el primero
//                                System.out.println("Se usa primero:");
//                                System.out.println("last.puntero: " + last.puntero + ". last.hijos.size(): " + last.hijos.size());
//                                System.out.println(otro.nombreIzq.nombre);
//                                System.out.println(this.numeroConjunto);
                                simbolos = Compi2_Proyecto1_1s2016.primero(last.hijos.get(last.puntero + 1));
                                for (int j = 0; j < simbolos.size(); j++) {
//                                    System.out.println("2se agrega simbolo: " + simbolos.get(j).nombre);
                                    otro.addSimbolo(simbolos.get(j));
                                }
                            }

                            this.addItem(otro);
                            limite++;
                        }
                    }
                }
            }
            elQueToca++;
        }

//        System.out.println("+++++++++++");
//        for (int j = 0; j < this.items.size(); j++) {
//            last = items.get(j);
//            System.out.println("last: " + last.nombreIzq.nombre);
//            for (int i = 0; i < last.hijos.size(); i++) {
//                try {
//                    System.out.println("--->>>" + ((Terminal) (last.hijos.get(i))).nombre);
//                } catch (Exception e) {
//                    System.out.println("--->>>" + ((NoTerminal) (last.hijos.get(i))).nombre);
//                }
//            }
//        }
    }
}
