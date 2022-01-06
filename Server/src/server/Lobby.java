package server;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import db.QuestionDTO;
import db.QuestionsDAO;
import server.util.MessageManager;

public class Lobby {
	
	private ConcurrentHashMap<String, Integer> points;
	private ConcurrentHashMap<String, Integer> pointer;
	
	private QuestionsDAO dao;
	private List<QuestionDTO> questions;
	
	public Lobby(String player1, String player2) {
		this.dao = new QuestionsDAO();
		questions = dao.getRandomQuestion();
		points = new ConcurrentHashMap<>();
		points.put(player1, 0);
		points.put(player2, 0);
		pointer = new ConcurrentHashMap<>();
		pointer.put(player1, -1);
		pointer.put(player2, -1);
	}
	
	public void addPoints(String player, int addition) {
		points.put(player, points.get(player) + addition);
	}
	
	public QuestionDTO getQuestion (String identifier) {
		try {
			pointer.put(identifier, pointer.get(identifier) + 1);
			return questions.get(pointer.get(identifier));
		} catch (Exception e) {
			if(isFinished()) sendResults();
			return null;
		}
	}
	
	private void sendResults() {
		points.forEach((k,v) -> {
			MessageManager.sendMessage("#res" + getResult(), k);
		});
	}

	private boolean isFinished() {
		for (int i : pointer.values()) {
			if(i != questions.size()) return false;
		}
		return true;
	}
	
	private String getResult() {
		StringBuilder b = new StringBuilder("");
		points.forEach((k, v) -> {
			b.append(k + "," + v + (b.length() > 0? "":",,"));
		});
		return b.toString();
	}
}
