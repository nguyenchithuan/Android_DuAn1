package edu.poly.duan1_fpoly.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.util.ArrayList;

import edu.poly.duan1_fpoly.R;
import edu.poly.duan1_fpoly.adapter.SanPhamAdapter;
import edu.poly.duan1_fpoly.adapter.SanPhamMoiAdapter;
import edu.poly.duan1_fpoly.dao.SanPhamDao;
import edu.poly.duan1_fpoly.utils.Server;

public class HomeFragment extends Fragment {
    private ViewFlipper viewFlipper;
    private SanPhamDao sanPhamDao;

    private SanPhamMoiAdapter sanPhamMoiAdapter;
    private RecyclerView rcvSpMoi;

    private SanPhamAdapter sanPhamAdapter;
    private RecyclerView rcvSp;



    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        actionViewFlipper();
    }

    private void actionViewFlipper() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(R.drawable.img_banner_4);
        list.add(R.drawable.img_banner_5);
        list.add(R.drawable.img_banner_6);

        for (int i = 0; i < list.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(list.get(i));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void initView(View view) {
        viewFlipper = view.findViewById(R.id.id_viewFlipper);
        sanPhamDao = new SanPhamDao();

        // sản phẩm mới
        rcvSpMoi = view.findViewById(R.id.rcv_sp_new);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rcvSpMoi.setLayoutManager(linearLayoutManager);

        sanPhamMoiAdapter = new SanPhamMoiAdapter(getContext());
        sanPhamDao.getSanPhamMoi(getContext(), Server.LinkSanPhamMoiNhat, sanPhamMoiAdapter); // tạo dữ liệu
        rcvSpMoi.setAdapter(sanPhamMoiAdapter);


        // sản phẩm
        rcvSp = view.findViewById(R.id.rcv_sp);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvSp.setLayoutManager(gridLayoutManager);
        sanPhamAdapter = new SanPhamAdapter(getContext());
        sanPhamDao.getSanPham(getContext(), Server.LinkSanPham, sanPhamAdapter);
        rcvSp.setAdapter(sanPhamAdapter);
    }
}