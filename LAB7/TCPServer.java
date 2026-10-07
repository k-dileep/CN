/* Using TCP/IP sockets, write a client – server program to make the client send the file name and
to make the server send back the contents of the requested file if present.*/


import java.net.*;
import java.io.*;

public class TCPServer {
    public static void main(String args[]) throws Exception {

        // Establishing the connection with the server
        ServerSocket sersock = new ServerSocket(4000);
        System.out.println("Server ready for connection");

        Socket sock = sersock.accept();

        System.out.println("Connection is successful and waiting for chatting");

        // Reading the file name from client
        InputStream istream = sock.getInputStream();
        BufferedReader fileRead =
                new BufferedReader(new InputStreamReader(istream));

        String fname = fileRead.readLine();

        // Reading file contents
        BufferedReader contentRead =
                new BufferedReader(new FileReader(fname));

        // Keeping output stream ready to send the contents
        OutputStream ostream = sock.getOutputStream();
        PrintWriter pwrite = new PrintWriter(ostream, true);

        String str;

        // Reading file line-by-line and sending to client
        while ((str = contentRead.readLine()) != null) {
            pwrite.println(str);
        }

        // Closing the connections
        sock.close();
        sersock.close();

        pwrite.close();
        fileRead.close();
        contentRead.close();
    }
}