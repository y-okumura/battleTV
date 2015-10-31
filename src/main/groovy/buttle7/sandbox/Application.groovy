package buttle7.sandbox

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @Value('${endpoint}')
    String endpoint

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    @Bean
    public AmazonDynamoDBClient getAmazonDynamoDBClient() {
        def client = new AmazonDynamoDBClient(new ProfileCredentialsProvider())
        client.setEndpoint(endpoint)

        return client
    }

    @Bean
    public DynamoDBMapper getDynamoDBMapper(AmazonDynamoDBClient client) {
        new DynamoDBMapper(amazonDynamoDBClient)
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
