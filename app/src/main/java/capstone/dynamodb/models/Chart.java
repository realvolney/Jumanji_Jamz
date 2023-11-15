package capstone.dynamodb.models;

import capstone.enums.Genre;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;

import java.util.Set;
import java.util.UUID;

/** Model for Charts table on DynamoDB
 */
public class Chart {
    UUID id;
    String name;
    String artist;
    Integer bpm;
    String Content;
    Set<Genre> genres;

    @DynamoDBHashKey(attributeName = "id")
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "artist")
    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @DynamoDBAttribute(attributeName = "bpm")
    public Integer getBpm() {
        return bpm;
    }

    public void setBpm(Integer bpm) {
        this.bpm = bpm;
    }

    @DynamoDBAttribute(attributeName = "content")
    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    @DynamoDBAttribute(attributeName = "genres")
    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }
}
