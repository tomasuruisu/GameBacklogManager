package com.thomas.gamebacklogmanager;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditGameActivity extends Activity {

    public static final String EXTRA_ID =
            "com.thomas.gamebacklogmanager.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.thomas.gamebacklogmanager.EXTRA_TITLE";
    public static final String EXTRA_PLATFORM =
            "com.thomas.gamebacklogmanager.EXTRA_PLATFORM";
    public static final String EXTRA_STATUS =
            "com.thomas.gamebacklogmanager.EXTRA_STATUS";
    public static final String EXTRA_NOTES =
            "com.thomas.gamebacklogmanager.EXTRA_NOTES";
    public static final String EXTRA_DATE =
            "com.thomas.gamebacklogmanager.EXTRA_DATE";

    EditText gameTitle;
    EditText gamePlatform;
    EditText gameNotes;
    Spinner gameStatus;
    String gameDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        gameTitle = findViewById(R.id.edit_title);
        gamePlatform = findViewById(R.id.edit_platform);
        gameNotes = findViewById(R.id.edit_notes);
        gameStatus = findViewById(R.id.edit_status);
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        gameDate = dateFormat.format(date);

        setTitle("Add Game");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateGameObject();
            }
        });
    }

    private void updateGameObject() {
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        String title = gameTitle.getText().toString();
        String platform = gamePlatform.getText().toString();
        String status = gameStatus.getSelectedItem().toString();
        String notes = gameNotes.getText().toString();
        String date = gameDate;

        if (title.trim().isEmpty() || platform.trim().isEmpty() || status.trim().isEmpty()) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_ID, id);
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_PLATFORM, platform);
        data.putExtra(EXTRA_STATUS, status);
        data.putExtra(EXTRA_NOTES, notes);
        data.putExtra(EXTRA_DATE, date);

        setResult(RESULT_OK, data);
        finish();

    }
}
