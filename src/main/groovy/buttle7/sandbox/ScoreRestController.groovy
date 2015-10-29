package buttle7.sandbox

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.dynamodbv2.*
import com.amazonaws.services.dynamodbv2.datamodeling.*

import org.springframework.web.bind.annotation.*

/**
 * スコアを扱うRestController
 */
@RestController
@RequestMapping("/score")
class ScoreRestController {
    private static AmazonDynamoDBClient client = new AmazonDynamoDBClient(
            new ProfileCredentialsProvider()
    )
    private static DynamoDBMapper mapper = new DynamoDBMapper(client)

    static {
        client.setEndpoint("http://localhost:8000");
    }

    @RequestMapping(method = RequestMethod.POST)
    public int save(@RequestBody Score score) {
println score
        mapper.save(score);
        return list(score.userId).sum{it.total}
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Score> list(@RequestParam(value="user") String userId) {
        return mapper.query(Score.class, new DynamoDBQueryExpression<Score>()
            .withHashKeyValues(new Score(userId: userId))
        )
    }


}

@DynamoDBTable(tableName = "Score")
class Score {
    @DynamoDBHashKey(attributeName ="user_id")
    String userId
    @DynamoDBRangeKey
    Date timestamp
    @DynamoDBAttribute
    int length
    @DynamoDBAttribute
    int total
}