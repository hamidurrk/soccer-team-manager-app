package com.main.soccer;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.main.soccer.adapter.ViewPagerAdapter;
import com.main.soccer.fragments.TeamsFragment;
import com.main.soccer.fragments.PlayersFragment;
import com.main.soccer.fragments.MatchesFragment;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        pagerAdapter = new ViewPagerAdapter(this, viewPager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(3); // Keep all fragments in memory

        new TabLayoutMediator(tabLayout, viewPager,
            (tab, position) -> {
                switch (position) {
                    case 0:
                        tab.setText("Teams");
                        break;
                    case 1:
                        tab.setText("Players");
                        break;
                    case 2:
                        tab.setText("Matches");
                        break;
                }
            }
        ).attach();

        // Add page change callback to invalidate options menu when switching tabs
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                invalidateOptionsMenu();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Fragment currentFragment = getSupportFragmentManager()
                        .findFragmentByTag("f" + viewPager.getCurrentItem());
                if (currentFragment instanceof TeamsFragment) {
                    ((TeamsFragment) currentFragment).filter(newText);
                } else if (currentFragment instanceof PlayersFragment) {
                    ((PlayersFragment) currentFragment).filter(newText);
                } else if (currentFragment instanceof MatchesFragment) {
                    ((MatchesFragment) currentFragment).filter(newText);
                }
                return true;
            }
        });
        
        return true;
    }
} 