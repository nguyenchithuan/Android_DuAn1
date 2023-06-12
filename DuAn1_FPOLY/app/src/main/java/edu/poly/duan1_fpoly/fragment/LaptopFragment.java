package edu.poly.duan1_fpoly.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import edu.poly.duan1_fpoly.R;
import edu.poly.duan1_fpoly.adapter.PagerLaptopAdapter;
import edu.poly.duan1_fpoly.adapter.PagerPhoneAdapter;
import edu.poly.duan1_fpoly.fragment.phone.TabPhoneFragment;

public class LaptopFragment extends Fragment {
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;

    public static LaptopFragment newInstance() {
        LaptopFragment fragment = new LaptopFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_laptop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View view) {
        viewPager2 = view.findViewById(R.id.id_viewPager);
        tabLayout = view.findViewById(R.id.id_tabLayout);

        PagerLaptopAdapter pagerAdapter = new PagerLaptopAdapter(getActivity());
        viewPager2.setAdapter(pagerAdapter);

        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Liên quan");
                        break;
                    case 1:
                        tab.setText("Mới nhất");
                        break;
                    case 2:
                        tab.setText("Giá giảm");
                        break;
                    case 3:
                        tab.setText("Giá tăng");
                        break;
                    default:
                        tab.setText("Liên quan");
                }
            }
        });

        mediator.attach();
    }
}