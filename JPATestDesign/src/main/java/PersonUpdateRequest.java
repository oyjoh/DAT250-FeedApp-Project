public class PersonUpdateRequest {
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

    protected PersonUpdateRequest() {
    }

    private PersonUpdateRequest(String name, String email, String pwd) {
        this.name = name;
        this.email = email;
        this.pwd = pwd;
    }

    public static PersonUpdateRequestBuilder builder() {
        return new PersonUpdateRequestBuilder();
    }

    public static class PersonUpdateRequestBuilder {
        private String name;
        private String email;
        private String pwd;

        public PersonUpdateRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PersonUpdateRequestBuilder email(String email) {
            this.email = email;
            return this;
        }

        public PersonUpdateRequestBuilder pwd(String pwd) {
            this.pwd = pwd;
            return this;
        }

        public PersonUpdateRequest build() {
            return new PersonUpdateRequest(name, email, pwd);
        }
    }

}
