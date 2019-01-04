package pratibha.co.repository.entity;

public class Url {
    private String userId;
    private String originalUrl;
    private String shortUrl;
    private String createdOn;

    public Url(String userId, String originalUrl,String shortUrl, String createdOn){
        this.userId = userId;
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.createdOn = createdOn;
    }

    public Url(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return "Url{" +
                "userId='" + userId + '\'' +
                ", originalUrl='" + originalUrl + '\'' +
                ", shortUrl='" + shortUrl + '\'' +
                ", createdOn='" + createdOn + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Url url = (Url) o;

        if (userId != null ? !userId.equals(url.userId) : url.userId != null) return false;
        if (originalUrl != null ? !originalUrl.equals(url.originalUrl) : url.originalUrl != null) return false;
        if (shortUrl != null ? !shortUrl.equals(url.shortUrl) : url.shortUrl != null) return false;
        return createdOn != null ? createdOn.equals(url.createdOn) : url.createdOn == null;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (originalUrl != null ? originalUrl.hashCode() : 0);
        result = 31 * result + (shortUrl != null ? shortUrl.hashCode() : 0);
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        return result;
    }
}
