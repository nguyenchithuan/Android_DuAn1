package edu.poly.duan1_fpoly.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import edu.poly.duan1_fpoly.fragment.laptop.TabLaptopAscFragment;
import edu.poly.duan1_fpoly.fragment.laptop.TabLaptopDescFragment;
import edu.poly.duan1_fpoly.fragment.laptop.TabLaptopFragment;
import edu.poly.duan1_fpoly.fragment.laptop.TabLaptopNewFragment;

public class PagerLaptopAdapter extends FragmentStateAdapter {
    public PagerLaptopAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = TabLaptopFragment.newInstance();
                break;
            case 1:
                fragment = TabLaptopNewFragment.newInstance();
                break;
            case 2:
                fragment = TabLaptopDescFragment.newInstance();
                break;
            case 3:
                fragment = TabLaptopAscFragment.newInstance();
                break;
            default:
                fragment = TabLaptopFragment.newInstance();
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
