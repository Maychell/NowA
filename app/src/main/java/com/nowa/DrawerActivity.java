package com.nowa;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.nowa.com.domain.Subject;
import com.nowa.com.utils.Parameter;
import com.parse.ParsePush;

public class DrawerActivity extends AppCompatActivity {

    private Drawer drawer;
    private AccountHeader accountHeader;

    protected void loadDrawer(Bundle savedInstanceState) {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(false)
                .withSavedInstance(savedInstanceState)
                .withThreeSmallProfileImages(false)
                .withHeaderBackground(R.color.primary)
                .addProfiles(
                        Parameter.user.getLogin().equals("segundo2") ?
                            new ProfileDrawerItem().withName(Parameter.user.getLogin()).withIcon(R.drawable.segundo2)
                            :
                            new ProfileDrawerItem().withName(Parameter.user.getLogin()).withIcon(R.drawable.material_drawer_circle_mask)
                )
                .build();

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDisplayBelowStatusBar(true)
                .withHeaderPadding(true)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.LEFT)
                .withSavedInstance(savedInstanceState)
                .withSelectedItem(-1)
                .withAccountHeader(accountHeader)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        return false;
                    }
                })
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        //IDrawerItem x = drawer.getDrawerItem(position);
                        if (position == drawer.getDrawerItems().size())
                            logout();
                        else {
                            try {
                                Subject subject = (Subject) drawerItem.getTag();
                                SubjectFeedActivity.subject = subject;
                                Intent i = new Intent(DrawerActivity.this, SubjectFeedActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        return false;
                    }
                })
                .build();

        for (Subject sub : Parameter.subjects ) {
            drawer.addItem(new PrimaryDrawerItem().withTag(sub).withName(sub.getName()).withIcon(R.drawable.ic_material));
        }

        drawer.addItem(new DividerDrawerItem());
        drawer.addItem(new PrimaryDrawerItem().withName("Logout"));

        drawer.keyboardSupportEnabled(this, true);
    }

    private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
            Toast.makeText(DrawerActivity.this, "onChackedChange: " + ( isChecked ? "true" : "false"), Toast.LENGTH_SHORT).show();
        }
    };

    private void logout() {
        SharedPreferences sharedpreferences = getSharedPreferences(Parameter.MY_PREFERENCES, this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        Parameter.user = null;
        editor.putString("User", null);
        editor.commit();

        Parameter.subjects = null;

        Intent i = new Intent(DrawerActivity.this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(DrawerActivity.this, FeedActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
