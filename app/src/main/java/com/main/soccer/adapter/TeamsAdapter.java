package com.main.soccer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.main.soccer.R;
import com.main.soccer.model.Team;
import java.util.List;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.TeamViewHolder> {
    private List<Team> teams;

    public TeamsAdapter(List<Team> teams) {
        this.teams = teams;
    }

    public void updateTeams(List<Team> newTeams) {
        this.teams = newTeams;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_team, parent, false);
        return new TeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        Team team = teams.get(position);
        holder.bind(team);
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    static class TeamViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameText;
        private final TextView leagueText;
        private final TextView countryText;
        private final TextView stadiumText;

        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.teamName);
            leagueText = itemView.findViewById(R.id.teamLeague);
            countryText = itemView.findViewById(R.id.teamCountry);
            stadiumText = itemView.findViewById(R.id.teamStadium);
        }

        public void bind(Team team) {
            nameText.setText(team.getName());
            leagueText.setText(team.getLeague());
            countryText.setText(team.getCountry());
            stadiumText.setText(team.getStadium());
        }
    }
} 