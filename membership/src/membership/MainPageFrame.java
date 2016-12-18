package membership;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;


public class MainPageFrame extends JFrame {
	JTabbedPane jTabbedPane = new JTabbedPane();
	ArrayList<HashMap<String, Object>> excelArray;
	String nowId;
	String nowPassword;

	public MainPageFrame(ArrayList<HashMap<String, Object>> excelArray, String nowId, String nowPassword) {
		setTitle("Membership");
		this.excelArray = excelArray;
		this.nowId = nowId;
		this.nowPassword = nowPassword;

		jTabbedPane.add("ȸ���˻�", new SearchMember(excelArray));
		jTabbedPane.add("ȸ����������", new ChangeMemberInformation(excelArray, nowId));
		jTabbedPane.add("ȸ��ä��", new ChatingClient(nowId));
		jTabbedPane.add("ȸ��Ż��", new DeleteMember(excelArray, nowId, nowPassword));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);

		add(jTabbedPane);
		setVisible(true);
	}
}