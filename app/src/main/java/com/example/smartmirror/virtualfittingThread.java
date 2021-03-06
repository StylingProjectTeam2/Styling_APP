package com.example.smartmirror;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Handler;

//////////////////////////////////////////////////////////////////////////////

public class virtualfittingThread extends AsyncTask<String,String,String>{
    private String output_message;
    private String input_message;
    Socket socket;
    private static String ip="192.9.116.211"; //TODO: have to set smart mirror IP address and port number
//    private static String ip="192.9.116.162";
    //private static String ip = "192.9.116.43";
    private int port;
    private DataOutputStream dataOutput;
    private DataInputStream dataInput;
    String msg;
    String result;
//    public virtualfittingThread()
//    {
//        try {
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    protected void onPreExecute()
    {
        super.onPreExecute();
    }
    protected String doInBackground(String... strings){
        try{
            Log.e("conneting...", "dfd");
           if (SocketHandler.getSocket()!=null)
           {
               socket=SocketHandler.getSocket();

               Log.e("tt", "ttt");
           }
           else{
               Log.e("else", "~~~");
               socket = new Socket(ip,9990);
               Log.e("make", "~~~");
               SocketHandler.setSocket(socket);
               Log.e("~~~", "~~~");
           }
            Log.e("[socket]","before");
            //InetAddress serverAddress=InetAddress.getByName(ip);
            //Log.e("serverAddress",String.valueOf(ip));
            //socket=new Socket(serverAddress,9999);

            Boolean result=socket.isConnected();
            Log.e("[socket]",String.valueOf(result));

            Log.e("[socket]","after");
            dataOutput=new DataOutputStream(socket.getOutputStream());
            dataInput=new DataInputStream(socket.getInputStream());
            output_message=strings[0];
            dataOutput.writeUTF(output_message);
            Log.e("send", output_message);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        try
        {
            Log.e("ouput",output_message);
            byte[] buffer=new byte[10000000];
            int read_Byte=dataInput.read(buffer);
            input_message=new String(buffer,0,read_Byte);
//            if(!input_message.equals("stop"))
//            {
//                publishProgress(input_message);
//            }
//            else //stop message
//            {
//
//            }
            if(input_message.equals("fitting"))
            {
                result="fitting";
            }
            else if(input_message.equals("camera"))
            {
                result="camera";
            }
            else if(input_message.startsWith("recommended"))
            {
                Log.e("rec input message",input_message);
                String[] recommendation=input_message.split(":");
                result=recommendation[1];
                Log.e("rec result",result);
                //result="recommended";
            }
            // Thread.sleep(2);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }
    protected void onProgressUpdate(String... params) // ???????????? ???????????? ????????? ????????? ????????????(?????? ?????????) -doInBackground()?????? publishProgress()???????????? ???????????? ?????? ?????? ?????? ??????
    {
        Log.e("[PROGRESS]","?????? ?????? ??? ,,,");
    }
    protected void onPostExecute(String result) // ????????? ????????? ?????? ?????? ?????? ????????? ??????(???????????????)
    {
        Log.e("[SUCCESS]","?????? ?????? ??????");
//        if(socket!=null) {
//            try {
//                socket.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        //TODO
    }
}


