package server;

import java.net.Socket;
import java.util.List;

import db.QuestionDTO;
import db.QuestionsDAO;

public class Lobby {
	private Socket player1;
	private int points1;
	private Socket player2;
	private int points2;
	
	private QuestionsDAO dao;
	private List<QuestionDTO> questions;
	
	public Lobby(Socket player1, Socket player2) {
		this.player1 = player1;
		this.points1 = 0;
		this.player2 = player2;
		this.points2 = 0;
		this.dao = new QuestionsDAO();
		questions = dao.getRandomQuestion();
	}
	
	public void addPoints(Socket player) {
		if(player1.equals(player)) points1++;
		if(player2.equals(player)) points2++;
	}
	
	public QuestionDTO getQuestion () {
		if(questions.size() <= 0) return null;
		else return questions.remove(0);
	}
	
	public int getWinner () {
		return points1 - points2;
	}
}
