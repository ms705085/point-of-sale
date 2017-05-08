package mx.iteso.ms.codigobarras.stores;

import android.content.Context;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;

import java.util.ArrayList;
import java.util.List;

import mx.iteso.ms.codigobarras.CognitoAWSCredentials;

public class Stores extends CognitoAWSCredentials {
    public Stores(Context appContext){
        super(appContext);
    }
    public List<String> getAll(){
        List<String> sucursales = null;
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(getAmazonDynamoDBClient());
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        PaginatedScanList<PvSucursal> sucursalesResult = dynamoDBMapper.scan(PvSucursal.class, scanExpression);
        if( sucursalesResult != null && !sucursalesResult.isEmpty()  ){
            sucursales = new ArrayList<>(sucursalesResult.size());
            for (PvSucursal pvSucursal:sucursalesResult) {
                sucursales.add(pvSucursal.getCodigo() + " - " + pvSucursal.getNombre());
            }
        }
        return sucursales;
    }
}