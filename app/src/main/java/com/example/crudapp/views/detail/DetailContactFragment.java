package com.example.crudapp.views.detail;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.crudapp.R;
import com.example.crudapp.data.remote.Address;
import com.example.crudapp.entity.Contact;
import com.example.crudapp.views.Dialogs;
import com.example.crudapp.views.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailContactFragment extends Fragment implements View.OnClickListener, DetailContactContract.View, View.OnFocusChangeListener {

    @BindView(R.id.et_detailcontact_fullname)
    EditText etFullName;
    @BindView(R.id.et_detailcontact_age)
    EditText etAge;
    @BindView(R.id.et_infoclient_phone)
    EditText etPhone;
    @BindView(R.id.et_detailcontact_cep)
    EditText etCep;
    @BindView(R.id.et_detailcontact_street)
    EditText etStreet;
    @BindView(R.id.et_detailcontact_number)
    EditText etNumber;
    @BindView(R.id.et_detailcontact_city)
    EditText etCity;
    @BindView(R.id.et_detailcontact_state)
    EditText etState;
    @BindView(R.id.bt_detailcontact_save)
    Button btSave;

    private DetailContactContract.Presenter presenter;
    private Integer idContact;

    public DetailContactFragment() {
        // Required empty public constructor
    }

    public static DetailContactFragment newInstance(Integer idContact) {
        DetailContactFragment detailContactFragment = new DetailContactFragment();
        Bundle args = new Bundle();
        args.putInt(MainActivity.ID_CONTACT, idContact);
        detailContactFragment.setArguments(args);
        return detailContactFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_contact, container, false);

        ButterKnife.bind(this, view);

        presenter = new DetailContactPresenter(getContext(), this);

        initArgs();
        initUI();

        return view;
    }

    private void initArgs(){
        if(getArguments() != null){
            this.idContact = getArguments().getInt(MainActivity.ID_CONTACT);
        }
    }

    private void initUI(){
        this.btSave.setOnClickListener(this);
        this.etCep.setOnFocusChangeListener(this);
    }

    @Override
    public void setPresenter(DetailContactContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.presenter.start();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bt_detailcontact_save){
            Contact contactForm = getClientForm();

            if(contactForm != null){
                presenter.saveContact(contactForm);
                Dialogs.showAlertDialog(getContext(), "Contact saved!", (dialog, which) -> {});
                presenter.openListContacts();
            }
        }
    }


    @Override
    public void loadContact() {
        if(this.idContact > 0){
            this.presenter.loadContact(this.idContact).observe(this, contact -> {
                if(contact != null){
                    fillContactForm(contact);
                } else{
                    Dialogs.showAlertDialog(getContext(), "Contact ID " + idContact + " not founded.", (dialog, which) -> {});
                }
            });
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(v.getId() == R.id.et_detailcontact_cep){
            String CEP = etCep.getText().toString();

            if(CEP != null && !CEP.isEmpty()){
                if(!hasFocus && CEP.length() == 8){
                    presenter.findAddress(CEP).observe(this, address -> {
                        if(address != null){
                            fillAddress(address);
                        } else{
                            Dialogs.showAlertDialog(getContext(), "CEP " + CEP + " not founded.", (dialog, which) -> {});
                        }
                    });
                }
            }
        }
    }

    @Override
    public void openListContacts() {
        ((MainActivity) getActivity()).loadClients();
    }

    private Contact getClientForm(){
        String msg = "Form Invalid. Fields missing: \n";
        String fields = "";
        Contact contact = new Contact();

        if(idContact > 0){
            contact.setId(idContact);
        }
        if(!etFullName.getText().toString().isEmpty() ){
            contact.setFullName(etFullName.getText().toString());
        } else{
            fields += "Full Name\n";
        }

        if(!etAge.getText().toString().isEmpty()){
            contact.setAge(Integer.valueOf(etAge.getText().toString()));
        } else{
            fields += "Age\n";
        }

        if(!etPhone.getText().toString().isEmpty()){
            contact.setPhone(etPhone.getText().toString());
        } else{
            fields += "Phone\n";
        }

        if(!etCep.getText().toString().isEmpty()){
            contact.setCep(etCep.getText().toString());
        } else{
            fields += "CEP\n";
        }

        if(!etStreet.getText().toString().isEmpty()){
            contact.setStreet(etStreet.getText().toString());
        } else{
            fields += "Street\n";
        }
        if(!etNumber.getText().toString().isEmpty()){
            contact.setStreetNumber(Integer.valueOf(etNumber.getText().toString()));
        } else{
            fields += "Street Number\n";
        }

        if(!etCity.getText().toString().isEmpty()){
            contact.setCity(etCity.getText().toString());
        } else{
            fields += "City\n";
        }

        if(!etState.getText().toString().isEmpty()){
            contact.setState(etState.getText().toString());
        } else{
            fields += "State\n";
        }

        if(fields.length() > 0){
            Dialogs.showAlertDialog(getContext(), msg + fields,(dialog, which) -> {});
            return null;
        } else{
            return contact;
        }
    }

    private void fillContactForm(Contact contact){
        etFullName.setText(contact.getFullName());
        etAge.setText(contact.getAge().toString());
        etPhone.setText(contact.getPhone());
        etCep.setText(contact.getCep());
        etStreet.setText(contact.getStreet());
        etNumber.setText(contact.getStreetNumber().toString());
        etCity.setText(contact.getCity());
        etState.setText(contact.getState());
    }

    private void fillAddress(Address address){
        etStreet.setText(address.getLogradouro());
        etNumber.setText("");
        etCity.setText(address.getLocalidade());
        etState.setText(address.getUf());
    }
}
