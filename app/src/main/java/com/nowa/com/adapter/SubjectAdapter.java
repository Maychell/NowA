package com.nowa.com.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nowa.R;
import com.nowa.SubjectFeedActivity;
import com.nowa.com.domain.Subject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
        new LoadImage(customViewHolder, subject).execute();
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

    private class LoadImage extends AsyncTask<Void, String, Bitmap> {
        Bitmap bitmap;
        ProgressDialog pDialog;
        SubjectAdapter.CustomViewHolder customViewHolder;
        Subject subject;

        LoadImage(SubjectAdapter.CustomViewHolder customViewHolder, Subject subject) {
            this.customViewHolder = customViewHolder;
            this.subject = subject;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ctx);
            pDialog.setMessage("Loading Image ....");
            pDialog.show();

        }
        protected Bitmap doInBackground(Void... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(subject.getPicture()).getContent());

            } catch (Exception e) {
                e.printStackTrace();
                bitmap = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.ic_geometry);
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {
            customViewHolder.imageView.setImageBitmap(image);

            customViewHolder.subjectName.setText(subject.getName());
            customViewHolder.subjectFollowers.setText("10");

            customViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent(ctx, SubjectFeedActivity.class);
                    SubjectFeedActivity.subject = subject;
                    ctx.startActivity(it);
                }
            });

            pDialog.dismiss();
        }
    }
}
