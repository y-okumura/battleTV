package buttle7.sandbox

import buttle7.sandbox.model.Score
import buttle7.sandbox.model.User
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
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

        def user = getUser(score.userId).addScore(score)
        mapper.save(user)
        return user.score
    }

    private User getUser(String userId) {
        User user = new User(userId:userId)
        if (mapper.count(User, new DynamoDBQueryExpression<User>().withHashKeyValues(user)) > 0) {
            return mapper.load(User, userId)
        } else {
            return user
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Score> list(@RequestParam(value="user") String userId) {
        return mapper.query(Score.class, new DynamoDBQueryExpression<Score>()
            .withHashKeyValues(new Score(userId: userId))
        )
    }

    @RequestMapping("/ranking")
    public List<User>ranking() {
        new ArrayList<>(mapper.scan(User, new DynamoDBScanExpression().withLimit(10))).sort{-it.score}
    }

}
