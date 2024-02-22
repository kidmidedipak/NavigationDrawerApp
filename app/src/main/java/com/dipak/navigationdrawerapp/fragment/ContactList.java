package com.dipak.navigationdrawerapp.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.dipak.navigationdrawerapp.R;
import com.dipak.navigationdrawerapp.database.MyDB;
import com.dipak.navigationdrawerapp.design.ContactAdapter;
import com.dipak.navigationdrawerapp.fragment.AddContactFragment;
import com.dipak.navigationdrawerapp.model.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ContactList extends Fragment {

      List<Contact> contactList ;
     ContactAdapter contactAdapter;
    ListView contactListView;
    SearchView searchView;
    private FloatingActionButton fabAddContact;
    ImageView profileImageView;
    MyDB myDB;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        fabAddContact = view.findViewById(R.id.btnOpenDialog);
        fabAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle FAB click (e.g., open add contact screen)
                openAddContactScreen();
            }
        });


        myDB=new MyDB(getContext().getApplicationContext());
        // Initialize the contact list
        contactList = new ArrayList<>();
         contactList.addAll(myDB.getAllCustomers());
        // Initialize the ListView
         contactListView = view.findViewById(R.id.contactListView);

        // Create the custom adapter
        contactAdapter = new ContactAdapter(requireContext(), contactList);

        // Set the adapter to the ListView
        contactListView.setAdapter(contactAdapter);

        // Set item click listener for editing contacts
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle click (e.g., open edit contact screen)
                // You can add your logic here
                editContactLogiic(position);
            }
        });

        // Set item long click listener for deleting contacts
        contactListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                // Handle long click (e.g., show delete confirmation)
                // You can add your logic here
                showDeleteConfirmationDialog(position);
                return true;
            }
        });


        searchView=view.findViewById(R.id.srcView);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
//                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                return true;
            }
        });


        return view;
    }



    private void editContactLogiic(int position) {

        Contact contact=contactList.get(position);
        int id=contact.getId();

        AddContactFragment addContactFragment = new AddContactFragment();
        addContactFragment.setContactData(String.valueOf(id) );

        // Use FragmentTransaction to replace the current fragment with AddContactFragment
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.container, addContactFragment);
        transaction.addToBackStack(null);  // Optional: add to back stack
        transaction.commit();
 

    }


    private void openAddContactScreen() {

        AddContactFragment addContactFragment = new AddContactFragment();

        // Replace the current fragment with AddContactFragment
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, addContactFragment)
                .addToBackStack(null)  // Allow back navigation to the previous fragment
                .commit();


        // Create and show the AddContactFragment
//        Dialog dialog=new Dialog(getContext());
//        dialog.setContentView(R.layout.add_update_contact);
//        EditText editName=dialog.findViewById(R.id.nameEditText);
//        EditText editNumber=dialog.findViewById(R.id.phoneEditText);
//        Button addcontact=dialog.findViewById(R.id.addButton);
//
//        //extra code         ===================================================================
//        Button selectImageBtn=dialog.findViewById(R.id.selectImageButton);
//          profileImageView=dialog.findViewById(R.id.imagePreview);
//
//        selectImageBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                launchImagePicker();
//            }
//        });

//        ==============================================================================


//        addcontact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String name=editName.getText().toString();
//                String number=editNumber.getText().toString();
//                if(!(name.trim().isEmpty()) && !(number.trim().isEmpty()))
//                {
//                    if(number.length()>=10) {
//
//                        contactList.add(new Contact(name,number,R.drawable.bg_side_icon));
//                        contactAdapter.notifyDataSetChanged();
//                        dialog.dismiss();
//                    }else{
//                        Toast.makeText(getContext(), "Phone number must be at least 10 numbers", Toast.LENGTH_SHORT).show();
//
//                    }
//                }else{
//                    Toast.makeText(getContext(), "All Fields are required", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        dialog.show();
    }

    private void launchImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Image"),1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            // Handle the selected image URI
            Uri selectedImageUri = data.getData();
            // Now you can use this URI to display the selected image or perform other actions
            // For simplicity, this example sets the image in the ImageView
            profileImageView.setImageURI(selectedImageUri);
        }
    }

    // Method to add a new contact
    public void addContact(Contact contact) {
        contactList.add(contact);
        Toast.makeText(getContext(), "hii.... "+contact.toString(), Toast.LENGTH_SHORT).show();
        contactAdapter.notifyDataSetChanged();
    }

    // Method to edit an existing contact
    public void editContact(int position, Contact newContact) {
        contactList.set(position, newContact);
        contactAdapter.notifyDataSetChanged();
    }

    // Method to delete an existing contact
    private void deleteContact(int position) {

        Contact contact=contactList.get(position);
        boolean isdelete=myDB.deleteContact(contact);
        if(isdelete)
            Toast.makeText(getContext(), "Contact successfully delete", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getContext(), "Something wrong", Toast.LENGTH_SHORT).show();

        contactList.remove(position);
        contactAdapter.notifyDataSetChanged();
    }

    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Delete Contact")
                .setMessage("Are you sure you want to delete this contact?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteContact(position);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }



    private void filterList(String s) {
        List<Contact> al=new ArrayList<>();
        List<Contact> remList=new ArrayList<>();
        for (Contact obj:contactList)
        {
            if(obj.getName().toLowerCase().contains(s.toLowerCase()))
            {
                al.add(obj);
            }else{
                remList.add(obj);
            }
        }

        if(al.isEmpty())
        {
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
        }else{
            al.addAll(remList);
            contactList=new ArrayList<>();
            contactList.addAll(al);
            contactAdapter = new ContactAdapter(requireContext(), contactList);
            // Set the adapter to the ListView
            contactListView.setAdapter(contactAdapter);

        }
    }
}