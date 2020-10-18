package ca.bcit.asn1;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BaseArticles {
    @SerializedName("status")
    @Expose
    private String status;
    public String getStatus() {
        return status;
    }

    @SerializedName("totalResults")
    @Expose
    private int totalResult;
    public int getTotalResult() {
        return totalResult;
    }

    @SerializedName("articles")
    @Expose
    private ArrayList<Article> articles;
    public ArrayList<Article> getArticles() {
        return articles;
    }
}
