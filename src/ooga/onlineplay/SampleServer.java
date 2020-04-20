package ooga.onlineplay;
import java.net.*;
import java.io.*;

import java.net.*;
import java.io.*;

import java.net.*;
import java.io.*;

class SampleServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.out.println("Could not listen on port: " + 4444 + ", " + e);
            System.exit(1);
        }

        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
            System.out.println("Connection from:" + clientSocket.getInetAddress());
        } catch (IOException e) {
            System.out.println("Accept failed: " + 4444 + ", " + e);
            System.exit(1);
        }

        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter pw = new PrintWriter(
                    new BufferedOutputStream(clientSocket.getOutputStream(), 1024), false);
            SampleProtocol kks = new SampleProtocol();
            String inputLine, outputLine;

            outputLine = kks.processInput(null);
            pw.println(outputLine);
            pw.flush();

            while ((inputLine = br.readLine()) != null) {
                outputLine = kks.processInput(inputLine);
                pw.println(outputLine);
                pw.flush();
                if (outputLine.equals("Bye."))
                    break;
            }
            pw.close();
            br.close();
            clientSocket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}