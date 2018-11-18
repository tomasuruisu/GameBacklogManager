package com.thomas.gamebacklogmanager;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class GameObjectViewModel extends AndroidViewModel {
    private GameObjectRepository repository;
    private LiveData<List<GameObject>> allGameObjects;

    public GameObjectViewModel(@NonNull Application application) {
        super(application);
        repository = new GameObjectRepository(application);
        allGameObjects = repository.getAllGameObjects();
    }

    public void insert(GameObject gameObject) {
        repository.insert(gameObject);
    }

    public void update(GameObject gameObject) {
        repository.update(gameObject);
    }

    public void delete(GameObject gameObject) {
        repository.delete(gameObject);
    }

    public void deleteAllGameObjects() {
        repository.deleteAllGameObjects();
    }

    public LiveData<List<GameObject>> getAllGameObjects() {
        return allGameObjects;
    }
}
