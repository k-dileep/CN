// Write a program for congestion control using leaky bucket algorithm.

import java.util.Random;
import java.util.Scanner;

class leaky_bucket {

    public static void main(String args[]) {

        int drop = 0, mini;
        int nsec, p_remain = 0;
        int o_rate, b_size;
        int packet[];

        packet = new int[100];

        Scanner in = new Scanner(System.in);

        System.out.println("Enter bucket size:");
        b_size = in.nextInt();

        System.out.println("Enter the output rate:");
        o_rate = in.nextInt();

        System.out.println("Enter the number of seconds you want to simulate:");
        nsec = in.nextInt();

        Random rand = new Random();

        // Generate random packets
        for (int i = 0; i < nsec; i++) {
            packet[i] = (rand.nextInt(9) + 1) * 10;
        }

        System.out.println();
        System.out.println("Seconds | Packets Received | Packets Sent | Packets Left | Packets Dropped");
        System.out.println("--------------------------------------------------------------------------");

        // Simulate each second
        for (int i = 0; i < nsec; i++) {

            // Add newly received packets
            p_remain = p_remain + packet[i];

            // Check bucket overflow
            if (p_remain > b_size) {
                drop = p_remain - b_size;
                p_remain = b_size;
            }

            // Send packets at output rate
            mini = Math.min(p_remain, o_rate);

            p_remain = p_remain - mini;

            // Display result
            System.out.printf("%7d | %17d | %13d | %13d | %15d%n",
                    i + 1,
                    packet[i],
                    mini,
                    p_remain,
                    drop);

            // Reset dropped packets
            drop = 0;
        }

        // Empty remaining packets from the bucket
        int second = nsec + 1;

        while (p_remain > 0) {

            mini = Math.min(p_remain, o_rate);

            p_remain = p_remain - mini;

            System.out.printf("%7d | %17d | %13d | %13d | %15d%n",
                    second,
                    0,
                    mini,
                    p_remain,
                    0);

            second++;
        }

        in.close();
    }
}