package com.manvish.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.manvish.Util.VideoList;
import com.manvish.my_application.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<VideoList> videoLists;

    RecyclerAdapter(List<VideoList> videoLists) {
        this.videoLists = new ArrayList<>();
        this.videoLists.addAll(videoLists);
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.iddetails_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Glide.with(this.context)
                .load(videoLists.get(position).getThumb())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumb_iv);

        String id = "<font color='#EE0000'>ID: </font>";
        String title = "<font color='#EE0000'>Title: </font>";

        holder.id_tv.setText(Html.fromHtml(id + videoLists.get(position).getId()));
        holder.title_tv.setText(Html.fromHtml(title + videoLists.get(position).getTitle()));

        holder.constraintlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecyclerAdapter.this.context, PlayVideo.class);
                intent.putExtra("LIST", videoLists);
                intent.putExtra("POSITION", holder.getAdapterPosition());
                RecyclerAdapter.this.context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoLists.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title_tv;
        private final TextView id_tv;
        private final ImageView thumb_iv;
        private ConstraintLayout constraintlayout;

        ViewHolder(View v) {
            super(v);
            title_tv = v.findViewById(R.id.title_tv);
            id_tv = v.findViewById(R.id.id_tv);
            thumb_iv = v.findViewById(R.id.thumb_iv);
            constraintlayout = v.findViewById(R.id.constraintlayout);
        }

    }

}
