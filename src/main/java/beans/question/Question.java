package beans.question;

import beans.User;

import java.sql.Array;
import java.util.Date;

public class Question {

    private int id;
    private String text;
    private Answer[] answers;
    private Integer solution;
    private String difficulty;
    private User Creator;
    private Date lastModify;

    public Question() {

        this.id = 0;
        this.answers = null;

    }

    public Question(int id, String text, Answer[] answers, Integer solution, String difficulty, User user, Date lastModify) {

        this.id = id;
        this.text = text;
        this.answers = answers;
        this.solution = solution;
        this.difficulty = difficulty;
        this.Creator = user;
        this.lastModify = lastModify;

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

    public Date getLastModify() {
        return lastModify;
    }

    public void setLastModify(Date lastModify) {
        this.lastModify = lastModify;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public boolean isEmpty() {

        boolean isEmptyId = this.id == 0;
        boolean isEmptyAnswers = this.answers.length == 0;

        return isEmptyAnswers && isEmptyId;

    }

}
