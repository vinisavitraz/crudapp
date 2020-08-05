package com.example.crudapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.crudapp.R;

import com.example.crudapp.views.detail.DetailContactFragment;
import com.example.crudapp.views.list.ListFragment;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 1;
    public static final String ID_CONTACT = "id_contact";
    private Menu optionsMenu;
    private Boolean detailClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.detailClient = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        this.optionsMenu = menu;

        MenuItem addClient = menu.findItem(R.id.menu_add_client);
        addClient.setIcon(new IconicsDrawable(this).icon(GoogleMaterial.Icon.gmd_add).color(Color.WHITE).sizeDp(24));

        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED){
            requestPermission();
        } else{
            loadClients();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_add_client:
                if(!detailClient){
                    loadDetailContact(0);
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(detailClient){
            detailClient = false;
        }
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.INTERNET}, REQUEST_PERMISSION);
    }

    public void loadClients(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fraglayout_main_content, new ListFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        this.detailClient = false;
    }

    public void loadDetailContact(int idContact){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fraglayout_main_content, DetailContactFragment.newInstance(idContact));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        this.detailClient = true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_PERMISSION && grantResults.length > 0 ){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadClients();
            } else {
                Dialogs.showAlertDialog(MainActivity.this, "Missing permissions...", (dialogInterface, i) -> {
                    requestPermission();
                });
            }
        }
    }
}
