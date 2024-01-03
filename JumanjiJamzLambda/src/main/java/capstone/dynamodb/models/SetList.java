package capstone.dynamodb.models;

import capstone.converters.SetChartModelConverter;
import capstone.models.ChartModel;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;

import java.util.Set;

import static capstone.utils.CollectionUtils.copyToSet;


/**
 * SetList representation for the SetLists table.
 */
@DynamoDBTable(tableName = "setlists")
public class SetList {

    private static final String SETLIST_NAME_GSI = "SetListNameAndMadeByIndex";
    private String id;
    private String name;
    private Set<ChartModel> charts;
    private Set<String> genres;
    private String madeBy;

    /**
     * Getter for id.
     * @return id
     */
    @DynamoDBHashKey(attributeName = "id")
    public String getId() {
        return id; }

    /**
     * Setter for id.
     * @param id to be set.
     */
    public void setId(String id) {
        this.id = id; }

    /**
     * Getter for name.
     * @return name
     */
    @DynamoDBIndexHashKey(globalSecondaryIndexName = SETLIST_NAME_GSI, attributeName = "name")
    public String getName() {
        return name; }

    /**
     * Setter for name.
     * @param name to be set
     */
    public void setName(String name) {
        this.name = name; }

    /**
     * Getter for charts.
     * @return charts
     */
    @DynamoDBTypeConverted(converter = SetChartModelConverter.class)
    @DynamoDBAttribute(attributeName = "charts")
    public Set<ChartModel> getCharts() {
        return copyToSet(charts);
    }

    /**
     * Setter for charts.
     * @param charts to be set
     */
    public void setCharts(Set<ChartModel> charts) {
        this.charts = charts; }

    /**
     * Getter for genres.
     * @return genres
     */
    @DynamoDBAttribute(attributeName = "genres")
    public Set<String> getGenres() {
        return copyToSet(genres);
    }

    /**
     * Setter for genres.
     * @param genres to be set
     */
    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }

    /**
     * Getter for madeBy.
     * @return madeBY
     */
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = SETLIST_NAME_GSI, attributeName = "madeBy")
    public String getMadeBy() {
        return madeBy; }

    /**
     * Setter for madeBy.
     * @param madeBy to be set
     */
    public void setMadeBy(String madeBy) {
        this.madeBy = madeBy; }

    @Override
    public String toString() {
        return "SetList{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", charts=" + charts +
                ", genres=" + genres +
                ", madeBy='" + madeBy + '\'' +
                '}';
    }
}
