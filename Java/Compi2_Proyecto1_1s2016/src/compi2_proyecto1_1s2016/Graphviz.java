package compi2_proyecto1_1s2016;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Graphviz {

    static String codigo;
    static File texto;
    static String path;
    static int contador = 0;

    public class Nodo {

        String data;
        String nombre;
        String simbolo;
        boolean relacionado;

        public Nodo(String nombre, String simbolo) {
            this.relacionado = false;
            this.nombre = nombre;
            this.simbolo = simbolo;
            this.data = nombre + "[ label = " + "\"" + simbolo + "\"" + " ];\n";
        }
    }

    public class Relacion {

        String data;

        public Relacion(String nodoInicial, String nodoFinal) {
            this.data
                    = "\"" + nodoInicial + "\"" + " -- " + "\"" + nodoFinal + "\"" + ";\n";
        }
    }

    public void graficarSintactico(NodoArbolSintactico root) {
        codigo = "";
        contador = 0;
        graficarSintacticoAux(root, 0);
        archivoGraphviz(codigo, "ArbolSintactico");
        generar("ArbolSintactico");
    }

    void graficarSintacticoAux(NodoArbolSintactico root, int padre) {
        if (root.hijos != null) {
            codigo += "\"" + root.nombre + "_" + contador + "\" [ label = \"" + root.nombre + "\" ]";

            for (int i = root.hijos.size() - 1; i >= 0; i--) {
                NodoArbolSintactico hijo = root.hijos.get(i);
                contador++;
                codigo += "\"" + root.nombre + "_" + padre + "\" -> \"" + hijo.nombre + "_" + contador + "\"";
                graficarSintacticoAux(hijo, contador);
            }
            if (root.hijos.isEmpty()) {
                codigo += root.nombre + "_" + contador + "[ label = \"" + root.nombre + "\" ]";
            }
        } else {
            codigo += "\"" + root.nombre + "_" + contador + "\" [ label = \"" + root.nombre + "\" ]";
        }
    }

    public void graficarAST(nodoAST root) {
        contador = 0;
        System.out.println("GRAFICANDO...");
        codigo = "";
        graficarASTAux(root, 0);
        archivoGraphviz(codigo, "pruebaAST");
        generar("pruebaAST");

        contador = 0;
        codigo = "";
        graficarASTAux2(root, 0);
        archivoGraphviz(codigo, "pruebaAST_Tipo");
        generar("pruebaAST_Tipo");

        contador = 0;
        codigo = "";
        graficarASTAux3(root, 0);
        archivoGraphviz(codigo, "pruebaAST_Valor");
        generar("pruebaAST_Valor");
    }

    public void graficarASTAux(nodoAST root, int padre) {
        if (root.hijos != null) {
            codigo += "\"" + root.nombre + "_" + contador + "\" [ label = \"" + root.tipoDato + "\" ]";

            for (nodoAST hijo : root.hijos) {
                contador++;
                codigo += "\"" + root.nombre + "_" + padre + "\" -> \"" + hijo.nombre + "_" + contador + "\"";
                graficarASTAux(hijo, contador);
            }
            if (root.hijos.isEmpty()) {
                codigo += root.nombre + "_" + contador + "[ label = \"" + root.tipoDato + "\" ]";
            }
        } else {
            codigo += "\"" + root.nombre + "_" + contador + "\" [ label = \"" + root.tipoDato + "\" ]";
        }
    }

    public void graficarASTAux2(nodoAST root, int padre) {
        if (root.hijos != null) {
            codigo += "\"" + root.nombre + "_" + contador + "\" [ label = \"" + root.tipo + "\" ]";

            for (nodoAST hijo : root.hijos) {
                contador++;
                codigo += "\"" + root.nombre + "_" + padre + "\" -> \"" + hijo.nombre + "_" + contador + "\"";
                graficarASTAux2(hijo, contador);
            }
            if (root.hijos.isEmpty()) {
                codigo += root.nombre + "_" + contador + "[ label = \"" + root.tipo + "\" ]";
            }
        } else {
            codigo += "\"" + root.nombre + "_" + contador + "\" [ label = \"" + root.tipo + "\" ]";
        }
    }

    public void graficarASTAux3(nodoAST root, int padre) {
        if (root.hijos != null) {
            if (root.valor == null) {
                codigo += "\"" + root.nombre + "_" + contador + "\" [ label = \"" + "null" + "\" ]";
            } else {
                codigo += "\"" + root.nombre + "_" + contador + "\" [ label = \"" + root.valor + "\" ]";
            }
            for (nodoAST hijo : root.hijos) {
                contador++;
                codigo += "\"" + root.nombre + "_" + padre + "\" -> \"" + hijo.nombre + "_" + contador + "\"";
                graficarASTAux3(hijo, contador);
            }
            if (root.hijos.isEmpty()) {
                if (root.valor == null) {
                    codigo += root.nombre + "_" + contador + "[ label = \"" + "null" + "\" ]";
                } else {
                    codigo += root.nombre + "_" + contador + "[ label = \"" + root.valor + "\" ]";
                }
            }
        } else {
            if (root.valor == null) {
                codigo += "\"" + root.nombre + "_" + contador + "\" [ label = \"" + "null" + "\" ]";
            } else {
                codigo += "\"" + root.nombre + "_" + contador + "\" [ label = \"" + root.valor + "\" ]";
            }
        }
    }

    public void crearXML(String codigoXML, String nombre) {
        try {
            path = "C:\\Users\\Mac\\Desktop\\" + nombre + ".xml";
            Graphviz.texto = new File(path);
            try (FileWriter writer = new FileWriter(texto); BufferedWriter buf = new BufferedWriter(writer)) {
                buf.write(codigoXML);
                buf.flush();
            }
        } catch (Exception ex) {
        }
    }

    public void archivoGraphviz(String codigo1, String nombre) {
        try {
            path = "C:\\Users\\Mac\\Desktop\\" + nombre + ".txt";
            Graphviz.texto = new File(path);
            try (FileWriter writer = new FileWriter(texto); BufferedWriter buf = new BufferedWriter(writer)) {
                buf.write(
                        "digraph G {\n"
                        + "node [shape = circle];"
                        + codigo1
                        + "}"
                );
                buf.flush();
            }
        } catch (Exception ex) {
        }
    }

    /**
     *
     * @param nombre
     */
    public void generar(String nombre) {
        try {
            String dotPath = "dot";
            String fileInputPath = "C:\\Users\\Mac\\Desktop\\" + nombre + ".txt";
            String fileOutputPath = "C:\\Users\\Mac\\Desktop\\" + nombre + ".png";
            String tParam = "-Tpng";
            String tOParam = "-o";
            String[] cmd = new String[5];
            cmd[0] = dotPath;
            cmd[1] = tParam;
            cmd[2] = fileInputPath;
            cmd[3] = tOParam;
            cmd[4] = fileOutputPath;

            Runtime rt = Runtime.getRuntime();
            rt.exec(cmd);

        } catch (Exception ex) {
        }
    }

}
