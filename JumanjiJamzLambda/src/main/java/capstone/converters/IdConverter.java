package capstone.converters;



import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.util.Objects;
import java.util.UUID;

public class IdConverter implements DynamoDBTypeConverter<String, UUID> {


    @Override
    public String convert(UUID id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return String.valueOf(id);
    }

    @Override
    public UUID unconvert(String id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return UUID.fromString(id);
    }
}
