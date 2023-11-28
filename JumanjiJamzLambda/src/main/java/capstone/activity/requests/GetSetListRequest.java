package capstone.activity.requests;

public class GetSetListRequest {

    private final String id;

    private GetSetListRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "GetSetListRequest{" +
                "id='" + id + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private String id;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public GetSetListRequest build() {
            return new GetSetListRequest(id);
        }
    }
}
