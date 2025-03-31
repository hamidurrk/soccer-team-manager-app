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
import com.main.soccer.adapter.TeamsAdapter;
import com.main.soccer.data.DataProvider;
import com.main.soccer.model.Team;
import com.main.soccer.repository.Repository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TeamsFragment extends Fragment {
    private Repository<Team> repository;
    private TeamsAdapter adapter;
    private List<Team> allTeams;
    private String currentQuery = "";
    private SortOption currentSortOption = SortOption.NAME;
    private FilterOption currentFilterOption = FilterOption.ALL;

    private enum SortOption {
        NAME, LEAGUE, COUNTRY, FOUNDED_YEAR
    }

    private enum FilterOption {
        ALL, EUROPEAN_LEAGUES, AMERICAN_LEAGUES, FOUNDED_BEFORE_1900
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        repository = new Repository<>();
        allTeams = DataProvider.createSampleTeams();
        for (Team team : allTeams) {
            repository.add(team);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teams, container, false);
        
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        adapter = new TeamsAdapter(repository.getAll());
        recyclerView.setAdapter(adapter);
        
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.teams_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        
        if (itemId == R.id.sort_by_name) {
            currentSortOption = SortOption.NAME;
        } else if (itemId == R.id.sort_by_league) {
            currentSortOption = SortOption.LEAGUE;
        } else if (itemId == R.id.sort_by_country) {
            currentSortOption = SortOption.COUNTRY;
        } else if (itemId == R.id.sort_by_founded_year) {
            currentSortOption = SortOption.FOUNDED_YEAR;
        } else if (itemId == R.id.filter_all) {
            currentFilterOption = FilterOption.ALL;
        } else if (itemId == R.id.filter_european) {
            currentFilterOption = FilterOption.EUROPEAN_LEAGUES;
        } else if (itemId == R.id.filter_american) {
            currentFilterOption = FilterOption.AMERICAN_LEAGUES;
        } else if (itemId == R.id.filter_historic) {
            currentFilterOption = FilterOption.FOUNDED_BEFORE_1900;
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
        List<Team> filteredTeams = new ArrayList<>(allTeams);

        // Apply search query filter
        if (currentQuery != null && !currentQuery.isEmpty()) {
            String lowercaseQuery = currentQuery.toLowerCase();
            filteredTeams = repository.filter(team ->
                team.getName().toLowerCase().contains(lowercaseQuery) ||
                team.getLeague().toLowerCase().contains(lowercaseQuery) ||
                team.getCountry().toLowerCase().contains(lowercaseQuery)
            );
        }

        // Apply category filter
        switch (currentFilterOption) {
            case EUROPEAN_LEAGUES:
                filteredTeams = repository.filter(team ->
                    team.getCountry().equals("Spain") ||
                    team.getCountry().equals("England") ||
                    team.getCountry().equals("Germany") ||
                    team.getCountry().equals("Italy") ||
                    team.getCountry().equals("France") ||
                    team.getCountry().equals("Netherlands")
                );
                break;
            case AMERICAN_LEAGUES:
                filteredTeams = repository.filter(team ->
                    team.getCountry().equals("Brazil") ||
                    team.getCountry().equals("Argentina") ||
                    team.getCountry().equals("United States")
                );
                break;
            case FOUNDED_BEFORE_1900:
                filteredTeams = repository.filter(team -> team.getFoundedYear() < 1900);
                break;
        }

        // Apply sorting
        Comparator<Team> comparator = null;
        switch (currentSortOption) {
            case NAME:
                comparator = Comparator.comparing(Team::getName);
                break;
            case LEAGUE:
                comparator = Comparator.comparing(Team::getLeague);
                break;
            case COUNTRY:
                comparator = Comparator.comparing(Team::getCountry);
                break;
            case FOUNDED_YEAR:
                comparator = Comparator.comparing(Team::getFoundedYear);
                break;
        }

        if (comparator != null) {
            filteredTeams.sort(comparator);
        }

        adapter.updateTeams(filteredTeams);
    }
} 