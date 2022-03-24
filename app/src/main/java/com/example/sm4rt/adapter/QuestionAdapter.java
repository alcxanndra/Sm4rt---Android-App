package com.example.sm4rt.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sm4rt.R;
import com.example.sm4rt.model.QuestionModel;
import com.example.sm4rt.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<QuestionModel> questionList;

    public static OnItemClickListener onItemClickListener;

    public QuestionAdapter(List<QuestionModel> questionList, OnItemClickListener onItemClickListener) {
        this.questionList = questionList;
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        private final TextView questionTitle;
        private final ConstraintLayout layout;
        private final Button button;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            questionTitle = itemView.findViewById(R.id.question_title);
            layout = itemView.findViewById(R.id.container);
            button = itemView.findViewById(R.id.button);
        }

        public void bind(QuestionModel question){
            questionTitle.setText(question.getTitle());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(question);
                }
            });
        }

    }

    // Filter RecyclerView items
    public void filterList(ArrayList<QuestionModel> filterList) {
        questionList = filterList;
        // Notify adapter of change in recycler view data.
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public QuestionAdapter.QuestionViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_question, viewGroup, false);
        return new QuestionAdapter.QuestionViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(QuestionAdapter.QuestionViewHolder viewHolder, final int position) {
        viewHolder.bind(questionList.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return questionList.size();
    }
}
