package capstone.activity.requests;

import capstone.models.ChartModel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Set;

@JsonDeserialize(builder = UpdateSetListRequest.Builder.class)
public class UpdateSetListRequest {
    private final String id;
    private final String name;
    private final Set<String> genres;
    private final String madeBy;

    private UpdateSetListRequest(String id, String name, Set<String> genres, String madeBy) {
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.madeBy = madeBy;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public String getMadeBy() {
        return madeBy;
    }

    @Override
    public String toString() {
        return "UpdateSetListRequest{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", genres=" + genres +
                ", madeBy='" + madeBy + '\'' +
                '}';
    }
//CHECKSTYLE:OFF:Builder

    public static Builder builder() { return new Builder(); }

    @JsonPOJOBuilder
    public static class Builder {
        private String id;
        private String name;
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

        public Builder withGenres(Set<String> genres) {
            this.genres = genres;
            return this;
        }

        public Builder withMadeBy(String madeBy) {
            this.madeBy = madeBy;
            return this;
        }

        public UpdateSetListRequest build() {
            return new UpdateSetListRequest(id, name, genres, madeBy);
        }
    }
}
