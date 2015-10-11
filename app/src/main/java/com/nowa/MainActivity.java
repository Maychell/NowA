package com.nowa;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

/**
 * Created by maychellfernandesdeoliveira on 05/10/2015.
 */
public class MainActivity extends TabActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources ressources = getResources();
        TabHost tabHost = getTabHost();

        // Android tab
        Intent intentAndroid = new Intent().setClass(this, FeedActivity.class);

        TabHost.TabSpec tabSpecAndroid = tabHost
                .newTabSpec("NEWS")
                .setIndicator("NEWS", ressources.getDrawable(R.drawable.feed))
                .setContent(intentAndroid);

        // Apple tab
        Intent intentApple = new Intent().setClass(this, FollowingActivity.class);

        TabHost.TabSpec tabSpecApple = tabHost
                .newTabSpec("FOLLOWING")
                .setIndicator("FOLLOWING", ressources.getDrawable(R.drawable.following))
                .setContent(intentApple);

        // add all tabs
        tabHost.addTab(tabSpecAndroid);
        tabHost.addTab(tabSpecApple);

        TextView tv = ((TextView) ((TabWidget) tabHost.getTabWidget().findViewById(android.R.id.tabs)).getChildTabViewAt(0).findViewById(android.R.id.title));
        tv.setTextSize(20);
        tv.setTypeface(null, Typeface.BOLD);

        TextView tv1 = ((TextView) ((TabWidget) tabHost.getTabWidget().findViewById(android.R.id.tabs)).getChildTabViewAt(1).findViewById(android.R.id.title));
        tv1.setTextSize(20);
        tv1.setTypeface(null, Typeface.BOLD);

        //set Windows tab as default (zero based)
        tabHost.setCurrentTab(0);
    }

}