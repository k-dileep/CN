/*Write a program on datagram socket for client/server to display the messages on client side, typed
at the server side.*/


import java.io.*;
import java.net.*;

class UDPServer
{
    public static void main(String args[]) throws Exception
    {
        DatagramSocket serverSocket = new DatagramSocket(9876);

        BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in)
        );

        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];

        // Receive packet
        DatagramPacket receivePacket =
            new DatagramPacket(receiveData, receiveData.length);

        // Receive message from client
        serverSocket.receive(receivePacket);

        String sentence = new String(
            receivePacket.getData(),
            0,
            receivePacket.getLength()
        );

        System.out.println("RECEIVED: " + sentence);

        // Get client's IP address and port number
        InetAddress IPAddress = receivePacket.getAddress();
        int port = receivePacket.getPort();

        // Enter reply message
        System.out.println("Enter the Message");
        String data = br.readLine();

        sendData = data.getBytes();

        // Send reply to client
        DatagramPacket sendPacket =
            new DatagramPacket(
                sendData,
                sendData.length,
                IPAddress,
                port
            );

        serverSocket.send(sendPacket);

        // Close socket
        serverSocket.close();
    }
}