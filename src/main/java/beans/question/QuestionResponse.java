package beans.question;

import beans.BasicObjectResponse;

public class QuestionResponse extends BasicObjectResponse {

    private Question question;

    public QuestionResponse() {
        this.question = null;
        this.setStatus(false);
    }

    public QuestionResponse(Boolean status, Question question) {
        this.question = question;
        this.setStatus(status);
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
