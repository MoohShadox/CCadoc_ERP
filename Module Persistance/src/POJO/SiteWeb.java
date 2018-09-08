package POJO;

public class SiteWeb {
    private String url, description;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        SiteWeb s=(SiteWeb)obj;
        return this.url.equals(s.url);
    }
}
