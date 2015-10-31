package buttle7.sandbox

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    @Bean
    public AmazonDynamoDBClient getAmazonDynamoDBClient() {
        def client = new AmazonDynamoDBClient(new ProfileCredentialsProvider())
        client.setEndpoint("http://localhost:8000")

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
