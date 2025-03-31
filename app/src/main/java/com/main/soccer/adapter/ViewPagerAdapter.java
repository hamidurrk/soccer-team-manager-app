package com.main.soccer.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.main.soccer.fragments.TeamsFragment;
import com.main.soccer.fragments.PlayersFragment;
import com.main.soccer.fragments.MatchesFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private ViewPager2 viewPager;
    private FragmentActivity fragmentActivity;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, ViewPager2 viewPager) {
        super(fragmentActivity);
        this.viewPager = viewPager;
        this.fragmentActivity = fragmentActivity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new TeamsFragment();
                break;
            case 1:
                fragment = new PlayersFragment();
                break;
            case 2:
                fragment = new MatchesFragment();
                break;
            default:
                throw new IllegalArgumentException("Invalid position: " + position);
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void filter(String query) {
        Fragment currentFragment = getFragment(viewPager.getCurrentItem());
        if (currentFragment instanceof TeamsFragment) {
            ((TeamsFragment) currentFragment).filter(query);
        } else if (currentFragment instanceof PlayersFragment) {
            ((PlayersFragment) currentFragment).filter(query);
        } else if (currentFragment instanceof MatchesFragment) {
            ((MatchesFragment) currentFragment).filter(query);
        }
    }

    private Fragment getFragment(int position) {
        return fragmentActivity.getSupportFragmentManager()
                .findFragmentByTag("f" + position);
    }
} 