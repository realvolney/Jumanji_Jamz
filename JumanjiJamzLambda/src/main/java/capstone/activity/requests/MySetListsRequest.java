package capstone.activity.requests;

public class MySetListsRequest {
    private final String madeBy;

    private MySetListsRequest(String madeBy) {
        this.madeBy = madeBy;
    }

    public String getMadeBy() {
        return madeBy;
    }

    @Override
    public String toString() {
        return "MySetListsRequest{" +
                "madeBy='" + madeBy + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private String madeBy;

        public Builder withMadeBy(String madeBy) {
            this.madeBy= madeBy;
            return this;
        }

        public MySetListsRequest build() {
            return new MySetListsRequest(madeBy);
        }
    }
}
