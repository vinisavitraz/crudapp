package com.example.crudapp.views.detail;

import androidx.lifecycle.LiveData;

import com.example.crudapp.BasePresenter;
import com.example.crudapp.BaseView;
import com.example.crudapp.data.remote.Address;
import com.example.crudapp.entity.Contact;

public interface DetailContactContract {

    interface View extends BaseView<Presenter> {
        void loadContact();
        void openListContacts();
    }

    interface Presenter extends BasePresenter {
        LiveData<Contact> loadContact(int id);
        LiveData<Address> findAddress(String CEP);
        void saveContact(Contact contact);
        void openListContacts();
    }
}
