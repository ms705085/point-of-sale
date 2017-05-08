package mx.iteso.ms.codigobarras.auth;

import android.content.Context;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;

import java.util.HashMap;
import java.util.Map;

import mx.iteso.ms.codigobarras.CognitoAWSCredentials;
import mx.iteso.ms.codigobarras.users.PvUsuario;


public class Authentication extends CognitoAWSCredentials{
    public static int AUTH_INVALID = -1;
    public static int AUTH_SUCCESSFUL = 1;
    public static int AUTH_USER_INACTIVE = 0;
    public Authentication(Context appContext){
        super(appContext);
    }
    public int auth(String username, String pass){
        int authValid = AUTH_INVALID;
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(getAmazonDynamoDBClient());
        PvUsuario pvUsuarioToFind = new PvUsuario();
        pvUsuarioToFind.setUsername(username);
        Condition filterCondition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString()).withAttributeValueList(new AttributeValue().withS(pass));
        Map<String, Condition> filters = new HashMap<>(1);
        filters.put("Pass", filterCondition);
        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression().withHashKeyValues(pvUsuarioToFind).withQueryFilter(filters).withConsistentRead(false);
        PaginatedQueryList<PvUsuario> paginatedQueryList = dynamoDBMapper.query(PvUsuario.class, queryExpression);
        if( paginatedQueryList != null && !paginatedQueryList.isEmpty()  ){
            if(paginatedQueryList.get(0).getActive()){
                authValid = AUTH_SUCCESSFUL;
            }else{
                authValid = AUTH_USER_INACTIVE;
            }
        }
        return authValid;
    }
}