package com.nowa.com.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.nowa.R;
import com.nowa.com.adapter.FileAdapter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 * Created by maychellfernandesdeoliveira on 26/11/2015.
 */
public class DialogOpenFile extends DialogFragment {

    public interface ILoadFile {
        public void load(com.nowa.com.domain.File file);
    }

    // Stores names of traversed directories
    ArrayList<String> str = new ArrayList<String>();

    // Check if the first level of the directory structure is the one showing
    private Boolean firstLvl = true;

    private static final String TAG = "F_PATH";

    private Item[] fileList;
    private File path = new File(Environment.getExternalStorageDirectory() + "");
    private String chosenFile;
    private static final int DIALOG_LOAD_FILE = 1000;

    private ListAdapter adapter;

    public static FileAdapter ctx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFileList();
    }

    private void loadFileList() {

        try {
            path.mkdirs();
        } catch (SecurityException e) {
            Log.e(TAG, "unable to write on the sd card ");
        }

        // Checks whether path exists
        if (path.exists()) {
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    File sel = new File(dir, filename);
                    // Filters based on whether the file is hidden or not
                    return (sel.isFile() || sel.isDirectory())
                            && !sel.isHidden();

                }
            };

            String[] fList = path.list(filter);
            fileList = new Item[fList.length];
            for (int i = 0; i < fList.length; i++) {
                if(fList[i].endsWith(".csv"))
                    fileList[i] = new Item(fList[i], R.drawable.ic_csv);
                else
                    fileList[i] = new Item(fList[i], R.mipmap.ic_file);

                // Convert into file path
                File sel = new File(path, fList[i]);

                // Set drawables
                if (sel.isDirectory()) {
                    fileList[i].icon = R.mipmap.ic_folder;
                    Log.d("DIRECTORY", fileList[i].file);
                } else {
                    Log.d("FILE", fileList[i].file);
                }
            }

            if (!firstLvl) {
                Item temp[] = new Item[fileList.length + 1];
                for (int i = 0; i < fileList.length; i++) {
                    temp[i + 1] = fileList[i];
                }
                temp[0] = new Item("Back", R.mipmap.ic_up);
                fileList = temp;
            }
        } else {
            Log.e(TAG, "path does not exist");
        }

        adapter = new ArrayAdapter<Item>(ctx.getCtx(),
                android.R.layout.select_dialog_item, android.R.id.text1,
                fileList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // creates view
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view
                        .findViewById(android.R.id.text1);

                // put the image on the text view
                textView.setCompoundDrawablesWithIntrinsicBounds(
                        fileList[position].icon, 0, 0, 0);

                int dp5 = (int) (5 * ctx.getCtx().getResources().getDisplayMetrics().density + 0.5f);
                textView.setCompoundDrawablePadding(dp5);

                return view;
            }
        };
        update();
    }

    private void update() {
        if(builder == null) return;

        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chosenFile = fileList[which].file;
                File sel = new File(path + "/" + chosenFile);
                if (sel.isDirectory()) {
                    firstLvl = false;

                    // Adds chosen directory to list
                    str.add(chosenFile);
                    fileList = null;
                    path = new File(sel + "");

                    loadFileList();
                    Log.d(TAG, path.getAbsolutePath());

                }

                // Checks if 'up' was clicked
                else if (chosenFile.equalsIgnoreCase("Back") && !sel.exists()) {

                    // present directory removed from list
                    String s = str.remove(str.size() - 1);

                    // path modified to exclude present directory
                    path = new File(path.toString().substring(0,
                            path.toString().lastIndexOf(s)));
                    fileList = null;

                    // if there are no more directories in the list, then
                    // its the first level
                    if (str.isEmpty()) {
                        firstLvl = true;
                    }
                    loadFileList();

                    Log.d(TAG, path.getAbsolutePath());

                }
                // File picked
                else {
                    //Toast.makeText(FeedActivity.this, "path: "+sel.getPath(), Toast.LENGTH_SHORT).show();
                    com.nowa.com.domain.File newFile = new com.nowa.com.domain.File();
                    newFile.setName(chosenFile);
                    String[] format = chosenFile.split(".");
                    newFile.setPath(path.getAbsolutePath());
                    if (format != null && format.length > 0)
                        newFile.setFormat(format[format.length - 1]);
                    //filesToUpload.add(newFile);
                    //ILoadFile loadFile = (Fed) ctx;
                    //FileAdapter feedActivity = (FileAdapter) ctx;//.notifyDataSetChanged();
                    FileAdapter fileAdapter = ctx;
                    fileAdapter.load(newFile);
                }

            }
        });
        builder.show();
    }

    private class Item {
        public String file;
        public int icon;

        public Item(String file, Integer icon) {
            this.file = file;
            this.icon = icon;
        }

        @Override
        public String toString() {
            return file;
        }
    }

    private AlertDialog.Builder builder;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity());

        if (fileList == null) {
            Log.e(TAG, "No files loaded");
            return builder.create();
        }

        builder.setTitle("Choose your file");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chosenFile = fileList[which].file;
                File sel = new File(path + "/" + chosenFile);
                if (sel.isDirectory()) {
                    firstLvl = false;

                    // Adds chosen directory to list
                    str.add(chosenFile);
                    fileList = null;
                    path = new File(sel + "");

                    loadFileList();

                    //removeDialog(DIALOG_LOAD_FILE);
                    //showDialog(DIALOG_LOAD_FILE);
                    Log.d(TAG, path.getAbsolutePath());

                }

                // Checks if 'up' was clicked
                else if (chosenFile.equalsIgnoreCase("Back") && !sel.exists()) {

                    // present directory removed from list
                    String s = str.remove(str.size() - 1);

                    // path modified to exclude present directory
                    path = new File(path.toString().substring(0,
                            path.toString().lastIndexOf(s)));
                    fileList = null;

                    // if there are no more directories in the list, then
                    // its the first level
                    if (str.isEmpty()) {
                        firstLvl = true;
                    }
                    loadFileList();

                    //removeDialog(DIALOG_LOAD_FILE);
                    //showDialog(DIALOG_LOAD_FILE);
                    Log.d(TAG, path.getAbsolutePath());

                }
                // File picked
                else {
                    //Toast.makeText(FeedActivity.this, "path: "+sel.getPath(), Toast.LENGTH_SHORT).show();
                    com.nowa.com.domain.File newFile = new com.nowa.com.domain.File();
                    newFile.setName(chosenFile);
                    String[] format = chosenFile.split(".");
                    if (format != null && format.length > 0)
                        newFile.setFormat(format[format.length - 1]);

                    //filesToUpload.add(newFile);
                }

            }
        });
        return builder.create();
    }
}
