package buttle7.sandbox.model

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import groovy.transform.ToString

/**
 * Created by uokumura on 2015/10/31.
 */
@DynamoDBTable(tableName = "Score")
@ToString
class Score {
    @DynamoDBHashKey(attributeName = "user_id")
    String userId
    @DynamoDBRangeKey
    long timestamp
    @DynamoDBAttribute
    int length
    @DynamoDBAttribute
    int total
}
