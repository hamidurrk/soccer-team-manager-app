package com.main.soccer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.main.soccer.R;
import com.main.soccer.model.Player;
import java.util.List;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.PlayerViewHolder> {
    private List<Player> players;

    public PlayersAdapter(List<Player> players) {
        this.players = players;
    }

    public void updatePlayers(List<Player> newPlayers) {
        this.players = newPlayers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_player, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = players.get(position);
        holder.bind(player);
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    static class PlayerViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameText;
        private final TextView teamText;
        private final TextView positionText;
        private final TextView numberText;
        private final TextView nationalityText;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.playerName);
            teamText = itemView.findViewById(R.id.playerTeam);
            positionText = itemView.findViewById(R.id.playerPosition);
            numberText = itemView.findViewById(R.id.playerNumber);
            nationalityText = itemView.findViewById(R.id.playerNationality);
        }

        public void bind(Player player) {
            nameText.setText(player.getName());
            teamText.setText(player.getTeam());
            positionText.setText(player.getPosition());
            numberText.setText(String.valueOf(player.getNumber()));
            nationalityText.setText(player.getNationality());
        }
    }
} 