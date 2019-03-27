package com.geekykelvin.taskplanner;


import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.ContentValues;

import android.content.DialogInterface;
import android.content.Intent;

import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.geekykelvin.taskplanner.data.TaskContract;
import com.geekykelvin.taskplanner.data.TaskDbHelper;

/**
 * Created by Geeky Kelvin on 11/7/2018.
 * Email: Kelvinator4leo@gmail.com
 */
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private FloatingActionButton mAddTaskButton;
    private Toolbar mToolbar;
    TaskCursorAdapter mCursorAdapter;
    TaskDbHelper mTaskDbHelper = new TaskDbHelper(this);
    ListView taskListView;
    ProgressDialog prgDialog;
    TextView taskText;

    private String alarmTitle = "";

    private static final int VEHICLE_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);


        taskListView = (ListView) findViewById(R.id.list);
        taskText = (TextView) findViewById(R.id.taskText);


        View emptyView = findViewById(R.id.empty_view);
        taskListView.setEmptyView(emptyView);

        mCursorAdapter = new TaskCursorAdapter(this, null);
        taskListView.setAdapter(mCursorAdapter);

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);

                Uri currentVehicleUri = ContentUris.withAppendedId(TaskContract.AlarmTaskEntry.CONTENT_URI, id);

                // Set the URI on the data field of the intent
                intent.setData(currentVehicleUri);

                startActivity(intent);

            }
        });


        mAddTaskButton = (FloatingActionButton) findViewById(R.id.fab);

        mAddTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(v.getContext(), AddTaskActivity.class);
                //startActivity(intent);
                addTaskTitle();
            }
        });

        getSupportLoaderManager().initLoader(VEHICLE_LOADER, null, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle item selection
        switch (item.getItemId()) {
            case R.id.about_menu_item:
                Intent aboutIntent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;

            case R.id.privacy_menu_item:
                Intent privacyIntent = new Intent(MainActivity.this, PrivacyActivity.class);
                startActivity(privacyIntent);
                return true;

                default:
                    return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                TaskContract.AlarmTaskEntry._ID,
                TaskContract.AlarmTaskEntry.KEY_TITLE,
                TaskContract.AlarmTaskEntry.KEY_DATE,
                TaskContract.AlarmTaskEntry.KEY_TIME,
                TaskContract.AlarmTaskEntry.KEY_REPEAT,
                TaskContract.AlarmTaskEntry.KEY_REPEAT_NO,
                TaskContract.AlarmTaskEntry.KEY_REPEAT_TYPE,
                TaskContract.AlarmTaskEntry.KEY_ACTIVE

        };

        return new CursorLoader(this,   // Parent activity context
                TaskContract.AlarmTaskEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
        if (cursor.getCount() > 0){
            taskText.setVisibility(View.VISIBLE);
        }else{
            taskText.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);

    }

    public void addTaskTitle(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set Task Title");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (input.getText().toString().isEmpty()){
                    return;
                }

                alarmTitle = input.getText().toString();
                ContentValues values = new ContentValues();

                values.put(TaskContract.AlarmTaskEntry.KEY_TITLE, alarmTitle);

                Uri newUri = getContentResolver().insert(TaskContract.AlarmTaskEntry.CONTENT_URI, values);

                restartLoader();


                if (newUri == null) {
                    Toast.makeText(getApplicationContext(), "Setting Task Title failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Title set successfully", Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void restartLoader(){
        getSupportLoaderManager().restartLoader(VEHICLE_LOADER, null, this);
    }
}
