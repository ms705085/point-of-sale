package mx.iteso.ms.codigobarras;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import mx.iteso.ms.codigobarras.stores.Stores;
import mx.iteso.ms.codigobarras.users.Users;

public class RegisterActivity extends ActivityBase {
    private Spinner spinnerSucursales;
    private EditText txtUsername;
    private EditText txtName;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        spinnerSucursales = (Spinner)findViewById(R.id.reg_sp_sucursal);
        StoreAsyncTask storeAsyncTask =  new StoreAsyncTask();
        storeAsyncTask.execute();
        txtUsername = ((EditText)findViewById(R.id.reg_txt_username));
        txtName = ((EditText)findViewById(R.id.reg_txt_name));
        txtPassword = ((EditText)findViewById(R.id.reg_txt_pass));
    }

    public void request(View view){
        if( !isTextEmpty(txtUsername) ){
            Toast.makeText(this, R.string.err_username_empty, Toast.LENGTH_LONG).show();
            return;
        }
        if( !isTextEmpty(txtName) ){
            Toast.makeText(this, R.string.err_name_empty, Toast.LENGTH_LONG).show();
            return;
        }
        if( !isTextEmpty(txtPassword) ){
            Toast.makeText(this, R.string.err_pass_empty, Toast.LENGTH_LONG).show();
            return;
        }
        RequestAsyncTask requestAsyncTask = new RequestAsyncTask();
        requestAsyncTask.execute(spinnerSucursales.getSelectedItem().toString(), txtUsername.getText().toString(), txtName.getText().toString(), txtPassword.getText().toString());
    }

    public void cancel(View view){
        onBackPressed();
    }

    private class RequestAsyncTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String ... params) {
            Users users = new Users(getApplicationContext());
            return users.request(params[0], params[1], params[2], params[3]);
        }

        @Override
        protected void onPostExecute(Integer messageCode) {
            if(messageCode == Users.USER_IS_ACTIVE)
                Toast.makeText(getApplicationContext(), R.string.message_user_is_active_register, Toast.LENGTH_LONG).show();
            else if(messageCode == Users.USER_IS_INACTIVE)
                Toast.makeText(getApplicationContext(), R.string.message_user_is_inactive_register, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), R.string.message_sent_register, Toast.LENGTH_LONG).show();
            onBackPressed();
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    private class StoreAsyncTask extends AsyncTask<Void, Void, List<String>> {
        @Override
        protected List<String> doInBackground(Void ... params) {
            Stores stores =  new Stores(getApplicationContext());
            return stores.getAll();
        }

        @Override
        protected void onPostExecute(List<String> sucursales) {
            if(sucursales != null && sucursales.size() >0 ){
                ArrayAdapter<CharSequence> adapter = new ArrayAdapter(spinnerSucursales.getContext(),android.R.layout.simple_spinner_item, sucursales.toArray());
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSucursales.setAdapter(adapter);
            }
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}