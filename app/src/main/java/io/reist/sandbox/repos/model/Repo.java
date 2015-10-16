package io.reist.sandbox.repos.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Reist on 10/14/15.
 */
public class Repo {

    @SerializedName("name")
    public String name;

    @SerializedName("html_url")
    public String url;

}
