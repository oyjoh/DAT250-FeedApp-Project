public class EntryUpdateRequest {
    private Value value;
    private Integer number;

    public Value getValue() {
        return value;
    }

    public Integer getNumber() {
        return number;
    }


    protected EntryUpdateRequest() {
    }

    private EntryUpdateRequest(Value value, Integer number) {
        this.value = value;
        this.number = number;
    }

    public static EntryUpdateRequestBuilder builder() {
        return new EntryUpdateRequestBuilder();
    }

    public static class EntryUpdateRequestBuilder {
        private Value value;
        private Integer number;

        public EntryUpdateRequestBuilder value(Value value) {
            this.value = value;
            return this;
        }

        public EntryUpdateRequestBuilder number(Integer number) {
            this.number = number;
            return this;
        }

        public EntryUpdateRequest build() {
            return new EntryUpdateRequest(value, number);
        }
    }

}
