package capstone.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.HashSet;
import java.util.Set;

/**
 * Chart entry representation for Charts table.
 */
@DynamoDBTable(tableName = "charts")
public class Chart {
    private String id;
    private String name;
    private String artist;
    private Integer bpm;
    private String content;
    private Set<String> genres;
    private String madeBy;

    /**
     * Getter for id.
     * @return id
     */
    @DynamoDBHashKey(attributeName = "id")
    public String getId() {
        return id;
    }

    /**
     * Setter for id.
     * @param id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for name.
     * @return name
     */
    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    /**
     * Setter for name.
     * @param name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for artist.
     * @return artist
     */
    @DynamoDBAttribute(attributeName = "artist")
    public String getArtist() {
        return artist;
    }

    /**
     * Setter for artist.
     * @param artist to be set.
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Getter for bpm.
     * @return bpm
     */
    @DynamoDBAttribute(attributeName = "bpm")
    public Integer getBpm() {
        return bpm;
    }

    /**
     * Setter for bpm.
     * @param bpm to be set
     */
    public void setBpm(Integer bpm) {
        this.bpm = bpm;
    }

    /**
     * Getter for content.
     * @return content
     */
    @DynamoDBAttribute(attributeName = "content")
    public String getContent() {
        return content;
    }

    /**
     * Setter for content.
     * @param content to be set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Getter for genres.
     * @return genres
     */
    @DynamoDBAttribute(attributeName = "genres")
    public Set<String> getGenres() {

        if (genres == null) {
            return genres;
        }
        return new HashSet<>(genres);
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
     * @return madeBy
     */
    @DynamoDBAttribute(attributeName = "madeBy")
    public String getMadeBy() {
        return madeBy;
    }

    /**
     * Setter for madeBy.
     * @param madeBy to be set
     */
    public void setMadeBy(String madeBy) {
        this.madeBy = madeBy;
    }
}
