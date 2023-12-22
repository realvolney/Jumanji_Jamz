package capstone.activity.requests;

public class MySetListsRequest {
    private final String createdBy;

    private MySetListsRequest(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public String toString() {
        return "MySetListsRequest{" +
                "createdBy='" + createdBy + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private String createdBy;

        public Builder withCreatedBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public MySetListsRequest build() {
            return new MySetListsRequest(createdBy);
        }
    }
}
