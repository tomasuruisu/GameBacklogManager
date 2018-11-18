package com.thomas.gamebacklogmanager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GameObjectAdapter extends RecyclerView.Adapter<GameObjectAdapter.GameObjectHolder> {
    private List<GameObject> gameObjects = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public GameObjectHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.game_card, viewGroup, false);
        return new GameObjectHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GameObjectHolder gameObjectHolder, int i) {
        GameObject currentGameObject = gameObjects.get(i);
        gameObjectHolder.title.setText(currentGameObject.getTitle());
        gameObjectHolder.platform.setText(currentGameObject.getPlatform());
        gameObjectHolder.status.setText(currentGameObject.getStatus());
        gameObjectHolder.date.setText(currentGameObject.getDate());
    }

    @Override
    public int getItemCount() {
        return gameObjects.size();
    }

    public void setGameObjects(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
        notifyDataSetChanged();
    }

    public GameObject getGameObjectAt(int position) {
        return gameObjects.get(position);
    }

    class GameObjectHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView platform;
        private TextView status;
        private TextView date;

        public GameObjectHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            platform = itemView.findViewById(R.id.platform);
            status = itemView.findViewById(R.id.status);
            date = itemView.findViewById(R.id.date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    listener.onItemClick(gameObjects.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(GameObject gameObject);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
