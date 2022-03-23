package com.example.sm4rt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {

    private List<TopicModel> topicList;

    public TopicAdapter(List<TopicModel> topicList) {
        this.topicList = topicList;
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class TopicViewHolder extends RecyclerView.ViewHolder {
        private final TextView topicName;
        private final TextView topicDescription;
        private final ImageView topicImage;

        private final ConstraintLayout layout;

        public TopicViewHolder(View itemView) {
            super(itemView);
            // Define click listener for the ViewHolder's View

            topicName = itemView.findViewById(R.id.topic_name);
            topicDescription = itemView.findViewById(R.id.topic_description);
            topicImage = itemView.findViewById(R.id.iv_picture);
            layout = itemView.findViewById(R.id.container);
        }

        public void bind(TopicModel topic){
            topicName.setText(topic.getName());
            topicDescription.setText(topic.getDescription());
            topicImage.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), topic.getImageId()));
        }

    }

    // Filter RecyclerView items
    public void filterList(ArrayList<TopicModel> filterList) {
        topicList = filterList;
        // Notify adapter of change in recycler view data.
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.topic_item, viewGroup, false);
        return new TopicViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(TopicViewHolder viewHolder, final int position) {
        viewHolder.bind(topicList.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return topicList.size();
    }
}

