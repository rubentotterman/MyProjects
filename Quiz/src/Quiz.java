import database.BinaryDbOperation;
import database.MultiChoiceDbOperation;
import database.ScoreDbOperation;
import database.UserDbOperations;
import model.Binary;
import model.MultiChoice;
import model.Score;
import model.User;
import util.UniqueIdUtil;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Quiz {

    private static final UserDbOperations userDbOperations = new UserDbOperations();
    private static final MultiChoiceDbOperation multiChoiceDbOperation = new MultiChoiceDbOperation();
    private static final BinaryDbOperation binaryDbOperation = new BinaryDbOperation();
    private static final ScoreDbOperation scoreDbOperation = new ScoreDbOperation();
    private static User loggedUser;

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to QUIZ");
        while (true) {
            System.out.println("Select below option");
            System.out.println("1. Play");
            System.out.println("2. Quit");
            String choice = scanner.next();
            if (choice.equalsIgnoreCase("2")) {
                System.out.println("Exiting application! Play Again");
                System.exit(0);
            } else {
                mainMenu(scanner);
            }
        }
    }

    public static void mainMenu(Scanner scanner) throws SQLException {
        System.out.println("Select below option");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Quit");
        String choice = scanner.next();
        if (choice.equalsIgnoreCase("1")) {
            login(scanner);
        } else if (choice.equalsIgnoreCase("2")) {
            register(scanner);
        } else {
            System.out.println("Exiting application! Play Again");
            System.exit(0);
        }

    }

    public static void login(Scanner scanner) throws SQLException {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        User user = userDbOperations.login(username, password);
        if (user == null) {
            System.out.println("Invalid username or password! Try Again");
        } else {
            loggedUser = user;
            boolean isPlaying = true;
            while (isPlaying) {
                System.out.println("Select below option: ");
                System.out.println("1. Play MultiChoice Quiz -> Movies");
                System.out.println("2. Play Binary Quiz -> Sports");
                System.out.println("3. View Score Card");
                System.out.println("4. Quit");
                String choice = scanner.next();

                if (choice.equalsIgnoreCase("1")) {
                    playMultiChoice(scanner);
                } else if (choice.equalsIgnoreCase("2")) {
                    playBinary(scanner);
                } else if (choice.equalsIgnoreCase("3")) {
                    viewScore();
                } else {
                    isPlaying = false;
                }
            }
        }
    }

    public static void register(Scanner scanner) {
        boolean isRegistering = true;
        while (isRegistering) {
            scanner.nextLine();
            System.out.println("Enter name: ");
            String name = scanner.nextLine();
            System.out.println("Enter username: ");
            String username = scanner.next();
            System.out.println("Enter password: ");
            String password = scanner.next();

            User user = new User();
            user.setName(name);
            user.setUsername(username);
            user.setPassword(password);

            try {
                userDbOperations.insertUser(user);
                isRegistering = false;
            } catch (Exception e) {
                System.out.println("Username is already taken! Try again with different username");
            }
        }
    }

    public static void playMultiChoice(Scanner scanner) throws SQLException {
        List<MultiChoice> allQuestions = multiChoiceDbOperation.getAllQuestions();
        Collections.shuffle(allQuestions);
        int count = 0;
        for (MultiChoice question : allQuestions) {

            System.out.println(question.getQuestion());
            System.out.println("1." + question.getAnswerA());
            System.out.println("2." + question.getAnswerB());
            System.out.println("3." + question.getAnswerC());
            System.out.println("4." + question.getAnswerD());
            System.out.println("Enter your answer (1/2/3/4): ");
            String userAnswer = scanner.next();

            String correctAnswerNumber = "4";
            if (question.getCorrectAnswer().equalsIgnoreCase(question.getAnswerA())) {
                correctAnswerNumber = "1";
            } else if (question.getCorrectAnswer().equalsIgnoreCase(question.getAnswerB())) {
                correctAnswerNumber = "2";
            } else if (question.getCorrectAnswer().equalsIgnoreCase(question.getAnswerC())) {
                correctAnswerNumber = "3";
            }

            if (userAnswer.equalsIgnoreCase(correctAnswerNumber)) {
                count++;
            }
        }

        Score score = new Score();
        score.setId(UniqueIdUtil.getUniqueId());
        score.setScore(count);
        score.setUsername(loggedUser.getUsername());
        score.setTopic("Movies");

        scoreDbOperation.insertScore(score);

        viewScore();
    }

    public static void playBinary(Scanner scanner) throws SQLException {
        List<Binary> allQuestions = binaryDbOperation.getAllQuestions();
        Collections.shuffle(allQuestions);
        int count = 0;
        for (Binary question : allQuestions) {

            System.out.println(question.getQuestion());
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.println("Enter your answer (1/2): ");
            String userAnswer = scanner.next();

            String correctAnswerNumber;
            if (question.getCorrectAnswer().equalsIgnoreCase("Yes")) {
                correctAnswerNumber = "1";
            } else {
                correctAnswerNumber = "2";
            }

            if (userAnswer.equalsIgnoreCase(correctAnswerNumber)) {
                count++;
            }
        }

        Score score = new Score();
        score.setId(UniqueIdUtil.getUniqueId());
        score.setScore(count);
        score.setUsername(loggedUser.getUsername());
        score.setTopic("Sports");

        scoreDbOperation.insertScore(score);
        viewScore();
    }

    public static void viewScore() throws SQLException {
        List<Score> scores = scoreDbOperation.getScores();
        if (scores.isEmpty()) {
            System.out.println("No Scores Yet!");
        } else {
            for (Score score : scores) {
                System.out.println(score);
            }
        }
    }

}
