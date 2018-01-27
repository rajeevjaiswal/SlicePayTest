
package com.rajeevjaiswal.mvp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Photos {

    @SerializedName("page")
    private int page;

    @SerializedName("pages")
    private String pages;

    @SerializedName("perpage")
    private int perpage;

    @SerializedName("total")
    private String total;

    @SerializedName("photo")
    private List<Photo> photo = null;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Photo> getPhoto() {
        return photo;
    }

    public void setPhoto(List<Photo> photo) {
        this.photo = photo;
    }


}
