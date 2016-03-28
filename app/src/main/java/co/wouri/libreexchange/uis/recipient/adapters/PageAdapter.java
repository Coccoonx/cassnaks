package co.wouri.libreexchange.uis.recipient.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import co.wouri.libreexchange.uis.recipient.fragments.RecentRecipientsListFragment;
import co.wouri.libreexchange.uis.recipient.fragments.RecipientsListFragment;

public class PageAdapter extends FragmentStatePagerAdapter{
    int mNumOfTabs;

    public PageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                RecipientsListFragment tab1 = RecipientsListFragment.newInstance();
                return tab1;
            case 1:
                RecentRecipientsListFragment tab2 = RecentRecipientsListFragment.newInstance();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
