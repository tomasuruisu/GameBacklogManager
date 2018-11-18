package com.thomas.gamebacklogmanager;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    RecyclerView recyclerView;
    private GameObjectViewModel gameObjectViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final GameObjectAdapter adapter = new GameObjectAdapter();
        recyclerView.setAdapter(adapter);

        gameObjectViewModel = ViewModelProviders.of(this).get(GameObjectViewModel.class);
        gameObjectViewModel.getAllGameObjects().observe(this, new Observer<List<GameObject>>() {
            @Override
            public void onChanged(@Nullable List<GameObject> gameObjects) {
                adapter.setGameObjects(gameObjects);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                gameObjectViewModel.delete(adapter.getGameObjectAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Game deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new GameObjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GameObject gameObject) {
                Intent intent = new Intent(MainActivity.this, EditGameActivity.class);

                intent.putExtra(EditGameActivity.EXTRA_ID, gameObject.getId());
                intent.putExtra(EditGameActivity.EXTRA_TITLE, gameObject.getTitle());
                intent.putExtra(EditGameActivity.EXTRA_PLATFORM, gameObject.getPlatform());
                intent.putExtra(EditGameActivity.EXTRA_STATUS, gameObject.getStatus());
                intent.putExtra(EditGameActivity.EXTRA_NOTES, gameObject.getNotes());
                intent.putExtra(EditGameActivity.EXTRA_DATE, gameObject.getDate());

                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddGameActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddGameActivity.EXTRA_TITLE);
            String platform = data.getStringExtra(AddGameActivity.EXTRA_PLATFORM);
            String status = data.getStringExtra(AddGameActivity.EXTRA_STATUS);
            String notes = data.getStringExtra(AddGameActivity.EXTRA_NOTES);
            String date = data.getStringExtra(AddGameActivity.EXTRA_DATE);

            GameObject gameObject = new GameObject(title, platform, notes, status, date);
            gameObjectViewModel.insert(gameObject);

            Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK && data.getIntExtra(EditGameActivity.EXTRA_ID, -1) != -1){
            String title = data.getStringExtra(AddGameActivity.EXTRA_TITLE);
            String platform = data.getStringExtra(AddGameActivity.EXTRA_PLATFORM);
            String status = data.getStringExtra(AddGameActivity.EXTRA_STATUS);
            String notes = data.getStringExtra(AddGameActivity.EXTRA_NOTES);
            String date = data.getStringExtra(AddGameActivity.EXTRA_DATE);

            GameObject gameObject = new GameObject(title, platform, notes, status, date);
            gameObject.setId(data.getIntExtra(EditGameActivity.EXTRA_ID, -1));
            gameObjectViewModel.update(gameObject);
            Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Game not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
