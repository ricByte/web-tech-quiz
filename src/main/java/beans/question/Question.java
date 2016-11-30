package beans.question;

import beans.User;

import java.sql.Array;

public class Question {

    private int id;
    private String text;
    private Answer[] answers;
    private Integer solution;
    private String difficulty;
    private User Creator;

    public Question() {

        this.id = 0;
        this.answers = null;

    }

    public Question(int id, String text, Answer[] answers, Integer solution, String difficulty, User user) {

        this.text = text;
        this.answers = answers;
        this.solution = solution;
        this.difficulty = difficulty;
        this.Creator = user;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {

        this.text = text;
    }

    public Answer[] getAnswers() {
        return answers;
    }


    public void setAnswers(Answer[] answers) {
        this.answers = answers;
    }

    public Integer getSolution() {
        return solution;
    }

    public void setSolution(Integer solution) {
        this.solution = solution;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public User getCreator() {
        return Creator;
    }

    public void setCreator(User creator) {
        Creator = creator;
    }

    public boolean equals(Question other) {



        return true;

    }
}
