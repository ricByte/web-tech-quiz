    package controller.question;

    import beans.question.QuestionResponse;
    import com.google.gson.JsonObject;
    import services.GsonFactory;
    import services.ParameterGetter;
    import services.ResponseFactory;
    import services.question.QuestionService;

    import javax.servlet.ServletException;
    import javax.servlet.annotation.WebServlet;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import java.io.IOException;
    import java.sql.SQLException;

    @WebServlet(name = "QuestionPersist", urlPatterns = {"/question/persist"})
    public class QuestionPersist extends HttpServlet {

        public QuestionPersist() {
            super();
            // TODO Auto-generated constructor stub
        }

        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            JsonObject requestData = ParameterGetter.handleRequest(request);
            QuestionResponse responseObj = new QuestionResponse();


            try {

                String session = GsonFactory.getJsonValue(requestData, "session");
                JsonObject question = requestData.get("question").getAsJsonObject();
                int sizeOfQuestion = question.get("answers").getAsJsonArray().size();
                int answers = question.get("solution").getAsInt();

                if (sizeOfQuestion > 0 && answers > 0) {

                    responseObj = QuestionService.saveQuestion(session, question);

                } else {

                    HttpServletResponse responseHeader = ResponseFactory.createResponse(response);
                    ResponseFactory.sendResponse(responseHeader, responseObj, "persistQuestion", 412);

                }


            } catch (Exception e) {

            }


            HttpServletResponse responseHeader = ResponseFactory.createResponse(response);
            ResponseFactory.sendResponse(responseHeader, responseObj, "persistQuestion");
        }

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            HttpServletResponse responseHeader = ResponseFactory.createResponse(response);
            ResponseFactory.sendResponse(responseHeader, "{}", "persistQuestion");
        }
    }
