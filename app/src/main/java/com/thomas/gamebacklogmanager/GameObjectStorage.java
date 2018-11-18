package com.thomas.gamebacklogmanager;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = GameObject.class, version = 1)
public abstract class GameObjectStorage extends RoomDatabase {

    private static GameObjectStorage instance;

    public abstract GameObjectDao gameObjectDao();

    public static synchronized GameObjectStorage getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
            GameObjectStorage.class, "game_database")
            .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private GameObjectDao gameObjectDao;

        private PopulateDbAsyncTask(GameObjectStorage db) {
            gameObjectDao = db.gameObjectDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            gameObjectDao.insert(new GameObject("Game 1", "Xbox One", "fun", "Want to play", "Dec 2018"));
            return null;
        }
    }
}
