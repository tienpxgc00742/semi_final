/*
 * Copyright (c) 2015 Evan Kale
 * Email: EvanKale91@gmail.com
 * Website: www.ISeeDeadPixel.com
 * 
 * This file is part of WebImageListViewSample.
 *
 * WebImageListViewSample is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.tnaapp.tnalayout.tien.box;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ImageAndCaption {

    private final String imageURL;
    private final String imageClubName;
    private final String id;


    public ImageAndCaption(String imageURL, String imageClubName, String id) {
        this.imageURL = imageURL;
        this.imageClubName = imageClubName;
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getImageClubName() {
        return imageClubName;
    }

    public String getId() {
        return id;
    }

    public static ArrayList<ImageAndCaption> getList() {
        ArrayList<ImageAndCaption> list = new ArrayList<ImageAndCaption>();
        String json = "[{\"id\":600,\"name\":\"Bournemouth\",\"league_id\":11,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":602,\"name\":\"Arsenal\",\"league_id\":11,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":603,\"name\":\"Aston Villa\",\"league_id\":11,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":630,\"name\":\"Chelsea\",\"league_id\":11,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":642,\"name\":\"Crystal Palace\",\"league_id\":11,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":650,\"name\":\"Everton\",\"league_id\":11,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":673,\"name\":\"Leicester\",\"league_id\":11,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":676,\"name\":\"Liverpool\",\"league_id\":11,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":679,\"name\":\"Man City\",\"league_id\":11,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":680,\"name\":\"Man Utd\",\"league_id\":11,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":688,\"name\":\"Newcastle\",\"league_id\":11,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":691,\"name\":\"Norwich\",\"league_id\":11,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":713,\"name\":\"Southampton\",\"league_id\":11,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":721,\"name\":\"Stoke\",\"league_id\":11,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":722,\"name\":\"Sunderland\",\"league_id\":11,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":724,\"name\":\"Swansea\",\"league_id\":11,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":728,\"name\":\"Tottenham\",\"league_id\":11,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":732,\"name\":\"Watford\",\"league_id\":11,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":734,\"name\":\"West Brom\",\"league_id\":11,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":735,\"name\":\"West Ham\",\"league_id\":11,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":826,\"name\":\"AS Monaco FC\",\"league_id\":16,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":828,\"name\":\"AS Saint-Etienne\",\"league_id\":16,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":840,\"name\":\"En Avant de Guingamp\",\"league_id\":16,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":846,\"name\":\"FC Nantes\",\"league_id\":16,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":851,\"name\":\"Girondins Bordeaux\",\"league_id\":16,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":858,\"name\":\"LOSC\",\"league_id\":16,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":859,\"name\":\"Montpellier HSC\",\"league_id\":16,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":862,\"name\":\"OGC Nice\",\"league_id\":16,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":865,\"name\":\"Olympique Lyonnais\",\"league_id\":16,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":866,\"name\":\"OM\",\"league_id\":16,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":868,\"name\":\"Paris Saint-Germain\",\"league_id\":16,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":875,\"name\":\"Angers SCO\",\"league_id\":16,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":876,\"name\":\"SC Bastia\",\"league_id\":16,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":877,\"name\":\"Stade Malherbe Caen\",\"league_id\":16,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":884,\"name\":\"Stade Rennais FC\",\"league_id\":16,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":886,\"name\":\"Toulouse FC\",\"league_id\":16,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":901,\"name\":\"Leverkusen\",\"league_id\":22,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":907,\"name\":\"Dortmund\",\"league_id\":22,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":908,\"name\":\"M&#65533;nchengladbach\",\"league_id\":22,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":912,\"name\":\"Frankfurt\",\"league_id\":22,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":915,\"name\":\"Bayern M&#65533;nchen\",\"league_id\":22,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":916,\"name\":\"K&#65533;ln\",\"league_id\":22,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":918,\"name\":\"Mainz\",\"league_id\":22,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":920,\"name\":\"Schalke\",\"league_id\":22,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":927,\"name\":\"Hannover\",\"league_id\":22,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":947,\"name\":\"Hamburg\",\"league_id\":22,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":948,\"name\":\"Werder Bremen\",\"league_id\":22,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":960,\"name\":\"Stuttgart\",\"league_id\":22,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":961,\"name\":\"Wolfsburg\",\"league_id\":22,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1099,\"name\":\"A.C. Milan\",\"league_id\":32,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1100,\"name\":\"Roma\",\"league_id\":32,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1106,\"name\":\"Atalanta\",\"league_id\":32,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1111,\"name\":\"Bologna\",\"league_id\":32,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1115,\"name\":\"Carpi\",\"league_id\":32,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1122,\"name\":\"Chievo\",\"league_id\":32,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1126,\"name\":\"Empoli\",\"league_id\":32,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1129,\"name\":\"Fiorentina\",\"league_id\":32,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1132,\"name\":\"Genoa\",\"league_id\":32,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1135,\"name\":\"Inter\",\"league_id\":32,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1139,\"name\":\"Juventus\",\"league_id\":32,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1140,\"name\":\"Lazio\",\"league_id\":32,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1150,\"name\":\"Napoli\",\"league_id\":32,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1167,\"name\":\"Sampdoria\",\"league_id\":32,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1174,\"name\":\"Torino\",\"league_id\":32,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1178,\"name\":\"Udinese\",\"league_id\":32,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1664,\"name\":\"Athletic\",\"league_id\":67,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1687,\"name\":\"Atl&#65533;tico\",\"league_id\":67,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1705,\"name\":\"Deportivo\",\"league_id\":67,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1708,\"name\":\"Barcelona\",\"league_id\":67,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1710,\"name\":\"Getafe\",\"league_id\":67,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1714,\"name\":\"Granada\",\"league_id\":67,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1717,\"name\":\"Levante\",\"league_id\":67,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1724,\"name\":\"Celta\",\"league_id\":67,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1725,\"name\":\"Espanyol\",\"league_id\":67,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1729,\"name\":\"Rayo\",\"league_id\":67,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1733,\"name\":\"R. Betis\",\"league_id\":67,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1736,\"name\":\"R. Madrid\",\"league_id\":67,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1742,\"name\":\"R. Sociedad\",\"league_id\":67,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1744,\"name\":\"Sporting\",\"league_id\":67,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1753,\"name\":\"Eibar\",\"league_id\":67,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1759,\"name\":\"Sevilla\",\"league_id\":67,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1772,\"name\":\"Las Palmas\",\"league_id\":67,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1775,\"name\":\"Valencia\",\"league_id\":67,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1777,\"name\":\"Villarreal\",\"league_id\":67,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":1971,\"name\":\"ESTAC Troyes\",\"league_id\":16,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":2005,\"name\":\"FC Lorient\",\"league_id\":16,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":2015,\"name\":\"GFC Ajaccio\",\"league_id\":16,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":2047,\"name\":\"Stade de Reims\",\"league_id\":16,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":2096,\"name\":\"M&#65533;laga\",\"league_id\":67,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":2194,\"name\":\"Palermo\",\"league_id\":32,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":2199,\"name\":\"Frosinone\",\"league_id\":32,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":2201,\"name\":\"Verona\",\"league_id\":32,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":2238,\"name\":\"Augsburg\",\"league_id\":22,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":2247,\"name\":\"Hertha\",\"league_id\":22,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":108997,\"name\":\"Darmstadt\",\"league_id\":22,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":879226,\"name\":\"Hoffenheim\",\"league_id\":22,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":3800256,\"name\":\"Sassuolo\",\"league_id\":32,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]},{\"id\":8714658,\"name\":\"Ingolstadt\",\"league_id\":22,\"tna_league\":null,\"tna_match\":[],\"tna_match1\":[]}]";
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<ClubItem>>() {
        }.getType();
        List<ClubItem> clubs = gson.fromJson(json, collectionType);
        for (ClubItem club : clubs) {
            list.add(new ImageAndCaption(
                    "https://localhost:8888/" +club.getId(),
                    club.getName(), club.getId()));
        }
        list.add(new ImageAndCaption(
                "https://avatars2.githubusercontent.com/u/11900325?v=3&s=40",
                "Abkhazia", ""));
        return list;
    }

    public class ClubItem {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("league_id")
        @Expose
        public Integer leagueId;
        @SerializedName("tna_league")
        @Expose
        public Object tnaLeague;
        @SerializedName("tna_match")
        @Expose
        public List<Object> tnaMatch = new ArrayList<Object>();
        @SerializedName("tna_match1")
        @Expose
        public List<Object> tnaMatch1 = new ArrayList<Object>();

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Integer getLeagueId() {
            return leagueId;
        }

        public Object getTnaLeague() {
            return tnaLeague;
        }

        public List<Object> getTnaMatch() {
            return tnaMatch;
        }

        public List<Object> getTnaMatch1() {
            return tnaMatch1;
        }
    }
}
