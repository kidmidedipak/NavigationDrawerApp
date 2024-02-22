package com.dipak.navigationdrawerapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.dipak.navigationdrawerapp.R;
import com.dipak.navigationdrawerapp.design.ApiListAdapter;
import com.dipak.navigationdrawerapp.model.MyApi;

import java.util.ArrayList;
import java.util.List;

public class APIFragment extends Fragment {

    List<MyApi>  apiList;
    ListView listView;
    ApiListAdapter apiListAdapter;
    //========================================================
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    TextView responseTxt, getApiResTxt;
    RequestQueue queue;

    public APIFragment() {
        // Required empty public constructor
    }


    public static APIFragment newInstance(String param1, String param2) {
        APIFragment fragment = new APIFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_a_p_i, container, false);

        initializeList();
        listView=view.findViewById(R.id.listview);
        apiListAdapter=new ApiListAdapter(requireContext(),apiList);
        listView.setAdapter(apiListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                openClickedApi(position);
            }
        });

        //====================================================================================

//        EditText apiText=view.findViewById(R.id.apiTxt);
//        Button sendGetBtn=view.findViewById(R.id.sendGetBtn );
//        Button sendPostBtn=view.findViewById(R.id.sendPostBtn);
//        Button sendPutBtn=view.findViewById(R.id.sendPutBtn);
//        Button sendDeleteBtn=view.findViewById(R.id.sendDeleteBtn);
//        getApiResTxt=view.findViewById(R.id.getApiRes);
//        responseTxt=view.findViewById(R.id.response);
//        queue = MySingleton.getInstance(this.getContext()).getRequestQueue();   //here get we get api class instant
//
//        sendGetBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getContext(), "GET Request Send", Toast.LENGTH_SHORT).show();
//                String api=apiText.getText().toString();
//                sendGetUrl(api);
//            }
//        });
//
//        sendPostBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getContext(), "POST Request Send", Toast.LENGTH_SHORT).show();
//                String api=apiText.getText().toString();
//                sendPostReq(api);
//            }
//        });
//
//        sendPutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getContext(), "PUT Request Send", Toast.LENGTH_SHORT).show();
//                String api=apiText.getText().toString();
//                sendPutReq(api);
//            }
//        });
//
//        sendDeleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getContext(), "DELETE Request Send", Toast.LENGTH_SHORT).show();
//                String api=apiText.getText().toString();
//                sendDeleteReq(api);
//            }
//        });



        return view;
    }

    private void initializeList() {
        apiList=new ArrayList<>();
        apiList.add(new MyApi("google webview", "https://google.com"));
        apiList.add(new MyApi("Syntech Website Webview","https://www.syntech.co.in/"));
        apiList.add(new MyApi("Open Instagram","https://www.instagram.com/"));

    }


    public void openClickedApi(int position)
    {
        MyApi myApi=apiList.get(position);
        String api=myApi.getApi();

        ApiWebView apiWebView = new ApiWebView();
        apiWebView.setApiData(api);

        // Use FragmentTransaction to replace the current fragment with AddContactFragment
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.container, apiWebView);
        transaction.addToBackStack(null);  // Optional: add to back stack
        transaction.commit();
    }

//    private void sendDeleteReq(String url){
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.DELETE, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        responseTxt.setText("Delete successful");
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Handle errors
//                        responseTxt.setText("Error occurred");
//                        Log.e("Volley Error", error.toString());
//                    }
//                }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                // Add custom headers if needed
//                Map<String, String> headers = new HashMap<>();
//                headers.put("Content-Type", "application/json");
//                return headers;
//            }
//        };
//
//        // Add the request to the RequestQueue
//        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
//
//    }
//
//    private void sendPutReq(String url) {
//
//        JSONObject requestBody = new JSONObject();
//        try {
//            requestBody.put("title", "Updated Title");
//            requestBody.put("body", "Updated Body");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        // Request a JSONObject response from the provided URL
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                Request.Method.PUT, url, requestBody,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // Handle the JSON response
//                        try {
//                            String updatedTitle = response.getString("title");
//                            responseTxt.setText("Updated Title: " + updatedTitle);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Handle errors
//                        responseTxt.setText("Error occurred");
//                        Log.e("Volley Error", error.toString());
//                    }
//                }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                // Add custom headers if needed
//                Map<String, String> headers = new HashMap<>();
//                headers.put("Content-Type", "application/json");
//                return headers;
//            }
//        };
//
//        // Add the request to the RequestQueue
//        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
//    }
//
//    private void sendPostReq(String url) {
//
//        // Request body parameters (if any)
//        JSONObject requestBody = new JSONObject();
//        try {
//            requestBody.put("title", "My Title");
//            requestBody.put("body", "My Body");
//            requestBody.put("userId", 1);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        // Request a JSONObject response from the provided URL
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                Request.Method.POST, url, requestBody,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // Handle the JSON response
//                        try {
//                            String postId = response.getString("id");
//                            responseTxt.setText("Post ID: " + postId);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Handle errors
//                        responseTxt.setText("Error occurred");
//                        Log.e("Volley Error", error.toString());
//                    }
//                }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                // Add custom headers if needed
//                Map<String, String> headers = new HashMap<>();
//                headers.put("Content-Type", "application/json");
//                return headers;
//            }
//        };
//
//        // Add the request to the RequestQueue
//        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
//
//    }
//
//    private void sendGetUrl(String url) {
//
//        // Request a JSONObject response from the provided URL
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            int userId=response.getInt("userId");
//                            int id=response.getInt("id");
//                            String title=response.getString("title");
//                            String body=response.getString("body");
//
//                            String resTxt="userId = "+userId+"\n"
//                                                +"id = "+id+"\n"
//                                                +"title = "+title+"\n"
//                                                +"body = "+body;
//                            getApiResTxt.setText(resTxt+"");
//                            responseTxt.setText("");
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Handle errors
//                            responseTxt.setText("");
//                        getApiResTxt.setText("Error occurred");
//                    }
//                }
//        );
//
//        // Add the request to the RequestQueue
//        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
//
//    }
}