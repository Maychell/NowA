package com.nowa.com.utils;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nowa.R;
import com.nowa.com.domain.User;

/**
 * Created by maychellfernandesdeoliveira on 14/10/2015.
 */
public class UserInformationDialog extends DialogFragment {

    private EditText input;
    private User user;
    private Context ctx;

    public UserInformationDialog(Context ctx, User user) {
        this.ctx=ctx;
        this.user=user;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_user_information, null);

        getDialog().setTitle("Perfil do usuário");

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        viewHolder.userName.setText(user.getName());
        viewHolder.course.setText(user.getCourse());
        viewHolder.registerNumber.setText(user.getRegisterNumber());
        viewHolder.loginUser.setText(user.getLogin());
        viewHolder.email.setText(user.getEmail());
        viewHolder.description.setText(user.getDescription());

        // if button is clicked, close the custom dialog
        viewHolder.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView userName, course, registerNumber, loginUser, email, description;
        protected Button btnClose;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.userName = (TextView) view.findViewById(R.id.txt_user_name);
            this.course = (TextView) view.findViewById(R.id.txt_course);
            this.registerNumber = (TextView) view.findViewById(R.id.txt_register_number);
            this.loginUser = (TextView) view.findViewById(R.id.txt_user);
            this.email = (TextView) view.findViewById(R.id.txt_email);
            this.description = (TextView) view.findViewById(R.id.txt_description);
            this.btnClose = (Button) view.findViewById(R.id.btn_close);
        }
    }
}