package mx.iteso.ms.codigobarras;

import android.content.Context;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

public class CognitoAWSCredentials {
    private AmazonDynamoDBClient amazonDynamoDBClient;
    public CognitoAWSCredentials(Context appContext){
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(appContext, "us-west-2:babf590a-612c-4881-8d3d-99b4fcaf5377",  Regions.US_WEST_2);
        amazonDynamoDBClient = Region.getRegion(Regions.US_WEST_2).createClient(AmazonDynamoDBClient.class, credentialsProvider, new ClientConfiguration());
    }
    public AmazonDynamoDBClient getAmazonDynamoDBClient(){
        return amazonDynamoDBClient;
    }
}