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
				System.out.print("> ");
				line = in.readLine();	
				
				if (line.equals("exit")) break;
				
				send = line.getBytes();
				
				System.out.println("Input: " + line);
			
				DatagramPacket sendPacket = new DatagramPacket (send, send.length, IP, 20001);
				//System.out.println("Input length: " + line.length());
				//System.out.println("Datagram length: " + send.length);		
				
				int maxAttempts = 5;
				int attempts = 0;
				boolean received = false;
				DatagramPacket recPacket = null;
				
				do {
					clientSocket.send(sendPacket);
					clientSocket.setSoTimeout(3000);
					try {
						recPacket = new DatagramPacket(rec, rec.length);
						clientSocket.receive(recPacket);
						received = true;
					}
					catch(InterruptedIOException e){
						attempts += 1;
						System.out.println("No response, resending data. Maximum of " + (maxAttempts - attempts) + " more attempts...");
					}
				} while ((!received) && (attempts < maxAttempts));
				
				
					
			    if (received) {
			    	String response = new String(recPacket.getData(), 0, recPacket.getLength());
					System.out.println("Response from server: " + response + "\n");
			    } else {
			    	System.out.println("No response -- giving up.");
			    }

			}
			catch (IOException e){
				System.err.println("errr: " + e);
			}
		
		}
				
		clientSocket.close();
		
	}

}