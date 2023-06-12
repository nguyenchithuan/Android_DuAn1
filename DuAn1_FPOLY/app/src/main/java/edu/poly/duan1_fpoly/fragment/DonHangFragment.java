package edu.poly.duan1_fpoly.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import edu.poly.duan1_fpoly.R;
import edu.poly.duan1_fpoly.adapter.DonHangAdapter;
import edu.poly.duan1_fpoly.dao.DonHangDao;
import edu.poly.duan1_fpoly.dao.UserDao;
import edu.poly.duan1_fpoly.models.DonHang;
import edu.poly.duan1_fpoly.models.Item;
import edu.poly.duan1_fpoly.models.SanPham;
import edu.poly.duan1_fpoly.utils.Server;

public class DonHangFragment extends Fragment {
    private RecyclerView rcvDonHang;
    private DonHangDao donHangDao;

    public static DonHangFragment newInstance() {
        DonHangFragment fragment = new DonHangFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_don_hang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View view) {
        rcvDonHang = view.findViewById(R.id.rcv_don_hang);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvDonHang.setLayoutManager(linearLayoutManager);

        donHangDao = new DonHangDao(); // tạo đối tượng dao
        DonHangAdapter donHangAdapter = new DonHangAdapter(getContext());
        // lấy ra dữ liệu của đơn hàng và đưa vào cho adapter
        donHangDao.getData(getContext(), Server.LinkXemDonHang, getHashMap(), donHangAdapter);
        rcvDonHang.setAdapter(donHangAdapter);
    }

    private HashMap<String, String> getHashMap() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("iduser", UserDao.listUser.get(UserDao.index).getId() + "");
        return hashMap;
    }
}

