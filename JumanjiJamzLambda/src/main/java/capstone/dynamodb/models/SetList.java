package capstone.dynamodb.models;

import capstone.converters.IdConverter;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;

import java.util.HashSet;
import java.util.Set;

@DynamoDBTable(tableName = "setlists")
public class SetList {
    private String id;
    private String name;
    private Set<String> charts;
    private Set<String> genres;
    private String madeBy;

    @DynamoDBHashKey(attributeName = "id")
//    @DynamoDBTypeConverted(converter = IdConverter.class)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    @DynamoDBAttribute(attributeName = "name")
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
    @DynamoDBAttribute(attributeName = "charts")
    public Set<String> getCharts() {
        if(charts == null) {
            return null;
        }
        return new HashSet<>(charts);
    }

    public void setCharts(Set<String> charts) { this.charts = charts; }
    @DynamoDBAttribute(attributeName = "genres")
    public Set<String> getGenres() {
        if(genres == null) {
            return null;
        }
        return new HashSet<>(genres);
    }
    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }
    @DynamoDBAttribute(attributeName = "madeBy")
    public String getMadeBy() { return madeBy; }

    public void setMadeBy(String madeBy) { this.madeBy = madeBy; }
}
