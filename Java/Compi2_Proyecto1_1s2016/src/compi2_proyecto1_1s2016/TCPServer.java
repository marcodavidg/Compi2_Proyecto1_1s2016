/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compi2_proyecto1_1s2016;

import java.net.*;
import java.io.*;
import java.util.*;

public class TCPServer implements Runnable {

    static ServerSocket socket1;
    protected final static int port = 19999;
    static Socket connection;

    static boolean first;
    static StringBuffer process;
    static String TimeStamp;

    public static void startServer() {
        (new Thread(new TCPServer())).start();

    }

    @Override
    public void run() {
        try {
            socket1 = new ServerSocket(port);
            System.out.println("SingleSocketServer Initialized");
            int character;
            int i = 0;
            while (true) {
                i++;
                connection = socket1.accept();
                BufferedInputStream is = new BufferedInputStream(connection.getInputStream());
                InputStreamReader isr = new InputStreamReader(is);
                process = new StringBuffer();

                String tipoRequest = "none";

                ciclo:
                while (true) {
                    character = isr.read();
                    switch (character) {
                        case 1:
                            tipoRequest = "analizar";
                            break ciclo;
                        case 13:
                            tipoRequest = "test";
                            break ciclo;
                        default:
                            process.append((char) character);
                    }

                }
//                System.out.println(process);
                //need to wait 10 seconds for the app to update database
//                try {
//                    Thread.sleep(10000);
//                } catch (Exception e) {
//                }
                TimeStamp = new java.util.Date().toString();
                String returnCode;
                BufferedOutputStream os;
                OutputStreamWriter osw;
                switch (tipoRequest) {
                    case "none":
                        break;
                    case "analizar":
                        if (Compi2_Proyecto1_1s2016.Conjuntos.isEmpty()) {
                            returnCode = "Ningun analizador sintactico valido ha sido creado.";
                            os = new BufferedOutputStream(connection.getOutputStream());
                            osw = new OutputStreamWriter(os, "US-ASCII");
                            osw.write(returnCode);
                            osw.flush();
                            break;
                        }
                        String entrada = process + "";
                        System.out.println("entrada:" + process);
                        try {
                            returnCode = Compi2_Proyecto1_1s2016.analizarCadena(entrada);
                            if (!Compi2_Proyecto1_1s2016.cadenaReturn.equalsIgnoreCase("")) {
                                returnCode += "\n\nSALIDA:\n" + Compi2_Proyecto1_1s2016.cadenaReturn;
                            }
                            if (!Compi2_Proyecto1_1s2016.cadenaErrores.equalsIgnoreCase("")) {
                                returnCode += "\n\nERRORES:\n" + Compi2_Proyecto1_1s2016.cadenaErrores;
                            }
                            returnCode += (char) 13;
                        } catch (Exception e) {
                            e.printStackTrace();
                            returnCode = e.toString();
                        }
                        os = new BufferedOutputStream(connection.getOutputStream());
                        osw = new OutputStreamWriter(os, "US-ASCII");
                        osw.write(returnCode);
                        osw.flush();
                        break;
                    case "test":
                        returnCode = "Conectado exitosamente al socket server el " + TimeStamp + "." + (char) 13;
                        os = new BufferedOutputStream(connection.getOutputStream());
                        osw = new OutputStreamWriter(os, "US-ASCII");
                        osw.write(returnCode);
                        osw.flush();
                        break;
                }
            }

        } catch (IOException e) {
            System.out.println("Error server:\n" + e.toString());
        }
        try {
            connection.close();
        } catch (IOException e) {
            System.out.println("Error server. Connection not closed.");
        }
        System.out.println("Ciclo Terminado.");
    }
}
