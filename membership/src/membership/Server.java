package membership;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Server extends JFrame implements Runnable {
	Vector vector;
	JPanel jPanel;
	JTextArea jTextArea;

	public Server() {
		vector = new Vector();
		setTitle("Server");
		setSize(500, 800);
		setLayout(new BorderLayout());
	
		jTextArea = new JTextArea();
				
		add(new JScrollPane(jTextArea));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(9317);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("생성오류 : " + e);
			return;
		}

		while (true) {
			try {
				jTextArea.append("클라이언트 접속대기중\n");
				Socket socket = serverSocket.accept();
				jTextArea.append("클라이언트 접속처리\n");

				Service service = new Service(socket);
				service.start();
				service.myName = service.in.readLine();
				service.vectorMessageAll("/c" + service.myName);
				vector.add(service);
				
				for(int i = 0; i < vector.size(); i++) {
					Service service1 = (Service) vector.elementAt(i);
					service.sendMessage("/c" + service1.myName);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
	}

	class Service extends Thread {
		String myName = "guest";
		BufferedReader in;
		OutputStream out;
		Socket socket;

		public Service(Socket socket) {
			try {
				this.socket = socket;
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = socket.getOutputStream();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}

		public void run() {
			while (true) {
				try {
					String message = in.readLine();
					jTextArea.append("\n" + "읽음 : " + message + "\n");

					if (message == null) {
						return;
					}

					if (message.charAt(0) == '/') {
						if (message.charAt(1) == 'q') {
							try {
								for (int i = 0; i < vector.size(); i++) {
									Service vectorService = (Service) vector.get(i);
									if (myName.equals(vectorService.myName)) {
										vector.remove(i);
										break;
									}
								}
								vectorMessageAll("/q" + myName);

								in.close();
								out.close();
								socket.close();
								return;
							} catch (Exception e) {
								vectorMessageAll("/q" + myName);
							}
						}
					} else {
						vectorMessageAll(myName + ">" + message);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
			}

		}

		public void vectorMessageAll(String vectorMessage) {
			for (int i = 0; i < vector.size(); i++) {
				try {
					Service vectorService = (Service) vector.elementAt(i);

					vectorService.sendMessage(vectorMessage);
				} catch (Exception e) {
					vector.removeElementAt(i--);
				}
			}
		}

		public void sendMessage(String message) throws Exception {
			out.write((message + "\n").getBytes());
			jTextArea.append("보냄 : " + message + "\n");
		}
	}

	public static void main(String[] args) {
		Server server = new Server();
		new Thread(server).start();
	}
}