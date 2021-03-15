import java.util.logging.Logger;
import java.util.logging.Level;
import java.io.IOException;
import java.net.SocketException;
import java.net.*;  

public class UDPServer {

    public String receviedData;
    public static void main ( String [] args ) {
        try {
            DatagramSocket aSocket = new DatagramSocket (9876);
            byte [] buffer = new byte [1024];
            UsersRequest newUsersRequest = new UsersRequest();
            while (true) 
            {
                DatagramPacket request = new DatagramPacket (buffer, buffer.length);
                aSocket.receive(request);
                String dataReceived = new String(request.getData());
                dataReceived = dataReceived.substring(0,request.getLength());
                String dataToSend = newUsersRequest.processDataAndSendResponse(dataReceived);
                System.out.println(dataToSend);
                DatagramPacket requestSend = new DatagramPacket (dataToSend.getBytes(), dataToSend.length(), request.getAddress(), request.getPort ());
                aSocket.send(requestSend);
            }
        } 
        catch ( SocketException ex) 
        {
        Logger.getLogger(UDPServer.class.getName()).log( Level.SEVERE, null, ex);
        }        
        catch ( IOException ex) 
        {
        Logger.getLogger(UDPServer.class.getName()).log( Level.SEVERE, null, ex);
        }
    }
    

}