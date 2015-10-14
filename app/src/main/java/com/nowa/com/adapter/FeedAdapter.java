package com.nowa.com.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nowa.R;
import com.nowa.com.domain.Post;
import com.nowa.com.utils.UserInformationDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by maychellfernandesdeoliveira on 05/10/2015.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.CustomViewHolder> {

    private Context ctx;
    List<Post> posts;

    public FeedAdapter(Context ctx, List<Post> posts) {
        this.ctx = ctx;
        this.posts = posts;
    }

    @Override
    public FeedAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.feed_list_row, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FeedAdapter.CustomViewHolder customViewHolder, int i) {
        final Post post = posts.get(i);

        //Setting text view title
        customViewHolder.user.setText(post.getUser().getName());
        customViewHolder.datetime.setText(convertDate(post.getDate())+" - "+post.getTime());
        customViewHolder.message.setText(post.getMessage());

        customViewHolder.user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = ((Activity) ctx).getFragmentManager();
                UserInformationDialog userDialog = new UserInformationDialog(ctx, post.getUser());
                userDialog.show(fm, "");
            }
        });
    }

    private String convertDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dateFormat = null;
        try {
            dateFormat = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        return android.text.format.DateFormat.format("MMM", dateFormat) +
                " " + Integer.parseInt((String) android.text.format.DateFormat.format("MM", dateFormat));
    }

    @Override
    public int getItemCount() { return posts.size(); }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView user, datetime, message;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.user = (TextView) view.findViewById(R.id.user);
            this.datetime = (TextView) view.findViewById(R.id.date_time);
            this.message = (TextView) view.findViewById(R.id.message);
        }
    }
}