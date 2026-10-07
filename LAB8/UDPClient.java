/*Write a program on datagram socket for client/server to display the messages on client side, typed
at the server side.*/


import java.io.*;
import java.net.*;

class UDPClient
{
    public static void main(String args[]) throws Exception
    {
        BufferedReader in = new BufferedReader(
            new InputStreamReader(System.in)
        );

        DatagramSocket clientSocket = new DatagramSocket();

        InetAddress IPAddress =
            InetAddress.getByName("localhost");

        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];

        String sentence = "Hello Server";

        sendData = sentence.getBytes();

        DatagramPacket sendPacket =
            new DatagramPacket(
                sendData,
                sendData.length,
                IPAddress,
                9876
            );

        clientSocket.send(sendPacket);

        DatagramPacket receivePacket =
            new DatagramPacket(
                receiveData,
                receiveData.length
            );

        clientSocket.receive(receivePacket);

        String modifiedSentence = new String(
            receivePacket.getData(),
            0,
            receivePacket.getLength()
        );

        System.out.println("FROM SERVER: " + modifiedSentence);

        clientSocket.close();
    }
}