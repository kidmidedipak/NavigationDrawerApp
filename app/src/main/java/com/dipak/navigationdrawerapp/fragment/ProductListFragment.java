package com.dipak.navigationdrawerapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dipak.navigationdrawerapp.R;
import com.dipak.navigationdrawerapp.design.ProductAdapter;
import com.dipak.navigationdrawerapp.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductListFragment extends Fragment {


    ListView listView;
    public ProductListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_product_list, container, false);
          listView=view.findViewById(R.id.cutom_list_view);

        getAllProductFromUrl();

        return  view;
    }

    private void getAllProductFromUrl(){

            // Continue with your connection logic
            // ...
        String apiUrl = "https://192.168.0.137/hospital_api/demo_gallery_data";
//        String apiUrl = "https://api.chucknorris.io/jokes/random?category=music";


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray=response.getJSONArray("data");
                            parseApiResponse(jsonArray);

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "exception", Toast.LENGTH_SHORT).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "exception", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
        );

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);

    }

    private void parseApiResponse(JSONArray response) {
        ArrayList<Product> productArrayList1=new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject jsonObject = response.getJSONObject(i);

                int img_id = jsonObject.getInt("img_id");
                String img_url = jsonObject.getString("img_url");
                String img_title = jsonObject.getString("img_title");
                String img_sub_title = jsonObject.getString("img_sub_title");
                String img_info = jsonObject.getString("img_info");

                // Create an instance of your data model and add it to the ArrayList

                productArrayList1.add( new Product(img_id,img_url,img_info,img_title,img_sub_title));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        productArrayList1.addAll(productArrayList1);
        ProductAdapter productAdapter=new ProductAdapter(requireContext(),productArrayList1);
        listView.setAdapter(productAdapter);

        listView.setDivider(null);  // or setDivider(new ColorDrawable(Color.TRANSPARENT));
        listView.setDividerHeight(0);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                ProductInfoFragment infoFragment=new ProductInfoFragment();
                infoFragment.setProductData(position,productArrayList1);

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.container, infoFragment);
                transaction.addToBackStack(null);  // Optional: add to back stack
                transaction.commit();
            }
        });

    }
}