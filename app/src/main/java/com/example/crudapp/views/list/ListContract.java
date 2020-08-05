package com.example.crudapp.views.list;

import androidx.lifecycle.LiveData;

import com.example.crudapp.BasePresenter;
import com.example.crudapp.BaseView;
import com.example.crudapp.entity.Contact;

import java.util.List;

public interface ListContract {


    interface View extends BaseView<ListContract.Presenter> {
        void loadContacts();
        void showContacts(List<Contact> contacts);
        void openDetailContact(Contact contact);
    }

    interface Presenter extends BasePresenter {
        LiveData<List<Contact>> loadContacts();
        void showContacts(List<Contact> contacts);
        void openDetailContact(int position);
        void deleteContact(int position);
    }
}
