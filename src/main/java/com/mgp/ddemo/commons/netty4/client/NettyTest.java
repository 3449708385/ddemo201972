package com.mgp.ddemo.commons.netty4.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class NettyTest {

    public static void main(String[] args){
        try {
            Socket socket=new Socket("localhost",8888);
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            PrintWriter printWriter=new PrintWriter(outputStream);
            for(int i=0;i<1000;i++){
                printWriter.write("{\"id\":"+ i +",\"name\":\"testing\",\"age\":12,\"state\":12}$E$&");
                printWriter.flush();


                System.out.println(i);
            }
            /* try {
                    Thread.sleep(50000);
                }catch (Exception e){

                }*/
           /* byte[] bytes = new byte[inputStream.available()];
            while(inputStream.read(bytes)!=-1){
                System.out.println(new String(bytes,"UTF-8"));
            }*/
           /* socket.shutdownOutput();
            socket.close();*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
