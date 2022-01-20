package server.db;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import server.Main;

public class QuestionsDA {
	
	public static List<QuestionDTO> getRandomQuestion () {
		List<QuestionDTO> result = new ArrayList<>();
		try(Statement stmt = ConnectionManager.getConnection().createStatement(); ResultSet rs = stmt.executeQuery("SELECT text, right, eva00, eva01, eva02 FROM questions ORDER BY RANDOM() LIMIT " + Main.RANDOM_COUNT)) {
			while(rs.next()) result.add(new QuestionDTO(
					rs.getString("text"),
					rs.getString("right"),
					rs.getString("eva00"),
					rs.getString("eva01"),
					rs.getString("eva02")
					));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
