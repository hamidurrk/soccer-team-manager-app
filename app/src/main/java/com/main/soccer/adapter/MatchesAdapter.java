package com.main.soccer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.main.soccer.R;
import com.main.soccer.model.Match;
import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MatchViewHolder> {
    private List<Match> matches;

    public MatchesAdapter(List<Match> matches) {
        this.matches = matches;
    }

    public void updateMatches(List<Match> newMatches) {
        this.matches = newMatches;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_match, parent, false);
        return new MatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        Match match = matches.get(position);
        holder.bind(match);
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    static class MatchViewHolder extends RecyclerView.ViewHolder {
        private final TextView competitionText;
        private final TextView homeTeamText;
        private final TextView scoreText;
        private final TextView awayTeamText;
        private final TextView venueText;
        private final TextView dateText;

        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);
            competitionText = itemView.findViewById(R.id.matchCompetition);
            homeTeamText = itemView.findViewById(R.id.matchHomeTeam);
            scoreText = itemView.findViewById(R.id.matchScore);
            awayTeamText = itemView.findViewById(R.id.matchAwayTeam);
            venueText = itemView.findViewById(R.id.matchVenue);
            dateText = itemView.findViewById(R.id.matchDate);
        }

        public void bind(Match match) {
            competitionText.setText(match.getCompetition());
            homeTeamText.setText(match.getHomeTeam());
            scoreText.setText(match.getScore());
            awayTeamText.setText(match.getAwayTeam());
            venueText.setText(match.getVenue());
            dateText.setText(match.getDate());
        }
    }
} 