

package beans.question;

import beans.BasicObjectResponse;

public class QuestionListResponse extends BasicObjectResponse {

    private Question[] questionList;

    public QuestionListResponse() {
        this.questionList = null;
        this.setStatus(false);
    }

    public QuestionListResponse(Boolean status, Question[] questionList) {
        this.questionList = questionList;
        this.setStatus(status);
    }

    public Question[] getQuestions() {
        return questionList;
    }

    public void setQuestions(Question[] questions) {
        this.questionList = questions;
    }
}
