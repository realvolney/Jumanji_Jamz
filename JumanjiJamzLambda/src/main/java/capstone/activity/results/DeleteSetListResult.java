package capstone.activity.results;

import capstone.activity.requests.DeleteSetListRequest;

public class DeleteSetListResult {
    private final Boolean success;
    private final String message;

    private DeleteSetListResult(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "DeleteSetListResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private Boolean success;
        private String message;

        public Builder withSuccess(Boolean success) {
            this.success = success;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public DeleteSetListResult build() {
            return new DeleteSetListResult(success, message);
        }
    }
}
