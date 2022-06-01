package database;

import config.DatabaseConfiguration;
import model.MultiChoice;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MultiChoiceDbOperation {

    private final String SELECT_ALL_QUERY = "select id, question, answerA, answerB, answerC, answerD, correctAnswer from multichoicequiz";

    public List<MultiChoice> getAllQuestions() throws SQLException {
        List<MultiChoice> questions = new ArrayList<>();
        try (PreparedStatement statement = DatabaseConfiguration.connect().prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                questions.add(getQuestion(results));
            }
            return questions;
        }
    }


    private MultiChoice getQuestion(ResultSet results) throws SQLException {
        MultiChoice multiChoice = new MultiChoice();
        multiChoice.setId(results.getInt("id"));
        multiChoice.setQuestion(results.getString("question"));
        multiChoice.setAnswerA(results.getString("answerA"));
        multiChoice.setAnswerB(results.getString("answerB"));
        multiChoice.setAnswerC(results.getString("answerC"));
        multiChoice.setAnswerD(results.getString("answerD"));
        multiChoice.setCorrectAnswer(results.getString("correctAnswer"));
        return multiChoice;
    }
}
