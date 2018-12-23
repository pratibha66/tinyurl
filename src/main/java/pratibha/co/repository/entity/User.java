package pratibha.co.repository.entity;

public class User {
    private String userId;
    private String cname;
    private String createdOn;

    public User(String userId, String cname, String createdOn){
        this.userId = userId;
        this.cname = cname;
        this.createdOn = createdOn;
    }
    public User(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != null ? !userId.equals(user.userId) : user.userId != null) return false;
        if (cname != null ? !cname.equals(user.cname) : user.cname != null) return false;
        return createdOn != null ? createdOn.equals(user.createdOn) : user.createdOn == null;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (cname != null ? cname.hashCode() : 0);
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", cname='" + cname + '\'' +
                ", createdOn='" + createdOn + '\'' +
                '}';
    }
}
