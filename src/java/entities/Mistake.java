package entities;
public class Mistake {
	private String CourseName;//���Կ�Ŀ
	private String time;//����ʱ��
	private String knowledgeName;//����֪ʶ��
	private String context;//��Ŀ
	private String selections;//ѡ��
	private int Score;//��ֵ
	private String userAnswer;//�û���
	private String systemAnswer;//ϵͳ��
	private String analysis;//��Ŀ����
	
	public String getCourseName() {
		return CourseName;
	}
	public void setCourseName(String courseName) {
		CourseName = courseName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getKnowledgeName() {
		return knowledgeName;
	}
	public void setKnowledgeName(String knowledgeName) {
		this.knowledgeName = knowledgeName;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public int getScore() {
		return Score;
	}
	public void setScore(int score) {
		Score = score;
	}
	public String getUserAnswer() {
		return userAnswer;
	}
	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}
	public String getSystemAnswer() {
		return systemAnswer;
	}
	public void setSystemAnswer(String systemAnswer) {
		this.systemAnswer = systemAnswer;
	}
	public String getSelections() {
		return selections;
	}
	public void setSelections(String selections) {
		this.selections = selections;
	}
	public String getAnalysis() {
		return analysis;
	}
	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
	
	
}
