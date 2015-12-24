package com.captech.jhong.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.captech.jhong.popularmovies.R;
import com.captech.jhong.popularmovies.activities.MoreInfoActivity;
import com.captech.jhong.popularmovies.AppConstants;
import com.captech.jhong.popularmovies.model.GetDiscoverMovieResponse;
import com.captech.jhong.popularmovies.model.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by jhong on 12/22/15.
 */
public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.ViewHolder> implements OnClickListener {

    private List<Result> mResults;
    public HomePageAdapter (GetDiscoverMovieResponse response){
        mResults = response.getResults();
    }
    @Override
    public HomePageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Context context = holder.mPosterImage.getContext();
        if (context != null){
            holder.mCard.setOnClickListener(this);
            holder.mCard.setTag(position);
            Result result = mResults.get(position);
            String posterPath = result.getPosterPath();
            if (!TextUtils.isEmpty(posterPath)){
                String url = AppConstants.PICTURE_BASE_URL_500 + posterPath;
                Picasso.with(context).load(url).placeholder(R.drawable.placeholder).into(holder.mPosterImage);
            }

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
        int position = view.getTag() != null ? (Integer) view.getTag() : 0;
        Context context = view.getContext();
        if (context != null){
            Intent intent = new Intent(context, MoreInfoActivity.class);
            intent.putExtra("position", position);
            context.startActivity(intent);

        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView mPosterImage;
        public CardView mCard;

        public ViewHolder(View itemView) {
            super(itemView);
            mCard = (CardView) itemView.findViewById(R.id.grid_card);
            mPosterImage = (ImageView) itemView.findViewById(R.id.poster_image);

        }


    }


}
