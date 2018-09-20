package POJO;

import Exceptions.BuildingException;
import Interfaces.DAOAble;

import java.net.URL;
import java.util.HashMap;

public class SiteWeb implements DAOAble<SiteWeb> {
    private String url, description;
    private Contact contact;

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

    @Override
    public HashMap<String, String> getRepositoryAttributs() throws IllegalAccessException {
        HashMap<String,String> H = new HashMap<>();
        H.put("URL",url);
        H.put("DESCRIPTION_URL",description);
        return H;
    }

    @Override
    public String getTableName() {
        return "SITEW";
    }

    @Override
    public String getReference() {
        return url;
    }

    @Override
    public String getKeyName() {
        return "URL";
    }

    @Override
    public SiteWeb buildFromRepData(HashMap<String, String> H) throws BuildingException, IllegalAccessException {
        SiteWeb SW = new SiteWeb();
        if(H.containsKey("DESCRIPTION_URL"))
            SW.description = H.get("DESCRIPTION_URL");
        if(H.containsKey("URL"))
            SW.url = H.get("URL");
        return SW;
    }
}
