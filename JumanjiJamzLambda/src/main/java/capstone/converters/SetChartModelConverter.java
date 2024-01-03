package capstone.converters;

import capstone.models.ChartModel;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;

import java.util.Set;

@Singleton
public class SetChartModelConverter implements DynamoDBTypeConverter<String, Set<ChartModel>> {

    private ObjectMapper mapper = new ObjectMapper();
    private Logger log = LogManager.getLogger();

    @Override
    public String convert(Set<ChartModel> object) {
        log.error("object {}", object);
        try {
            return mapper.writeValueAsString(object);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error attempting to serialize the Set<ChartModel>", e);
        }
    }

    @Override
    public Set<ChartModel> unconvert(String object) {
        log.error("object {}", object);
        try {
            // Use readValue method to deserialize the JSON string into a Set<ChartModel>
            return mapper.readValue(object, mapper.getTypeFactory().constructCollectionType(Set.class, ChartModel.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error attempting to deserialize the JSON string", e);
        }
    }
}
