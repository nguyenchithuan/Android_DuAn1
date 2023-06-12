package edu.poly.duan1_fpoly.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import edu.poly.duan1_fpoly.fragment.phone.TabPhoneAscFragment;
import edu.poly.duan1_fpoly.fragment.phone.TabPhoneDescFragment;
import edu.poly.duan1_fpoly.fragment.phone.TabPhoneFragment;
import edu.poly.duan1_fpoly.fragment.phone.TabPhoneNewFragment;

public class PagerPhoneAdapter extends FragmentStateAdapter {
    public PagerPhoneAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = TabPhoneFragment.newInstance();
                break;
            case 1:
                fragment = TabPhoneNewFragment.newInstance();
                break;
            case 2:
                fragment = TabPhoneDescFragment.newInstance();
                break;
            case 3:
                fragment = TabPhoneAscFragment.newInstance();
                break;
            default:
                fragment = TabPhoneFragment.newInstance();
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
