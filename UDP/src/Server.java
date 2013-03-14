import java.io.*;
import java.net.*;



public class Server {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DatagramSocket svrSocket = null;
		
		try{
		svrSocket = new DatagramSocket(9999);

		}
		catch (SocketException e){
			System.err.println(e);
		}
		
		byte[] rec = new byte[1024];
		byte[] send = new byte[1024];
		byte[] ack = new byte[1024];
		
		while(true){
			try{
				

			DatagramPacket recPacket = new DatagramPacket(rec, rec.length);
			svrSocket.receive(recPacket);
			
			
			String line = new String(recPacket.getData(), 0 , recPacket.getLength());
			//System.out.println("Received: " + line + "|||");
			InetAddress IP = recPacket.getAddress();
			int port = recPacket.getPort();
			
			System.out.println("From: " + IP + ":" + port);
			System.out.println("Datagram length: " + rec.length);
			System.out.println("Line length: " + line.length());
			System.out.println("Message: " + line + "\n\n");
			
			ack = "ACK".getBytes();
			DatagramPacket ackPacket = new DatagramPacket(ack, ack.length, IP, port);	
			svrSocket.send(ackPacket);
			
			//String lineCap = line.toUpperCase();
			//send = lineCap.getBytes();
			
			//DatagramPacket sendPacket = new DatagramPacket(send, send.length, IP, port);
			//svrSocket.send(sendPacket);
			}
			catch (IOException e){
				System.err.println(e);
			}
		}
			
		
	}

}