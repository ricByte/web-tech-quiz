package beans;

import java.sql.Array;

public class Question {
    private String text;
    private Array answers;
    private Integer solution;
    private String difficulty;
    private String link;
    private String image;

    public Question() {

    }

    public Question(String text, Array answers, Integer solution, String difficulty, String link) {

        this.text = text;
        this.answers = answers;
        this.solution = solution;
        this.difficulty = difficulty;
        this.link = link;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Array getAnswers() {
        return answers;
    }

    public void setAnswers(Array answers) {
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
