package com.nowa.com.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nowa.FeedActivity;
import com.nowa.R;
import com.nowa.com.domain.File;
import com.nowa.com.domain.Post;
import com.nowa.com.utils.DialogOpenFile;
import com.nowa.com.utils.UserInformationDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by maychellfernandesdeoliveira on 26/11/2015.
 */
public class FileAdapter extends RecyclerView.Adapter<FileAdapter.CustomViewHolder>
        implements DialogOpenFile.ILoadFile {

    private Context ctx;
    private List<File> files;

    public FileAdapter(Context ctx, List<File> files) {
        this.ctx = ctx;
        this.files = files;
    }

    @Override
    public FileAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        CardView view = (CardView) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.file_list_row, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FileAdapter.CustomViewHolder customViewHolder, final int i) {
        final File file = files.get(i);
        DialogOpenFile.ctx = this;
        //Setting text view title
        if(file.getName().equals("add")) {
            customViewHolder.imageView.setImageResource(R.mipmap.ic_add);
            customViewHolder.fileName.setText("");
            customViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogOpenFile dialogOpenFile = new DialogOpenFile();
                    FragmentTransaction ft = ((FragmentActivity)ctx).getSupportFragmentManager().beginTransaction();
                    dialogOpenFile.show(ft, "fdsf");
                    notifyDataSetChanged();
                }
            });
        } else {
            customViewHolder.fileName.setText(file.getName());
            customViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    files.remove(i);
                    notifyDataSetChanged();
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() { return files.size(); }

    @Override
    public void load(File file) {
        if(files != null)
            files.add(file);
        notifyDataSetChanged();
    }
    public Context getCtx() {
        return ctx;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView fileName;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.file_image);
            this.fileName = (TextView) view.findViewById(R.id.file_name);
        }
    }
}