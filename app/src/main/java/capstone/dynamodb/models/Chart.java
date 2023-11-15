package capstone.dynamodb.models;

import capstone.converters.IdConverter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Chart {
    private UUID id;
    private String name;
    private String artist;
    private Integer bpm;
    private String content;
    private Set<String> genres;

    @DynamoDBHashKey(attributeName = "id")
    @DynamoDBTypeConverted(converter = IdConverter.class)
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
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @DynamoDBAttribute(attributeName = "genres")
    public Set<String> getGenres() {

        if (genres == null) {
            return genres;
        }
        return new HashSet<>(genres);
    }

    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }
}