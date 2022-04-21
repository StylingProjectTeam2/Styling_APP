package com.example.smartmirror;

import android.util.Log;

import java.io.IOException;
import java.net.Socket;

public class SocketHandler {
    private static Socket socket = null;
    private static String ip = "172.16.17.242"; // TODO: smart mirror IP address

    public static Socket getSocket(){
        return socket;
    }

    public static void setSocket(Socket socket1){

        socket = socket1;
    }
}