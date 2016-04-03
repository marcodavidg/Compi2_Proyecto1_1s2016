/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools |     Templates
 * and open the template in the editor.
 */
package compi2_proyecto1_1s2016;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Mac
 */
public class nodoAST {

    public static int numeroHijos;
    public String nombre;
    public ArrayList<nodoAST> hijos;
    public String tipo;
    public int tipoDato;
    public int salto; //para el FOR
    public int posicionEnPila; //para atributos
    public static int tipoDatoAComparar;
    public static Object valorAComparar;
    /*
    
    
     tipo
     identifier
     num
     arreglo
     break
     dimension
     variables
    
     suma
     resta
     division
     multiplicacion
     potencia
     aumento
     decremento
    
     mayor
     mayorigual
     menor
     menorigual
     diferente
     igual
    
     or
     xor
     and
     not
    
     sentencias
     body
     if
     elseif
     else
     switch
     default
     case
     write
     for
     while
     metodo
     parametros
     parametro
     */
    public Object valor;

    public nodoAST(String nombre, String tipo, Object valor) {
        this.nombre = nombre;
        hijos = null;
        this.tipo = tipo;
        this.valor = valor;
    }

    public nodoAST(String nombre, String tipo, Object valor, nodoAST... hijos) {
        this.nombre = nombre;
        this.hijos = new ArrayList<>();
        this.hijos.addAll(Arrays.asList(hijos));
        this.tipo = tipo;
        this.valor = valor;
    }

    public void setTipoDato(int tipo) {
        this.tipoDato = tipo;
        /*
         -1 = error
         1 = int
         2 = double
         3 = string
         4 = char
         5 = bool
         6 = float
         */
    }

    public void addHijo(nodoAST hijo) {
        this.hijos.add(hijo);
    }

    public void setSalto(int salto) {
        this.salto = salto;
    }

    public void ejecutar() {
        ejecutar(false);
    }

    public void ejecutar(int numeroHijos) {
        nodoAST.numeroHijos = numeroHijos;
        ejecutar(false);
    }

    public void ejecutar(boolean ejecutarMetodo) {
        nodoAST hijo1, hijo2;
        switch (this.tipo) {
            case "sentencias":
                for (nodoAST hijo : this.hijos) {
                    hijo.ejecutar();
                    if (hijo.tipoDato == -1) {
                        break;
                    }
                }
                System.out.println("RESULTADO::\n\n" + Compi2_Proyecto1_1s2016.cadenaReturn);
                System.out.println("ERRORES::\n\n" + Compi2_Proyecto1_1s2016.cadenaErrores);
                break;
            case "identifier":
                this.tipoDato = 0;
                if (Compi2_Proyecto1_1s2016.pilaLlamadas.isEmpty()) {
                    Simbolo sim = Compi2_Proyecto1_1s2016.buscarSimbolo(this.nombre);
                    if (sim != null) {
                        this.valor = sim.valor;
                        this.tipoDato = sim.tipoDato;
                    } else {
                        this.tipoDato = -1;
                        addError("Variable " + this.nombre + " no encontrada.");
                    }
                } else {
                    Parametro a = null;
                    ArrayList<Parametro> ultimaLlamada = Compi2_Proyecto1_1s2016.pilaLlamadas.peek();
                    for (Parametro parametro : ultimaLlamada) {
                        System.out.println("param:" + parametro.nombre + "." + this.nombre);
                        if (parametro.nombre.equalsIgnoreCase(this.nombre)) {
                            a = parametro;
                            break;
                        }
                    }
                    if (a != null) {
                        System.out.println("this.valor:" + a.valor);
                        this.valor = a.valor;
                        this.tipoDato = a.tipoDato;
                    } else {
                        Simbolo sim = Compi2_Proyecto1_1s2016.buscarSimbolo(this.nombre);
                        if (sim != null) {
                            this.valor = sim.valor;
                            this.tipoDato = sim.tipoDato;
                        } else {
                            this.tipoDato = -1;
                            addError("Variable " + this.nombre + " no encontrada.");
                        }
                    }
                }
                break;
            case "body":
                this.tipoDato = 0;
                for (nodoAST hijo : this.hijos) {
                    hijo.ejecutar();
                    if (hijo.tipoDato == -1) {
                        this.tipoDato = -1;
                        break;
                    } else if (hijo.tipoDato == -2) {
                        this.tipoDato = -2;
                        break;
                    } else if (hijo.tipoDato == -3) { //return
                        this.valor = hijo.valor;
                        this.tipoDato = -3;
                        break;
                    }
                }
                break;
            case "arreglo/funcion":
                this.tipoDato = 0;
                hijo1 = this.hijos.get(0);
                hijo2 = this.hijos.get(1);
                this.nombre = hijo1.nombre;
                Simbolo simTemp = Compi2_Proyecto1_1s2016.buscarSimbolo(hijo1.nombre);
                if (simTemp != null) {
                    if (simTemp.isArreglo()) {
                        if (hijo2.hijos.size() == simTemp.numeroDimensiones) {
                            int[] dimensiones = new int[simTemp.numeroDimensiones];
                            boolean hayError = false;
                            for (int i = 0; i < simTemp.numeroDimensiones; i++) {
                                nodoAST item = hijo2.hijos.get(i);
                                item.ejecutar();
                                if (item.tipoDato == 1) {
                                    dimensiones[i] = (int) item.valor;
                                } else {
                                    addError("El dato proporcionado para la dimension #" + i + " del arreglo " + simTemp.nombre + " no es de tipo int.");
                                    hayError = true;
                                }
                            }
                            if (!hayError) {
                                System.out.println("dim:" + dimensiones.length);
                                this.valor = simTemp.getObjectArreglo(dimensiones);
                                this.tipoDato = simTemp.tipoDato;
                            } else {
                                this.tipoDato = -1;
                            }
                        } else {
                            addError("El numero proporcionado de dimensiones para el arreglo " + simTemp.nombre + " es invalido.");
                            this.tipoDato = -1;
                        }
                    } else if (simTemp.isMetodo()) {
                        System.out.println("El metodo tiene " + simTemp.numeroParametros + " parametros.");
                        boolean error = false;
                        if (this.hijos.get(1).hijos != null) {
                            if (this.hijos.get(1).hijos.size() == simTemp.numeroParametros) {
                                for (int i = 0; i < simTemp.numeroParametros; i++) {
                                    this.hijos.get(1).hijos.get(i).ejecutar();
                                    if (simTemp.parametros.get(i).tipoDato == this.hijos.get(1).hijos.get(i).tipoDato) {
                                        simTemp.parametros.get(i).valor = this.hijos.get(1).hijos.get(i).valor;
                                    } else {
                                        this.tipoDato = -1;
                                        addError("El parametro #" + i + " introducido para el metodo/funcion " + simTemp.nombre + " son invalidos.");
                                        error = true;
                                        break;
                                    }
                                }
                                if (!error) {
                                    ArrayList<Parametro> newA = new ArrayList<>();
                                    for (Parametro parametro : simTemp.parametros) {
                                        Parametro temp = new Parametro(parametro.nombre, parametro.tipoDato);
                                        temp.valor = parametro.valor;
                                        newA.add(temp);
                                    }
                                    Compi2_Proyecto1_1s2016.pilaLlamadas.push(newA);
                                    String retornoAux = Compi2_Proyecto1_1s2016.cadenaReturn;
                                    simTemp.mainNodo.ejecutar();
                                    if (simTemp.tipoDato != 1711) {
                                        if (simTemp.mainNodo.tipoDato == -3) {
                                            nodoAST retorno = (nodoAST) simTemp.mainNodo.valor;
                                            if (retorno.tipoDato == simTemp.tipoDato) {
                                                this.valor = retorno.valor;
                                                System.out.println("rV:" + retorno.valor);
                                                this.tipoDato = retorno.tipoDato;
                                            } else {
                                                this.tipoDato = -1;
                                                addError("La sentencia return para el metodo " + simTemp.nombre + " arroja un valor invalido.");
                                                Compi2_Proyecto1_1s2016.cadenaReturn = retornoAux;
                                            }
                                        } else {
                                            this.tipoDato = -1;
                                            System.out.println("::" + simTemp.mainNodo.tipo);
                                            addError("No se encontro sentencia return para el metodo " + simTemp.nombre);
                                            Compi2_Proyecto1_1s2016.cadenaReturn = retornoAux;
                                        }
                                    }
                                    Compi2_Proyecto1_1s2016.pilaLlamadas.pop();
                                }
                            }
                        } else {
                            this.tipoDato = -1;
                            addError("El numero proporcionado de parametros para el metodo/funcion " + simTemp.nombre + " es invalido. Numero de parametros: " + simTemp.numeroParametros + ". Parametros introducidos: " + this.hijos.get(1).hijos.size() + ".");
                        }
                    }
                } else {
                    this.tipoDato = -1;
                    addError("La variable o metodo/funcion " + hijo1.nombre + " no fue encontrada.");
                }
                break;
            case "parametros":

                break;
            case "parametro":

                break;
            case "metodo":
                this.tipoDato = 0;
                hijo1 = this.hijos.get(0);
                hijo2 = this.hijos.get(1);
                Simbolo tempMetodo = new Simbolo(hijo1.nombre, null, 0, 1711);
                tempMetodo.makeMetodo();
                if (this.hijos.size() == 2) {
                    tempMetodo.mainNodo = hijo2;
                } else {
                    for (nodoAST parametro : hijo2.hijos) {
                        nodoAST id = parametro.hijos.get(0);
                        nodoAST type = parametro.hijos.get(1);
                        type.ejecutar();
                        System.out.println("parametro: " + id.nombre + "," + type.valor);
                        tempMetodo.addParametro(id.nombre, (int) type.valor);
                    }
                    tempMetodo.mainNodo = this.hijos.get(2);
                }
                Compi2_Proyecto1_1s2016.TablaSimbolos.add(tempMetodo);
                break;
            case "funcion":
                this.tipoDato = 0;
                hijo1 = this.hijos.get(0); //identifier
                hijo2 = this.hijos.get(1); //parametros o tipo
                nodoAST hijoA3 = this.hijos.get(2); //tipo o body
                if (this.hijos.size() == 3) {
                    hijo2.ejecutar();
                    Simbolo tempFuncion = new Simbolo(hijo1.nombre, null, 0, (int) hijo2.valor);
                    tempFuncion.makeMetodo();
                    tempFuncion.mainNodo = hijoA3;
                    Compi2_Proyecto1_1s2016.TablaSimbolos.add(tempFuncion);
                } else {
                    nodoAST hijo4 = this.hijos.get(3); // body
                    hijoA3.ejecutar();
                    Simbolo tempFuncion = new Simbolo(hijo1.nombre, null, 0, (int) hijoA3.valor);
                    tempFuncion.makeMetodo();
                    for (nodoAST parametro : hijo2.hijos) {
                        nodoAST id = parametro.hijos.get(0);
                        nodoAST type = parametro.hijos.get(1);
                        type.ejecutar();
                        System.out.println("parametro: " + id.nombre + "," + type.valor);
                        tempFuncion.addParametro(id.nombre, (int) type.valor);
                    }
                    tempFuncion.mainNodo = hijo4;
                    Compi2_Proyecto1_1s2016.TablaSimbolos.add(tempFuncion);
                }

                break;
            case "return":
                this.hijos.get(0).ejecutar();
                this.valor = this.hijos.get(0);
                this.tipoDato = -3;
                break;
            case "result":
                this.tipoDato = 0;
                int index = 0;
                hijo1 = this.hijos.get(0);
                int numero = (int) hijo1.valor;
                if (numero == 0) {
//                    System.out.println("RESULT!!:: Se guarda.");
                    this.valor = null;
                    this.posicionEnPila = Compi2_Proyecto1_1s2016.pilaParaAtributos.size();
                    this.tipoDato = 10;
                } else {
                    if (Compi2_Proyecto1_1s2016.pilaParaAtributos.size() < numeroHijos) {
                        index = numero - 1;
                    } else {
                        index = Compi2_Proyecto1_1s2016.pilaParaAtributos.size() - numeroHijos + numero - 1;
                    }
                    this.posicionEnPila = index;
                    System.out.println("index:" + index + "=" + Compi2_Proyecto1_1s2016.pilaParaAtributos.size() + "-" + numeroHijos + "+" + numero + "-1");
                    Atributo selected = Compi2_Proyecto1_1s2016.pilaParaAtributos.get(index);
                    this.tipoDato = selected.tipo;
                    this.valor = selected.valor;
                }
                break;
            case "asignacion":
//                Simbolo simTempAsignacion;
                this.tipoDato = 0;
                hijo1 = this.hijos.get(0);
                hijo2 = this.hijos.get(1);
                hijo1.ejecutar();
                hijo2.ejecutar();
                String identifier = "" + hijo1.nombre;
                Simbolo simTempAsignacion = Compi2_Proyecto1_1s2016.buscarSimbolo(identifier);

                Object expresion = hijo2.valor;
                if (expresion != null) {
                    if (hijo1.tipo.equalsIgnoreCase("result")) {
                        if (hijo1.tipoDato == 10) { //Solo se guarda para asignarlo luego
                            Compi2_Proyecto1_1s2016.valorEnHold = hijo2.valor;
                            Compi2_Proyecto1_1s2016.tipoDatoEnHold = hijo2.tipoDato;
                        } else { //De una vez se asigna el valor a la pila
                            if (hijo1.tipoDato == hijo2.tipoDato) {
                                Compi2_Proyecto1_1s2016.pilaParaAtributos.get(hijo1.posicionEnPila).valor = hijo2.valor;
                            } else {
                                this.tipoDato = -1;
                                addError("Los tipos de dato no son compatibles.");
                            }
                        }
                    } else if (hijo1.tipo.equalsIgnoreCase("arreglo/funcion")) {
                        String nombreArray = hijo1.hijos.get(0).nombre;
                        simTempAsignacion = Compi2_Proyecto1_1s2016.buscarSimbolo(nombreArray);
                        if (simTempAsignacion == null) {
                            this.tipoDato = -1;
                            addError("No se encontro la variable " + nombreArray + ".");
                        } else if (simTempAsignacion.isArreglo()) {
                            if (simTempAsignacion.tipoDato == hijo2.tipoDato) {
                                int indices[] = new int[simTempAsignacion.numeroDimensiones];
                                for (int i = 0; i < simTempAsignacion.numeroDimensiones; i++) {
                                    nodoAST item = hijo1.hijos.get(1).hijos.get(i);
                                    item.ejecutar();
                                    if (item.tipoDato == 1) {
                                        indices[i] = (int) item.valor;
                                    } else {
                                        this.tipoDato = -1;
                                        addError("Error en arreglo " + nombreArray);
                                    }
                                }
                                simTempAsignacion.setObjectArreglo(hijo2.valor, indices);
                            } else {
                                this.tipoDato = -1;
                                addError("Error en tipos de datos al tratar de asignar valor al arreglo " + nombreArray + ".");
                            }

                        } else if (simTempAsignacion.isMetodo()) {
                            this.tipoDato = -1;
                            addError("Se trato de asignar un valor a un metodo.");
                        }
                    } else {
                        if (simTempAsignacion == null) {
                            addError("No se encontro la variable " + identifier + ".");
                            this.tipoDato = -1;
                        } else if (!simTempAsignacion.isArreglo() && !simTempAsignacion.isMetodo()) {
                            if (simTempAsignacion.tipoDato != hijo2.tipoDato) {
                                String tipo1 = "", tipo2 = "";
                                switch (simTempAsignacion.tipoDato) {
                                    case 1:
                                        tipo1 = "int";
                                        break;
                                    case 2:
                                        tipo1 = "double";
                                        break;
                                    case 3:
                                        tipo1 = "String";
                                        break;
                                    case 4:
                                        tipo1 = "char";
                                        break;
                                    case 5:
                                        tipo1 = "boolean";
                                        break;
                                }
                                switch (hijo2.tipoDato) {
                                    case 1:
                                        tipo2 = "int";
                                        break;
                                    case 2:
                                        tipo2 = "double";
                                        break;
                                    case 3:
                                        tipo2 = "String";
                                        break;
                                    case 4:
                                        tipo2 = "char";
                                        break;
                                    case 5:
                                        tipo2 = "boolean";
                                        break;
                                }
                                addError("Se esta intentando asignar un valor tipo " + tipo2 + " a la variable '" + simTempAsignacion.nombre + "' de tipo " + tipo1 + ".");
                                this.tipoDato = -1;
                            } else {
                                simTempAsignacion.valor = hijo2.valor;
                            }
                        }
                    }
                } else {
                    addError("No se pueden asignar valores null.");
                    this.tipoDato = -1;
                }
                break;
            case "declaracion":
                this.tipoDato = 0;
                hijo1 = this.hijos.get(0);
                hijo2 = this.hijos.get(1);
                int tipoD = (int) hijo2.valor;
                if (hijo1.tipo.equalsIgnoreCase("arreglo")) {
                    String nombreD = hijo1.hijos.get(0).nombre;
                    Simbolo temp = new Simbolo(nombreD, null, 0, tipoD);
                    temp.makeArray();
                    for (int i = 1; i < hijo1.hijos.size(); i++) {
                        temp.addDimension((int) hijo1.hijos.get(i).valor);
                    }
                    temp.inicializarValores();
                    Compi2_Proyecto1_1s2016.TablaSimbolos.add(temp);
                    break;
                } else if (hijo1.tipo.equalsIgnoreCase("variables")) {
                    for (nodoAST var : hijo1.hijos) {
                        Compi2_Proyecto1_1s2016.TablaSimbolos.add(new Simbolo(var.nombre, null, 0, tipoD));
                    }
                }
                this.tipoDato = 0;
                break;
            case "if": //De una vez ejecuta el elseif y else
                this.tipoDato = 0;
                hijo1 = this.hijos.get(0);
                hijo1.ejecutar();
                hijo2 = this.hijos.get(1);
                if (hijo1.tipoDato == 5 && hijo1.valor != null) {
                    if (((boolean) hijo1.valor) == true) {
                        hijo2.ejecutar();
                        this.tipoDato = hijo2.tipoDato;
                    } else if (this.hijos.size() > 2) {
                        boolean algunoTrue = false;
                        for (int i = 2; i < this.hijos.size() - 1; i++) {
                            nodoAST elsif = this.hijos.get(i);
                            hijo1 = elsif.hijos.get(0);
                            hijo1.ejecutar();
                            hijo2 = elsif.hijos.get(1);
                            if (hijo1.tipoDato == 5 && hijo1.valor != null) {
                                if (((boolean) hijo1.valor) == true) {
                                    hijo2.ejecutar();
                                    this.tipoDato = hijo2.tipoDato;
                                    algunoTrue = true;
                                    break;
                                } else {
                                    this.tipoDato = 0;
                                }
                            } else {
                                addError("La condicion del ELSEIF no es valida.");
                                this.tipoDato = -1;
                                algunoTrue = true;
                                break;
                            }
                        }
                        if (!algunoTrue) {
                            nodoAST elsif = this.hijos.get(this.hijos.size() - 1);
                            hijo1 = elsif.hijos.get(0);
                            hijo1.ejecutar();
                            this.tipoDato = hijo1.tipoDato;
                        }
                    } else {
                        this.tipoDato = 0;
                    }

                } else {
                    addError("La condicion del IF no es valida.");
                    this.tipoDato = -1;
                }
                break;
            case "loop":
                this.tipoDato = 0;
                hijo1 = this.hijos.get(0);
                int iteraciones = 0;
                while (true || iteraciones > 100) {
                    iteraciones++;
                    hijo1.ejecutar();
                    if (hijo1.tipoDato == -1) {
                        this.tipoDato = -1;
                        break;
                    }
                    if (hijo1.tipoDato == -2) {
                        this.tipoDato = 0;
                        break;
                    } else if (hijo1.tipoDato == -3) {
                        this.tipoDato = -3;
                        this.valor = hijo1.valor;
                        break;
                    }
                }
                break;
            case "dountil":
                this.tipoDato = 0;
                hijo1 = this.hijos.get(0);
                hijo2 = this.hijos.get(1);
                hijo2.ejecutar();
                if (hijo2.tipoDato == -1) {
                    this.tipoDato = -1;
                    break;
                }
                if (hijo2.tipoDato == -2) {
                    this.tipoDato = 0;
                    break;
                } else if (hijo2.tipoDato == -3) {
                    this.tipoDato = -3;
                    this.valor = hijo2.valor;
                    break;
                }
                hijo1.ejecutar();
                if (hijo1.tipoDato == 5 && hijo1.valor != null) {
                    while ((boolean) hijo1.valor == false) {
                        hijo2.ejecutar();
                        if (hijo2.tipoDato == -1) {
                            this.tipoDato = -1;
                            break;
                        }
                        if (hijo2.tipoDato == -2) {
                            this.tipoDato = 0;
                            break;
                        } else if (hijo2.tipoDato == -3) {
                            this.tipoDato = -3;
                            this.valor = hijo2.valor;
                            break;
                        }
                        hijo1.ejecutar();
                        if (hijo1.tipoDato != 5 || hijo1.valor == null) {
                            addError("La condicion del DO/UNTIL se torno invalida.");
                            this.tipoDato = -1;
                            break;
                        }
                    }
                } else {
                    this.tipoDato = -1;
                    addError("La condicion del DO/UNTIL no es valida.");
                }
                break;
            case "while":
                this.tipoDato = 0;
                hijo1 = this.hijos.get(0);
                hijo1.ejecutar();
                hijo2 = this.hijos.get(1);
                if (hijo1.tipoDato == 5 && hijo1.valor != null) {
                    while (((boolean) hijo1.valor) == true) {
                        hijo2.ejecutar();
                        this.tipoDato = hijo2.tipoDato;
                        if (this.tipoDato == -1) {
                            break;
                        }
                        if (this.tipoDato == -2) {
                            this.tipoDato = 0;
                            break;
                        } else if (this.tipoDato == -3) {
                            this.tipoDato = -3;
                            this.valor = hijo2.valor;
                            break;
                        }
                        hijo1.ejecutar();
                        if (hijo1.tipoDato != 5 || hijo1.valor == null) {
                            addError("La condicion del WHILE se torno invalida.");
                            this.tipoDato = -1;
                            break;
                        }
                    }
                } else {
                    this.tipoDato = -1;
                    addError("La condicion del WHILE no es valida.");
                }
                break;
            case "for":
                this.tipoDato = 0;
                hijo1 = this.hijos.get(0);
                hijo2 = this.hijos.get(1);
                nodoAST hijo3 = this.hijos.get(2);
                nodoAST hijo4 = this.hijos.get(3);
                hijo1.ejecutar();
                hijo2.ejecutar();
                hijo3.ejecutar();
                simTemp = Compi2_Proyecto1_1s2016.buscarSimbolo(hijo1.nombre);
                if (simTemp != null) {
                    if (simTemp.tipoDato == 1) {
                        simTemp.valor = hijo2.valor;
                        if (this.salto > 0) {
                            while ((int) simTemp.valor <= (int) hijo3.valor) {
                                hijo4.ejecutar();
                                this.tipoDato = hijo4.tipoDato;
                                if (this.tipoDato == -1) {
                                    break;
                                }
                                if (this.tipoDato == -2) {
                                    this.tipoDato = 0;
                                    break;
                                } else if (this.tipoDato == -3) {
                                    this.valor = hijo4.valor;
                                    break;
                                }
                                simTemp.valor = (int) simTemp.valor + salto;
                            }
                        } else {
                            while ((int) simTemp.valor >= (int) hijo3.valor) {
                                hijo4.ejecutar();
                                this.tipoDato = hijo4.tipoDato;
                                if (this.tipoDato == -1) {
                                    break;
                                }
                                if (this.tipoDato == -2) {
                                    this.tipoDato = 0;
                                    break;
                                } else if (this.tipoDato == -3) {
                                    this.valor = hijo4.valor;
                                    break;
                                }
                                simTemp.valor = (int) simTemp.valor + salto;
                            }
                        }
                    } else {
                        this.tipoDato = -1;
                        addError("El tipo de la variable del FOR no es aceptable.");
                    }
                } else {
                    this.tipoDato = -1;
                    addError("La variable del FOR no fue encontrada.");
                }
                break;
            case "switch":
                nodoAST.tipoDatoAComparar = -9865;
                nodoAST.valorAComparar = null;
                this.tipoDato = 0;
                hijo1 = this.hijos.get(0);
                hijo1.ejecutar();
                nodoAST.tipoDatoAComparar = hijo1.tipoDato;
                nodoAST.valorAComparar = hijo1.valor;
                if (nodoAST.valorAComparar == null) {
                    addError("La expresion del SWITCH es nula.");
                } else {
                    hijo2 = this.hijos.get(1);
                    hijo2.ejecutar();
                    this.tipoDato = hijo2.tipoDato;
                }
                nodoAST.tipoDatoAComparar = -9865;
                nodoAST.valorAComparar = null;
                break;
            case "cases":
                nodoAST left;
                nodoAST right;
                casos:
                for (nodoAST caso : this.hijos) {
                    left = caso.hijos.get(0);
                    right = caso.hijos.get(1);
                    if (left.tipo.equalsIgnoreCase("default")) {
                        right.ejecutar();
                        if (right.tipoDato == -1) {
                            this.tipoDato = -1;
                            break;
                        } else if (right.tipoDato == -2) {
                            this.tipoDato = 0;
                            break;
                        } else if (right.tipoDato == -3) {
                            this.tipoDato = -3;
                            this.valor = right.valor;
                            break;
                        }
                    } else {
                        left.ejecutar();
                        if (left.tipoDato == nodoAST.tipoDatoAComparar) {
                            switch (left.tipoDato) {
                                case 1:
                                    if ((int) left.valor == (int) nodoAST.valorAComparar) {
                                        right.ejecutar();
                                        if (right.tipoDato == -1) {
                                            this.tipoDato = -1;
                                            break casos;
                                        } else if (right.tipoDato == -2) {
                                            this.tipoDato = 0;
                                            break casos;
                                        } else if (right.tipoDato == -3) {
                                            this.tipoDato = -3;
                                            this.valor = right.valor;
                                            break casos;
                                        }
                                    }
                                    break;
                                case 2:
                                    if ((double) left.valor == (double) nodoAST.valorAComparar) {
                                        right.ejecutar();
                                        if (right.tipoDato == -1) {
                                            this.tipoDato = -1;
                                            break casos;
                                        } else if (right.tipoDato == -2) {
                                            this.tipoDato = 0;
                                            break casos;
                                        } else if (right.tipoDato == -3) {
                                            this.tipoDato = -3;
                                            this.valor = right.valor;
                                            break casos;
                                        }
                                    }
                                    break;
                                case 3:
                                    if (((String) left.valor).equals((String) nodoAST.valorAComparar)) {
                                        right.ejecutar();
                                        if (right.tipoDato == -1) {
                                            this.tipoDato = -1;
                                            break casos;
                                        } else if (right.tipoDato == -2) {
                                            this.tipoDato = 0;
                                            break casos;
                                        } else if (right.tipoDato == -3) {
                                            this.tipoDato = -3;
                                            this.valor = right.valor;
                                            break casos;
                                        }
                                    }
                                    break;
                                case 4:
                                    if ((int) ((char) left.valor) == (int) ((char) nodoAST.valorAComparar)) {
                                        right.ejecutar();
                                        if (right.tipoDato == -1) {
                                            this.tipoDato = -1;
                                            break casos;
                                        } else if (right.tipoDato == -2) {
                                            this.tipoDato = 0;
                                            break casos;
                                        } else if (right.tipoDato == -3) {
                                            this.tipoDato = -3;
                                            this.valor = right.valor;
                                            break casos;
                                        }
                                    }
                                    break;
                                case 5:
                                    if ((boolean) left.valor == (boolean) nodoAST.valorAComparar) {
                                        right.ejecutar();
                                        if (right.tipoDato == -1) {
                                            this.tipoDato = -1;
                                            break casos;
                                        } else if (right.tipoDato == -3) {
                                            this.tipoDato = -3;
                                            this.valor = right.valor;
                                            break casos;
                                        }
                                    }
                                    break;
                            }
                        } else {
                            this.tipoDato = -1;
                            addError("El tipo de dato del valor del CASE no es compatible con la expresion del SWITCH.");
                            break;
                        }
                    }
                }
                break;

            case "case":

                break;
            case "break":
                this.tipoDato = -2;
                break;
            case "true":
                this.tipoDato = 5;
                break;
            case "false":
                this.tipoDato = 5;
                break;
            case "double":
                this.tipoDato = 2;
                break;
            case "string":
                this.tipoDato = 3;
                break;
            case "char":
                this.tipoDato = 4;
                break;
            case "num":
                this.tipoDato = 1;
                break;
            case "xor":
                this.tipoDato = 0;
                hijo1 = this.hijos.get(0);
                hijo2 = this.hijos.get(1);
                hijo1.ejecutar();
                hijo2.ejecutar();
                if (hijo1.valor == null || hijo2.valor == null) {
                    this.tipoDato = -1;
                    addError("Error en XOR, un dato es null");
                } else {
                    if (hijo1.tipoDato == 5 && hijo2.tipoDato == 5) {
                        if ((boolean) hijo1.valor == true) {
                            this.tipoDato = 5;
                            this.valor = (boolean) hijo2.valor != true;
                        } else {
                            this.tipoDato = 5;
                            this.valor = (boolean) hijo2.valor == true;
                        }
                    } else {
                        addError("Los elementos que integran el XOR no son booleanos.");
                        this.tipoDato = -1;
                    }
                }
                break;
            case "or":
                this.tipoDato = 0;
                hijo1 = this.hijos.get(0);
                hijo2 = this.hijos.get(1);
                hijo1.ejecutar();
                hijo2.ejecutar();
                if (hijo1.valor == null || hijo2.valor == null) {
                    this.tipoDato = -1;
                    addError("Error en OR, un dato es null");
                } else {
                    if (hijo1.tipoDato == 5 && hijo2.tipoDato == 5) {
                        this.tipoDato = 5;
                        this.valor = (boolean) hijo1.valor == true || (boolean) hijo2.valor == true;
                    } else {
                        addError("Los elementos que integran el OR no son booleanos.");
                        this.tipoDato = -1;
                    }
                }
                break;
            case "and":
                this.tipoDato = 0;
                hijo1 = this.hijos.get(0);
                hijo2 = this.hijos.get(1);
                hijo1.ejecutar();
                hijo2.ejecutar();
                if (hijo1.valor == null || hijo2.valor == null) {
                    this.tipoDato = -1;
                    addError("Error en AND, un dato es null");
                } else {
                    if (hijo1.tipoDato == 5 && hijo2.tipoDato == 5) {
                        this.tipoDato = 5;
                        this.valor = (boolean) hijo1.valor && (boolean) hijo2.valor;
                    } else {
                        addError("Los elementos que integran el AND no son booleanos.");
                        this.tipoDato = -1;
                    }
                }
                break;
            case "not":
                this.tipoDato = 0;
                hijo1 = this.hijos.get(0);
                hijo1.ejecutar();
                if (hijo1.valor == null) {
                    this.tipoDato = -1;
                    addError("Error en NOT, el dato es null");
                } else {
                    if (hijo1.tipoDato == 5) {
                        this.valor = !(boolean) hijo1.valor;
                        this.tipoDato = 5;
                    } else {
                        addError("El elemento que integra al NOT no es booleanos.");
                        this.tipoDato = -1;
                    }
                }
                break;
            case "mayor":
                this.tipoDato = 0;
                hijo1 = this.hijos.get(0);
                hijo2 = this.hijos.get(1);
                hijo1.ejecutar();
                hijo2.ejecutar();
                if (hijo1.valor == null || hijo2.valor == null) {
                    this.tipoDato = -1;
                    addError("Error en >, un dato es null");
                } else {
                    if (hijo1.tipoDato == 5 || hijo2.tipoDato == 5) { //NO ES VALIDO SI ALGUNO ES BOOLEANO
                        addError("Los elementos que integran el > son invalidos.");
                        this.tipoDato = -1;
                    } else if (((hijo1.tipoDato == 3) && (hijo2.tipoDato == 1 || hijo2.tipoDato == 2)) //NO ES VALIDO MEZCLAR STRING CON NUMERICO
                            || ((hijo2.tipoDato == 3) && (hijo1.tipoDato == 1 || hijo1.tipoDato == 2))) {
                        addError("Los elementos que integran el > son invalidos.");
                        this.tipoDato = -1;
                    } else if (hijo1.tipoDato == 3 && hijo2.tipoDato == 4) { //STRING CON CHAR
                        String first = (String) hijo1.valor;
                        char second = (char) hijo2.valor;

                        int ascii1 = (int) second;
                        int ascii2 = 0;
                        for (int i = 0; i < first.length(); i++) {
                            ascii2 += (int) first.charAt(i);
                        }
                        this.valor = ascii2 > ascii1;
                        this.tipoDato = 5;
                    } else if (hijo2.tipoDato == 3 && hijo1.tipoDato == 4) { //CHAR CON STRING
                        String second = (String) hijo2.valor;
                        char first = (char) hijo1.valor;
                        int ascii1 = (int) first;
                        int ascii2 = 0;
                        for (int i = 0; i < second.length(); i++) {
                            ascii2 += (int) second.charAt(i);
                        }
                        this.valor = ascii1 > ascii2;
                        this.tipoDato = 5;
                    } else if (hijo2.tipoDato == 3 && hijo1.tipoDato == 3) { //CHAR CON CHAR
                        char c1 = (char) hijo2.valor;
                        char c2 = (char) hijo1.valor;
                        int ascii1 = (int) c1;
                        int ascii2 = (int) c2;
                        this.valor = ascii2 > ascii1;
                        this.tipoDato = 5;
                    } else if (hijo2.tipoDato == 4 && hijo1.tipoDato == 4) { //STRING CON STRING
                        String primerHijo = (String) hijo1.valor;
                        String segundoHijo = (String) hijo2.valor;
                        int ascii1 = 0, ascii2 = 0;
                        for (int i = 0; i < primerHijo.length(); i++) {
                            ascii1 += (int) primerHijo.charAt(i);
                        }
                        for (int i = 0; i < segundoHijo.length(); i++) {
                            ascii2 += (int) segundoHijo.charAt(i);
                        }
                        this.valor = ascii1 > ascii2;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 4 && hijo2.tipoDato == 1) { //CHAR CON INT
                        char c = (char) hijo1.valor;
                        int i = (int) hijo2.valor;
                        this.valor = ((int) c) > i;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 4) { //INT CON CHAR
                        char c = (char) hijo2.valor;
                        int i = (int) hijo1.valor;
                        this.valor = i > ((int) c);
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 4 && hijo2.tipoDato == 2) { //CHAR CON DOUBLE
                        char c = (char) hijo1.valor;
                        double i = (double) hijo2.valor;
                        this.valor = ((int) c) > i;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 4) { //DOUBLE CON CHAR
                        char c = (char) hijo2.valor;
                        double i = (double) hijo1.valor;
                        this.valor = i > ((int) c);
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 1) { //INT CON INT
                        int primero = (int) hijo1.valor;
                        int segundo = (int) hijo2.valor;
                        this.valor = primero > segundo;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 1) { //DOUBLE CON INT
                        double primero = (double) hijo1.valor;
                        int segundo = (int) hijo2.valor;
                        this.valor = primero > segundo;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 2) { //INT CON DOUBLE
                        int primero = (int) hijo1.valor;
                        double segundo = (double) hijo2.valor;
                        this.valor = primero > segundo;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 2) { //DOUBLE CON DOUBLE
                        double primero = (double) hijo1.valor;
                        double segundo = (double) hijo2.valor;
                        this.valor = primero > segundo;
                        this.tipoDato = 5;
                    } else {
                        addError("Los elementos que integran el > son invalidos.");
                        this.tipoDato = -1;
                    }
                }
                break;
            case "mayorigual":
                this.tipoDato = 0;
                hijo1 = this.hijos.get(0);
                hijo2 = this.hijos.get(1);
                hijo1.ejecutar();
                hijo2.ejecutar();
                if (hijo1.valor == null || hijo2.valor == null) {
                    this.tipoDato = -1;
                    addError("Error en >=, un dato es null");
                } else {
                    if (hijo1.tipoDato == 5 || hijo2.tipoDato == 5) { //NO ES VALIDO SI ALGUNO ES BOOLEANO
                        addError("Los elementos que integran el >= son invalidos.");
                        this.tipoDato = -1;
                    } else if (((hijo1.tipoDato == 3) && (hijo2.tipoDato == 1 || hijo2.tipoDato == 2)) //NO ES VALIDO MEZCLAR STRING CON NUMERICO
                            || ((hijo2.tipoDato == 3) && (hijo1.tipoDato == 1 || hijo1.tipoDato == 2))) {
                        addError("Los elementos que integran el >= son invalidos.");
                        this.tipoDato = -1;
                    } else if (hijo1.tipoDato == 3 && hijo2.tipoDato == 4) { //STRING CON CHAR
                        String first = (String) hijo1.valor;
                        char second = (char) hijo2.valor;

                        int ascii1 = (int) second;
                        int ascii2 = 0;
                        for (int i = 0; i < first.length(); i++) {
                            ascii2 += (int) first.charAt(i);
                        }
                        this.valor = ascii2 >= ascii1;
                        this.tipoDato = 5;
                    } else if (hijo2.tipoDato == 3 && hijo1.tipoDato == 4) { //CHAR CON STRING
                        String second = (String) hijo2.valor;
                        char first = (char) hijo1.valor;
                        int ascii1 = (int) first;
                        int ascii2 = 0;
                        for (int i = 0; i < second.length(); i++) {
                            ascii2 += (int) second.charAt(i);
                        }
                        this.valor = ascii1 >= ascii2;
                        this.tipoDato = 5;
                    } else if (hijo2.tipoDato == 3 && hijo1.tipoDato == 3) { //CHAR CON CHAR
                        char c1 = (char) hijo2.valor;
                        char c2 = (char) hijo1.valor;
                        int ascii1 = (int) c1;
                        int ascii2 = (int) c2;
                        this.valor = ascii2 >= ascii1;
                        this.tipoDato = 5;
                    } else if (hijo2.tipoDato == 4 && hijo1.tipoDato == 4) { //STRING CON STRING
                        String primerHijo = (String) hijo1.valor;
                        String segundoHijo = (String) hijo2.valor;
                        int ascii1 = 0, ascii2 = 0;
                        for (int i = 0; i < primerHijo.length(); i++) {
                            ascii1 += (int) primerHijo.charAt(i);
                        }
                        for (int i = 0; i < segundoHijo.length(); i++) {
                            ascii2 += (int) segundoHijo.charAt(i);
                        }
                        this.valor = ascii1 >= ascii2;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 4 && hijo2.tipoDato == 1) { //CHAR CON INT
                        char c = (char) hijo1.valor;
                        int i = (int) hijo2.valor;
                        this.valor = ((int) c) >= i;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 4) { //INT CON CHAR
                        char c = (char) hijo2.valor;
                        int i = (int) hijo1.valor;
                        this.valor = i >= ((int) c);
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 4 && hijo2.tipoDato == 2) { //CHAR CON DOUBLE
                        char c = (char) hijo1.valor;
                        double i = (double) hijo2.valor;
                        this.valor = ((int) c) >= i;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 4) { //DOUBLE CON CHAR
                        char c = (char) hijo2.valor;
                        double i = (double) hijo1.valor;
                        this.valor = i >= ((int) c);
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 1) { //INT CON INT
                        int primero = (int) hijo1.valor;
                        int segundo = (int) hijo2.valor;
                        this.valor = primero >= segundo;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 1) { //DOUBLE CON INT
                        double primero = (double) hijo1.valor;
                        int segundo = (int) hijo2.valor;
                        this.valor = primero >= segundo;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 2) { //INT CON DOUBLE
                        int primero = (int) hijo1.valor;
                        double segundo = (double) hijo2.valor;
                        this.valor = primero >= segundo;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 2) { //DOUBLE CON DOUBLE
                        double primero = (double) hijo1.valor;
                        double segundo = (double) hijo2.valor;
                        this.valor = primero >= segundo;
                        this.tipoDato = 5;
                    } else {
                        addError("Los elementos que integran el >= son invalidos.");
                        this.tipoDato = -1;
                    }
                }
                break;
            case "menor":
                this.tipoDato = 0;
                hijo1 = this.hijos.get(0);
                hijo2 = this.hijos.get(1);
                hijo1.ejecutar();
                hijo2.ejecutar();
                if (hijo1.valor == null || hijo2.valor == null) {
                    this.tipoDato = -1;
                    addError("Error en <, un dato es null");
                } else {
                    if (hijo1.tipoDato == 5 || hijo2.tipoDato == 5) { //NO ES VALIDO SI ALGUNO ES BOOLEANO
                        addError("Los elementos que integran el < son invalidos.");
                        this.tipoDato = -1;
                    } else if (((hijo1.tipoDato == 3) && (hijo2.tipoDato == 1 || hijo2.tipoDato == 2)) //NO ES VALIDO MEZCLAR STRING CON NUMERICO
                            || ((hijo2.tipoDato == 3) && (hijo1.tipoDato == 1 || hijo1.tipoDato == 2))) {
                        addError("Los elementos que integran el < son invalidos.");
                        this.tipoDato = -1;
                    } else if (hijo1.tipoDato == 3 && hijo2.tipoDato == 4) { //STRING CON CHAR
                        String first = (String) hijo1.valor;
                        char second = (char) hijo2.valor;

                        int ascii1 = (int) second;
                        int ascii2 = 0;
                        for (int i = 0; i < first.length(); i++) {
                            ascii2 += (int) first.charAt(i);
                        }
                        this.valor = ascii2 < ascii1;
                        this.tipoDato = 5;
                    } else if (hijo2.tipoDato == 3 && hijo1.tipoDato == 4) { //CHAR CON STRING
                        String second = (String) hijo2.valor;
                        char first = (char) hijo1.valor;
                        int ascii1 = (int) first;
                        int ascii2 = 0;
                        for (int i = 0; i < second.length(); i++) {
                            ascii2 += (int) second.charAt(i);
                        }
                        this.valor = ascii1 < ascii2;
                        this.tipoDato = 5;
                    } else if (hijo2.tipoDato == 3 && hijo1.tipoDato == 3) { //CHAR CON CHAR
                        char c1 = (char) hijo2.valor;
                        char c2 = (char) hijo1.valor;
                        int ascii1 = (int) c1;
                        int ascii2 = (int) c2;
                        this.valor = ascii2 < ascii1;
                        this.tipoDato = 5;
                    } else if (hijo2.tipoDato == 4 && hijo1.tipoDato == 4) { //STRING CON STRING
                        String primerHijo = (String) hijo1.valor;
                        String segundoHijo = (String) hijo2.valor;
                        int ascii1 = 0, ascii2 = 0;
                        for (int i = 0; i < primerHijo.length(); i++) {
                            ascii1 += (int) primerHijo.charAt(i);
                        }
                        for (int i = 0; i < segundoHijo.length(); i++) {
                            ascii2 += (int) segundoHijo.charAt(i);
                        }
                        this.valor = ascii1 < ascii2;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 4 && hijo2.tipoDato == 1) { //CHAR CON INT
                        char c = (char) hijo1.valor;
                        int i = (int) hijo2.valor;
                        this.valor = ((int) c) < i;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 4) { //INT CON CHAR
                        char c = (char) hijo2.valor;
                        int i = (int) hijo1.valor;
                        this.valor = i < ((int) c);
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 4 && hijo2.tipoDato == 2) { //CHAR CON DOUBLE
                        char c = (char) hijo1.valor;
                        double i = (double) hijo2.valor;
                        this.valor = ((int) c) < i;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 4) { //DOUBLE CON CHAR
                        char c = (char) hijo2.valor;
                        double i = (double) hijo1.valor;
                        this.valor = i < ((int) c);
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 1) { //INT CON INT
                        int primero = (int) hijo1.valor;
                        int segundo = (int) hijo2.valor;
                        this.valor = primero < segundo;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 1) { //DOUBLE CON INT
                        double primero = (double) hijo1.valor;
                        int segundo = (int) hijo2.valor;
                        this.valor = primero < segundo;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 2) { //INT CON DOUBLE
                        int primero = (int) hijo1.valor;
                        double segundo = (double) hijo2.valor;
                        this.valor = primero < segundo;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 2) { //DOUBLE CON DOUBLE
                        double primero = (double) hijo1.valor;
                        double segundo = (double) hijo2.valor;
                        this.valor = primero < segundo;
                        this.tipoDato = 5;
                    } else {
                        addError("Los elementos que integran el < son invalidos.");
                        this.tipoDato = -1;
                    }
                }
                break;
            case "menorigual":
                this.tipoDato = 0;
                hijo1 = this.hijos.get(0);
                hijo2 = this.hijos.get(1);
                hijo1.ejecutar();
                hijo2.ejecutar();
                if (hijo1.valor == null || hijo2.valor == null) {
                    this.tipoDato = -1;
                    addError("Error en <=, un dato es null");
                } else {
                    if (hijo1.tipoDato == 5 || hijo2.tipoDato == 5) { //NO ES VALIDO SI ALGUNO ES BOOLEANO
                        addError("Los elementos que integran el <= son invalidos.");
                        this.tipoDato = -1;
                    } else if (((hijo1.tipoDato == 3) && (hijo2.tipoDato == 1 || hijo2.tipoDato == 2)) //NO ES VALIDO MEZCLAR STRING CON NUMERICO
                            || ((hijo2.tipoDato == 3) && (hijo1.tipoDato == 1 || hijo1.tipoDato == 2))) {
                        addError("Los elementos que integran el <= son invalidos.");
                        this.tipoDato = -1;
                    } else if (hijo1.tipoDato == 3 && hijo2.tipoDato == 4) { //STRING CON CHAR
                        String first = (String) hijo1.valor;
                        char second = (char) hijo2.valor;

                        int ascii1 = (int) second;
                        int ascii2 = 0;
                        for (int i = 0; i < first.length(); i++) {
                            ascii2 += (int) first.charAt(i);
                        }
                        this.valor = ascii2 <= ascii1;
                        this.tipoDato = 5;
                    } else if (hijo2.tipoDato == 3 && hijo1.tipoDato == 4) { //CHAR CON STRING
                        String second = (String) hijo2.valor;
                        char first = (char) hijo1.valor;
                        int ascii1 = (int) first;
                        int ascii2 = 0;
                        for (int i = 0; i < second.length(); i++) {
                            ascii2 += (int) second.charAt(i);
                        }
                        this.valor = ascii1 <= ascii2;
                        this.tipoDato = 5;
                    } else if (hijo2.tipoDato == 3 && hijo1.tipoDato == 3) { //CHAR CON CHAR
                        char c1 = (char) hijo2.valor;
                        char c2 = (char) hijo1.valor;
                        int ascii1 = (int) c1;
                        int ascii2 = (int) c2;
                        this.valor = ascii2 <= ascii1;
                        this.tipoDato = 5;
                    } else if (hijo2.tipoDato == 4 && hijo1.tipoDato == 4) { //STRING CON STRING
                        String primerHijo = (String) hijo1.valor;
                        String segundoHijo = (String) hijo2.valor;
                        int ascii1 = 0, ascii2 = 0;
                        for (int i = 0; i < primerHijo.length(); i++) {
                            ascii1 += (int) primerHijo.charAt(i);
                        }
                        for (int i = 0; i < segundoHijo.length(); i++) {
                            ascii2 += (int) segundoHijo.charAt(i);
                        }
                        this.valor = ascii1 <= ascii2;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 4 && hijo2.tipoDato == 1) { //CHAR CON INT
                        char c = (char) hijo1.valor;
                        int i = (int) hijo2.valor;
                        this.valor = ((int) c) <= i;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 4) { //INT CON CHAR
                        char c = (char) hijo2.valor;
                        int i = (int) hijo1.valor;
                        this.valor = i <= ((int) c);
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 4 && hijo2.tipoDato == 2) { //CHAR CON DOUBLE
                        char c = (char) hijo1.valor;
                        double i = (double) hijo2.valor;
                        this.valor = ((int) c) <= i;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 4) { //DOUBLE CON CHAR
                        char c = (char) hijo2.valor;
                        double i = (double) hijo1.valor;
                        this.valor = i <= ((int) c);
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 1) { //INT CON INT
                        int primero = (int) hijo1.valor;
                        int segundo = (int) hijo2.valor;
                        this.valor = primero <= segundo;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 1) { //DOUBLE CON INT
                        double primero = (double) hijo1.valor;
                        int segundo = (int) hijo2.valor;
                        this.valor = primero <= segundo;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 2) { //INT CON DOUBLE
                        int primero = (int) hijo1.valor;
                        double segundo = (double) hijo2.valor;
                        this.valor = primero <= segundo;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 2) { //DOUBLE CON DOUBLE
                        double primero = (double) hijo1.valor;
                        double segundo = (double) hijo2.valor;
                        this.valor = primero <= segundo;
                        this.tipoDato = 5;
                    } else {
                        addError("Los elementos que integran el <= son invalidos.");
                        this.tipoDato = -1;
                    }
                }
                break;
            case "diferente":
                this.tipoDato = 0;
                hijo1 = this.hijos.get(0);
                hijo2 = this.hijos.get(1);
                hijo1.ejecutar();
                hijo2.ejecutar();
                if (hijo1.valor == null || hijo2.valor == null) {
                    this.tipoDato = -1;
                    addError("Error en !=, un dato es null");
                } else {
                    if (hijo1.tipoDato == 5 || hijo2.tipoDato == 5) { //NO ES VALIDO SI ALGUNO ES BOOLEANO
                        addError("Los elementos que integran el != son invalidos.");
                        this.tipoDato = -1;
                    } else if (((hijo1.tipoDato == 3) && (hijo2.tipoDato == 1 || hijo2.tipoDato == 2)) //NO ES VALIDO MEZCLAR STRING CON NUMERICO
                            || ((hijo2.tipoDato == 3) && (hijo1.tipoDato == 1 || hijo1.tipoDato == 2))) {
                        addError("Los elementos que integran el != son invalidos.");
                        this.tipoDato = -1;
                    } else if (hijo1.tipoDato == 3 && hijo2.tipoDato == 4) { //STRING CON CHAR
                        String first = (String) hijo1.valor;
                        char second = (char) hijo2.valor;

                        int ascii1 = (int) second;
                        int ascii2 = 0;
                        for (int i = 0; i < first.length(); i++) {
                            ascii2 += (int) first.charAt(i);
                        }
                        this.valor = ascii2 != ascii1;
                        this.tipoDato = 5;
                    } else if (hijo2.tipoDato == 3 && hijo1.tipoDato == 4) { //CHAR CON STRING
                        String second = (String) hijo2.valor;
                        char first = (char) hijo1.valor;
                        int ascii1 = (int) first;
                        int ascii2 = 0;
                        for (int i = 0; i < second.length(); i++) {
                            ascii2 += (int) second.charAt(i);
                        }
                        this.valor = ascii1 != ascii2;
                        this.tipoDato = 5;
                    } else if (hijo2.tipoDato == 3 && hijo1.tipoDato == 3) { //CHAR CON CHAR
                        char c1 = (char) hijo2.valor;
                        char c2 = (char) hijo1.valor;
                        int ascii1 = (int) c1;
                        int ascii2 = (int) c2;
                        this.valor = ascii2 != ascii1;
                        this.tipoDato = 5;
                    } else if (hijo2.tipoDato == 4 && hijo1.tipoDato == 4) { //STRING CON STRING
                        String primerHijo = (String) hijo1.valor;
                        String segundoHijo = (String) hijo2.valor;
                        this.valor = !primerHijo.equals(segundoHijo);
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 4 && hijo2.tipoDato == 1) { //CHAR CON INT
                        char c = (char) hijo1.valor;
                        int i = (int) hijo2.valor;
                        this.valor = ((int) c) != i;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 4) { //INT CON CHAR
                        char c = (char) hijo2.valor;
                        int i = (int) hijo1.valor;
                        this.valor = i != ((int) c);
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 4 && hijo2.tipoDato == 2) { //CHAR CON DOUBLE
                        char c = (char) hijo1.valor;
                        double i = (double) hijo2.valor;
                        this.valor = ((int) c) != i;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 4) { //DOUBLE CON CHAR
                        char c = (char) hijo2.valor;
                        double i = (double) hijo1.valor;
                        this.valor = i != ((int) c);
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 1) { //INT CON INT
                        int primero = (int) hijo1.valor;
                        int segundo = (int) hijo2.valor;
                        this.valor = primero != segundo;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 1) { //DOUBLE CON INT
                        double primero = (double) hijo1.valor;
                        int segundo = (int) hijo2.valor;
                        this.valor = primero != segundo;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 2) { //INT CON DOUBLE
                        int primero = (int) hijo1.valor;
                        double segundo = (double) hijo2.valor;
                        this.valor = primero != segundo;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 2) { //DOUBLE CON DOUBLE
                        double primero = (double) hijo1.valor;
                        double segundo = (double) hijo2.valor;
                        this.valor = primero != segundo;
                        this.tipoDato = 5;
                    } else {
                        addError("Los elementos que integran el != son invalidos.");
                        this.tipoDato = -1;
                    }
                }
                break;
            case "igual":
                this.tipoDato = 0;
                hijo1 = this.hijos.get(0);
                hijo2 = this.hijos.get(1);
                hijo1.ejecutar();
                hijo2.ejecutar();
                if (hijo1.valor == null || hijo2.valor == null) {
                    this.tipoDato = -1;
                    addError("Error en ==, un dato es null");
                } else {
                    if (hijo1.tipoDato == 5 || hijo2.tipoDato == 5) { //NO ES VALIDO SI ALGUNO ES BOOLEANO
                        addError("Los elementos que integran el == son invalidos.");
                        this.tipoDato = -1;
                    } else if (((hijo1.tipoDato == 3) && (hijo2.tipoDato == 1 || hijo2.tipoDato == 2)) //NO ES VALIDO MEZCLAR STRING CON NUMERICO
                            || ((hijo2.tipoDato == 3) && (hijo1.tipoDato == 1 || hijo1.tipoDato == 2))) {
                        addError("Los elementos que integran el == son invalidos.");
                        this.tipoDato = -1;
                    } else if (hijo1.tipoDato == 3 && hijo2.tipoDato == 4) { //STRING CON CHAR
                        String first = (String) hijo1.valor;
                        char second = (char) hijo2.valor;

                        int ascii1 = (int) second;
                        int ascii2 = 0;
                        for (int i = 0; i < first.length(); i++) {
                            ascii2 += (int) first.charAt(i);
                        }
                        this.valor = ascii2 == ascii1;
                        this.tipoDato = 5;
                    } else if (hijo2.tipoDato == 3 && hijo1.tipoDato == 4) { //CHAR CON STRING
                        String second = (String) hijo2.valor;
                        char first = (char) hijo1.valor;
                        int ascii1 = (int) first;
                        int ascii2 = 0;
                        for (int i = 0; i < second.length(); i++) {
                            ascii2 += (int) second.charAt(i);
                        }
                        this.valor = ascii1 == ascii2;
                        this.tipoDato = 5;
                    } else if (hijo2.tipoDato == 3 && hijo1.tipoDato == 3) { //CHAR CON CHAR
                        char c1 = (char) hijo2.valor;
                        char c2 = (char) hijo1.valor;
                        int ascii1 = (int) c1;
                        int ascii2 = (int) c2;
                        this.valor = ascii2 == ascii1;
                        this.tipoDato = 5;
                    } else if (hijo2.tipoDato == 4 && hijo1.tipoDato == 4) { //STRING CON STRING
                        String primerHijo = (String) hijo1.valor;
                        String segundoHijo = (String) hijo2.valor;
                        this.valor = primerHijo.equals(segundoHijo);
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 4 && hijo2.tipoDato == 1) { //CHAR CON INT
                        char c = (char) hijo1.valor;
                        int i = (int) hijo2.valor;
                        this.valor = ((int) c) == i;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 4) { //INT CON CHAR
                        char c = (char) hijo2.valor;
                        int i = (int) hijo1.valor;
                        this.valor = i == ((int) c);
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 4 && hijo2.tipoDato == 2) { //CHAR CON DOUBLE
                        char c = (char) hijo1.valor;
                        double i = (double) hijo2.valor;
                        this.valor = ((int) c) == i;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 4) { //DOUBLE CON CHAR
                        char c = (char) hijo2.valor;
                        double i = (double) hijo1.valor;
                        this.valor = i == ((int) c);
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 1) { //INT CON INT
                        int primero = (int) hijo1.valor;
                        int segundo = (int) hijo2.valor;
                        this.valor = primero == segundo;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 1) { //DOUBLE CON INT
                        double primero = (double) hijo1.valor;
                        int segundo = (int) hijo2.valor;
                        this.valor = primero == segundo;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 2) { //INT CON DOUBLE
                        int primero = (int) hijo1.valor;
                        double segundo = (double) hijo2.valor;
                        this.valor = primero == segundo;
                        this.tipoDato = 5;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 2) { //DOUBLE CON DOUBLE
                        double primero = (double) hijo1.valor;
                        double segundo = (double) hijo2.valor;
                        this.valor = primero == segundo;
                        this.tipoDato = 5;
                    } else {
                        addError("Los elementos que integran el == son invalidos.");
                        this.tipoDato = -1;
                    }
                }
                break;
            case "suma":
                this.tipoDato = 0;
                hijo1 = this.hijos.get(0);
                hijo2 = this.hijos.get(1);
                hijo1.ejecutar();
                hijo2.ejecutar();
                if (hijo1.valor == null || hijo2.valor == null) {
                    this.tipoDato = -1;
                    addError("Error en SUMA, un dato es null");
                } else {
                    if (hijo1.tipoDato == 1 && hijo2.tipoDato == 2) {
                        int n1 = (int) hijo1.valor;
                        double n2 = (double) hijo2.valor;
                        this.valor = n1 + n2;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 1) {
                        double n1 = (double) hijo1.valor;
                        int n2 = (int) hijo2.valor;
                        this.valor = n1 + n2;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 4) {
                        double n1 = (double) hijo1.valor;
                        char nt2 = (char) hijo2.valor;
                        int n2 = (int) nt2;
                        this.valor = n1 + n2;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 4 && hijo2.tipoDato == 2) {
                        char nt1 = (char) hijo1.valor;
                        int n1 = (int) nt1;
                        double n2 = (double) hijo2.valor;
                        this.valor = n1 + n2;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 5 && hijo2.tipoDato == 2) {
                        int n1;
                        if ((boolean) hijo1.valor) {
                            n1 = 1;
                        } else {
                            n1 = 0;
                        }
                        double n2 = (double) hijo2.valor;
                        this.valor = n1 + n2;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 5) {
                        double n1 = (double) hijo1.valor;
                        int n2;
                        if ((boolean) hijo2.valor) {
                            n2 = 1;
                        } else {
                            n2 = 0;
                        }
                        this.valor = n1 + n2;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 2) {
                        double n1 = (double) hijo1.valor;
                        double n2 = (double) hijo2.valor;
                        this.valor = n1 + n2;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 4) {
                        int n1 = (int) hijo1.valor;
                        int n2 = (int) ((char) hijo2.valor);
                        this.valor = n1 + n2;
                        this.tipoDato = 1;
                    } else if (hijo1.tipoDato == 4 && hijo2.tipoDato == 1) {
                        int n1 = (int) ((char) hijo1.valor);
                        int n2 = (int) hijo2.valor;
                        this.valor = n1 + n2;
                        this.tipoDato = 1;
                    } else if (hijo1.tipoDato == 5 && hijo2.tipoDato == 1) {
                        int n1;
                        if ((boolean) hijo1.valor) {
                            n1 = 1;
                        } else {
                            n1 = 0;
                        }
                        int n2 = (int) hijo2.valor;
                        this.valor = n1 + n2;
                        this.tipoDato = 1;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 5) {
                        int n1 = (int) hijo1.valor;
                        int n2;
                        if ((boolean) hijo2.valor) {
                            n2 = 1;
                        } else {
                            n2 = 0;
                        }
                        this.valor = n1 + n2;
                        this.tipoDato = 1;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 1) {
                        int n1 = (int) hijo1.valor;
                        int n2 = (int) hijo2.valor;
                        this.valor = n1 + n2;
                        this.tipoDato = 1;
                    } else if (hijo1.tipoDato == 3 && hijo2.tipoDato == 1) {
                        String n1 = (String) hijo1.valor;
                        String n2 = (int) hijo2.valor + "";
                        this.valor = n1 + n2 + "";
                        this.tipoDato = 3;
                    } else if (hijo1.tipoDato == 3 && hijo2.tipoDato == 2) {
                        String n1 = (String) hijo1.valor;
                        String n2 = (double) hijo2.valor + "";
                        this.valor = n1 + n2;
                        this.tipoDato = 3;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 3) {
                        String n1 = (double) hijo1.valor + "";
                        String n2 = (String) hijo2.valor;
                        this.valor = n1 + n2;
                        this.tipoDato = 3;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 3) {
                        String n1 = (int) hijo1.valor + "";
                        String n2 = (String) hijo2.valor;
                        this.valor = n1 + n2;
                        this.tipoDato = 3;
                    } else if (hijo1.tipoDato == 3 && hijo2.tipoDato == 4) {
                        String n1 = (String) hijo1.valor;
                        String n2 = Character.toString((char) hijo2.valor);
                        this.valor = n1 + n2;
                        this.tipoDato = 3;
                    } else if (hijo1.tipoDato == 4 && hijo2.tipoDato == 3) {
                        String n1 = Character.toString((char) hijo1.valor);
                        String n2 = (String) hijo2.valor;
                        this.valor = n1 + n2;
                        this.tipoDato = 3;
                    } else if (hijo1.tipoDato == 3 && hijo2.tipoDato == 3) {
                        String n1 = (String) hijo1.valor;
                        String n2 = (String) hijo2.valor;
                        this.valor = n1 + n2;
                        this.tipoDato = 3;
                    } else if (hijo1.tipoDato == 5 && hijo2.tipoDato == 5) {
                        int n1, n2;
                        if ((boolean) hijo1.valor) {
                            n1 = 1;
                        } else {
                            n1 = 0;
                        }
                        if ((boolean) hijo1.valor) {
                            n2 = 1;
                        } else {
                            n2 = 0;
                        }
                        int res = n1 + n2;
                        this.valor = res == 1 || res == 2;
                        this.tipoDato = 5;
                    } else {
                        addError("Los elementos que integran la SUMA son invalidos.");
                        this.tipoDato = -1;
                    }
                }
                break;
            case "resta":
                this.tipoDato = 0;
                hijo1 = this.hijos.get(0);
                hijo2 = this.hijos.get(1);
                hijo1.ejecutar();
                hijo2.ejecutar();
                if (hijo1.valor == null || hijo2.valor == null) {
                    this.tipoDato = -1;
                    addError("Error en RESTA, un dato es null");
                } else {
                    if (hijo1.tipoDato == 1 && hijo2.tipoDato == 2) {
                        int n1 = (int) hijo1.valor;
                        double n2 = (double) hijo2.valor;
                        this.valor = n1 - n2;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 1) {
                        double n1 = (double) hijo1.valor;
                        int n2 = (int) hijo2.valor;
                        this.valor = n1 - n2;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 4) {
                        double n1 = (double) hijo1.valor;
                        int n2 = (int) ((char) hijo2.valor);
                        this.valor = n1 - n2;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 4 && hijo2.tipoDato == 2) {
                        int n1 = (int) ((char) hijo1.valor);
                        double n2 = (double) hijo2.valor;
                        this.valor = n1 - n2;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 5 && hijo2.tipoDato == 2) {
                        int n1;
                        if ((boolean) hijo1.valor) {
                            n1 = 1;
                        } else {
                            n1 = 0;
                        }
                        double n2 = (double) hijo2.valor;
                        this.valor = n1 - n2;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 5) {
                        double n1 = (double) hijo1.valor;
                        int n2;
                        if ((boolean) hijo2.valor) {
                            n2 = 1;
                        } else {
                            n2 = 0;
                        }
                        this.valor = n1 - n2;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 2) {
                        double n1 = (double) hijo1.valor;
                        double n2 = (double) hijo2.valor;
                        this.valor = n1 - n2;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 4) {
                        int n1 = (int) hijo1.valor;
                        int n2 = (int) ((char) hijo2.valor);
                        this.valor = n1 - n2;
                        this.tipoDato = 1;
                    } else if (hijo1.tipoDato == 4 && hijo2.tipoDato == 1) {
                        int n1 = (int) ((char) hijo1.valor);
                        int n2 = (int) hijo2.valor;
                        this.valor = n1 - n2;
                        this.tipoDato = 1;
                    } else if (hijo1.tipoDato == 5 && hijo2.tipoDato == 1) {
                        int n1;
                        if ((boolean) hijo1.valor) {
                            n1 = 1;
                        } else {
                            n1 = 0;
                        }
                        int n2 = (int) hijo2.valor;
                        this.valor = n1 - n2;
                        this.tipoDato = 1;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 5) {
                        int n1 = (int) hijo1.valor;
                        int n2;
                        if ((boolean) hijo2.valor) {
                            n2 = 1;
                        } else {
                            n2 = 0;
                        }
                        this.valor = n1 - n2;
                        this.tipoDato = 1;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 1) {
                        int n1 = (int) hijo1.valor;
                        int n2 = (int) hijo2.valor;
                        this.valor = n1 - n2;
                        this.tipoDato = 1;
                    } else {
                        addError("Los elementos que integran la RESTA son invalidos.");
                        this.tipoDato = -1;
                    }
                }
                break;
            case "multiplicacion":
                this.tipoDato = 0;
                hijo1 = this.hijos.get(0);
                hijo2 = this.hijos.get(1);
                hijo1.ejecutar();
                hijo2.ejecutar();
                if (hijo1.valor == null || hijo2.valor == null) {
                    this.tipoDato = -1;
                    addError("Error en MULTIPLICACION, un dato es null");
                } else {
                    if (hijo1.tipoDato == 1 && hijo2.tipoDato == 2) {
                        int n1 = (int) hijo1.valor;
                        double n2 = (double) hijo2.valor;
                        this.valor = n1 * n2;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 1) {
                        double n1 = (double) hijo1.valor;
                        int n2 = (int) hijo2.valor;
                        this.valor = n1 * n2;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 4) {
                        double n1 = (double) hijo1.valor;
                        int n2 = (int) ((char) hijo2.valor);
                        this.valor = n1 * n2;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 4 && hijo2.tipoDato == 2) {
                        int n1 = (int) ((char) hijo1.valor);
                        double n2 = (double) hijo2.valor;
                        this.valor = n1 * n2;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 5 && hijo2.tipoDato == 2) {
                        if ((boolean) hijo1.valor) {
                            this.valor = (double) hijo2.valor;
                        } else {
                            this.valor = 0;
                        }
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 5) {
                        double n1 = (double) hijo1.valor;
                        if ((boolean) hijo2.valor) {
                            this.valor = n1;
                        } else {
                            this.valor = 0;
                        }
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 2) {
                        double n1 = (double) hijo1.valor;
                        double n2 = (double) hijo2.valor;
                        this.valor = n1 * n2;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 4) {
                        int n1 = (int) hijo1.valor;
                        int n2 = (int) ((char) hijo2.valor);
                        this.valor = n1 * n2;
                        this.tipoDato = 1;
                    } else if (hijo1.tipoDato == 4 && hijo2.tipoDato == 1) {
                        int n1 = (int) ((char) hijo1.valor);
                        int n2 = (int) hijo2.valor;
                        this.valor = n1 * n2;
                        this.tipoDato = 1;
                    } else if (hijo1.tipoDato == 5 && hijo2.tipoDato == 1) {
                        if ((boolean) hijo1.valor) {
                            this.valor = (int) hijo2.valor;
                        } else {
                            this.valor = 0;
                        }
                        this.tipoDato = 1;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 5) {
                        if ((boolean) hijo2.valor) {
                            this.valor = (int) hijo1.valor;
                        } else {
                            this.valor = 0;
                        }
                        this.tipoDato = 1;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 1) {
                        int n1 = (int) hijo1.valor;
                        int n2 = (int) hijo2.valor;
                        this.valor = n1 * n2;
                        this.tipoDato = 1;
                    } else if (hijo1.tipoDato == 5 && hijo2.tipoDato == 5) {
                        boolean n1 = (boolean) hijo1.valor;
                        boolean n2 = (boolean) hijo2.valor;
                        if (n1 && n2) {
                            this.valor = true;
                        } else {
                            this.valor = false;
                        }
                        this.tipoDato = 5;
                    } else {
                        addError("Los elementos que integran la MULTIPLICACION son invalidos.");
                        this.tipoDato = -1;
                    }
                }
                break;
            case "division":
                this.tipoDato = 0;
                hijo1 = this.hijos.get(0);
                hijo2 = this.hijos.get(1);
                hijo1.ejecutar();
                hijo2.ejecutar();
                if (hijo1.valor == null || hijo2.valor == null) {
                    this.tipoDato = -1;
                    addError("Error en DIVISION, un dato es null");
                } else {
                    if (hijo1.tipoDato == 1 && hijo2.tipoDato == 2) {
                        int n1 = (int) hijo1.valor;
                        double n2 = (double) hijo2.valor;
                        this.valor = n1 / n2;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 1) {
                        double n1 = (double) hijo1.valor;
                        int n2 = (int) hijo2.valor;
                        this.valor = n1 / n2;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 4) {
                        double n1 = (double) hijo1.valor;
                        int n2 = (int) ((char) hijo2.valor);
                        this.valor = n1 / n2;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 4 && hijo2.tipoDato == 2) {
                        int n1 = (int) ((char) hijo1.valor);
                        double n2 = (double) hijo2.valor;
                        this.valor = n1 / n2;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 5 && hijo2.tipoDato == 2) {
                        double n1;
                        if ((boolean) hijo1.valor) {
                            n1 = 1;
                        } else {
                            n1 = 0;
                        }
                        double n2 = (double) hijo2.valor;
                        this.valor = n1 / n2;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 5) {
                        if ((boolean) hijo2.valor) {
                            this.valor = (double) hijo1.valor;
                            this.tipoDato = 2;
                        } else {
                            this.tipoDato = -1;
                        }
                    } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 2) {
                        double n1 = (double) hijo1.valor;
                        double n2 = (double) hijo2.valor;
                        this.valor = n1 / n2;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 4) {
                        double n1 = (double) ((int) hijo1.valor);
                        int n2 = (int) ((char) hijo2.valor);
                        this.valor = (double) (n1 / n2);
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 4 && hijo2.tipoDato == 1) {
                        int n1 = (int) ((char) hijo1.valor);
                        double n2 = (double) ((int) hijo2.valor);
                        this.valor = n1 / n2;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 5 && hijo2.tipoDato == 1) {
                        double n1;
                        if ((boolean) hijo1.valor) {
                            n1 = 1;
                        } else {
                            n1 = 0;
                        }
                        int n2 = (int) hijo2.valor;
                        this.valor = n1 / n2;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 5) {
                        int n1 = (int) hijo1.valor;
                        if ((boolean) hijo2.valor) {
                            this.valor = n1;
                            this.tipoDato = 2;
                        } else {
                            this.tipoDato = -1;
                        }
                    } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 1) {
                        double n1 = (double) ((int) hijo1.valor);
                        double n2 = (double) ((int) hijo2.valor);
                        this.valor = (double) (n1 / n2);
                        this.tipoDato = 2;
                    } else {
                        addError("Los elementos que integran la DIVISION son invalidos.");
                        this.tipoDato = -1;
                    }
                }
                break;
            case "potencia":
                this.tipoDato = 0;
                hijo1 = this.hijos.get(0);
                hijo1.ejecutar();
                hijo2 = this.hijos.get(1);
                hijo2.ejecutar();
                if (hijo1.valor == null || hijo2.valor == null) {
                    this.tipoDato = -1;
                    addError("Error en POTENCIA, un dato es null");
                } else {
                    try {
                        if (hijo1.tipoDato == 1 && hijo2.tipoDato == 2) { //int - double
                            this.valor = (double) (Math.pow((int) hijo1.valor, (double) hijo2.valor));
                            this.tipoDato = 2;
                        } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 1) { //double - int
                            this.valor = (double) (Math.pow((double) hijo1.valor, (int) hijo2.valor));
                            this.tipoDato = 2;
                        } else if (hijo1.tipoDato == 4 && hijo2.tipoDato == 2) { //char - double
                            this.valor = (double) (Math.pow((double) ((int) ((char) hijo1.valor)), (double) hijo2.valor));
                            this.tipoDato = 2;
                        } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 4) { //double - char
                            this.valor = (double) (Math.pow((double) hijo1.valor, (double) ((int) ((char) hijo2.valor))));
                            this.tipoDato = 2;
                        } else if (hijo1.tipoDato == 5 && hijo2.tipoDato == 2) { //bool - double
                            if ((boolean) hijo1.valor == true) {
                                this.valor = (double) 1;
                            } else {
                                this.valor = (double) 0;
                            }
                            this.tipoDato = 2;
                        } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 5) { //double - bool
                            if (((boolean) hijo2.valor) == true) {
                                this.valor = (double) hijo1.valor;
                            } else {
                                this.valor = (double) 1;
                            }
                            this.tipoDato = 2;
                        } else if (hijo1.tipoDato == 2 && hijo2.tipoDato == 2) { //double - double
                            this.valor = (double) (Math.pow(((double) hijo1.valor), ((double) hijo2.valor)) + 0.0000);
                            this.tipoDato = 2;
                        } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 4) { //int - char
                            this.valor = (int) Math.pow((int) hijo1.valor, (int) ((char) hijo2.valor));
                            this.tipoDato = 1;
                        } else if (hijo1.tipoDato == 4 && hijo2.tipoDato == 1) { //char - int
                            this.valor = (int) Math.pow((int) ((char) hijo1.valor), (int) hijo2.valor);
                            this.tipoDato = 1;
                        } else if (hijo1.tipoDato == 5 && hijo2.tipoDato == 1) { //bool - int
                            if ((boolean) hijo1.valor == true) {
                                this.valor = 1;
                            } else {
                                this.valor = 0;
                            }
                            this.tipoDato = 1;
                        } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 5) { //int - bool
                            if ((boolean) hijo2.valor == true) {
                                this.valor = (int) hijo1.valor;
                            } else {
                                this.valor = 1;
                            }
                            this.tipoDato = 1;
                        } else if (hijo1.tipoDato == 1 && hijo2.tipoDato == 1) { //int - int
                            this.valor = (int) Math.pow((int) hijo1.valor, (int) hijo2.valor);
                            this.tipoDato = 1;
                        } else {
                            addError("Los elementos que integran la PONTENCIA son invalidos.");
                            this.tipoDato = -1;
                        }
                    } catch (Exception e) {
                        addError("Error en POTENCIA.");
                        this.tipoDato = -1;
                    }
                }
                break;
            case "aumento":
                this.tipoDato = 0;
                Simbolo simTempAumento;
                hijo1 = this.hijos.get(0);
                hijo1.ejecutar();
                if (hijo1.valor == null) {
                    this.tipoDato = -1;
                    addError("Error en AUMENTO, el dato es null");
                } else {
                    simTempAumento = Compi2_Proyecto1_1s2016.buscarSimbolo(hijo1.nombre);
                    if (hijo1.tipoDato == 2) {
                        if (simTempAumento != null) {
                            simTempAumento.valor = (double) hijo1.valor + 0.01;
                            simTempAumento.tipoDato = 2;
                        }
                        this.valor = (double) hijo1.valor + 0.01;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 1) {
                        if (simTempAumento != null) {
                            simTempAumento.valor = (int) hijo1.valor + 1;
                            simTempAumento.tipoDato = 1;
                        }
                        this.valor = (int) hijo1.valor + 1;
                        this.tipoDato = 1;
                    } else if (hijo1.tipoDato == 4) {
                        if (simTempAumento != null) {
                            simTempAumento.valor = (char) (((int) (char) hijo1.valor) + 1);
                            simTempAumento.tipoDato = 4;
                        }
                        char a = (char) (((int) (char) hijo1.valor) + 1);
                        this.valor = a;
                        this.tipoDato = 4;
                    } else {
                        addError("El elemento al que se le quiere aplicar la operacion ++ es invalido.");
                        this.tipoDato = -1;
                    }
                }
                break;
            case "decremento":
                this.tipoDato = 0;
                Simbolo simTempDecremento;
                hijo1 = this.hijos.get(0);
                hijo1.ejecutar();
                if (hijo1.valor == null) {
                    this.tipoDato = -1;
                    addError("Error en DECREMENTO, el dato es null");
                } else {
                    simTempDecremento = Compi2_Proyecto1_1s2016.buscarSimbolo(hijo1.nombre);
                    if (hijo1.tipoDato == 2) {
                        if (simTempDecremento != null) {
                            simTempDecremento.valor = (double) hijo1.valor - 0.01;
                            simTempDecremento.tipoDato = 2;
                        }
                        this.valor = (double) hijo1.valor - 0.01;
                        this.tipoDato = 2;
                    } else if (hijo1.tipoDato == 1) {
                        if (simTempDecremento != null) {
                            simTempDecremento.valor = (int) hijo1.valor - 1;
                            simTempDecremento.tipoDato = 1;
                        }
                        this.valor = (int) hijo1.valor - 1;
                        this.tipoDato = 1;
                    } else if (hijo1.tipoDato == 4) {
                        if (simTempDecremento != null) {
                            simTempDecremento.valor = (char) (((int) (char) hijo1.valor) - 1);
                            simTempDecremento.tipoDato = 4;
                        }
                        char a = (char) (((int) (char) hijo1.valor) - 1);
                        this.valor = a;
                        this.tipoDato = 4;
                    } else {
                        addError("El elemento al que se le quiere aplicar la operacion -- es invalido.");
                        this.tipoDato = -1;
                    }
                }
                break;
            case "write":
                this.tipoDato = 0;
                hijo1 = this.hijos.get(0);
                hijo1.ejecutar();
                if (hijo1.valor != null) {
                    if (hijo1.tipoDato != -1) {
                        addReturn(hijo1.valor + "");
                        this.tipoDato = 0;
                    } else {
                        this.tipoDato = -1;
                    }

                } else {
                    addError("Se trato de imprimir un valor null.");
                    this.tipoDato = -1;
                }
                break;
        }
    }

    public static void addReturn(String retorno) {
        Compi2_Proyecto1_1s2016.cadenaReturn += retorno + "\n";
    }

    public static void addError(String ocasion) {
        Compi2_Proyecto1_1s2016.cadenaErrores += ocasion + "\n";
    }
}
