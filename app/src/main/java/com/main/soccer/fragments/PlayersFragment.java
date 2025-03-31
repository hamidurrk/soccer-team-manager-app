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
import com.main.soccer.adapter.PlayersAdapter;
import com.main.soccer.data.DataProvider;
import com.main.soccer.model.Player;
import com.main.soccer.repository.Repository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PlayersFragment extends Fragment {
    private Repository<Player> repository;
    private PlayersAdapter adapter;
    private List<Player> allPlayers;
    private String currentQuery = "";
    private SortOption currentSortOption = SortOption.NAME;
    private FilterOption currentFilterOption = FilterOption.ALL;

    private enum SortOption {
        NAME, TEAM, AGE, NUMBER
    }

    private enum FilterOption {
        ALL, FORWARDS, MIDFIELDERS, DEFENDERS, GOALKEEPERS, YOUNG_TALENTS, EXPERIENCED
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        repository = new Repository<>();
        allPlayers = DataProvider.createSamplePlayers();
        for (Player player : allPlayers) {
            repository.add(player);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_players, container, false);
        
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        adapter = new PlayersAdapter(repository.getAll());
        recyclerView.setAdapter(adapter);
        
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.players_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        
        if (itemId == R.id.sort_by_name) {
            currentSortOption = SortOption.NAME;
        } else if (itemId == R.id.sort_by_team) {
            currentSortOption = SortOption.TEAM;
        } else if (itemId == R.id.sort_by_age) {
            currentSortOption = SortOption.AGE;
        } else if (itemId == R.id.sort_by_number) {
            currentSortOption = SortOption.NUMBER;
        } else if (itemId == R.id.filter_all) {
            currentFilterOption = FilterOption.ALL;
        } else if (itemId == R.id.filter_forwards) {
            currentFilterOption = FilterOption.FORWARDS;
        } else if (itemId == R.id.filter_midfielders) {
            currentFilterOption = FilterOption.MIDFIELDERS;
        } else if (itemId == R.id.filter_defenders) {
            currentFilterOption = FilterOption.DEFENDERS;
        } else if (itemId == R.id.filter_goalkeepers) {
            currentFilterOption = FilterOption.GOALKEEPERS;
        } else if (itemId == R.id.filter_young_talents) {
            currentFilterOption = FilterOption.YOUNG_TALENTS;
        } else if (itemId == R.id.filter_experienced) {
            currentFilterOption = FilterOption.EXPERIENCED;
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
        List<Player> filteredPlayers = new ArrayList<>(allPlayers);

        // Apply search query filter
        if (currentQuery != null && !currentQuery.isEmpty()) {
            String lowercaseQuery = currentQuery.toLowerCase();
            filteredPlayers = repository.filter(player ->
                player.getName().toLowerCase().contains(lowercaseQuery) ||
                player.getTeam().toLowerCase().contains(lowercaseQuery) ||
                player.getPosition().toLowerCase().contains(lowercaseQuery) ||
                player.getNationality().toLowerCase().contains(lowercaseQuery)
            );
        }

        // Apply category filter
        switch (currentFilterOption) {
            case FORWARDS:
                filteredPlayers = repository.filter(player -> 
                    player.getPosition().equals("Forward"));
                break;
            case MIDFIELDERS:
                filteredPlayers = repository.filter(player -> 
                    player.getPosition().equals("Midfielder"));
                break;
            case DEFENDERS:
                filteredPlayers = repository.filter(player -> 
                    player.getPosition().equals("Defender"));
                break;
            case GOALKEEPERS:
                filteredPlayers = repository.filter(player -> 
                    player.getPosition().equals("Goalkeeper"));
                break;
            case YOUNG_TALENTS:
                filteredPlayers = repository.filter(player -> player.getAge() <= 23);
                break;
            case EXPERIENCED:
                filteredPlayers = repository.filter(player -> player.getAge() >= 30);
                break;
        }

        // Apply sorting
        Comparator<Player> comparator = null;
        switch (currentSortOption) {
            case NAME:
                comparator = Comparator.comparing(Player::getName);
                break;
            case TEAM:
                comparator = Comparator.comparing(Player::getTeam);
                break;
            case AGE:
                comparator = Comparator.comparing(Player::getAge).reversed(); // Oldest first
                break;
            case NUMBER:
                comparator = Comparator.comparing(Player::getNumber);
                break;
        }

        if (comparator != null) {
            filteredPlayers.sort(comparator);
        }

        adapter.updatePlayers(filteredPlayers);
    }
} 