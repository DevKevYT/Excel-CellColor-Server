

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Connection  {
	
	public static long SESSION_COUNTER = 0;
	
	public final long sessionId;
	
	public long startTime;
	
	public final Socket client;
	public final BufferedWriter writer;
	
	public Thread thread;
	
	public static final byte STATUS_OK = 0;
	public static final byte STATUS_ERROR = 1;
	
	public int status = Codes.CODE_UNKNOWN_ERROR;
	
	public Connection(Socket client) throws IOException {
		this.sessionId = SESSION_COUNTER;
		this.client = client;
		this.writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		SESSION_COUNTER++;
	}
	
	public void terminateConnection() throws IOException {
		client.close();
		writer.close();
	}
	
	public void addThread(Thread thread) {
		this.thread = thread;
	}
	
	public synchronized void activate() {
		this.startTime = System.currentTimeMillis();
		this.thread.start();
	}
	
	//Bitte im JSON Format
	public void sendMessage(String json) throws IOException {
		writer.write(json);
		writer.flush();
	}
}
