package retrofit;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.sm4rt.database.data.Question;

public class QuestionApiModel implements Parcelable {
    private String category;
    private String question;
    private String correct_answer;

    public QuestionApiModel(Parcel parcel) {
        String[] data = new String[3];
        parcel.readStringArray(data);

        this.category = data[0];
        this.question = data[1];
        this.correct_answer = data[2];
    }

    public String getCategory() {
        return category;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correct_answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.category, this.question, this.correct_answer});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){

        @Override
        public QuestionApiModel createFromParcel(Parcel parcel) {
            return new QuestionApiModel(parcel);
        }

        @Override
        public QuestionApiModel[] newArray(int i) {
            return new QuestionApiModel[i];
        }
    };
}
