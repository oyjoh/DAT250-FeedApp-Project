public class PollUpdateRequest {
    private String summary;
    private Boolean isPublic;

    public String getSummary() {
        return summary;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    protected PollUpdateRequest() {
    }

    private PollUpdateRequest(String summary, Boolean isPublic) {
        this.summary = summary;
        this.isPublic = isPublic;
    }

    public static PollUpdateRequestBuilder builder() {
        return new PollUpdateRequestBuilder();
    }

    public static class PollUpdateRequestBuilder {
        private String summary;
        private Boolean isPublic;

        public PollUpdateRequestBuilder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public PollUpdateRequestBuilder email(Boolean isPublic) {
            this.isPublic = isPublic;
            return this;
        }


        public PollUpdateRequest build() {
            return new PollUpdateRequest(summary, isPublic);
        }
    }

}
