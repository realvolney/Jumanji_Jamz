package capstone.activity.requests;

public class DeleteSetListRequest {
    private final String id;
    private final String madeBy;

    private DeleteSetListRequest(String id, String madeBy) {
        this.id = id;
        this.madeBy = madeBy;
    }

    public String getId() {
        return id;
    }

    public String getMadeBy() {
        return madeBy;
    }

    @Override
    public String toString() {
        return "DeleteSetListRequest{" +
                "id='" + id + '\'' +
                ", madeBy='" + madeBy + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private String id;
        private String madeBy;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withMadBy(String madeBy) {
            this.madeBy = madeBy;
            return this;
        }

        public DeleteSetListRequest build() {
            return new DeleteSetListRequest(id, madeBy);
        }
    }
}
