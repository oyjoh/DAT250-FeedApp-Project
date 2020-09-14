public class BrukarUpdateRequest {
    private String name;
    private String email;
    private String pwd;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPwd() {
        return pwd;
    }

    protected BrukarUpdateRequest() {
    }

    private BrukarUpdateRequest(String name, String email, String pwd) {
        this.name = name;
        this.email = email;
        this.pwd = pwd;
    }

    public static BrukarUpdateRequestBuilder builder() {
        return new BrukarUpdateRequestBuilder();
    }

    public static class BrukarUpdateRequestBuilder {
        private String name;
        private String email;
        private String pwd;

        public BrukarUpdateRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        public BrukarUpdateRequestBuilder email(String email) {
            this.email = email;
            return this;
        }

        public BrukarUpdateRequestBuilder pwd(String pwd) {
            this.pwd = pwd;
            return this;
        }

        public BrukarUpdateRequest build() {
            return new BrukarUpdateRequest(name, email, pwd);
        }
    }

}
