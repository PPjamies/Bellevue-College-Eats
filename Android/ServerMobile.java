package com.example.bc_eats;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerMobile {

    public static Socket socket;
    public static String host = "";
    public static final int port =8000;
    public static BufferedReader in= null;
    public static PrintWriter out= null;


    public static void sendMessageToServer(final String str) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);
                    if (!str.isEmpty()){
                        out.println(str);
                        out.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void communicate() {

        new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    socket = new Socket(host, port);
                    Log.d("Socket was created","socket was created");
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    Log.d("", "unknown host*");
                } catch (IOException e) {
                    Log.d("", "io exception*");
                    e.printStackTrace();
                }

                try {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
                    Log.d("Socket connection","socket connection went through");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                while (true) {
                    String msg = null;
                    try {
                        msg = in.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (msg == null) {
                        try {
                            in.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    } else if (msg.equals("#0")) {


                    } else if (msg.equals("#1")) {


                    } else if (msg.startsWith("?")) {


                    }else if (msg.equals("@@1")){


                    }else if (msg.equals("@@0")){


                    }else if (msg.equals("%%1")){


                    }else if (msg.equals("%%0")){

                    }
                }  // end while

            }  // end run

        }).start();
    }
}
