package capstone.activity.results;

import capstone.models.SetListModel;

import java.util.List;

public class MySetListsResult {
    private final List<SetListModel> setLists;

    private MySetListsResult(List<SetListModel> setLists) {
        this.setLists = setLists;
    }

    public List<SetListModel> getSetLists() {
        return setLists;
    }

    @Override
    public String toString() {
        return "MySetListResult{" +
                "setLists=" + setLists +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private List<SetListModel> setLists;

        public Builder withSetLists(List<SetListModel> setLists) {
            this.setLists = setLists;
            return this;
        }

        public MySetListsResult build() {
            return new MySetListsResult(setLists);
        }
    }
}
