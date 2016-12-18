package membership;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

public class MembershipManager extends JFrame {
	ArrayList<HashMap<String, Object>> excelArray;
	public MembershipManager() {
		excelArray = new ArrayList<HashMap<String, Object>>();
	}
	public void start() {
		CallExcel callExcel = new CallExcel();
		excelArray = callExcel.Call();
		
		LoginFrame loginFrame = new LoginFrame(excelArray);		
	}
}