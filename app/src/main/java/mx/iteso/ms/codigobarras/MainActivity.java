package mx.iteso.ms.codigobarras;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import mx.iteso.ms.codigobarras.auth.Authentication;

public class MainActivity extends ActivityBase {
    private EditText txtUsername;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_title);
        txtUsername = ((EditText)findViewById(R.id.txt_login_username));
        txtPassword = ((EditText)findViewById(R.id.txt_login_pass));
    }

    public void auth(View view){
        if( !isTextEmpty(txtUsername) ){
            Toast.makeText(this, R.string.err_username_empty, Toast.LENGTH_LONG).show();
            return;
        }
        if( !isTextEmpty(txtPassword) ){
            Toast.makeText(this, R.string.err_pass_empty, Toast.LENGTH_LONG).show();
            return;
        }
        new AuthenticationAsyncTask(this).execute(txtUsername.getText().toString(), txtPassword.getText().toString());
    }

    public void register(View view){
        Intent intentToRegister = new Intent(this, RegisterActivity.class);
        intentToRegister.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intentToRegister);
    }

    private class AuthenticationAsyncTask extends AsyncTask<String, Void, Integer> {
        Activity activity;
        public AuthenticationAsyncTask(Activity activity){
            this.activity = activity;
        }
        @Override
        protected Integer doInBackground(String... params) {
            Integer auth = Authentication.AUTH_INVALID;
            Authentication authentication = new Authentication(getApplicationContext());
            auth = authentication.auth(params[0], params[1]);
            return auth;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if(Authentication.AUTH_SUCCESSFUL==result){
                //Toast.makeText(activity, "USER VALID!!", Toast.LENGTH_LONG).show();
                //Todo Enviar el intent para la pantalla principal
            }else if(Authentication.AUTH_USER_INACTIVE==result){
                Toast.makeText(activity, R.string.err_auth_user_inactive, Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(activity, R.string.err_auth_fail, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}