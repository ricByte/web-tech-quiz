package beans.question;

import java.sql.Array;

public class Question {

    private int id;
    private String text;
    private Answer[] answers;
    private Integer solution;
    private String difficulty;

    public Question() {

        this.id = 0;
        this.answers = null;

    }

    public Question(String text, Answer[] answers, Integer solution, String difficulty, String link) {

        this.text = text;
        this.answers = answers;
        this.solution = solution;
        this.difficulty = difficulty;

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

}
