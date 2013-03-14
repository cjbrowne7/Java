import java.io.*;
import java.net.*;


public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		DatagramSocket clientSocket = null;
		
		try{
			clientSocket = new DatagramSocket();
		}
		catch (SocketException e){
			System.err.println(e);
		}
		
		InetAddress IP = null;
		
		try{
			IP = InetAddress.getByName("localhost");
		}
		catch (UnknownHostException e){
			System.err.println(e);
		}
		
		
		byte[] send = new byte[5*1024];
		byte[] rec = new byte[5*1024];
		
		String line = null;
		while (true){
			try{
				line = in.readLine();	
				
				if (line.equals("exit")) break;
				
				send = line.getBytes();
				
				System.out.println("Input: " + line);
			
				DatagramPacket sendPacket = new DatagramPacket (send, send.length, IP, 9999);
				System.out.println("Input length: " + line.length());
				System.out.println("Datagram length: " + send.length);
				clientSocket.send(sendPacket);
				//clientSocket.setSoTimeout(1000);
				
				DatagramPacket recPacket = new DatagramPacket(rec, rec.length);
				clientSocket.receive(recPacket);
				
				String response = new String(recPacket.getData(), 0, recPacket.getLength());
				System.out.println("From server: " + response + "\n\n");
			
			}
			catch (IOException e){
				System.err.println(e);
			}
		
		}
				
		clientSocket.close();
		
	}

}