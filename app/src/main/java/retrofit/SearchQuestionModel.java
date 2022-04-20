package retrofit;

import com.example.sm4rt.database.data.Question;

import java.util.List;

public class SearchQuestionModel {

    private List<Question> results;
    private Long response_code;

    public List<Question> getResults() {
        return results;
    }

    public Long getResponseCode() {
        return response_code;
    }
}