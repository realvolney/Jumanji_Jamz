package capstone.activity.results;

import capstone.models.SetListModel;

import java.util.List;
import java.util.Set;

public class MySetListResult {
    private final List<SetListModel> setLists;

    private MySetListResult(List<SetListModel> setLists) {
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

    public static Builder build() {
        return new Builder();
    }
    public static class Builder {
        private List<SetListModel> setLists;

        public Builder withSetLists(List<SetListModel> setLists) {
            this.setLists = setLists;
            return this;
        }

        public MySetListResult build() {
            return new MySetListResult(setLists);
        }
    }
}
