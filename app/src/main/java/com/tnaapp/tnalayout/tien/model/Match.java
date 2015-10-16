package com.tnaapp.tnalayout.tien.model;

/**
 * Created by TIENPX3010 on 10/15/2015.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Match {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("homeId")
    @Expose
    private Integer homeId;
    @SerializedName("awayId")
    @Expose
    private Integer awayId;
    @SerializedName("homeGoals")
    @Expose
    private Integer homeGoals;
    @SerializedName("awayGoals")
    @Expose
    private Integer awayGoals;
    @SerializedName("homeSquad")
    @Expose
    private String homeSquad;
    @SerializedName("awaySquad")
    @Expose
    private String awaySquad;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("youtube")
    @Expose
    private String youtube;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("lId")
    @Expose
    private Integer lId;
    @SerializedName("tna_club")
    @Expose
    private Object tnaClub;
    @SerializedName("tna_club1")
    @Expose
    private Object tnaClub1;
    @SerializedName("tna_league")
    @Expose
    private Object tnaLeague;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The date
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     * The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The homeId
     */
    public Integer getHomeId() {
        return homeId;
    }

    /**
     *
     * @param homeId
     * The homeId
     */
    public void setHomeId(Integer homeId) {
        this.homeId = homeId;
    }

    /**
     *
     * @return
     * The awayId
     */
    public Integer getAwayId() {
        return awayId;
    }

    /**
     *
     * @param awayId
     * The awayId
     */
    public void setAwayId(Integer awayId) {
        this.awayId = awayId;
    }

    /**
     *
     * @return
     * The homeGoals
     */
    public Integer getHomeGoals() {
        return homeGoals;
    }

    /**
     *
     * @param homeGoals
     * The homeGoals
     */
    public void setHomeGoals(Integer homeGoals) {
        this.homeGoals = homeGoals;
    }

    /**
     *
     * @return
     * The awayGoals
     */
    public Integer getAwayGoals() {
        return awayGoals;
    }

    /**
     *
     * @param awayGoals
     * The awayGoals
     */
    public void setAwayGoals(Integer awayGoals) {
        this.awayGoals = awayGoals;
    }

    /**
     *
     * @return
     * The homeSquad
     */
    public String getHomeSquad() {
        return homeSquad;
    }

    /**
     *
     * @param homeSquad
     * The homeSquad
     */
    public void setHomeSquad(String homeSquad) {
        this.homeSquad = homeSquad;
    }

    /**
     *
     * @return
     * The awaySquad
     */
    public String getAwaySquad() {
        return awaySquad;
    }

    /**
     *
     * @param awaySquad
     * The awaySquad
     */
    public void setAwaySquad(String awaySquad) {
        this.awaySquad = awaySquad;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     * The youtube
     */
    public String getYoutube() {
        return youtube;
    }

    /**
     *
     * @param youtube
     * The youtube
     */
    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The lId
     */
    public Integer getLId() {
        return lId;
    }

    /**
     *
     * @param lId
     * The lId
     */
    public void setLId(Integer lId) {
        this.lId = lId;
    }

    /**
     *
     * @return
     * The tnaClub
     */
    public Object getTnaClub() {
        return tnaClub;
    }

    /**
     *
     * @param tnaClub
     * The tna_club
     */
    public void setTnaClub(Object tnaClub) {
        this.tnaClub = tnaClub;
    }

    /**
     *
     * @return
     * The tnaClub1
     */
    public Object getTnaClub1() {
        return tnaClub1;
    }

    /**
     *
     * @param tnaClub1
     * The tna_club1
     */
    public void setTnaClub1(Object tnaClub1) {
        this.tnaClub1 = tnaClub1;
    }

    /**
     *
     * @return
     * The tnaLeague
     */
    public Object getTnaLeague() {
        return tnaLeague;
    }

    /**
     *
     * @param tnaLeague
     * The tna_league
     */
    public void setTnaLeague(Object tnaLeague) {
        this.tnaLeague = tnaLeague;
    }

}