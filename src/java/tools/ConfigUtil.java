package tools;

import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {
	private Properties pro = new Properties();

	public double getStudentRadio() throws IOException {
//		pro.load(this.getClass().getResourceAsStream("/config.properties"));
//		String str = pro.getProperty("studentRadio");
		return Double.parseDouble("0.6");
	}

	public double getTeacherRadio() throws IOException {
		pro.load(this.getClass().getResourceAsStream("/config.properties"));
		String str = pro.getProperty("teacherRadio");
		return Double.parseDouble(str);
	}
	
	
	public int getPageSize() throws IOException {
		pro.load(this.getClass().getResourceAsStream("/config.properties"));
		String str = pro.getProperty("pageSize");
		return Integer.parseInt(str);
	}
	
	
}
