package capstone.converters;

import capstone.models.ChartModel;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

/**
 * DynamoDBMapper converts lists to {@link java.util.ArrayList}s by default, but for our project.
 * we want to convert to a list of strings
 */
public class SetStringConverter implements DynamoDBTypeConverter<String, Set> {
    private static final Gson GSON = new Gson();
    private final Type listType = new TypeToken<List<String>>() { } .getType();
    private final Logger log = LogManager.getLogger();

    @Override
    public String convert(Set listToBeConverted) {
        log.info("listToBeConverted {}", listToBeConverted);
        if (listToBeConverted == null || listToBeConverted.isEmpty()) {
            return null;
        }
        return GSON.toJson(listToBeConverted);
    }



    @Override
    public Set unconvert(String dynamoDbRepresentation) {
        if (dynamoDbRepresentation == null || dynamoDbRepresentation.isEmpty()) {
            return null;
        }
        return GSON.fromJson(dynamoDbRepresentation, listType);
    }
}
