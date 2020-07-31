package com.trulyfuture.seklo.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.trulyfuture.seklo.fragments.profileFragments.ProfileEducationFragment;
import com.trulyfuture.seklo.fragments.profileFragments.ProfileExperienceFragment;
import com.trulyfuture.seklo.fragments.profileFragments.ProfileOverviewFragment;
import com.trulyfuture.seklo.fragments.profileFragments.ProfileSkillsFragment;

public class ProfileViewPagerAdapter extends FragmentStateAdapter {


    public ProfileViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                return new ProfileOverviewFragment();
            case 1:
                return new ProfileEducationFragment();
            case 2:
                return new ProfileExperienceFragment();
            case 3:
                return new ProfileSkillsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
