package capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Set;

import static capstone.utils.CollectionUtils.copyToSet;

/**
 * This class represents a request to create a chart.
 * It is used as part of the CreateChartActivity API.
 */
//CHECKSTYLE:OFF:CreateChartRequest
@JsonDeserialize(builder = CreateChartRequest.Builder.class)
public class CreateChartRequest {
    private final String name;
    private final String artist;
    private final Integer bpm;
    private final String content;
    private final Set<String> genres;
    private final String madeBy;

    private CreateChartRequest(String name, String artist, Integer bpm,
                              String content, Set<String> genres, String madeBy) {
        this.name = name;
        this.artist = artist;
        this.bpm = bpm;
        this.content = content;
        this.genres = genres;
        this.madeBy = madeBy;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public Integer getBpm() {
        return bpm;
    }

    public String getContent() {
        return content;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public String getMadeBy() { return madeBy; }

    @Override
    public String toString() {
        return "CreateSetListRequest{" +
                "name= " + name + '\'' +
                ", artist= " + artist + '\'' +
                ", bpm= " + bpm + '\'' +
                ", content= " + content + '\'' +
                ", genres= " + genres + '\'' +
                ", madeBy= " + madeBy + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String name;
        private String artist;
        private Integer bpm;
        private String content;
        private Set<String> genres;
        private String madeBy;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withArtist(String artist) {
            this.artist = artist;
            return this;
        }

        public Builder withBpm(Integer bpm) {
            this.bpm = bpm;
            return this;
        }

        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public Builder withGenres(Set<String> genres) {
            this.genres = copyToSet(genres);
            return this;
        }

        public Builder withMadeBy(String madeBy) {
            this.madeBy = madeBy;
            return this;
        }

        public CreateChartRequest build() {
            return new CreateChartRequest(name, artist, bpm, content, genres, madeBy);
        }
    }
}
