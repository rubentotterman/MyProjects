package database;

import config.DatabaseConfiguration;
import model.Score;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScoreDbOperation {

    private final String SELECT_SCORE_QUERY = "select id, username, score, topic from score";
    private final String INSERT_SCORE_QUERY = "insert into score (id, username, score, topic) values (?,?,?,?)";

    public Score insertScore(Score score) throws SQLException {
        try (PreparedStatement statement = DatabaseConfiguration.connect().prepareStatement(INSERT_SCORE_QUERY)) {
            int index = 0;
            statement.setLong(++index, score.getId());
            statement.setString(++index, score.getUsername());
            statement.setInt(++index, score.getScore());
            statement.setString(++index, score.getTopic());
            statement.executeUpdate();
        }
        return score;
    }

    public List<Score> getScores() throws SQLException {
        List<Score> scores = new ArrayList<>();
        try (PreparedStatement statement = DatabaseConfiguration.connect().prepareStatement(SELECT_SCORE_QUERY)) {
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                scores.add(getScore(results));
            }
            return scores;
        }
    }

    private Score getScore(ResultSet results) throws SQLException {
        Score score = new Score();
        score.setId(results.getInt("id"));
        score.setUsername(results.getString("username"));
        score.setScore(results.getInt("score"));
        score.setTopic(results.getString("topic"));
        return score;
    }
}
