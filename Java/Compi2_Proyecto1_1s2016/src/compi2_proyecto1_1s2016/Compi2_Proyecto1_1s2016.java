/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compi2_proyecto1_1s2016;

import java.util.ArrayList;
import java.util.Stack;
import javax.xml.parsers.*;
import org.xml.sax.InputSource;
import org.w3c.dom.*;
import java.io.*;
import org.xml.sax.SAXException;

/**
 *
 * @author Mac
 */
public class Compi2_Proyecto1_1s2016 {

    public static ArrayList<Metodo> ListaMetodos;
    public static Object valorEnHold;
    public static int tipoDatoEnHold;
    public static ArrayList<Precedencia> Precedencias;
    public static ArrayList<NoTerminal> NoTerminales;
    public static ArrayList<Terminal> Terminales;
    public static ArrayList<Simbolo> TablaSimbolos;
    public static ArrayList<Conjunto> Conjuntos;
    public static ArrayList<Terminal> Primeros;
    public static NoTerminal inicial;
    public static ArrayList<Object> listaAux;
    public static ArrayList<String> auxIr_A;
    public static Terminal dolar;
    public static ArrayList<itemTabla> TablaTransiciones;
    public static ArrayList<String> auxConjuntos;
    public static ArrayList<TokenCadenaEntrada> Tokens;
    public static Stack pilaAnalisis;
    public static int punteroToken;
    public static Stack<Atributo> pilaParaAtributos;
    public static Stack<NodoArbolSintactico> pilaArbolSintactico;
    public static String cadenaReturn = "", cadenaErrores = "";
    public static Stack<ArrayList<Parametro>> pilaLlamadas;

    public static void main(String[] args) {
        pilaLlamadas = new Stack<>();
        valorEnHold = null;
        tipoDatoEnHold = -58;
        Precedencias = new ArrayList<>();
        Tokens = new ArrayList<>();
        auxConjuntos = new ArrayList<>();
        TablaTransiciones = new ArrayList<>();
        inicial = null;
        Conjuntos = new ArrayList<>();
        TablaSimbolos = new ArrayList<>();
        Terminales = new ArrayList<>();
        NoTerminales = new ArrayList<>();
        auxIr_A = new ArrayList<>();
        Main.start();
    }

    public static String analizarCadena(String xml) {
        if (xml.equalsIgnoreCase("")) {
            xml = "<tokens>               "
                    + " <token>"
                    + " <nombre> e </nombre>"
                    + " <tipo> int </tipo>"
                    + "<valor> 1 </valor>"
                    + "<yyline> 1 </yyline>"
                    + "<yyrow> 0 </yyrow>"
                    + " </token>"
                    + " <token>"
                    + " <nombre> e </nombre>"
                    + " <tipo> int </tipo>"
                    + "<valor> 2 </valor>"
                    + "<yyline> 1 </yyline>"
                    + "<yyrow> 0 </yyrow>"
                    + " </token>"
                    + " <token>"
                    + " <nombre> d </nombre>"
                    + " <tipo> int </tipo>"
                    + "<valor> 3 </valor>"
                    + "<yyline> 1 </yyline>"
                    + "<yyrow> 0 </yyrow>"
                    + " </token>"
                    + " <token>"
                    + " <nombre> e </nombre>"
                    + " <tipo> int </tipo>"
                    + "<valor> 4 </valor>"
                    + "<yyline> 1 </yyline>"
                    + "<yyrow> 0 </yyrow>"
                    + " </token>"
                    + " <token>"
                    + " <nombre> e </nombre>"
                    + " <tipo> int </tipo>"
                    + "<valor> 5 </valor>"
                    + "<yyline> 1 </yyline>"
                    + "<yyrow> 0 </yyrow>"
                    + " </token>"
                    + " <token>"
                    + " <nombre> d </nombre>"
                    + " <tipo> int </tipo>"
                    + "<valor> 6 </valor>"
                    + "<yyline> 1 </yyline>"
                    + "<yyrow> 0 </yyrow>"
                    + " </token>"
                    + " </tokens>";
        }
//        xml = "<tokens>"
//                + "<token>"
//                + "<nombre> e </nombre>"
//                + "<yyline> 0 </yyline>"
//                + "<yyrow> 0 </yyrow>"
//                + "</token>"
//                + "<token>"
//                + "<nombre> e </nombre>"
//                + "<yyline> 0 </yyline>"
//                + "<yyrow> 0 </yyrow>"
//                + "</token>"
//                + "<token>"
//                + "<nombre> d </nombre>"
//                + "<yyline> 0 </yyline>"
//                + "<yyrow> 0 </yyrow>"
//                + "</token>"
//                + "<token>"
//                + "<nombre> e </nombre>"
//                + "<yyline> 0 </yyline>"
//                + "<yyrow> 0 </yyrow>"
//                + "</token>"
//                + "<token>"
//                + "<nombre> e </nombre>"
//                + "<yyline> 0 </yyline>"
//                + "<yyrow> 0 </yyrow>"
//                + "</token>"
//                + "<token>"
//                + "<nombre> d </nombre>"
//                + "<yyline> 0 </yyline>"
//                + "<yyrow> 0 </yyrow>"
//                + "</token>"
//                + "</tokens>";
        /*
         xml = "<tokens>"
         + "<token>"
         + "<nombre> id </nombre>"
         + "<yyline> 0 </yyline>"
         + "<yyrow> 0 </yyrow>"
         + "</token>"
         + "<token>"
         + "<nombre> mas </nombre>"
         + "<yyline> 0 </yyline>"
         + "<yyrow> 0 </yyrow>"
         + "</token>"
         + "<token>"
         + "<nombre> id </nombre>"
         + "<yyline> 0 </yyline>"
         + "<yyrow> 0 </yyrow>"
         + "</token>"
         + "<token>"
         + "<nombre> por </nombre>"
         + "<yyline> 0 </yyline>"
         + "<yyrow> 0 </yyrow>"
         + "</token>"
         + "<token>"
         + "<nombre> id </nombre>"
         + "<yyline> 0 </yyline>"
         + "<yyrow> 0 </yyrow>"
         + "</token>"
         + "<token>"
         + "<nombre> menos </nombre>"
         + "<yyline> 0 </yyline>"
         + "<yyrow> 0 </yyrow>"
         + "</token>"
         + "<token>"
         + "<nombre> id </nombre>"
         + "<yyline> 0 </yyline>"
         + "<yyrow> 0 </yyrow>"
         + "</token>"
         + "<token>"
         + "<nombre> div </nombre>"
         + "<yyline> 0 </yyline>"
         + "<yyrow> 0 </yyrow>"
         + "</token>"
         + "<token>"
         + "<nombre> id </nombre>"
         + "<yyline> 0 </yyline>"
         + "<yyrow> 0 </yyrow>"
         + "</token>"
         + "<token>"
         + "<nombre> por </nombre>"
         + "<yyline> 0 </yyline>"
         + "<yyrow> 0 </yyrow>"
         + "</token>"
         + "<token>"
         + "<nombre> id </nombre>"
         + "<yyline> 0 </yyline>"
         + "<yyrow> 0 </yyrow>"
         + "</token>"
         + "</tokens>";*/

        pilaLlamadas = new Stack<>();
        pilaArbolSintactico = new Stack<>();
        pilaParaAtributos = new Stack<>();
        Tokens = new ArrayList<>();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            Document doc = null;
            try {
                doc = db.parse(is);
            } catch (SAXException | IOException e1) {
                return e1.toString();
            }

            NodeList nodes = doc.getElementsByTagName("token");

            // iterate the employees
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                String type = "-313";
                String val = "-313";

                Element line = (Element) element.getElementsByTagName("nombre").item(0);
                String name = getCharacterDataFromElement(line).trim();

                line = (Element) element.getElementsByTagName("yyline").item(0);
                int linea = Integer.parseInt(getCharacterDataFromElement(line).trim());

                line = (Element) element.getElementsByTagName("yyrow").item(0);
                int row = Integer.parseInt(getCharacterDataFromElement(line).trim());

                try {
                    line = (Element) element.getElementsByTagName("tipo").item(0);
                    type = getCharacterDataFromElement(line).trim();

                    line = (Element) element.getElementsByTagName("valor").item(0);
                    val = getCharacterDataFromElement(line).trim();
                } catch (Exception e) {
                }
                if (val.equals("-313") || type.equals("-313")) {
                    Tokens.add(new TokenCadenaEntrada(name, linea, row));
                } else {
                    Tokens.add(new TokenCadenaEntrada(name, linea, row, type, val));
                }
            }
            Tokens.add(new TokenCadenaEntrada("$", 31300, 31300));
        } catch (Exception e) {
            return e.toString();
        }
//        for (TokenCadenaEntrada Token : Tokens) {
//            System.out.println("" + Token.nombre);
//            System.out.println("  " + Token.line);
//            System.out.println("  " + Token.row);
//            if (Token.hasValor()) {
//                System.out.println("  " + Token.tipo);
//                System.out.println("  " + Token.valor);
//            }
//        }
        pilaAnalisis = new Stack();
        punteroToken = 0;
        pilaAnalisis.push(0);
        boolean siga = true;
        String retorno = "";
        while (Tokens.size() != punteroToken && siga) {
            retorno = desplazarReducir(Tokens.get(punteroToken));
            siga = retorno.equalsIgnoreCase("true");
        }
        System.out.println("PILA ATRIBUTOS:");
        int contador = 0;
        for (Atributo pila : pilaParaAtributos) {
            try {
                Terminal x = (Terminal) pila.TerminalNoTerminal;
                System.out.println("T:" + x.nombre + ". " + contador);
                contador++;
            } catch (Exception e) {
                NoTerminal x = (NoTerminal) pila.TerminalNoTerminal;
                System.out.println("NT:" + x.nombre + ". " + contador);
                contador++;
            }
        }
        if (!pilaArbolSintactico.isEmpty()) {
            NodoArbolSintactico root = pilaArbolSintactico.pop();
            root.mostrar();

        }

        System.out.println("PILA:");
        for (Object pila : pilaAnalisis) {
            System.out.println(pila);
        }
        if (!cadenaReturn.equalsIgnoreCase("")) {
            System.out.println("CADENA RESULTANTE:\n\n" + cadenaReturn);
        }
        if (!cadenaErrores.equalsIgnoreCase("")) {
            System.out.println("ERRORES:\n\n" + cadenaErrores);
        }

        return retorno;
    }

    public static void showPrecedencias() {
        for (Precedencia Precedencia : Precedencias) {
            System.out.println("P:" + Precedencia.ponderacion + ". Hijos: " + Precedencia.TerminalesAfectados.size());
            for (Terminal TerminalAfectado : Precedencia.TerminalesAfectados) {
                System.out.println("  -" + TerminalAfectado.nombre);
            }
        }
    }

    public static String desplazarReducir(TokenCadenaEntrada nextToken) {
        System.out.println("nextToken: " + nextToken.nombre);
        for (int i = 0; i < pilaParaAtributos.size(); i++) {
            try {
                System.out.println(((Terminal) pilaParaAtributos.get(i).TerminalNoTerminal).nombre + " - " + i);
            } catch (Exception e) {
                System.out.println(((NoTerminal) pilaParaAtributos.get(i).TerminalNoTerminal).nombre + " - " + i);
            }
        }
        ArrayList<itemTabla> TransicionesAvailable = new ArrayList<>();;
        for (itemTabla Transicion : TablaTransiciones) {
            try {
                int noConj = (int) pilaAnalisis.peek();
            } catch (Exception e) {
                System.out.println("rtyujqwef----------------" + pilaAnalisis.peek());
            }
            int noConj = (int)pilaAnalisis.peek();
            
            try {
                String nom = ((Terminal) Transicion.simboloConElQueSeDesplaza).nombre;
//                System.out.println("nom: " + nom);
                TransicionesAvailable = new ArrayList<>();
//                System.out.println("********");
                for (itemTabla Transicion1 : TablaTransiciones) {
                    try {
                        String nom2 = ((Terminal) Transicion1.simboloConElQueSeDesplaza).nombre;
                        if (Transicion1.numeroEstado == noConj && nom2.equalsIgnoreCase(nextToken.nombre)) {
                            TransicionesAvailable.add(Transicion1);
//                            System.out.println("T:" + Transicion1.numeroEstado + "," + noConj + " -> " + nom2 + "," + nextToken.nombre + " -> " + Transicion1.DestinoReduccionDesplazamiento);

                        }
                    } catch (Exception e) {
                    }

                }
                if (TransicionesAvailable.size() > 1) {
//                    System.out.println("T-Hay 2, no se que hacer lolz." + TransicionesAvailable.size());
                    String yaAnalizado = "" + pilaAnalisis.get(pilaAnalisis.size() - 4);
                    String current = ((Terminal) TransicionesAvailable.get(0).simboloConElQueSeDesplaza).nombre;
                    int valAnalizado = -1;
                    int valCurrent = -1;
                    Precedencia contenedor = null;
//                    System.out.println("COMPARA: '" + yaAnalizado + "' y '" + current + "'");
                    boolean anal = false, cur = false;
                    for (Precedencia Precedencia : Precedencias) {
                        if (anal && cur) {
                            break;
                        }
                        if (!anal) {
                            valAnalizado = Precedencia.getValor(yaAnalizado);
                            contenedor = Precedencia;
                        }
                        if (!cur) {
                            valCurrent = Precedencia.getValor(current);
                        }
                        if (valAnalizado != -1) {
                            anal = true;
                        }
                        if (valCurrent != -1) {
                            cur = true;
                        }
                    }

                    if (valCurrent == -1 || valAnalizado == -1) {
                        System.out.println("Problema shift/reduce. Resuelvelo con precedencia.");
                        nodoAST.addError("Problema shift/reduce. Resuelvelo con precedencia.");
                        return "false";
                    } else {
                        System.out.println("valores:" + (int) (valAnalizado / 1000) + "," + (int) (valAnalizado % 1000) + "-" + (int) (valCurrent / 1000) + "," + (int) (valCurrent % 1000));
                        if (((int) (valAnalizado / 1000)) == ((int) (valCurrent / 1000))) {
                            if (contenedor.asociatividad == 0 || contenedor.asociatividad == -1) {
                                if ((int) (valAnalizado % 1000) < (int) (valCurrent % 1000)) {
                                    System.out.println("Se Reduce");
                                    for (itemTabla TransicionAvailable : TransicionesAvailable) {
                                        if (TransicionAvailable.tipo != 1) {
                                            try {
                                                System.out.println("R" + TransicionAvailable.DestinoReduccionDesplazamiento + ". " + ((Terminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre);

                                            } catch (Exception e) {
                                                System.out.println("R" + TransicionAvailable.DestinoReduccionDesplazamiento + ". " + ((NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre);
                                            }
                                            if (TransicionAvailable.DestinoReduccionDesplazamiento == -1) {
                                                System.out.println("ACEPTAR!!");
                                                return "aceptar";
                                            }
                                            int numReduccion = TransicionAvailable.DestinoReduccionDesplazamiento;
                                            Conjunto cero = Conjuntos.get(Conjuntos.size() - 1);
                                            ItemConjunto reduccion = cero.items.get(numReduccion);
                                            int numeroHijos = reduccion.hijos.size();
                                            NodoArbolSintactico temp = new NodoArbolSintactico(reduccion.nombreIzq.nombre, 0);
                                            for (int i = 0; i < numeroHijos; i++) {
                                                temp.addHijo(pilaArbolSintactico.pop());
                                                pilaAnalisis.pop();
                                                pilaAnalisis.pop();
                                                pilaParaAtributos.pop();
                                            }
                                            if (valorEnHold != null && tipoDatoEnHold != -58) {
                                                if (reduccion.nombreIzq.tipoReturn == tipoDatoEnHold || reduccion.nombreIzq.tipoReturn == -98) {
                                                    pilaParaAtributos.add(new Atributo(valorEnHold, reduccion.nombreIzq.tipoReturn, reduccion.nombreIzq));
                                                } else {
                                                    nodoAST.addError("Error en tipo de dato al utilizar la variable RESULT.");
                                                    pilaParaAtributos.add(new Atributo(null, reduccion.nombreIzq.tipoReturn, reduccion.nombreIzq));
                                                }
                                                valorEnHold = null;
                                                tipoDatoEnHold = -58;
                                            } else {
                                                pilaParaAtributos.add(new Atributo(null, reduccion.nombreIzq.tipoReturn, reduccion.nombreIzq));
                                            }
                                            System.out.println("SE AGREGA: " + reduccion.nombreIzq.nombre);

                                            pilaArbolSintactico.push(temp);
                                            int numeroConjunto = (int) pilaAnalisis.peek();
                                            pilaAnalisis.push(reduccion.nombreIzq.nombre);
                                            System.out.println("push-" + reduccion.nombreIzq.nombre);
                                            String nombre = reduccion.nombreIzq.nombre;
                                            for (itemTabla Transicion1 : TablaTransiciones) {
                                                try {
                                                    if (Transicion1.numeroEstado == numeroConjunto && ((NoTerminal) Transicion1.simboloConElQueSeDesplaza).nombre.equalsIgnoreCase(nombre)) {
                                                        if (Transicion1.tipo == 0) { //Es NoTerminal
                                                            pilaAnalisis.push(Transicion1.DestinoReduccionDesplazamiento);
                                                            if (Transicion1.accion != null) {
                                                                Transicion1.accion.ejecutar(Transicion1.numeroItem);
                                                            }
                                                            return "true";
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("error aca?");
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    System.out.println("Se Desplaza");
                                    for (itemTabla TransicionAvailable : TransicionesAvailable) {
                                        if (TransicionAvailable.tipo == 1) { //Desplazamiento
                                            punteroToken++;
                                            try {
                                                System.out.println("D" + TransicionAvailable.DestinoReduccionDesplazamiento + ". " + ((Terminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre);
                                                NodoArbolSintactico temp = new NodoArbolSintactico(((Terminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre, 0);
                                                pilaArbolSintactico.add(temp);
                                                System.out.println("NoHijos: " + TransicionAvailable.numeroItem);
                                                pilaParaAtributos.add(new Atributo(null, ((Terminal) TransicionAvailable.simboloConElQueSeDesplaza).tipoReturn, (Terminal) TransicionAvailable.simboloConElQueSeDesplaza, Tokens.get(punteroToken - 1).valor, Tokens.get(punteroToken - 1).tipo));
                                                System.out.println("VALOR TERMINAL: " + Tokens.get(punteroToken - 1).valor);
                                                if (TransicionAvailable.accion != null) {
                                                    TransicionAvailable.accion.ejecutar(TransicionAvailable.numeroItem);
                                                }
                                            } catch (Exception e) {
                                                System.out.println("D" + TransicionAvailable.DestinoReduccionDesplazamiento + ". " + ((NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre);
                                                pilaArbolSintactico.add(new NodoArbolSintactico(((NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre, 1));
                                                System.out.println("NoHijos: " + TransicionAvailable.numeroItem);
                                                pilaParaAtributos.add(new Atributo(null, ((NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza).tipoReturn, (Terminal) TransicionAvailable.simboloConElQueSeDesplaza));
                                                if (TransicionAvailable.accion != null) {
                                                    Transicion.accion.ejecutar(Transicion.numeroItem);
                                                }
                                            }
                                            pilaAnalisis.push(nextToken.nombre);
                                            pilaAnalisis.push(TransicionAvailable.DestinoReduccionDesplazamiento);
                                            return "true";
                                        }
                                    }
                                }
                            } else {
                                if ((int) (valAnalizado % 1000) > (int) (valCurrent % 1000)) {
                                    System.out.println("Se Reduce");
                                    for (itemTabla TransicionAvailable : TransicionesAvailable) {
                                        if (TransicionAvailable.tipo != 1) {
                                            try {
                                                System.out.println("R" + TransicionAvailable.DestinoReduccionDesplazamiento + ". " + ((Terminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre);

                                            } catch (Exception e) {
                                                System.out.println("R" + TransicionAvailable.DestinoReduccionDesplazamiento + ". " + ((NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre);
                                            }
                                            if (TransicionAvailable.DestinoReduccionDesplazamiento == -1) {
                                                System.out.println("ACEPTAR!!");
                                                return "aceptar";
                                            }
                                            int numReduccion = TransicionAvailable.DestinoReduccionDesplazamiento;
                                            Conjunto cero = Conjuntos.get(Conjuntos.size() - 1);
                                            ItemConjunto reduccion = cero.items.get(numReduccion);
                                            int numeroHijos = reduccion.hijos.size();
                                            NodoArbolSintactico temp = new NodoArbolSintactico(reduccion.nombreIzq.nombre, 0);
                                            for (int i = 0; i < numeroHijos; i++) {
                                                temp.addHijo(pilaArbolSintactico.pop());
                                                pilaAnalisis.pop();
                                                pilaAnalisis.pop();
                                                pilaParaAtributos.pop();
                                            }
                                            if (valorEnHold != null && tipoDatoEnHold != -58) {
                                                if (reduccion.nombreIzq.tipoReturn == tipoDatoEnHold || reduccion.nombreIzq.tipoReturn == -98) {
                                                    pilaParaAtributos.add(new Atributo(valorEnHold, reduccion.nombreIzq.tipoReturn, reduccion.nombreIzq));
                                                } else {
                                                    nodoAST.addError("Error en tipo de dato al utilizar la variable RESULT.");
                                                    pilaParaAtributos.add(new Atributo(null, reduccion.nombreIzq.tipoReturn, reduccion.nombreIzq));
                                                }
                                                valorEnHold = null;
                                                tipoDatoEnHold = -58;
                                            } else {
                                                pilaParaAtributos.add(new Atributo(null, reduccion.nombreIzq.tipoReturn, reduccion.nombreIzq));
                                            }
                                            System.out.println("SE AGREGA: " + reduccion.nombreIzq.nombre);
                                            pilaArbolSintactico.push(temp);
                                            int numeroConjunto = (int) pilaAnalisis.peek();
                                            pilaAnalisis.push(reduccion.nombreIzq.nombre);
                                            System.out.println("push-" + reduccion.nombreIzq.nombre);
                                            String nombre = reduccion.nombreIzq.nombre;
                                            for (itemTabla Transicion1 : TablaTransiciones) {
                                                try {
                                                    if (Transicion1.numeroEstado == numeroConjunto && ((NoTerminal) Transicion1.simboloConElQueSeDesplaza).nombre.equalsIgnoreCase(nombre)) {
                                                        if (Transicion1.tipo == 0) { //Es NoTerminal
                                                            pilaAnalisis.push(Transicion1.DestinoReduccionDesplazamiento);
                                                            if (Transicion1.accion != null) {
                                                                Transicion1.accion.ejecutar(Transicion1.numeroItem);
                                                            }
                                                            return "true";
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("error aca?");
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    System.out.println("Se Desplaza");
                                    for (itemTabla TransicionAvailable : TransicionesAvailable) {
                                        if (TransicionAvailable.tipo == 1) { //Desplazamiento
                                            punteroToken++;
                                            try {
                                                System.out.println("D" + TransicionAvailable.DestinoReduccionDesplazamiento + ". " + ((Terminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre);
                                                NodoArbolSintactico temp = new NodoArbolSintactico(((Terminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre, 0);
                                                pilaArbolSintactico.add(temp);
                                                System.out.println("NoHijos: " + TransicionAvailable.numeroItem);
                                                pilaParaAtributos.add(new Atributo(null, ((Terminal) TransicionAvailable.simboloConElQueSeDesplaza).tipoReturn, (Terminal) TransicionAvailable.simboloConElQueSeDesplaza, Tokens.get(punteroToken - 1).valor, Tokens.get(punteroToken - 1).tipo));
                                                System.out.println("VALOR TERMINAL: " + Tokens.get(punteroToken - 1).valor);
                                                if (TransicionAvailable.accion != null) {
                                                    TransicionAvailable.accion.ejecutar(TransicionAvailable.numeroItem);
                                                }
                                            } catch (Exception e) {
                                                System.out.println("D" + TransicionAvailable.DestinoReduccionDesplazamiento + ". " + ((NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre);
                                                pilaArbolSintactico.add(new NodoArbolSintactico(((NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre, 1));
                                                System.out.println("NoHijos: " + TransicionAvailable.numeroItem);
                                                pilaParaAtributos.add(new Atributo(null, ((NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza).tipoReturn, (NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza));
                                                if (TransicionAvailable.accion != null) {
                                                    Transicion.accion.ejecutar(TransicionAvailable.numeroItem);
                                                }
                                            }
                                            pilaAnalisis.push(nextToken.nombre);
                                            pilaAnalisis.push(TransicionAvailable.DestinoReduccionDesplazamiento);
                                            return "true";
                                        }
                                    }
                                }
                            }
                        } else {
                            if (valAnalizado > valCurrent) {
                                System.out.println("Se Reduce");
                                for (itemTabla TransicionAvailable : TransicionesAvailable) {
                                    if (TransicionAvailable.tipo != 1) {
                                        try {
                                            System.out.println("R" + TransicionAvailable.DestinoReduccionDesplazamiento + ". " + ((Terminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre);

                                        } catch (Exception e) {
                                            System.out.println("R" + TransicionAvailable.DestinoReduccionDesplazamiento + ". " + ((NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre);
                                        }
                                        if (TransicionAvailable.DestinoReduccionDesplazamiento == -1) {
                                            System.out.println("ACEPTAR!!");
                                            return "aceptar";
                                        }
                                        int numReduccion = TransicionAvailable.DestinoReduccionDesplazamiento;
                                        Conjunto cero = Conjuntos.get(Conjuntos.size() - 1);
                                        ItemConjunto reduccion = cero.items.get(numReduccion);
                                        int numeroHijos = reduccion.hijos.size();
                                        NodoArbolSintactico temp = new NodoArbolSintactico(reduccion.nombreIzq.nombre, 0);
                                        for (int i = 0; i < numeroHijos; i++) {
                                            temp.addHijo(pilaArbolSintactico.pop());
                                            pilaAnalisis.pop();
                                            pilaAnalisis.pop();
                                            pilaParaAtributos.pop();
                                        }
                                        if (valorEnHold != null && tipoDatoEnHold != -58) {
                                            if (reduccion.nombreIzq.tipoReturn == tipoDatoEnHold || reduccion.nombreIzq.tipoReturn == -98) {
                                                pilaParaAtributos.add(new Atributo(valorEnHold, reduccion.nombreIzq.tipoReturn, reduccion.nombreIzq));
                                            } else {
                                                nodoAST.addError("Error en tipo de dato al utilizar la variable RESULT.");
                                                pilaParaAtributos.add(new Atributo(null, reduccion.nombreIzq.tipoReturn, reduccion.nombreIzq));
                                            }
                                            valorEnHold = null;
                                            tipoDatoEnHold = -58;
                                        } else {
                                            pilaParaAtributos.add(new Atributo(null, reduccion.nombreIzq.tipoReturn, reduccion.nombreIzq));
                                        }
                                        System.out.println("SE AGREGA: " + reduccion.nombreIzq.nombre);
                                        pilaArbolSintactico.push(temp);
                                        int numeroConjunto = (int) pilaAnalisis.peek();
                                        pilaAnalisis.push(reduccion.nombreIzq.nombre);
                                        System.out.println("push-" + reduccion.nombreIzq.nombre);
                                        String nombre = reduccion.nombreIzq.nombre;
                                        for (itemTabla Transicion1 : TablaTransiciones) {
                                            try {
                                                if (Transicion1.numeroEstado == numeroConjunto && ((NoTerminal) Transicion1.simboloConElQueSeDesplaza).nombre.equalsIgnoreCase(nombre)) {
                                                    if (Transicion1.tipo == 0) { //Es NoTerminal
                                                        pilaAnalisis.push(Transicion1.DestinoReduccionDesplazamiento);
                                                        if (Transicion1.accion != null) {
                                                            Transicion1.accion.ejecutar(Transicion1.numeroItem);
                                                        }
                                                        return "true";
                                                    }
                                                }
                                            } catch (Exception e) {
                                                System.out.println("error aca?");
                                            }
                                        }
                                    }
                                }
                            } else {
                                System.out.println("Se Desplaza");
                                for (itemTabla TransicionAvailable : TransicionesAvailable) {
                                    if (TransicionAvailable.tipo == 1) { //Desplazamiento
                                        punteroToken++;
                                        try {
                                            System.out.println("D" + TransicionAvailable.DestinoReduccionDesplazamiento + ". " + ((Terminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre);
                                            NodoArbolSintactico temp = new NodoArbolSintactico(((Terminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre, 0);
                                            pilaArbolSintactico.add(temp);
                                            System.out.println("NoHijos: " + TransicionAvailable.numeroItem);
                                            pilaParaAtributos.add(new Atributo(null, ((Terminal) TransicionAvailable.simboloConElQueSeDesplaza).tipoReturn, (Terminal) TransicionAvailable.simboloConElQueSeDesplaza, Tokens.get(punteroToken - 1).valor, Tokens.get(punteroToken - 1).tipo));
                                            System.out.println("VALOR TERMINAL: " + Tokens.get(punteroToken - 1).valor);
                                            if (TransicionAvailable.accion != null) {
                                                TransicionAvailable.accion.ejecutar(TransicionAvailable.numeroItem);
                                            }
                                        } catch (Exception e) {
                                            System.out.println("D" + TransicionAvailable.DestinoReduccionDesplazamiento + ". " + ((NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre);
                                            pilaArbolSintactico.add(new NodoArbolSintactico(((NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre, 1));
                                            System.out.println("NoHijos: " + TransicionAvailable.numeroItem);
                                            pilaParaAtributos.add(new Atributo(null, ((NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza).tipoReturn, (NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza));
                                            if (TransicionAvailable.accion != null) {
                                                Transicion.accion.ejecutar(TransicionAvailable.numeroItem);
                                            }
                                        }
                                        pilaAnalisis.push(nextToken.nombre);
                                        pilaAnalisis.push(TransicionAvailable.DestinoReduccionDesplazamiento);
                                        return "true";
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (Transicion.numeroEstado == noConj && nom.equalsIgnoreCase(nextToken.nombre)) {
                        if (Transicion.tipo == 1) { //Desplazamiento
                            punteroToken++;
                            try {
                                System.out.println("D" + Transicion.DestinoReduccionDesplazamiento + ". " + ((Terminal) Transicion.simboloConElQueSeDesplaza).nombre);
                                NodoArbolSintactico temp = new NodoArbolSintactico(((Terminal) Transicion.simboloConElQueSeDesplaza).nombre, 0);
                                pilaArbolSintactico.add(temp);
                                System.out.println("NoHijos: " + Transicion.numeroItem);
                                System.out.println("654--" + Tokens.get(punteroToken - 1).tipo);
                                Atributo tempAtributo = new Atributo(null, ((Terminal) Transicion.simboloConElQueSeDesplaza).tipoReturn, ((Terminal) Transicion.simboloConElQueSeDesplaza), Tokens.get(punteroToken - 1).valor, Tokens.get(punteroToken - 1).tipo);
                                pilaParaAtributos.add(tempAtributo);
                                System.out.println("VALOR TERMINAL: " + Tokens.get(punteroToken - 1).tipo);
                                if (Transicion.accion != null) {
                                    Transicion.accion.ejecutar(Transicion.numeroItem);
                                }
                            } catch (Exception e) {
                                System.out.println("D" + Transicion.DestinoReduccionDesplazamiento + ". " + ((NoTerminal) Transicion.simboloConElQueSeDesplaza).nombre);
                                pilaArbolSintactico.add(new NodoArbolSintactico(((NoTerminal) Transicion.simboloConElQueSeDesplaza).nombre, 1));
                                System.out.println("NoHijos: " + Transicion.numeroItem);
                                pilaParaAtributos.add(new Atributo(null, ((NoTerminal) Transicion.simboloConElQueSeDesplaza).tipoReturn, ((NoTerminal) Transicion.simboloConElQueSeDesplaza)));
                                if (Transicion.accion != null) {
                                    Transicion.accion.ejecutar(Transicion.numeroItem);
                                }
                            }
                            pilaAnalisis.push(nextToken.nombre);
                            pilaAnalisis.push(Transicion.DestinoReduccionDesplazamiento);
                        } else { //Reduccion
                            try {
                                System.out.println("R" + Transicion.DestinoReduccionDesplazamiento + ". " + ((Terminal) Transicion.simboloConElQueSeDesplaza).nombre);

                            } catch (Exception e) {
                                System.out.println("R" + Transicion.DestinoReduccionDesplazamiento + ". " + ((NoTerminal) Transicion.simboloConElQueSeDesplaza).nombre);
                            }
                            if (Transicion.DestinoReduccionDesplazamiento == -1) {
                                System.out.println("ACEPTAR!!");
                                return "aceptar";
                            }
                            int numReduccion = Transicion.DestinoReduccionDesplazamiento;
                            Conjunto cero = Conjuntos.get(Conjuntos.size() - 1);
                            ItemConjunto reduccion = cero.items.get(numReduccion);
                            int numeroHijos = reduccion.hijos.size();
                            NodoArbolSintactico temp = new NodoArbolSintactico(reduccion.nombreIzq.nombre, 0);
                            for (int i = 0; i < numeroHijos; i++) {
                                temp.addHijo(pilaArbolSintactico.pop());
                                pilaAnalisis.pop();
                                pilaAnalisis.pop();
                                pilaParaAtributos.pop();
                            }
                            pilaArbolSintactico.push(temp);
                            if (valorEnHold != null && tipoDatoEnHold != -58) {
                                if (reduccion.nombreIzq.tipoReturn == tipoDatoEnHold || reduccion.nombreIzq.tipoReturn == -98) {
                                    pilaParaAtributos.add(new Atributo(valorEnHold, reduccion.nombreIzq.tipoReturn, reduccion.nombreIzq));
                                } else {
                                    nodoAST.addError("Error en tipo de dato al utilizar la variable RESULT.");
                                    pilaParaAtributos.add(new Atributo(null, reduccion.nombreIzq.tipoReturn, reduccion.nombreIzq));
                                }
                                valorEnHold = null;
                                tipoDatoEnHold = -58;
                            } else {
                                pilaParaAtributos.add(new Atributo(null, reduccion.nombreIzq.tipoReturn, reduccion.nombreIzq));
                            }
                            System.out.println("SE AGREGA: " + reduccion.nombreIzq.nombre);
                            int numeroConjunto = (int) pilaAnalisis.peek();
                            pilaAnalisis.push(reduccion.nombreIzq.nombre);
                            System.out.println("push-" + reduccion.nombreIzq.nombre);
                            String nombre = reduccion.nombreIzq.nombre;
                            for (itemTabla Transicion1 : TablaTransiciones) {
                                try {
                                    if (Transicion1.numeroEstado == numeroConjunto && ((NoTerminal) Transicion1.simboloConElQueSeDesplaza).nombre.equalsIgnoreCase(nombre)) {
                                        if (Transicion1.tipo == 0) { //Es NoTerminal
                                            pilaAnalisis.push(Transicion1.DestinoReduccionDesplazamiento);
                                            if (Transicion1.accion != null) {
                                                try {
                                                    Transicion1.accion.ejecutar(Transicion1.numeroItem);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                            return "true";
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("error aca?" + e.toString());
                                }
                            }

                        }
                        return "true";
                    }
                }

            } catch (Exception e) {
                
                String nom = ((NoTerminal) Transicion.simboloConElQueSeDesplaza).nombre;
//                System.out.println("nom: " + nom);
                TransicionesAvailable = new ArrayList<>();
//                System.out.println("********");
                for (itemTabla Transicion1 : TablaTransiciones) {
                    try {
                        String nom2 = ((NoTerminal) Transicion1.simboloConElQueSeDesplaza).nombre;
                        if (Transicion1.numeroEstado == noConj && nom2.equalsIgnoreCase(nextToken.nombre)) {

                            TransicionesAvailable.add(Transicion1);
                            System.out.println("T:" + Transicion1.numeroEstado + "," + noConj + " -> " + nom2 + "," + nextToken.nombre + " -> " + Transicion1.DestinoReduccionDesplazamiento);
                        }
                    } catch (Exception e2) {
                    }

                }

                if (TransicionesAvailable.size() > 1) {
//                    System.out.println("T-Hay 2, no se que hacer lolz." + TransicionesAvailable.size());
                    String yaAnalizado = "" + pilaAnalisis.get(pilaAnalisis.size() - 4);
                    String current = ((Terminal) TransicionesAvailable.get(0).simboloConElQueSeDesplaza).nombre;
                    int valAnalizado = -1;
                    int valCurrent = -1;
                    Precedencia contenedor = null;
//                    System.out.println("COMPARA: '" + yaAnalizado + "' y '" + current + "'");
                    boolean anal = false, cur = false;
                    for (Precedencia Precedencia : Precedencias) {
                        if (anal && cur) {
                            break;
                        }
                        if (!anal) {
                            valAnalizado = Precedencia.getValor(yaAnalizado);
                            contenedor = Precedencia;
                        }
                        if (!cur) {
                            valCurrent = Precedencia.getValor(current);
                        }
                        if (valAnalizado != -1) {
                            anal = true;
                        }
                        if (valCurrent != -1) {
                            cur = true;
                        }
                    }
                    if (valCurrent == -1 || valAnalizado == -1) {
                        nodoAST.addError("Problema shift/reduce. Resuelvelo con precedencia.");
                    } else {
                        System.out.println("valores:" + (int) (valAnalizado / 1000) + "," + (int) (valAnalizado % 1000) + "-" + (int) (valCurrent / 1000) + "," + (int) (valCurrent % 1000));
                        if (((int) (valAnalizado / 1000)) == ((int) (valCurrent / 1000))) {
                            if (contenedor.asociatividad == 0 || contenedor.asociatividad == -1) {
                                if ((int) (valAnalizado % 1000) > (int) (valCurrent % 1000)) {
                                    System.out.println("Se Reduce");
                                    for (itemTabla TransicionAvailable : TransicionesAvailable) {
                                        if (TransicionAvailable.tipo != 1) {
                                            try {
                                                System.out.println("R" + TransicionAvailable.DestinoReduccionDesplazamiento + ". " + ((Terminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre);

                                            } catch (Exception e2) {
                                                System.out.println("R" + TransicionAvailable.DestinoReduccionDesplazamiento + ". " + ((NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre);
                                            }
                                            if (TransicionAvailable.DestinoReduccionDesplazamiento == -1) {
                                                System.out.println("ACEPTAR!!");
                                                return "aceptar";
                                            }
                                            int numReduccion = TransicionAvailable.DestinoReduccionDesplazamiento;
                                            Conjunto cero = Conjuntos.get(Conjuntos.size() - 1);
                                            ItemConjunto reduccion = cero.items.get(numReduccion);
                                            int numeroHijos = reduccion.hijos.size();
                                            NodoArbolSintactico temp = new NodoArbolSintactico(reduccion.nombreIzq.nombre, 0);
                                            for (int i = 0; i < numeroHijos; i++) {
                                                temp.addHijo(pilaArbolSintactico.pop());
                                                pilaAnalisis.pop();
                                                pilaAnalisis.pop();
                                                pilaParaAtributos.pop();
                                            }
                                            pilaArbolSintactico.push(temp);
                                            if (valorEnHold != null && tipoDatoEnHold != -58) {
                                                if (reduccion.nombreIzq.tipoReturn == tipoDatoEnHold || reduccion.nombreIzq.tipoReturn == -98) {
                                                    pilaParaAtributos.add(new Atributo(valorEnHold, reduccion.nombreIzq.tipoReturn, reduccion.nombreIzq));
                                                } else {
                                                    nodoAST.addError("Error en tipo de dato al utilizar la variable RESULT.");
                                                    pilaParaAtributos.add(new Atributo(null, reduccion.nombreIzq.tipoReturn, reduccion.nombreIzq));
                                                }
                                                valorEnHold = null;
                                                tipoDatoEnHold = -58;
                                            } else {
                                                pilaParaAtributos.add(new Atributo(null, reduccion.nombreIzq.tipoReturn, reduccion.nombreIzq));
                                            }
                                            System.out.println("SE AGREGA: " + reduccion.nombreIzq.nombre);
                                            int numeroConjunto = (int) pilaAnalisis.peek();
                                            pilaAnalisis.push(reduccion.nombreIzq.nombre);
                                            System.out.println("push-" + reduccion.nombreIzq.nombre);
                                            String nombre = reduccion.nombreIzq.nombre;
                                            for (itemTabla Transicion1 : TablaTransiciones) {
                                                try {
                                                    if (Transicion1.numeroEstado == numeroConjunto && ((NoTerminal) Transicion1.simboloConElQueSeDesplaza).nombre.equalsIgnoreCase(nombre)) {
                                                        if (Transicion1.tipo == 0) { //Es NoTerminal
                                                            pilaAnalisis.push(Transicion1.DestinoReduccionDesplazamiento);
                                                            if (Transicion1.accion != null) {
                                                                Transicion1.accion.ejecutar(Transicion1.numeroItem);
                                                            }
                                                            return "true";
                                                        }
                                                    }
                                                } catch (Exception e2) {
                                                    System.out.println("error aca?");
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    System.out.println("Se Desplaza");
                                    for (itemTabla TransicionAvailable : TransicionesAvailable) {
                                        if (TransicionAvailable.tipo == 1) { //Desplazamiento
                                            punteroToken++;
                                            try {
                                                System.out.println("D" + TransicionAvailable.DestinoReduccionDesplazamiento + ". " + ((Terminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre);
                                                NodoArbolSintactico temp = new NodoArbolSintactico(((Terminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre, 0);
                                                pilaArbolSintactico.add(temp);
                                                System.out.println("NoHijos: " + TransicionAvailable.numeroItem);
                                                pilaParaAtributos.add(new Atributo(null, ((Terminal) TransicionAvailable.simboloConElQueSeDesplaza).tipoReturn, (Terminal) TransicionAvailable.simboloConElQueSeDesplaza, Tokens.get(punteroToken - 1).valor, Tokens.get(punteroToken - 1).tipo));
                                                System.out.println("VALOR TERMINAL: " + Tokens.get(punteroToken - 1).valor);
                                                if (TransicionAvailable.accion != null) {
                                                    TransicionAvailable.accion.ejecutar(TransicionAvailable.numeroItem);
                                                }
                                            } catch (Exception e2) {
                                                System.out.println("D" + TransicionAvailable.DestinoReduccionDesplazamiento + ". " + ((NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre);
                                                pilaArbolSintactico.add(new NodoArbolSintactico(((NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre, 1));
                                                System.out.println("NoHijos: " + TransicionAvailable.numeroItem);
                                                pilaParaAtributos.add(new Atributo(null, ((NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza).tipoReturn, (NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza));
                                                if (TransicionAvailable.accion != null) {
                                                    Transicion.accion.ejecutar(TransicionAvailable.numeroItem);
                                                }
                                            }
                                            pilaAnalisis.push(nextToken.nombre);
                                            pilaAnalisis.push(TransicionAvailable.DestinoReduccionDesplazamiento);
                                            return "true";
                                        }
                                    }
                                }
                            } else {
                                if ((int) (valAnalizado % 1000) < (int) (valCurrent % 1000)) {
                                    System.out.println("Se Reduce");
                                    for (itemTabla TransicionAvailable : TransicionesAvailable) {
                                        if (TransicionAvailable.tipo != 1) {
                                            try {
                                                System.out.println("R" + TransicionAvailable.DestinoReduccionDesplazamiento + ". " + ((Terminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre);

                                            } catch (Exception e2) {
                                                System.out.println("R" + TransicionAvailable.DestinoReduccionDesplazamiento + ". " + ((NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre);
                                            }
                                            if (TransicionAvailable.DestinoReduccionDesplazamiento == -1) {
                                                System.out.println("ACEPTAR!!");
                                                return "aceptar";
                                            }
                                            int numReduccion = TransicionAvailable.DestinoReduccionDesplazamiento;
                                            Conjunto cero = Conjuntos.get(Conjuntos.size() - 1);
                                            ItemConjunto reduccion = cero.items.get(numReduccion);
                                            int numeroHijos = reduccion.hijos.size();
                                            NodoArbolSintactico temp = new NodoArbolSintactico(reduccion.nombreIzq.nombre, 0);
                                            for (int i = 0; i < numeroHijos; i++) {
                                                temp.addHijo(pilaArbolSintactico.pop());
                                                pilaAnalisis.pop();
                                                pilaAnalisis.pop();
                                                pilaParaAtributos.pop();
                                            }
                                            pilaArbolSintactico.push(temp);
                                            if (valorEnHold != null && tipoDatoEnHold != -58) {
                                                if (reduccion.nombreIzq.tipoReturn == tipoDatoEnHold || reduccion.nombreIzq.tipoReturn == -98) {
                                                    pilaParaAtributos.add(new Atributo(valorEnHold, reduccion.nombreIzq.tipoReturn, reduccion.nombreIzq));
                                                } else {
                                                    nodoAST.addError("Error en tipo de dato al utilizar la variable RESULT.");
                                                    pilaParaAtributos.add(new Atributo(null, reduccion.nombreIzq.tipoReturn, reduccion.nombreIzq));
                                                }
                                                valorEnHold = null;
                                                tipoDatoEnHold = -58;
                                            } else {
                                                pilaParaAtributos.add(new Atributo(null, reduccion.nombreIzq.tipoReturn, reduccion.nombreIzq));
                                            }
                                            System.out.println("SE AGREGA: " + reduccion.nombreIzq.nombre);
                                            int numeroConjunto = (int) pilaAnalisis.peek();
                                            pilaAnalisis.push(reduccion.nombreIzq.nombre);
                                            System.out.println("push-" + reduccion.nombreIzq.nombre);
                                            String nombre = reduccion.nombreIzq.nombre;
                                            for (itemTabla Transicion1 : TablaTransiciones) {
                                                try {
                                                    if (Transicion1.numeroEstado == numeroConjunto && ((NoTerminal) Transicion1.simboloConElQueSeDesplaza).nombre.equalsIgnoreCase(nombre)) {
                                                        if (Transicion1.tipo == 0) { //Es NoTerminal
                                                            pilaAnalisis.push(Transicion1.DestinoReduccionDesplazamiento);
                                                            if (Transicion1.accion != null) {
                                                                Transicion1.accion.ejecutar(Transicion1.numeroItem);
                                                            }
                                                            return "true";
                                                        }
                                                    }
                                                } catch (Exception e2) {
                                                    System.out.println("error aca?");
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    System.out.println("Se Desplaza");
                                    for (itemTabla TransicionAvailable : TransicionesAvailable) {
                                        if (TransicionAvailable.tipo == 1) { //Desplazamiento
                                            punteroToken++;
                                            try {
                                                System.out.println("D" + TransicionAvailable.DestinoReduccionDesplazamiento + ". " + ((Terminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre);
                                                NodoArbolSintactico temp = new NodoArbolSintactico(((Terminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre, 0);
                                                pilaArbolSintactico.add(temp);
                                                System.out.println("NoHijos: " + TransicionAvailable.numeroItem);
                                                pilaParaAtributos.add(new Atributo(null, ((Terminal) TransicionAvailable.simboloConElQueSeDesplaza).tipoReturn, (Terminal) TransicionAvailable.simboloConElQueSeDesplaza, Tokens.get(punteroToken - 1).valor, Tokens.get(punteroToken - 1).tipo));
                                                System.out.println("VALOR TERMINAL: " + Tokens.get(punteroToken - 1).valor);
                                                if (TransicionAvailable.accion != null) {
                                                    TransicionAvailable.accion.ejecutar(TransicionAvailable.numeroItem);
                                                }
                                            } catch (Exception e2) {
                                                System.out.println("D" + TransicionAvailable.DestinoReduccionDesplazamiento + ". " + ((NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre);
                                                pilaArbolSintactico.add(new NodoArbolSintactico(((NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre, 1));
                                                System.out.println("NoHijos: " + TransicionAvailable.numeroItem);
                                                pilaParaAtributos.add(new Atributo(null, ((NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza).tipoReturn, (NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza));
                                                if (TransicionAvailable.accion != null) {
                                                    Transicion.accion.ejecutar(TransicionAvailable.numeroItem);
                                                }
                                            }
                                            pilaAnalisis.push(nextToken.nombre);
                                            pilaAnalisis.push(TransicionAvailable.DestinoReduccionDesplazamiento);
                                            return "true";
                                        }
                                    }
                                }
                            }
                        } else {
                            if (valAnalizado > valCurrent) {
                                System.out.println("Se Reduce");
                                for (itemTabla TransicionAvailable : TransicionesAvailable) {
                                    if (TransicionAvailable.tipo != 1) {
                                        try {
                                            System.out.println("R" + TransicionAvailable.DestinoReduccionDesplazamiento + ". " + ((Terminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre);

                                        } catch (Exception e2) {
                                            System.out.println("R" + TransicionAvailable.DestinoReduccionDesplazamiento + ". " + ((NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre);
                                        }
                                        if (TransicionAvailable.DestinoReduccionDesplazamiento == -1) {
                                            System.out.println("ACEPTAR!!");
                                            return "aceptar";
                                        }
                                        int numReduccion = TransicionAvailable.DestinoReduccionDesplazamiento;
                                        Conjunto cero = Conjuntos.get(Conjuntos.size() - 1);
                                        ItemConjunto reduccion = cero.items.get(numReduccion);
                                        int numeroHijos = reduccion.hijos.size();
                                        NodoArbolSintactico temp = new NodoArbolSintactico(reduccion.nombreIzq.nombre, 0);
                                        for (int i = 0; i < numeroHijos; i++) {
                                            temp.addHijo(pilaArbolSintactico.pop());
                                            pilaAnalisis.pop();
                                            pilaAnalisis.pop();
                                            pilaParaAtributos.pop();
                                        }
                                        if (valorEnHold != null && tipoDatoEnHold != -58) {
                                            if (reduccion.nombreIzq.tipoReturn == tipoDatoEnHold || reduccion.nombreIzq.tipoReturn == -98) {
                                                pilaParaAtributos.add(new Atributo(valorEnHold, reduccion.nombreIzq.tipoReturn, reduccion.nombreIzq));
                                            } else {
                                                nodoAST.addError("Error en tipo de dato al utilizar la variable RESULT.");
                                                pilaParaAtributos.add(new Atributo(null, reduccion.nombreIzq.tipoReturn, reduccion.nombreIzq));
                                            }
                                            valorEnHold = null;
                                            tipoDatoEnHold = -58;
                                        } else {
                                            pilaParaAtributos.add(new Atributo(null, reduccion.nombreIzq.tipoReturn, reduccion.nombreIzq));
                                        }
                                        System.out.println("SE AGREGA: " + reduccion.nombreIzq.nombre);
                                        pilaArbolSintactico.push(temp);
                                        int numeroConjunto = (int) pilaAnalisis.peek();
                                        pilaAnalisis.push(reduccion.nombreIzq.nombre);
                                        System.out.println("push-" + reduccion.nombreIzq.nombre);
                                        String nombre = reduccion.nombreIzq.nombre;
                                        for (itemTabla Transicion1 : TablaTransiciones) {
                                            try {
                                                if (Transicion1.numeroEstado == numeroConjunto && ((NoTerminal) Transicion1.simboloConElQueSeDesplaza).nombre.equalsIgnoreCase(nombre)) {
                                                    if (Transicion1.tipo == 0) { //Es NoTerminal
                                                        pilaAnalisis.push(Transicion1.DestinoReduccionDesplazamiento);
                                                        if (Transicion1.accion != null) {
                                                            Transicion1.accion.ejecutar(Transicion1.numeroItem);
                                                        }
                                                        return "true";
                                                    }
                                                }
                                            } catch (Exception e2) {
                                                System.out.println("error aca?");
                                            }
                                        }
                                    }
                                }
                            } else {
                                System.out.println("Se Desplaza");
                                for (itemTabla TransicionAvailable : TransicionesAvailable) {
                                    if (TransicionAvailable.tipo == 1) { //Desplazamiento
                                        punteroToken++;
                                        try {
                                            System.out.println("D" + TransicionAvailable.DestinoReduccionDesplazamiento + ". " + ((Terminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre);
                                            NodoArbolSintactico temp = new NodoArbolSintactico(((Terminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre, 0);
                                            pilaArbolSintactico.add(temp);
                                            System.out.println("NoHijos: " + TransicionAvailable.numeroItem);
                                            pilaParaAtributos.add(new Atributo(null, ((Terminal) TransicionAvailable.simboloConElQueSeDesplaza).tipoReturn, (Terminal) TransicionAvailable.simboloConElQueSeDesplaza, Tokens.get(punteroToken - 1).valor, Tokens.get(punteroToken - 1).tipo));
                                            System.out.println("VALOR TERMINAL: " + Tokens.get(punteroToken - 1).valor);
                                            if (TransicionAvailable.accion != null) {
                                                TransicionAvailable.accion.ejecutar(TransicionAvailable.numeroItem);
                                            }
                                        } catch (Exception e2) {
                                            System.out.println("D" + TransicionAvailable.DestinoReduccionDesplazamiento + ". " + ((NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre);
                                            pilaArbolSintactico.add(new NodoArbolSintactico(((NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza).nombre, 1));
                                            System.out.println("NoHijos: " + TransicionAvailable.numeroItem);
                                            pilaParaAtributos.add(new Atributo(null, ((NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza).tipoReturn, (NoTerminal) TransicionAvailable.simboloConElQueSeDesplaza));
                                            if (TransicionAvailable.accion != null) {
                                                Transicion.accion.ejecutar(TransicionAvailable.numeroItem);
                                            }
                                        }
                                        pilaAnalisis.push(nextToken.nombre);
                                        pilaAnalisis.push(TransicionAvailable.DestinoReduccionDesplazamiento);
                                        return "true";
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (Transicion.numeroEstado == noConj && nom.equalsIgnoreCase(nextToken.nombre)) {
                        if (Transicion.tipo == 1) { //Desplazamiento
                            punteroToken++;
                            try {
                                System.out.println("D" + Transicion.DestinoReduccionDesplazamiento + ". " + ((Terminal) Transicion.simboloConElQueSeDesplaza).nombre);
                                if (Transicion.accion != null) {
                                    Transicion.accion.ejecutar(Transicion.numeroItem);
                                }
                            } catch (Exception e1) {
                                System.out.println("D" + Transicion.DestinoReduccionDesplazamiento + ". " + ((NoTerminal) Transicion.simboloConElQueSeDesplaza).nombre);
                                if (Transicion.accion != null) {
                                    Transicion.accion.ejecutar(Transicion.numeroItem);
                                }
                            }
                            pilaAnalisis.push(nextToken.nombre);
                            pilaAnalisis.push(Transicion.DestinoReduccionDesplazamiento);
                        } else { //Reduccion
                            try {
                                System.out.println("R" + Transicion.DestinoReduccionDesplazamiento + ". " + ((Terminal) Transicion.simboloConElQueSeDesplaza).nombre);
                            } catch (Exception e1) {
                                System.out.println("R" + Transicion.DestinoReduccionDesplazamiento + ". " + ((NoTerminal) Transicion.simboloConElQueSeDesplaza).nombre);
                            }
                            if (Transicion.DestinoReduccionDesplazamiento == -1) {
                                System.out.println("ACEPTAR!!");
                                return "aceptar";
                            }
                            int numReduccion = Transicion.DestinoReduccionDesplazamiento;
                            Conjunto cero = Conjuntos.get(0);
                            ItemConjunto reduccion = cero.items.get(numReduccion);
                            int numeroHijos = reduccion.hijos.size();
                            for (int i = 0; i < numeroHijos; i++) {
                                pilaAnalisis.pop();
                                pilaAnalisis.pop();
                                pilaParaAtributos.pop();
                            }
                            int numeroConjunto = (int) pilaAnalisis.peek();
                            if (valorEnHold != null && tipoDatoEnHold != -58) {
                                if (reduccion.nombreIzq.tipoReturn == tipoDatoEnHold || reduccion.nombreIzq.tipoReturn == -98) {
                                    pilaParaAtributos.add(new Atributo(valorEnHold, reduccion.nombreIzq.tipoReturn, reduccion.nombreIzq));
                                } else {
                                    nodoAST.addError("Error en tipo de dato al utilizar la variable RESULT.");
                                    pilaParaAtributos.add(new Atributo(null, reduccion.nombreIzq.tipoReturn, reduccion.nombreIzq));
                                }
                                valorEnHold = null;
                                tipoDatoEnHold = -58;
                            } else {
                                pilaParaAtributos.add(new Atributo(null, reduccion.nombreIzq.tipoReturn, reduccion.nombreIzq));
                            }
                            pilaAnalisis.push(reduccion.nombreIzq.nombre);
                            System.out.println("push-" + reduccion.nombreIzq.nombre);
                            String nombre = reduccion.nombreIzq.nombre;
                            for (itemTabla Transicion1 : TablaTransiciones) {
                                try {
                                    if (Transicion1.numeroEstado == numeroConjunto && ((NoTerminal) Transicion1.simboloConElQueSeDesplaza).nombre.equalsIgnoreCase(nombre)) {
                                        if (Transicion1.tipo == 0) { //Es NoTerminal
                                            pilaAnalisis.push(Transicion1.DestinoReduccionDesplazamiento);
                                            if (Transicion1.accion != null) {
                                                Transicion1.accion.ejecutar(Transicion1.numeroItem);
                                            }
                                            return "true";
                                        }
                                    }
                                } catch (Exception e1) {
                                    System.out.println("error aca?");
                                }
                            }
                        }
                        System.out.println("true");
                        return "true";
                    }
                }
            }
        }
        return "Error en la linea " + nextToken.line + " y columna " + nextToken.row + ". No se esperaba el token " + nextToken.nombre;
    }

    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "?";
    }

    public static void LR1() {
//        for (NoTerminal NoTerminal : NoTerminales) {
//            System.out.println("NoTerminal: " + NoTerminal.nombre);
//            for (int i = 0; i < NoTerminal.derivaciones.size(); i++) {
//                for (int j = 0; j < NoTerminal.derivaciones.get(i).size(); j++) {
//                    System.out.println("i:" + i);
//                    try {
//                        System.out.println(((Terminal) NoTerminal.derivaciones.get(i).get(j)).nombre);
//                    } catch (Exception e) {
//                        System.out.println(((NoTerminal) NoTerminal.derivaciones.get(i).get(j)).nombre);
//                    }
//                }
//            }
//        }

        //Crear Terminal $
        dolar = new Terminal("$");
        Terminales.add(dolar);
        //Aumentar Gramatica
        NoTerminal extra = new NoTerminal("extra");
        extra.addDerivacion();
        extra.addHijo(inicial, null);
        NoTerminales.add(extra);
        //Conjunto Inicial
        Cerradura(extra, 0);
        //Iterar
        auxIr_A = new ArrayList<>();
        int limite = Conjuntos.size();
        int i = 0;
        while (i < limite) {
            for (int j = 0; j < Conjuntos.get(i).items.size(); j++) {
                Conjunto conj = Conjuntos.get(i);
                ItemConjunto item = conj.items.get(j);
                if (item.hijos.size() != item.puntero) {
                    String simbolos = "";
                    for (Terminal simbolo : item.simbolos) {
                        simbolos += simbolo.nombre;
                    }
                    boolean seCreoOtro = Ir_a(simbolos, conj, item.hijos.get(item.puntero), item.acciones.get(item.puntero), item.hijos.size());
                    if (seCreoOtro) {
                        limite++;
                    }
                }
            }
            i++;
        }
        addReducciones();

        mostrarConjuntos();
    }

    public static void addReducciones() {
        int size = Conjuntos.size();
        int i = NoTerminales.size() - 1;
        Conjunto paraComparacion = new Conjunto(-313);
        ItemConjunto ini;
        NoTerminal NoTerminal = NoTerminales.get(i);
        for (int j = 0; j < NoTerminal.derivaciones.size(); j++) {
            ini = new ItemConjunto(NoTerminal.nombre, 0);
            for (int k = 0; k < NoTerminal.derivaciones.get(j).size(); k++) {
                ini.addHijo(NoTerminal.derivaciones.get(j).get(k), null);
            }
            ini.simbolos.add(dolar);
            paraComparacion.addItem(ini);
        }
        for (i = 0; i < NoTerminales.size() - 1; i++) {
            NoTerminal = NoTerminales.get(i);
            if (NoTerminal.derivaciones != null) {
                for (int j = 0; j < NoTerminal.derivaciones.size(); j++) {
                    ini = new ItemConjunto(NoTerminal.nombre, 0);
                    for (int k = 0; k < NoTerminal.derivaciones.get(j).size(); k++) {
                        ini.addHijo(NoTerminal.derivaciones.get(j).get(k), null);
                    }
                    ini.simbolos.add(dolar);
                    paraComparacion.addItem(ini);
                }
            }
        }
        Conjuntos.add(paraComparacion);

        for (int a = 0; a < size; a++) {
            Conjunto Conjunto = Conjuntos.get(a);

            if (Conjunto.numeroConjunto != 0) {
                for (i = 0; i < Conjunto.items.size(); i++) {
                    ItemConjunto item = Conjunto.items.get(i);
                    if (item.puntero == item.hijos.size()) {
//                        System.out.println("conj:" + Conjunto.numeroConjunto + ". item#" + i);
                        if (Conjunto.numeroConjunto == 1 && i == 0) {
                            TablaTransiciones.add(new itemTabla(Conjunto.numeroConjunto, 2, dolar, -1, null, item.hijos.size()));
                        } else {
                            master:
                            for (int j = 1; j < Conjuntos.get(Conjuntos.size() - 1).items.size(); j++) {
                                if (item.hijos.size() != Conjuntos.get(Conjuntos.size() - 1).items.get(j).hijos.size()) {
//                                    System.out.println("falla2 ->" + j);
                                } else {
                                    for (int k = 0; k < Conjuntos.get(Conjuntos.size() - 1).items.get(j).hijos.size(); k++) {
                                        Object hijo0 = Conjuntos.get(Conjuntos.size() - 1).items.get(j).hijos.get(k);
                                        Object hijoAct = item.hijos.get(k);
                                        if (isNonTerminal(hijoAct) && isNonTerminal(hijo0)) {
                                            NoTerminal cero = (NoTerminal) hijo0;
                                            NoTerminal act = (NoTerminal) hijoAct;
                                            if (!cero.nombre.equalsIgnoreCase(act.nombre)) {
//                                                System.out.println("falla3 ->" + j);
                                                continue master;
                                            }
                                        } else if (!isNonTerminal(hijoAct) && !isNonTerminal(hijo0)) {
                                            Terminal cero = (Terminal) hijo0;
                                            Terminal act = (Terminal) hijoAct;
                                            if (!cero.nombre.equalsIgnoreCase(act.nombre)) {
//                                                System.out.println("falla4 ->" + j);
                                                continue master;
                                            }
                                        } else {
//                                            System.out.println("falla5 ->" + j);
                                            continue master;
                                        }
                                    }
//                                    System.out.println("concordo con " + j);

//                                    numeroReduccion = j;
                                    for (Terminal simbolo : item.simbolos) {
//                                        System.out.println("addTransicion:" + Conjunto.numeroConjunto + "," + 2 + "," + simbolo.nombre + "," + j);
                                        TablaTransiciones.add(new itemTabla(Conjunto.numeroConjunto, 2, simbolo, j, null, item.hijos.size()));
                                    }
                                    break;
                                }

                            }
                        }
                    }
                }

            }
        }

    }

    public static boolean Cerradura(NoTerminal I, int primero) {
        if (primero == 0) {
            Conjunto nuevo = new Conjunto(0);
            ItemConjunto ini = new ItemConjunto("extra", 0);
            ini.addHijo(inicial, null);
            ini.addSimbolo(dolar);
            nuevo.addItem(ini);
            nuevo.addOtrosHijos();
            Conjuntos.add(nuevo);
            return true;
        }
        return false;
    }

    public static boolean Ir_a(String simbolos, Conjunto I, Object x, nodoAST accion, int numeroItem) {
        String nombre;

        int tipo;
        try {
            nombre = ((NoTerminal) x).nombre;
            tipo = 0;
        } catch (Exception e) {
            nombre = ((Terminal) x).nombre;
            tipo = 1;
        }
        if (!auxIr_A.contains(I.numeroConjunto + nombre)) {
            auxIr_A.add(I.numeroConjunto + nombre);
            Conjunto nuevo = new Conjunto(Conjuntos.size());
            auxConjuntos = new ArrayList<>();
            for (ItemConjunto item : I.items) {
//                System.out.println(item.nombreIzq.nombre + " itera, " + item.puntero + ". No. Conjuntos: " + Conjuntos.size());
//                System.out.println("I" + I.numeroConjunto + ". Nuevo #" + nuevo.numeroConjunto + ". X.nombre: " + nombre);
                ItemConjunto ini;
//                System.out.println("Puntero: " + item.puntero + ". Hijos size: " + item.hijos.size() + ". IzqNom: " + item.nombreIzq.nombre);
                if (item.puntero != item.hijos.size()) {

                    try {
//                    System.out.println(">>-->>" + ((NoTerminal) (item.hijos.get(item.puntero))).nombre);

                        if (((NoTerminal) (item.hijos.get(item.puntero))).nombre.equalsIgnoreCase(nombre)) {
                            ini = new ItemConjunto(item.nombreIzq.nombre, item.puntero + 1);
                            for (Terminal simbolo : item.simbolos) {
                                ini.addSimbolo(simbolo);
                            }
                            for (int i = 0; i < item.hijos.size(); i++) {
                                ini.addHijo(item.hijos.get(i), item.acciones.get(i));
                            }
                            nuevo.addItem(ini);
                            nuevo.addOtrosHijos();
                        }
                    } catch (Exception e) {
//                    System.out.println(">>-->>" + ((Terminal) (item.hijos.get(item.puntero))).nombre);

                        if (((Terminal) (item.hijos.get(item.puntero))).nombre.equalsIgnoreCase(nombre)) {
                            ini = new ItemConjunto(item.nombreIzq.nombre, item.puntero + 1);
                            for (Terminal simbolo : item.simbolos) {
                                ini.addSimbolo(simbolo);
                            }
                            for (int i = 0; i < item.hijos.size(); i++) {
                                ini.addHijo(item.hijos.get(i), item.acciones.get(i));
                            }
                            nuevo.addItem(ini);
                            nuevo.addOtrosHijos();
                        }

                    }
                }
            }
            Conjunto existe = existeConjunto(nuevo);
//            System.out.println("veces: " + veces);
//            if (veces > 1) {
//                for (ItemConjunto item : nuevo.items) {
//                    System.out.println("" + item.nombreIzq + ", size: " + item.hijos.size() + ", puntero: " + item.puntero);
//                }
//            }
            if (existe != null) {
                TablaTransiciones.add(new itemTabla(I.numeroConjunto, tipo, x, existe.numeroConjunto, accion, numeroItem));
                return false;
            } else {
                TablaTransiciones.add(new itemTabla(I.numeroConjunto, tipo, x, nuevo.numeroConjunto, accion, numeroItem));
                Conjuntos.add(nuevo);
                return true;
            }
        } else {
            return false;
        }
    }

    public static Conjunto existeConjunto(Conjunto nuevo) {
        gigante:
        for (Conjunto Conjunto : Conjuntos) {
            if (Conjunto.items.size() == nuevo.items.size()) {
                for (int i = 0; i < Conjunto.items.size(); i++) {
                    ItemConjunto itemConj = Conjunto.items.get(i);
                    ItemConjunto itemNuevo = nuevo.items.get(i);
                    if (!itemConj.nombreIzq.nombre.equalsIgnoreCase(itemNuevo.nombreIzq.nombre)) {
                        continue gigante;
                    }
                    if (itemConj.puntero != itemNuevo.puntero) {
                        continue gigante;
                    }
                    if (itemConj.hijos.size() == itemNuevo.hijos.size()) {
                        for (int j = 0; j < itemConj.hijos.size(); j++) {
                            Object hijoConj = itemConj.hijos.get(j);
                            Object hijoNuevo = itemNuevo.hijos.get(j);
                            if (isNonTerminal(hijoConj) && isNonTerminal(hijoNuevo)) {
                                NoTerminal NTConj = (NoTerminal) hijoConj;
                                NoTerminal NTNuevo = (NoTerminal) hijoNuevo;
                                if (!NTConj.nombre.equalsIgnoreCase(NTNuevo.nombre)) {
                                    continue gigante;
                                }
                            } else if (!isNonTerminal(hijoConj) && !isNonTerminal(hijoNuevo)) {
                                Terminal TConj = (Terminal) hijoConj;
                                Terminal TNuevo = (Terminal) hijoNuevo;
                                if (!TConj.nombre.equalsIgnoreCase(TNuevo.nombre)) {
                                    continue gigante;
                                }
                            } else {
                                continue gigante;
                            }
                        }
                    } else {
                        continue gigante;
                    }
                    if (itemConj.simbolos.size() == itemNuevo.simbolos.size()) {
                        for (int j = 0; j < itemConj.simbolos.size(); j++) {
                            Terminal SConj = itemConj.simbolos.get(j);
                            Terminal SNuevo = itemNuevo.simbolos.get(j);
                            if (!SConj.nombre.equalsIgnoreCase(SNuevo.nombre)) {
                                continue gigante;
                            }
                        }
                    } else {
                        continue gigante;
                    }
                    return Conjunto;
                }
            }
        }
        return null;
    }

    public static void mostrarConjuntos() {
        System.out.println("**************XML**************");
        String xml = "<reporte>";
        xml += "\n    <estados>";
        for (int j = 0; j < Conjuntos.size() - 1; j++) {
            xml += "\n        <estado>";
            xml += "\n            <nombre>I" + Conjuntos.get(j).numeroConjunto + "</nombre>";
            xml += "\n            <elementos>";
//            System.out.println("Conjunto Numero: " + Conjuntos.get(j).numeroConjunto + ". j: " + j);
//            System.out.println("Hijos:");
            for (int k = 0; k < Conjuntos.get(j).items.size(); k++) {
                xml += "\n                <elemento>";
                xml += "\n                    <noterminal>" + Conjuntos.get(j).items.get(k).nombreIzq.nombre + "</noterminal";
//                System.out.println("   -Nombre Hijo: " + Conjuntos.get(j).items.get(k).nombreIzq.nombre);
//                System.out.println("     Puntero: " + Conjuntos.get(j).items.get(k).puntero);
//                System.out.println("     Hijos:");
                xml += "\n                    <produccion>";
                for (int l = 0; l < Conjuntos.get(j).items.get(k).hijos.size(); l++) {
                    if (l == Conjuntos.get(j).items.get(k).puntero) {
                        xml += ".";
                    }
                    try {
                        xml += ((NoTerminal) Conjuntos.get(j).items.get(k).hijos.get(l)).nombre;
//                        System.out.println("       Nombre Hijo: " + ((NoTerminal) Conjuntos.get(j).items.get(k).hijos.get(l)).nombre);
                    } catch (Exception e) {
                        xml += ((Terminal) Conjuntos.get(j).items.get(k).hijos.get(l)).nombre;
//                        System.out.println("       Nombre Hijo: " + ((Terminal) Conjuntos.get(j).items.get(k).hijos.get(l)).nombre);
                    }
                    if (l + 1 == Conjuntos.get(j).items.get(k).puntero && l + 1 == Conjuntos.get(j).items.get(k).hijos.size()) {
                        xml += ".";
                    }
                }
                xml += "</produccion>";

//              
//                  System.out.println("    Simbolos:");
                for (int l = 0; l < Conjuntos.get(j).items.get(k).simbolos.size(); l++) {
//                    System.out.println("       Nombre Simbolo: " + Conjuntos.get(j).items.get(k).simbolos.get(l).nombre);
                    xml += "\n                    <anticipado>";
                    xml += Conjuntos.get(j).items.get(k).simbolos.get(l).nombre;
                    xml += "</anticipado>";
                }
                xml += "\n                </elemento>";
            }
            xml += "\n            </elementos>";
            xml += "\n        </estado>";

        }
        xml += "\n    </estados>";
        xml += "\n    <operaciones_ir_a>";
//        for (itemTabla Transicion : TablaTransiciones) {
//            if (Transicion.tipo == 0) { //No Terminal
//                System.out.println("I" + Transicion.numeroEstado + "," + ((NoTerminal) (Transicion.simboloConElQueSeDesplaza)).nombre + " = " + Transicion.DestinoReduccionDesplazamiento + ". Hijos: " + Transicion.numeroItem);
//                if (Transicion.accion != null) {
//                    System.out.println(".." + Transicion.accion.nombre);
//                }
//            } else if (Transicion.tipo == 1) { //Terminal
//                System.out.println("I" + Transicion.numeroEstado + "," + ((Terminal) (Transicion.simboloConElQueSeDesplaza)).nombre + " = D" + Transicion.DestinoReduccionDesplazamiento + ". Hijos: " + Transicion.numeroItem);
//                if (Transicion.accion != null) {
//                    System.out.println(".." + Transicion.accion.nombre);
//                }
//            } else {
//                if (Transicion.DestinoReduccionDesplazamiento == -1) {
//                    System.out.println("I" + Transicion.numeroEstado + "," + ((Terminal) (Transicion.simboloConElQueSeDesplaza)).nombre + " = Aceptar");
//                    if (Transicion.accion != null) {
//                        System.out.println(".." + Transicion.accion.nombre);
//                    }
//                } else {
//                    System.out.println("I" + Transicion.numeroEstado + "," + ((Terminal) (Transicion.simboloConElQueSeDesplaza)).nombre + " = R" + Transicion.DestinoReduccionDesplazamiento);
//                    if (Transicion.accion != null) {
//                        System.out.println(".." + Transicion.accion.nombre);
//                    }
//                }
//            }
//        }
        for (itemTabla Transicion : TablaTransiciones) {
            if (Transicion.tipo == 0) {
                xml += "\n        <ir_a>";
                xml += "\n            <desde>";
                xml += "I" + Transicion.numeroEstado;
                xml += "</desde>";
                xml += "\n            <mediante>";
                try {
                    xml += ((Terminal) (Transicion.simboloConElQueSeDesplaza)).nombre;
                } catch (Exception e) {
                    xml += ((NoTerminal) (Transicion.simboloConElQueSeDesplaza)).nombre;
                }
                xml += "</mediante>";
                xml += "\n            <destino>";
                xml += "I" + Transicion.DestinoReduccionDesplazamiento;
                xml += "</destino>";
                xml += "\n        </ir_a>";
            } else if (Transicion.tipo == 1) {
                xml += "\n        <ir_a>";
                xml += "\n            <desde>";
                xml += "I" + Transicion.numeroEstado;
                xml += "</desde>";
                xml += "\n            <mediante>";
                try {
                    xml += ((Terminal) (Transicion.simboloConElQueSeDesplaza)).nombre;
                } catch (Exception e) {
                    xml += ((NoTerminal) (Transicion.simboloConElQueSeDesplaza)).nombre;
                }
                xml += "</mediante>";
                xml += "\n            <destino>";
                xml += "I" + Transicion.DestinoReduccionDesplazamiento;
                xml += "</destino>";
                xml += "\n        </ir_a>";
            }
        }
        xml += "\n    </operaciones_ir_a>";
        xml += "\n</reporte>";
        Graphviz g = new Graphviz();
        g.crearXML(xml, "Registro de Operaciones");
    }

    public static ArrayList primero(Object sim) {
        Primeros = new ArrayList<>();
        listaAux = new ArrayList<>();
        primeroAux(sim);
//        for (int i = 0; i < Primeros.size(); i++) {
//            System.out.println(">>>" + Primeros.get(i).nombre);
//        }
        return Primeros;
    }

    static void primeroAux(Object sim) {
        if (!listaAux.contains(sim)) {
            listaAux.add(sim);
            if (isNonTerminal(sim)) {
                NoTerminal x = (NoTerminal) sim;
                if (x.derivaciones != null) {
                    for (ArrayList<Object> derivacion : x.derivaciones) {
                        primeroAux(derivacion.get(0));
                    }
                }
            } else {
                Primeros.add((Terminal) sim);
            }
        }
    }

    public static boolean isNonTerminal(Object testSubject) {
        try {
            NoTerminal test = (NoTerminal) testSubject;
            int a = test.tipoReturn;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Simbolo buscarSimbolo(String nombre) {
        for (Simbolo TablaSimbolo : TablaSimbolos) {
            if (TablaSimbolo.nombre.equalsIgnoreCase(nombre)) {
                return TablaSimbolo;
            }
        }
        return null;
    }

    public static void addNoTerminal(NoTerminal noterminal) {
        NoTerminales.add(noterminal);
    }

    public static void addTerminal(Terminal terminal) {
        Terminales.add(terminal);

    }

    public static NoTerminal getNoTerminal(String nombre) {
        for (NoTerminal nt : NoTerminales) {
            if (nt.nombre.equalsIgnoreCase(nombre)) {
                return nt;
            }
        }
        return null;
    }

    public static NoTerminal getInicial() {
        return inicial;
    }

    public static Object getObject(String nombre) {
        for (Terminal t : Terminales) {
            if (t.nombre.equalsIgnoreCase(nombre)) {
                return t;
            }
        }
        for (NoTerminal nt : NoTerminales) {
            if (nt.nombre.equalsIgnoreCase(nombre)) {
                return nt;
            }
        }
        return null;
    }

    public static void setInicial(String nombre) {
        for (NoTerminal nt : NoTerminales) {
            if (nt.nombre.equalsIgnoreCase(nombre)) {
                Compi2_Proyecto1_1s2016.inicial = nt;
                break;
            }
        }
    }

}
