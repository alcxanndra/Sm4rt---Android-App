package retrofit;

import com.example.sm4rt.database.data.Question;

import java.util.List;

public class SearchQuestionModel {

    private List<QuestionApiModel> results;
    private Long response_code;

    public List<QuestionApiModel> getResults() {
        return results;
    }

    public Long getResponseCode() {
        return response_code;
    }
}