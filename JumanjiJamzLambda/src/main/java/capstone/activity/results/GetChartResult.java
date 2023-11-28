package capstone.activity.results;

import java.util.Set;

public class GetChartResult {
    private String id;
    private String name;
    private String artist;
    private Integer bpm;
    private String content;
    private Set<String> genres;
    private String madeBy;

    private GetChartResult(String id, String name, String artist, Integer bpm,
                          String content, Set<String> genres, String madeBy) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.bpm = bpm;
        this.content = content;
        this.genres = genres;
        this.madeBy = madeBy;
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

    @Override
    public String toString() {
        return "GetChartResult{" +
                "id=" + id +'\'' +
                "name=" + name + '\'' +
                "artist=" + artist + '\'' +
                "bpm=" + bpm + '\'' +
                "content=" + content + '\'' +
                "genres=" + genres + '\'' +
                "madeBy=" + madeBy + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private String id;
        private String name;
        private String artist;
        private Integer bpm;
        private String content;
        private Set<String> genres;
        private String madeBy;

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

        public GetChartResult build() {
            return new GetChartResult(id, name, artist, bpm, content, genres, madeBy);
        }
    }
}

