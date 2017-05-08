package mx.iteso.ms.codigobarras.users;

import android.content.Context;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedQueryList;

import mx.iteso.ms.codigobarras.CognitoAWSCredentials;

public class Users extends CognitoAWSCredentials {
    public static final int USER_IS_INACTIVE = 0;
    public static final int USER_IS_ACTIVE = 1;
    public static final int USER_NOT_EXISTS = -1;
    private DynamoDBMapper dynamoDBMapper;
    public Users(Context appContext){
        super(appContext);
    }
    public int request(String sucursal, String username, String name, String pass){
        PvUsuario pvUsuario = new PvUsuario();
        pvUsuario.setPass(pass);
        pvUsuario.setActive(false);
        pvUsuario.setUsername(username);
        pvUsuario.setCodigoSucursal(sucursal.split("-")[0].trim());
        pvUsuario.setNombre(name);
        pvUsuario.setTipoUsuario("Vendedor");
        if(dynamoDBMapper==null)
            dynamoDBMapper = new DynamoDBMapper(getAmazonDynamoDBClient());
        int validateUser = userExists(pvUsuario);
        if(USER_NOT_EXISTS == validateUser)
            dynamoDBMapper.save(pvUsuario);
        return validateUser;
    }

    public int userExists(PvUsuario pvUsuario){
        int exists = USER_NOT_EXISTS;
        if(dynamoDBMapper==null)
            dynamoDBMapper = new DynamoDBMapper(getAmazonDynamoDBClient());
        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression().withHashKeyValues(pvUsuario).withConsistentRead(false);
        PaginatedQueryList<PvUsuario> paginatedQueryList = dynamoDBMapper.query(PvUsuario.class, queryExpression);
        if( paginatedQueryList != null && !paginatedQueryList.isEmpty()  ){
            if(paginatedQueryList.get(0).getActive()){
                exists = USER_IS_ACTIVE;
            }else{
                exists = USER_IS_INACTIVE;
            }
        }
        return exists;
    }
}