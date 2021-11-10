package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.Output;

public class QuestionsDAO {

	Connection cn;
	
	public QuestionsDAO () {
		this.cn = ConnectionManager.getConnection();
	}
	
	public List<QuestionDTO> getRandomQuestion () {
		List<QuestionDTO> result = new ArrayList<>();
		try(Statement stmt = cn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT text, right, eva00, eva01, eva02 FROM questions ORDER BY RANDOM() LIMIT 5")) {
			while(rs.next()) result.add(new QuestionDTO(
					rs.getString("text"),
					rs.getString("right"),
					rs.getString("eva00"),
					rs.getString("eva01"),
					rs.getString("eva02")
					));
			return result;
		} catch (Exception e) {
			Output.printException(e);
		}
		return null;
	}
	
}
