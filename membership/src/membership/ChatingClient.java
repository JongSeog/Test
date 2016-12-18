package membership;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ChatingClient extends JPanel implements Runnable {
	String nowId;
	JPanel northPanel = new JPanel();
	JPanel centerPanel = new JPanel();
	JPanel northLeftPanel = new JPanel();
	JPanel northRightPanel = new JPanel();
	JPanel northRightSmallPanel = new JPanel();
	JPanel centerLeftPanel = new JPanel();
	JPanel centerRightPanel = new JPanel();
	JPanel centerLeftBottomPanel = new JPanel();
	JButton connectButton = new JButton("접속");
	JButton okButton = new JButton("확인");
	JButton exitButton = new JButton("종료");
	JLabel checkLabel = new JLabel("      ");

	JPanel jp1 = new JPanel();
	JPanel jp2 = new JPanel();

	List list = new List(100);

	JTextArea chatingTextArea = new JTextArea();
	TextField chatingTextSend = new TextField(50);

	Socket socket;
	BufferedReader in;
	OutputStream out;

	int count;

	public ChatingClient(String nowId) {
		this.nowId = nowId;
		count = 0;

		setLayout(new BorderLayout());
		northPanel.setLayout(new GridLayout(0, 2));

		northLeftPanel.add(new JLabel("대화명"));
		northLeftPanel.add(new JLabel("             "));
		northLeftPanel.add(new JLabel(nowId));
		northLeftPanel.add(new JLabel("             "));
		northLeftPanel.add(connectButton);

		northRightPanel.setLayout(new GridLayout(0, 2));
		jp1.add(new JLabel("인원"));
		jp1.add(checkLabel);
		jp2.add(exitButton);
		northRightPanel.add(jp1);
		northRightPanel.add(jp2);

		northPanel.add(northLeftPanel);
		northPanel.add(northRightPanel);

		centerPanel.setLayout(new BorderLayout());

		centerLeftPanel.setLayout(new BorderLayout());
		centerLeftPanel.add(new JLabel("    "), BorderLayout.EAST);
		centerLeftPanel.add(new JLabel("    "), BorderLayout.WEST);
		chatingTextArea.setBackground(Color.white);
		centerLeftPanel.add(new JScrollPane(chatingTextArea), BorderLayout.CENTER);
		chatingTextArea.setLineWrap(true);
		centerLeftBottomPanel.add(chatingTextSend);
		centerLeftBottomPanel.add(okButton);
		centerLeftPanel.add(centerLeftBottomPanel, BorderLayout.SOUTH);

		centerRightPanel.setLayout(new BorderLayout());
		centerRightPanel.add(new JLabel("접속자 목록"), BorderLayout.NORTH);
		centerRightPanel.add(new JLabel("    "), BorderLayout.EAST);
		centerRightPanel.add(new JLabel("    "), BorderLayout.SOUTH);
		for (int i = 1; i <= 100; i++)
			list.add("");
		centerRightPanel.add(list, BorderLayout.CENTER);

		centerPanel.add(centerLeftPanel, BorderLayout.CENTER);
		centerPanel.add(centerRightPanel, BorderLayout.EAST);

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);

		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				connectProcess();
			}
		});
		chatingTextSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				sendProcess();
			}
		});
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				sendProcess();
			}
		});
		
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				endProcess();
			}
		});
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				String message = in.readLine();
				System.out.println("읽음 : " + message);

				if (message == null) {
					return;
				}

				if (message.charAt(0) == '/') {
					if (message.charAt(1) == 'c') {
						list.replaceItem(message.substring(2), count);
						count++;
						checkLabel.setText(String.valueOf(count));
						
						connectButton.setEnabled(false);

						chatingTextArea.append("***" + message.substring(2) + "님이 입장하였습니다***\n");
					} else if (message.charAt(1) == 'q') {
						String checkString = message.substring(2);
						chatingTextArea.append("***" + message.substring(2) + "님이 퇴장하셨습니다***\n");

						for (int i = 0; i < list.getItemCount(); i++) {
							if (checkString.equals(list.getItem(i))) {
								list.remove(i);
								count--;
								checkLabel.setText(String.valueOf(count));
								break;
							}
						}
					}
				} else {
					chatingTextArea.append(message + "\n");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				chatingTextArea.append(e.getMessage());
			}
		}
	}

	public void connectProcess() {
		try {
			socket = new Socket("localhost", 9317);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// 소켓 객체가 생성되면 입출력 스트림과 연결
			out = socket.getOutputStream();

			System.out.println(nowId);

			out.write((nowId + "\n").getBytes());

			System.out.println("Client connectProcess()보냄 " + nowId);

			new Thread(this).start();
		} catch (Exception e) {
			chatingTextArea.append(e.getMessage());
		}
	}

	public void sendProcess() {
		try {
			out.write((chatingTextSend.getText() + "\n").getBytes());
			System.out.println("Client sendProcess()보냄 : " + chatingTextSend.getText());
		} catch (Exception e) {
			System.out.println("error!!");
			chatingTextArea.append(e.getMessage());
		}
		chatingTextSend.setText(null);
		chatingTextSend.requestFocus();
	}

	public void endProcess() {
		try {
			out.write(("/q" + nowId + "\n").getBytes());
			System.out.println("보냄 : /q" + nowId);
			in.close();
			out.close();
			socket.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("보내기 오류 : " + e.getMessage());
		}
		System.exit(0);
	}
}