package com.dipak.navigationdrawerapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dipak.navigationdrawerapp.R;
import com.dipak.navigationdrawerapp.database.MyDB;
import com.dipak.navigationdrawerapp.model.Contact;

public class AddContactFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    MyDB myDB;
    private String mParam1;
    private String mParam2;

    EditText editName,editNumber;
    Button addcontact;
    TextView titleTXT;
    int id;

    public AddContactFragment() {
        // Required empty public constructor
    }

    public void setContactData(String strid) {
        Bundle args = new Bundle();
        args.putString("key", strid);
        setArguments(args);
    }


    public static AddContactFragment newInstance(String param1, String param2) {
        AddContactFragment fragment = new AddContactFragment();
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
        View view=inflater.inflate(R.layout.fragment_add_contact, container, false);


          editName= view.findViewById(R.id.nameEditText);
          editNumber=view.findViewById(R.id.phoneEditText);
          titleTXT=view.findViewById(R.id.title);
          addcontact=view.findViewById(R.id.addButton);

        myDB=new MyDB(getContext().getApplicationContext());

        addcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=editName.getText().toString();
                String number=editNumber.getText().toString();
                if(!(name.trim().isEmpty()) && !(number.trim().isEmpty()))
                {
                    if(number.length()>=10) {
                        Contact contact=new Contact(id,name, number);
                       boolean istrue= myDB.addContact(contact);

                       navigateBackToContactList( );

                    }else{
                        Toast.makeText(getContext(), "Phone number must be at least 10 numbers", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast .makeText(getContext(), "All Fields are required", Toast.LENGTH_SHORT).show();
                }
                id=0;
            }
        });

        getBundale();

        return view;
    }

    private void getBundale() {

        Bundle args = getArguments();
        if (args != null) {
            String strId = args.getString("key", "");

            int localid=Integer.parseInt(strId);
            id=localid;
        Contact contact=myDB.getContact(localid);
        titleTXT.setText("Update Contact");
            addcontact.setText("Update");
            editName.setText(contact.getName());
            editNumber.setText(contact.getPhone());
            // Use the data as needed
        }
    }

    private void navigateBackToContactList( ) {

        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.popBackStack();
    }
}