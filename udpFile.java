import java.io.*;
import java.net.*;

public class UDPReceiver {
    public static void main(String[] args) throws Exception {

        int PORT = 5005;
        int BUFFER_SIZE = 4096;

        DatagramSocket socket = new DatagramSocket(PORT);
        System.out.println("Receiver running... waiting for files.");

        byte[] buffer = new byte[BUFFER_SIZE];

        // Folder to store received files
        File receiverFolder = new File(System.getProperty("user.home") + "/udp_receiver");
        receiverFolder.mkdirs();

        while (true) {

            // Receive file name
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            String fileName = new String(packet.getData(), 0, packet.getLength());

            if (fileName.equals("EXIT")) break;

            // Send ACK to sender
            InetAddress clientAddr = packet.getAddress();
            int clientPort = packet.getPort();
            String ack = "FILENAME_RECEIVED";

            socket.send(new DatagramPacket(ack.getBytes(), ack.length(), clientAddr, clientPort));

            // Create output file
            File file = new File(receiverFolder, fileName);
            FileOutputStream fos = new FileOutputStream(file);

            while (true) {
                DatagramPacket chunkPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(chunkPacket);

                String data = new String(chunkPacket.getData(), 0, chunkPacket.getLength());

                if (data.equals("EOF")) break;  // End of file

                fos.write(chunkPacket.getData(), 0, chunkPacket.getLength());
            }

            fos.close();
            System.out.println("File " + fileName + " received successfully!");
        }

        System.out.println("All files received.");
        socket.close();
    }
}







import java.io.*;
import java.net.*;

public class UDPSender {
    public static void main(String[] args) throws Exception {

        String SERVER_IP = "127.0.0.1"; // Change to receiver IP if using another machine
        int PORT = 5005;
        int BUFFER_SIZE = 4096;

        DatagramSocket socket = new DatagramSocket();
        InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

        // Files to send
        String[] filesToSend = {
                "example.txt",
                "example.js",
                "tiny_audio.mp3",
                "tiny_video.mp4"
        };

        for (String fileName : filesToSend) {

            File file = new File(fileName);

            // Send filename first
            byte[] nameData = fileName.getBytes();
            socket.send(new DatagramPacket(nameData, nameData.length, serverAddr, PORT));

            // Wait for ACK
            byte[] ackBuf = new byte[BUFFER_SIZE];
            DatagramPacket ackPacket = new DatagramPacket(ackBuf, ackBuf.length);

            socket.receive(ackPacket);
            String ack = new String(ackPacket.getData(), 0, ackPacket.getLength());

            if (!ack.equals("FILENAME_RECEIVED")) {
                System.out.println("Receiver did not acknowledge " + fileName);
                continue;
            }

            // Send file data
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                socket.send(new DatagramPacket(buffer, bytesRead, serverAddr, PORT));
            }

            fis.close();

            // Send EOF marker
            socket.send(new DatagramPacket("EOF".getBytes(), 3, serverAddr, PORT));
            System.out.println("Sent file: " + fileName);
        }

        // Send exit marker
        socket.send(new DatagramPacket("EXIT".getBytes(), 4, serverAddr, PORT));
        socket.close();

        System.out.println("All files sent successfully!");
    }
}
