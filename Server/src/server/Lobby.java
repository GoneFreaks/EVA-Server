package server;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import db.QuestionDTO;
import db.QuestionsDAO;

public class Lobby {
	
	private ConcurrentHashMap<String, Integer> points;
	
	private QuestionsDAO dao;
	private List<QuestionDTO> questions;
	
	public Lobby(String player1, String player2) {
		this.dao = new QuestionsDAO();
		questions = dao.getRandomQuestion();
		points = new ConcurrentHashMap<>();
		points.put(player1, 0);
		points.put(player2, 0);
	}
	
	public void addPoints(String player, int addition) {
		points.put(player, points.get(player) + addition);
	}
	
	public QuestionDTO getQuestion () {
		return questions.get(0);
		/*if(questions.size() <= 0) return null;
		else return questions.remove(0);*/
	}
}
