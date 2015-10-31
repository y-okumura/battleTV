package buttle7.sandbox

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

    @Autowired
    public ScoreRestController(DynamoDBMapper mapper) {
        this.mapper = mapper
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