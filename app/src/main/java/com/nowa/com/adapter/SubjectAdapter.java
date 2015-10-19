package com.nowa.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nowa.R;
import com.nowa.SubjectFeedActivity;
import com.nowa.com.domain.Subject;

import java.util.List;

/**
 * Created by maychellfernandesdeoliveira on 07/10/2015.
 */
public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.CustomViewHolder> {

    private Context ctx;
    List<Subject> subjects;

    public SubjectAdapter(Context ctx, List<Subject> subjects) {
        this.ctx = ctx;
        this.subjects = subjects;
    }

    @Override
    public SubjectAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.subject_list_row, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SubjectAdapter.CustomViewHolder customViewHolder, final int i) {
        Subject subject = subjects.get(i);

        //Setting text view title
        if(subject.getName().equals("Android"))
            customViewHolder.imageView.setImageResource(R.drawable.ic_android);
        else if(subject.getName().equals("Database"))
            customViewHolder.imageView.setImageResource(R.drawable.ic_database);
        else
            customViewHolder.imageView.setImageResource(R.drawable.ic_geometry);

        customViewHolder.subjectName.setText(subject.getName());
        customViewHolder.subjectFollowers.setText("10");

        customViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(ctx, SubjectFeedActivity.class);
                SubjectFeedActivity.subject = subjects.get(i);
                ctx.startActivity(it);
            }
        });
    }

    @Override
    public int getItemCount() { return subjects.size(); }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView subjectName, subjectFollowers;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.subjectName = (TextView) view.findViewById(R.id.txt_subject);
            this.subjectFollowers = (TextView) view.findViewById(R.id.txt_followers_number);
        }
    }
}
