package capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Set;
@JsonDeserialize(builder =AddChartRequest.Builder.class)
public class AddChartRequest {

    private final String id;
    private final String name;
    private final String artist;
    private final Integer bpm;
    private final String content;
    private final Set<String> genres;
    private final String madeBy;
    private final String setListId;

    private AddChartRequest(String id, String name, String artist, Integer bpm,
                           String content, Set<String> genres, String madeBy, String setListId) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.bpm = bpm;
        this.content = content;
        this.genres = genres;
        this.madeBy = madeBy;
        this.setListId = setListId;
    }

    public String getId() {
        return id;
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

    public String getMadeBy() {
        return madeBy;
    }

    public String getSetListId() {
        return setListId;
    }

    @Override
    public String toString() {
        return "AddChartRequest{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", bpm=" + bpm +
                ", content='" + content + '\'' +
                ", genres=" + genres +
                ", madeBy='" + madeBy + '\'' +
                ", setListId='" + setListId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String id;
        private String name;
        private String artist;
        private Integer bpm;
        private String content;
        private Set<String> genres;
        private String madeBy;
        private String setListId;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

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
            this.genres = genres;
            return this;
        }

        public Builder withMadeBy(String madeBy) {
            this.madeBy = madeBy;
            return this;
        }

        public Builder withSetListId(String setListId) {
            this.setListId = setListId;
            return this;
        }

        public AddChartRequest build() {
            return new AddChartRequest(id, name, artist, bpm, content, genres, madeBy, setListId);
        }

    }
}
