package com.example.crudapp.views.list;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.crudapp.R;
import com.example.crudapp.entity.Contact;
import com.example.crudapp.views.Dialogs;
import com.example.crudapp.views.MainActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements ListContract.View, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {


    @BindView(R.id.list_contacts)
    ListView listContacts;
    ArrayAdapter<Contact> adapter;

    private ListContract.Presenter presenter;

    public ListFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        ButterKnife.bind(this, view);

        presenter = new ListPresenter(getContext(), this);

        initUI();

        return view;
    }

    private void initUI(){
        this.listContacts.setOnItemClickListener(this);
        this.listContacts.setOnItemLongClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.presenter.start();
    }

    public void loadContacts(){
        presenter.loadContacts().observe(this, contacts -> {
            presenter.showContacts(contacts);
        });
    }

    @Override
    public void showContacts(List<Contact> contacts) {
        this.adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, contacts);
        listContacts.setAdapter(adapter);

    }

    @Override
    public void openDetailContact(Contact contact) {
        ((MainActivity) getActivity()).loadDetailContact(contact.getId());
    }

    @Override
    public void setPresenter(ListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        presenter.openDetailContact(position);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Dialogs.showConfirmDialog(getContext(), "Do you want to delete this contact?", (dialog, which) -> {
            presenter.deleteContact(position);
        });


        return true;
    }
}
