package com.dipak.navigationdrawerapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.dipak.navigationdrawerapp.R;
import com.dipak.navigationdrawerapp.design.GridAdapter;

import java.util.ArrayList;

public class spinnerAutocompleteView extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Spinner spinner;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<String > arrIds=new ArrayList<>();
    ArrayList<String > arrCity=new ArrayList<>();
    AutoCompleteTextView actxtView;
    boolean isfirsttime=true;

    public spinnerAutocompleteView() {
        // Required empty public constructor
    }


    public static spinnerAutocompleteView newInstance(String param1, String param2) {
        spinnerAutocompleteView fragment = new spinnerAutocompleteView();
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
        View view= inflater.inflate(R.layout.fragment_spinner_autocomplete_view, container, false);

        spinner=view.findViewById(R.id.spinner);
        actxtView=view.findViewById(R.id.actxtView);

        //spinner
        defineSpinner();

        //autocompleteview
        defineAutocomplete();

        GridView gridView = view.findViewById(R.id.gridView);
        GridAdapter gridAdapter = new GridAdapter(getContext());
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Show a Toast with the selected item's position + 1
                Toast.makeText(getContext(), "Selected item: " + (position + 1), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void defineAutocomplete() {
        arrCity.add("Pune");
        arrCity.add("Mumbai");
        arrCity.add("Delhi");
        arrCity.add("Latur");
        arrCity.add("Nanded");
        arrCity.add("Beed");
        arrCity.add("Nashik");
//
        ArrayAdapter<String> actvAdapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,arrCity);
        actxtView.setAdapter(actvAdapter);
        actxtView.setThreshold(1);

        actxtView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item from the adapter
                String selectedItem = (String) parent.getItemAtPosition(position);

                // Update the TextView to display the selected item
                Toast.makeText(getContext(), selectedItem, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void defineSpinner() {
        arrIds.add("Aadhar card");
        arrIds.add("Pan card");
        arrIds.add("Voter ID card");
        arrIds.add("Driving License card");
        arrIds.add("Ration card");
        arrIds.add("10th Score card");
        arrIds.add("12th Score card");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                arrIds
        );

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Display the selected item in the TextView
                String selectedItem = (String) parentView.getItemAtPosition(position);
                if(!isfirsttime) {
                    Toast.makeText(getContext(), selectedItem, Toast.LENGTH_SHORT).show();
                }
                    isfirsttime = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
    }
}