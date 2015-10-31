import com.amazonaws.auth.profile.*
import com.amazonaws.services.dynamodbv2.*
import com.amazonaws.services.dynamodbv2.document.*
import com.amazonaws.services.dynamodbv2.model.*
import com.amazonaws.services.dynamodbv2.datamodeling.*

import buttle7.sandbox.*

def client = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
client.setEndpoint("http://localhost:8000");
DynamoDB dynamoDB = new DynamoDB(client);

dynamoDB.getTable("Score").delete()

DynamoDBMapper mapper = new DynamoDBMapper(client)
CreateTableRequest request = mapper.generateCreateTableRequest(Score)
    .withProvisionedThroughput(new ProvisionedThroughput()
        .withReadCapacityUnits(5L)
        .withWriteCapacityUnits(6L)
    )
            
dynamoDB.createTable(request).waitForActive()