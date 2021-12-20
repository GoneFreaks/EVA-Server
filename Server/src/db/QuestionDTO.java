package db;

public class QuestionDTO {

	private String question;
	private String correct;
	private String eva00;
	private String eva01;
	private String eva02;
	
	public QuestionDTO (String question, String correct, String eva00, String eva01, String eva02) {
		this.question = question;
		this.correct = correct;
		this.eva00 = eva00;
		this.eva01 = eva01;
		this.eva02 = eva02;
	}

	public String getQuestion() {
		return question;
	}

	public String getCorrect() {
		return correct;
	}

	public String getEva00() {
		return eva00;
	}

	public String getEva01() {
		return eva01;
	}

	public String getEva02() {
		return eva02;
	}

	@Override
	public String toString() {
		return question + "," + correct + "," + eva00 + "," + eva01 + "," + eva02;
	}
}
