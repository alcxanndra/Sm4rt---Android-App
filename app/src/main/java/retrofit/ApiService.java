package retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiService {

    @GET("/api.php")
    Call<SearchQuestionModel> getQuestionList(
            @Query("amount") String amount);
}