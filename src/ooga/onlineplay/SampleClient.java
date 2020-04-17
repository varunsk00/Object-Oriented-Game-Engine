package ooga.onlineplay;

import java.io.*;
import java.net.*;


public class SampleClient {
    public static void main(String[] args) {
        Socket kkSocket = null;
        PrintWriter pw = null;
        BufferedReader br = null;

        try {
            kkSocket = new Socket("MSI", 4444);
            pw = new PrintWriter(kkSocket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: MSI");
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: MSI");
        }

        if (kkSocket != null && pw != null && br != null) {
            try {
                StringBuffer buf = new StringBuffer(50);
                int c;
                String fromServer;

                while ((fromServer = br.readLine()) != null) {
                    System.out.println("Server: " + fromServer);
                    if (fromServer.equals("Bye."))
                        break;
                    while ((c = System.in.read()) != '\n') {
                        buf.append((char)c);
                    }
                    System.out.println("Client: " + buf);
                    pw.println(buf.toString());
                    pw.flush();
                    buf.setLength(0);
                }

                pw.close();
                br.close();
                kkSocket.close();
            } catch (UnknownHostException e) {
                System.err.println("Trying to connect to unknown host: " + e);
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
            }
        }
    }
}