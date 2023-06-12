package edu.poly.duan1_fpoly.fragment.laptop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import edu.poly.duan1_fpoly.R;
import edu.poly.duan1_fpoly.adapter.SanPhamAdapter;
import edu.poly.duan1_fpoly.dao.SanPhamDao;
import edu.poly.duan1_fpoly.utils.Server;

public class TabLaptopDescFragment extends Fragment {
    private RecyclerView recyclerView;
    private SanPhamAdapter sanPhamAdapter;
    private SanPhamDao sanPhamDao;

    public static TabLaptopDescFragment newInstance() {
        TabLaptopDescFragment fragment = new TabLaptopDescFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_laptop_desc, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.id_rcv);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        sanPhamAdapter = new SanPhamAdapter(getContext());
        sanPhamDao = new SanPhamDao();

        sanPhamDao.getSanPham(getContext(), Server.LinkSapXep, getHashMap(), sanPhamAdapter);
        recyclerView.setAdapter(sanPhamAdapter);
    }

    private HashMap<String, String> getHashMap() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("idsanpham", "2");
        hashMap.put("sapxep", "0");
        return hashMap;
    }
}