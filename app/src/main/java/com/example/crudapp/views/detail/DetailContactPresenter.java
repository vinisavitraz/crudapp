package com.example.crudapp.views.detail;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.crudapp.data.remote.Address;
import com.example.crudapp.entity.Contact;
import com.example.crudapp.model.ContactModel;



public class DetailContactPresenter implements DetailContactContract.Presenter {
    private final ContactModel model;
    private final DetailContactContract.View view;

    public DetailContactPresenter(Context context, DetailContactContract.View view){
        this.model = new ContactModel(context);
        this.view = view;
    }

    @Override
    public void start() {
        view.loadContact();
    }

    public LiveData<Contact> loadContact(int id){
        return this.model.getContact(id);
    }

    public LiveData<Address> findAddress(String CEP){
        return this.model.findAddress(CEP);
    }

    public void saveContact(Contact contact){
        this.model.saveContact(contact);
    }

    public void openListContacts(){
        this.view.openListContacts();
    }
}
