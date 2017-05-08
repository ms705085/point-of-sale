package mx.iteso.ms.codigobarras;

import android.app.Activity;
import android.widget.EditText;

/**
 * Created by josedelg on 5/6/17.
 */

public class ActivityBase extends Activity {
    private static final String EMPTY_STRING = "";
    protected boolean isTextEmpty(EditText editText){
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
}