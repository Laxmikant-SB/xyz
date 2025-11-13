import java.net.*;

public class UDPClient {
public static void main(String[] args) throws Exception {
DatagramSocket s = new DatagramSocket();
String msg = &quot;Hello UDP Server&quot;;
byte[] buf = msg.getBytes();
InetAddress ip = InetAddress.getByName(&quot;localhost&quot;);
DatagramPacket p = new DatagramPacket(buf, buf.length, ip, 9876);
System.out.println(&quot;Sending: &quot; + msg); // &lt;-- Added print
s.send(p);
s.close();
}
}

import java.net.*;

public class UDPServer {
public static void main(String[] args) throws Exception {
DatagramSocket s = new DatagramSocket(9876);
byte[] buf = new byte[1024];
DatagramPacket p = new DatagramPacket(buf, buf.length);
System.out.println(&quot;UDP Server running...&quot;);
s.receive(p);
System.out.println(&quot;Received: &quot; + new String(p.getData(), 0, p.getLength()));
s.close();
}
}