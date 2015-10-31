package buttle7.sandbox

import buttle7.sandbox.model.Score
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Created by uokumura on 2015/10/31.
 */
@RestController
@RequestMapping("/setup")
class SetupController {

    @Value('${credentials.dir}')
    String credentialDir

    @RequestMapping("/credentials")
    public String credentials(@RequestParam(value="content") String content) {
        def dir = new File(credentialDir)
        dir.mkdirs()
        new File(dir, "credentials").text = content
        return "success"
    }
}

@RestController
@RequestMapping("/dbsetup")
class DBSetupController {

    private final DynamoDBMapper mapper
    private final DynamoDB dynamoDB

    @Autowired
    public DBSetupController(DynamoDBMapper mapper, DynamoDB dynamoDB) {
        this.mapper = mapper
        this.dynamoDB = dynamoDB
    }

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
}