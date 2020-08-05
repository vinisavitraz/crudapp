package com.example.crudapp.views.list;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.crudapp.entity.Contact;
import com.example.crudapp.model.ContactModel;

import java.util.ArrayList;
import java.util.List;

public class ListPresenter implements ListContract.Presenter {

    private final ContactModel model;
    private final ListContract.View view;
    private List<Contact> contacts;

    public ListPresenter(Context context, ListContract.View view) {
        this.model = new ContactModel(context);
        this.view = view;
        this.contacts = new ArrayList<>();
    }

    @Override
    public void start() {
        view.loadContacts();
    }

    public LiveData<List<Contact>> loadContacts(){
        return model.getContacts();
    }

    public void showContacts(List<Contact> contacts){
        this.contacts = contacts;
        view.showContacts(contacts);
    }

    public void openDetailContact(int position){
        if(position < contacts.size()){
            Contact contact = contacts.get(position);
            if(contact != null){
                view.openDetailContact(contact);
            }
        }
    }

    public void deleteContact(int position){
        if(position < contacts.size()){
            Contact contact = contacts.get(position);
            if(contact != null){
                contacts.remove(contact);
                model.deleteContact(contact);
                view.showContacts(contacts);
            }
        }
    }
}
