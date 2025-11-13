import java.io.*;
import java.net.*;

public class tcpclient {
    public static void main(String[] args) {
        try {
            // Step 1: Create a Socket object and connect to the server
            Socket cs = new Socket("localhost", 12345);

            // Step 2: Create input and output streams for communication
            BufferedReader in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            PrintWriter out = new PrintWriter(cs.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            // Step 3: Send a message to the server
            //out.println("Hello, Server!");

            // Step 4: Receive and print response from the server
            String message;
			while (true)
			{
			   // Send message to server
			   System.out.print("You: ");
			   message = userInput.readLine();
			   out.println(message);
			   if (message.equalsIgnoreCase("bye")) {
			       System.out.println("You have left the chat.");
			       break;
			    }
			    // Receive message from server
			    message = in.readLine();
			    if (message.equalsIgnoreCase("bye")) {
			    	System.out.println("Server has closed the chat.");
			        break;
			    }
			    System.out.println("Server: " + message);
            }

            //String serverResponse = in.readLine();
            //System.out.println("Server response: " + serverResponse);

            // Step 5: Close streams and socket
            in.close();
            out.close();
            cs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

import java.io.*;
import java.net.*;

public class tcpserver {
    public static void main(String[] args) {
        try {
            // Step 1: Create a ServerSocket object and bind it to a port
            ServerSocket serverSocket = new ServerSocket(12345);

            System.out.println("Server is listening on port 12345...");

            // Step 2: Accept incoming client connections
            Socket clientSocket = serverSocket.accept();

            System.out.println("Client connected: " + clientSocket.getInetAddress());

            // Step 3: Create input and output streams for communication
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            // Step 4: Read data from client and send response
            String message;
			while (true)
			{
				// Receive message from client
			    message = in.readLine();
			    if (message.equalsIgnoreCase("bye"))
			    {
			         System.out.println("Client has left the chat.");
			         break;
			    }
			     System.out.println("Client: " + message);

			       // Send message to client
			       	System.out.print("You: ");
			        message = userInput.readLine();
			        out.println(message);
			        if (message.equalsIgnoreCase("bye")) {
			            System.out.println("You have left the chat.");
			            break;
			        }
            }

            //String clientMessage = in.readLine();
            //System.out.println("Client says: " + clientMessage);

            //out.println("Server received: " + clientMessage);

            // Step 5: Close streams and sockets
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}