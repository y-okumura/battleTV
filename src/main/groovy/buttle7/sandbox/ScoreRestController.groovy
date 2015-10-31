package buttle7.sandbox

import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
import groovy.transform.*

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.dynamodbv2.*
import com.amazonaws.services.dynamodbv2.datamodeling.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * スコアを扱うRestController
 */
@RestController
@RequestMapping("/score")
class ScoreRestController {

    private final DynamoDBMapper mapper
    private final DynamoDB dynamoDB

    @RequestMapping("/cleanup")
    public String cleanup() {
        dynamoDB.getTable("Score").delete()
        return "success"
    }


    @RequestMapping("/init")
    public String init() {
        CreateTableRequest request = mapper.generateCreateTableRequest(Score)
            .withProvisionedThroughput(
                new ProvisionedThroughput()
                    .withReadCapacityUnits(5L)
                    .withWriteCapacityUnits(6L)
            )

        dynamoDB.createTable(request).waitForActive()
        return "success"
    }

    @Autowired
    public ScoreRestController(DynamoDBMapper mapper, DynamoDB dynamoDB) {
        this.mapper = mapper
        this.dynamoDB = dynamoDB
    }

    @RequestMapping(method = RequestMethod.POST)
    public int save(@ModelAttribute Score score) {
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
@ToString
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