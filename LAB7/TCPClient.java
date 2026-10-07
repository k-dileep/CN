/* Using TCP/IP sockets, write a client – server program to make the client send the file name and
to make the server send back the contents of the requested file if present.*/

import java.net.*;
import java.io.*;

public class TCPClient {
    public static void main(String args[]) throws Exception {

        // Connecting to the server
        Socket sock = new Socket("127.0.0.1", 4000);

        // Reading the file name from keyboard
        System.out.print("Enter the file name: ");
        BufferedReader keyRead =
                new BufferedReader(new InputStreamReader(System.in));

        String fname = keyRead.readLine();

        // Sending the file name to server
        OutputStream ostream = sock.getOutputStream();
        PrintWriter pwrite = new PrintWriter(ostream, true);

        pwrite.println(fname);

        // Receiving the file contents from server
        InputStream istream = sock.getInputStream();
        BufferedReader socketRead =
                new BufferedReader(new InputStreamReader(istream));

        String str;

        // Reading line-by-line from server
        while ((str = socketRead.readLine()) != null) {
            System.out.println(str);
        }

        // Closing the connections
        pwrite.close();
        socketRead.close();
        keyRead.close();
        sock.close();
    }
}