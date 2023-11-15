package capstone.activity.requests;

import java.util.Set;

/**
 * This class represents a request to create a chart
 * It is used as part of the CreateChartActivity API
 */
public class CreateChartRequest {
    private final String name;
    private final String artist;
    private final Integer bpm;
    private final String content;
    private final Set<String> genres;

    private CreateChartRequest(String name, String artist, Integer bpm,
                              String content, Set<String> genres) {
        this.name = name;
        this.artist = artist;
        this.bpm = bpm;
        this.content = content;
        this.genres = genres;
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

    @Override
    public String toString() {
        return "CreateChartRequest{" +
                "name= " + name + '\'' +
                ", artist= " + artist + '\'' +
                ", bpm= " + bpm + '\'' +
                ", content= " + content + '\'' +
                ", genres= " + genres + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private String name;
        private String artist;
        private Integer bpm;
        private String content;
        private Set<String> genres;

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

        public CreateChartRequest build() {
            return new CreateChartRequest(name, artist, bpm, content, genres);
        }
    }
}
