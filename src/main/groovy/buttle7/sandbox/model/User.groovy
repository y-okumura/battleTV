package buttle7.sandbox.model

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import groovy.transform.ToString

/**
 * Created by uokumura on 2015/10/31.
 */
@DynamoDBTable(tableName = "User")
@ToString
class User {
    @DynamoDBHashKey
    String userId
    @DynamoDBAttribute
    long score

    User addScore(Score add) {
        score += add.total
        return this
    }
}
