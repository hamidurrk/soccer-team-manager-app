package com.main.soccer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.main.soccer.R;
import com.main.soccer.adapter.MatchesAdapter;
import com.main.soccer.data.DataProvider;
import com.main.soccer.model.Match;
import com.main.soccer.repository.Repository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MatchesFragment extends Fragment {
    private Repository<Match> repository;
    private MatchesAdapter adapter;
    private List<Match> allMatches;
    private String currentQuery = "";
    private SortOption currentSortOption = SortOption.DATE;
    private FilterOption currentFilterOption = FilterOption.ALL;

    private enum SortOption {
        DATE, COMPETITION, HOME_TEAM, AWAY_TEAM
    }

    private enum FilterOption {
        ALL, CHAMPIONS_LEAGUE, DOMESTIC_LEAGUES, HIGH_SCORING, DRAWS
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        repository = new Repository<>();
        allMatches = DataProvider.createSampleMatches();
        for (Match match : allMatches) {
            repository.add(match);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matches, container, false);
        
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        adapter = new MatchesAdapter(repository.getAll());
        recyclerView.setAdapter(adapter);
        
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.matches_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        
        if (itemId == R.id.sort_by_date) {
            currentSortOption = SortOption.DATE;
        } else if (itemId == R.id.sort_by_competition) {
            currentSortOption = SortOption.COMPETITION;
        } else if (itemId == R.id.sort_by_home_team) {
            currentSortOption = SortOption.HOME_TEAM;
        } else if (itemId == R.id.sort_by_away_team) {
            currentSortOption = SortOption.AWAY_TEAM;
        } else if (itemId == R.id.filter_all) {
            currentFilterOption = FilterOption.ALL;
        } else if (itemId == R.id.filter_champions_league) {
            currentFilterOption = FilterOption.CHAMPIONS_LEAGUE;
        } else if (itemId == R.id.filter_domestic) {
            currentFilterOption = FilterOption.DOMESTIC_LEAGUES;
        } else if (itemId == R.id.filter_high_scoring) {
            currentFilterOption = FilterOption.HIGH_SCORING;
        } else if (itemId == R.id.filter_draws) {
            currentFilterOption = FilterOption.DRAWS;
        } else {
            return super.onOptionsItemSelected(item);
        }
        
        applyFiltersAndSort();
        return true;
    }

    public void filter(String query) {
        this.currentQuery = query;
        applyFiltersAndSort();
    }

    private void applyFiltersAndSort() {
        List<Match> filteredMatches = new ArrayList<>(allMatches);

        // Apply search query filter
        if (currentQuery != null && !currentQuery.isEmpty()) {
            String lowercaseQuery = currentQuery.toLowerCase();
            filteredMatches = repository.filter(match ->
                match.getHomeTeam().toLowerCase().contains(lowercaseQuery) ||
                match.getAwayTeam().toLowerCase().contains(lowercaseQuery) ||
                match.getCompetition().toLowerCase().contains(lowercaseQuery) ||
                match.getVenue().toLowerCase().contains(lowercaseQuery)
            );
        }

        // Apply category filter
        switch (currentFilterOption) {
            case CHAMPIONS_LEAGUE:
                filteredMatches = repository.filter(match -> 
                    match.getCompetition().equals("Champions League"));
                break;
            case DOMESTIC_LEAGUES:
                filteredMatches = repository.filter(match -> {
                    String competition = match.getCompetition();
                    return competition.equals("La Liga") ||
                           competition.equals("Premier League") ||
                           competition.equals("Bundesliga") ||
                           competition.equals("Serie A") ||
                           competition.equals("Ligue 1");
                });
                break;
            case HIGH_SCORING:
                filteredMatches = repository.filter(match -> {
                    String[] scores = match.getScore().split("-");
                    int totalGoals = Integer.parseInt(scores[0]) + Integer.parseInt(scores[1]);
                    return totalGoals >= 3;
                });
                break;
            case DRAWS:
                filteredMatches = repository.filter(match -> {
                    String[] scores = match.getScore().split("-");
                    return scores[0].equals(scores[1]);
                });
                break;
        }

        // Apply sorting
        Comparator<Match> comparator = null;
        switch (currentSortOption) {
            case DATE:
                comparator = Comparator.comparing(Match::getDate).reversed(); // Most recent first
                break;
            case COMPETITION:
                comparator = Comparator.comparing(Match::getCompetition);
                break;
            case HOME_TEAM:
                comparator = Comparator.comparing(Match::getHomeTeam);
                break;
            case AWAY_TEAM:
                comparator = Comparator.comparing(Match::getAwayTeam);
                break;
        }

        if (comparator != null) {
            filteredMatches.sort(comparator);
        }

        adapter.updateMatches(filteredMatches);
    }
} 