package edu.poly.duan1_fpoly.dao;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import edu.poly.duan1_fpoly.adapter.DonHangAdapter;
import edu.poly.duan1_fpoly.models.DonHang;
import edu.poly.duan1_fpoly.models.Item;

public class DonHangDao {
    private ArrayList<DonHang> list = new ArrayList<>();

    public void getData(Context context, String link, HashMap<String, String> hashMap, DonHangAdapter donHangAdapter) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null || response.length() != 2) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            int idusert = jsonObject.getInt("iduser");;
                            String diaChi = jsonObject.getString("address");
                            String sdt = jsonObject.getString("address");
                            int soluong = jsonObject.getInt("soluong");
                            long tongTien = jsonObject.getInt("tongtien");
                            ArrayList<Item> arrayList = getListDataItem(jsonObject.getString("item"));

                            list.add(new DonHang(id, idusert, diaChi, sdt, soluong, tongTien, arrayList));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // đảo ngược mảng arraylist để hiển thị đơn hàng mới mua nhất
                    Collections.reverse(list);
                    donHangAdapter.setData(list);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }


    private ArrayList<Item> getListDataItem(String item) {
        ArrayList<Item> arrayList = new ArrayList<>();

        if(item != null || item.length() != 2) {
            try {
                JSONArray jsonArray = new JSONArray(item);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int idSp = jsonObject.getInt("idsanpham");
                    String tenSp = jsonObject.getString("tensanpham");
                    String hinhanh = jsonObject.getString("hinhanhsanpham");
                    int soLuong = jsonObject.getInt("soluongsanpham");
                    int giaSp = jsonObject.getInt("giasanpham");

                    arrayList.add(new Item(idSp, tenSp, hinhanh, soLuong, giaSp));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }


    public ArrayList<DonHang> getList() {
        return list;
    }
}
