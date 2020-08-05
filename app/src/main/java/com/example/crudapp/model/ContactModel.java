package com.example.crudapp.model;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.crudapp.data.local.ContactDao;
import com.example.crudapp.data.local.DataBase;
import com.example.crudapp.data.remote.Address;
import com.example.crudapp.entity.Contact;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ContactModel {

    final Executor executor = Executors.newFixedThreadPool(2);
    private ContactDao dao;
    private Context context;

    public ContactModel(Context context){
        this.context = context;
        this.dao = DataBase.getDataBase(context).clientDao();
    }

    public MutableLiveData<List<Contact>> getContacts(){
        MutableLiveData<List<Contact>> listLiveData = new MutableLiveData<>();

        executor.execute(() -> {
            List<Contact> contacts = this.dao.getAll();
            listLiveData.postValue(contacts);
        });

        return listLiveData;
    }

    public MutableLiveData<Contact> getContact(int id){
        MutableLiveData<Contact> contactLiveData = new MutableLiveData<>();

        executor.execute(() -> {
            Contact contact = this.dao.loadById(id);
            contactLiveData.postValue(contact);
        });

        return contactLiveData;
    }

    public void saveContact(Contact contact){
        executor.execute(() -> {
            if(contact.getId() == null){
                this.dao.insert(contact);
            } else{
                this.dao.update(contact);
            }
        });
    }

    public void deleteContact(Contact contact){
        executor.execute(() -> {
            this.dao.delete(contact);
        });
    }

    public MutableLiveData<Address> findAddress(String CEP){
        MutableLiveData<Address> addressLiveData = new MutableLiveData<>();
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url ="https://viacep.com.br/ws/" + CEP + "/json";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Address address = new Gson().fromJson(response, Address.class);
                        if(address != null){
                            addressLiveData.postValue(address);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                addressLiveData.postValue(null);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        return addressLiveData;
    }
}
