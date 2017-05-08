package mx.iteso.ms.codigobarras.users;


import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "pvUsuarios")
public class PvUsuario {
    private String username;
    private String codigoSucursal;
    private String nombre;
    private String pass;
    private String tipoUsuario;
    private Boolean active;

    @DynamoDBHashKey(attributeName = "Username")
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @DynamoDBAttribute(attributeName = "CodigoSucursal")
    public String getCodigoSucursal() {
        return codigoSucursal;
    }
    public void setCodigoSucursal(String codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    @DynamoDBAttribute(attributeName = "Nombre")
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @DynamoDBAttribute(attributeName = "Pass")
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }

    @DynamoDBAttribute(attributeName = "TipoUsuario")
    public String getTipoUsuario() {
        return tipoUsuario;
    }
    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @DynamoDBAttribute(attributeName = "Active")
    public Boolean getActive(){ return active; }
    public void setActive(Boolean active){ this.active = active; }
}