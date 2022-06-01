package database;

import config.DatabaseConfiguration;
import model.Binary;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BinaryDbOperation {
    private final String SELECT_ALL_QUERY = "select id, question, correctAnswer from binaryquiz";

    public List<Binary> getAllQuestions() throws SQLException {
        List<Binary> questions = new ArrayList<>();
        try (PreparedStatement statement = DatabaseConfiguration.connect().prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                questions.add(getQuestion(results));
            }
            return questions;
        }
    }


    private Binary getQuestion(ResultSet results) throws SQLException {
        Binary binary = new Binary();
        binary.setId(results.getInt("id"));
        binary.setQuestion(results.getString("question"));
        binary.setCorrectAnswer(results.getString("correctAnswer"));
        return binary;
    }
}
