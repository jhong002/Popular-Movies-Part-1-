package com.captech.jhong.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.captech.jhong.popularmovies.R;
import com.captech.jhong.popularmovies.model.GetReviewsResponse;
import com.captech.jhong.popularmovies.network.ReviewsResult;

import java.util.List;

/**
 * Created by jhong on 1/11/16.
 */
public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> implements OnClickListener {

    private List<ReviewsResult> mResults;

    public ReviewsAdapter (GetReviewsResponse response){
        mResults = response.getResults();
    }
    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Context context = holder.mPosterImage.getContext();
        if (context != null){


        }

    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        if (mResults != null){
            itemCount = mResults.size();
        }
        return itemCount;
    }

    @Override
    public void onClick(View view) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView content, author;
        public CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.grid_card);
            content = (CardView) itemView.findViewById(R.id.grid_card);

        }


    }

}
