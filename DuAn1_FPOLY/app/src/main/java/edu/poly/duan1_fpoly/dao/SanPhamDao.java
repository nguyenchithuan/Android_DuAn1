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
import java.util.HashMap;
import java.util.Map;

import edu.poly.duan1_fpoly.adapter.SanPhamAdapter;
import edu.poly.duan1_fpoly.adapter.SanPhamMoiAdapter;
import edu.poly.duan1_fpoly.models.SanPham;

public class SanPhamDao {
    // tạo 2 biến list vì trang chính đều dùng trong 1 đối tượng này
    private ArrayList<SanPham> listSpMoi = new ArrayList<>();
    private ArrayList<SanPham> listSp = new ArrayList<>();


    public void getSanPhamMoi(Context context, String link, SanPhamMoiAdapter adapter) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listSpMoi.clear();
                if(response != null && response.length() != 2) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String tenSp = jsonObject.getString("tensanpham");
                            int giaSp = jsonObject.getInt("giasanpham");
                            String hinhAnhSp = jsonObject.getString("hinhanhsanpham");
                            String moTaSp = jsonObject.getString("motasanpham");
                            int idLoaiSp = jsonObject.getInt("idsanpham");
                            listSpMoi.add(new SanPham(id, tenSp, giaSp, hinhAnhSp, moTaSp, idLoaiSp));
                        }
                        adapter.setData(listSpMoi);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }   
        });

        requestQueue.add(stringRequest);
    }

    // chỉ dùng để lấy dữ liệu
    public void getSanPham(Context context, String link, SanPhamAdapter adapter) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listSp.clear();
                if(response != null && response.length() != 2) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String tenSp = jsonObject.getString("tensanpham");
                            int giaSp = jsonObject.getInt("giasanpham");
                            String hinhAnhSp = jsonObject.getString("hinhanhsanpham");
                            String moTaSp = jsonObject.getString("motasanpham");
                            int idLoaiSp = jsonObject.getInt("idsanpham");
                            listSp.add(new SanPham(id, tenSp, giaSp, hinhAnhSp, moTaSp, idLoaiSp));
                        }
                        adapter.setData(listSp);
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
    }

    // có gửi dữ liệu lên php qua hashMap
    public void getSanPham(Context context, String link, HashMap<String, String> hashMap, SanPhamAdapter adapter) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listSp.clear();
                if(response != null && response.length() != 2) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String tenSp = jsonObject.getString("tensanpham");
                            int giaSp = jsonObject.getInt("giasanpham");
                            String hinhAnhSp = jsonObject.getString("hinhanhsanpham");
                            String moTaSp = jsonObject.getString("motasanpham");
                            int idLoaiSp = jsonObject.getInt("idsanpham");
                            listSp.add(new SanPham(id, tenSp, giaSp, hinhAnhSp, moTaSp, idLoaiSp));
                        }
                        adapter.setData(listSp);
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

    public ArrayList<SanPham> getListSp() {
        return listSp;
    }

    public void setListSp(ArrayList<SanPham> listSp) {
        this.listSp = listSp;
    }
}
