package com.thomas.gamebacklogmanager;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class GameObjectRepository {
    private GameObjectDao gameObjectDao;
    private LiveData<List<GameObject>> allGameObjects;

    public GameObjectRepository(Application application) {
        GameObjectStorage storage = GameObjectStorage.getInstance(application);
        gameObjectDao = storage.gameObjectDao();
        allGameObjects = gameObjectDao.getAllGameObjects();
    }

    public void insert(GameObject gameObject) {
        new InsertGameObjectAsyncTask(gameObjectDao).execute(gameObject);
    }

    public void update(GameObject gameObject) {
        new UpdateGameObjectAsyncTask(gameObjectDao).execute(gameObject);
    }

    public void delete(GameObject gameObject) {
        new DeleteGameObjectAsyncTask(gameObjectDao).execute(gameObject);
    }

    public void deleteAllGameObjects() {
        new DeleteAllGameObjectsAsyncTask(gameObjectDao).execute();
    }

    public LiveData<List<GameObject>> getAllGameObjects() {
        return allGameObjects;
    }

    private static class InsertGameObjectAsyncTask extends AsyncTask<GameObject, Void, Void> {
        private GameObjectDao gameObjectDao;

        private InsertGameObjectAsyncTask(GameObjectDao gameObjectDao) {
            this.gameObjectDao = gameObjectDao;
        }

        @Override
        protected Void doInBackground(GameObject... gameObjects) {
            gameObjectDao.insert(gameObjects[0]);
            return null;
        }
    }

    private static class UpdateGameObjectAsyncTask extends AsyncTask<GameObject, Void, Void> {
        private GameObjectDao gameObjectDao;

        private UpdateGameObjectAsyncTask(GameObjectDao gameObjectDao) {
            this.gameObjectDao = gameObjectDao;
        }

        @Override
        protected Void doInBackground(GameObject... gameObjects) {
            gameObjectDao.update(gameObjects[0]);
            return null;
        }
    }

    private static class DeleteGameObjectAsyncTask extends AsyncTask<GameObject, Void, Void> {
        private GameObjectDao gameObjectDao;

        private DeleteGameObjectAsyncTask(GameObjectDao gameObjectDao) {
            this.gameObjectDao = gameObjectDao;
        }

        @Override
        protected Void doInBackground(GameObject... gameObjects) {
            gameObjectDao.delete(gameObjects[0]);
            return null;
        }
    }

    private static class DeleteAllGameObjectsAsyncTask extends AsyncTask<Void, Void, Void> {
        private GameObjectDao gameObjectDao;

        private DeleteAllGameObjectsAsyncTask(GameObjectDao gameObjectDao) {
            this.gameObjectDao = gameObjectDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            gameObjectDao.deleteAllGameObjects();
            return null;
        }
    }

}
