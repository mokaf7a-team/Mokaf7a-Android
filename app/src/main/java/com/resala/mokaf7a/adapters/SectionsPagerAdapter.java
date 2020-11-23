package com.resala.mokaf7a.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.resala.mokaf7a.R;
import com.resala.mokaf7a.fragments.ShowDataFragment;
import com.resala.mokaf7a.fragments.StatisticsFragment;

import static com.resala.mokaf7a.LoginActivity.isAdmin;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES =
            new int[]{
                    R.string.tab_text_1,
                    R.string.tab_text_2
            };

    @StringRes
    private static final int[] ADMIN_TAB_TITLES =
            new int[]{
                    R.string.tab_text_1,
                    R.string.tab_text_2
            };

    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        //        return PlaceholderFragment.newInstance(position + 1);
        Fragment fragment = null;
        if (isAdmin) { // admin tabs
            switch (position) {
                case 0:
                    fragment = new StatisticsFragment();
                    break;
                case 1:
                    fragment = new ShowDataFragment();
                    break;
            }
        } else {
            switch (position) {
                case 0:
                    fragment = new StatisticsFragment();
                    break;
                case 1:
                    fragment = new ShowDataFragment();
                    break;
            }
        }
        assert fragment != null;
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (isAdmin) return mContext.getResources().getString(ADMIN_TAB_TITLES[position]);
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}

