package buttle7.sandbox.model

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import groovy.transform.ToString

/**
 * Created by uokumura on 2015/10/31.
 */
@DynamoDBTable(tableName = "Score")
@ToString
class User {
    @DynamoDBHashKey(attributeName = "user_id")
    String userId
}
