package capstone.models;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class ChartModel {
    UUID id;
    String name;
    String artist;
    Integer bpm;
    String Content;
    Set<String> genres;

    private ChartModel(UUID id, String name,
                      String artist, Integer bpm, String content, Set<String> genres) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.bpm = bpm;
        Content = content;
        this.genres = genres;
    }

    public UUID getId() {
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
        return Content;
    }

    public Set<String> getGenres() {
        return genres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChartModel that = (ChartModel) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(artist, that.artist) && Objects.equals(bpm, that.bpm) && Objects.equals(Content, that.Content) && Objects.equals(genres, that.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, artist, bpm, Content, genres);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;
        private String name;
        private String artist;
        private Integer bpm;
        private String content;
        private Set<String> genres;

        public Builder withId(UUID id) {
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

        public ChartModel build() {
            return new ChartModel(id, name, artist, bpm, content, genres);
        }
    }
}