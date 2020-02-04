package in.wingstud.grocitoseller.model;

import org.json.JSONObject;

import java.io.Serializable;

public class SearchModel implements Serializable {

    String id,name;

    JSONObject jsonObject = new JSONObject();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
