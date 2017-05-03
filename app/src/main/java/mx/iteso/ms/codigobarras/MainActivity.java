package mx.iteso.ms.codigobarras;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final String EMPTY_STRING = "";
    private EditText txtUsername;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUsername = ((EditText)findViewById(R.id.txt_login_username));
        txtPassword = ((EditText)findViewById(R.id.txt_login_pass));
    }

    private boolean isTextEmpty(EditText editText){
        if( editText == null ||
                editText.getText() == null ||
                editText.getText().toString() == null ||
                editText.getText().toString().equals(EMPTY_STRING) ){
            return false;
        }
        if( editText.getHint() != null ){
            if(editText.getText().toString().equals(editText.getHint().toString())){
                return false;
            }
        }
        return true;
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
        // TODO: 5/3/17 Replace with function to check user and pass in database
        Toast.makeText(this, R.string.err_auth_fail, Toast.LENGTH_LONG).show();
    }

    public void register(View view){
        //Todo send to the other screen to register username
    }
}