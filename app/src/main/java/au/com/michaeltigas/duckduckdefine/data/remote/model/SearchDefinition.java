package au.com.michaeltigas.duckduckdefine.data.remote.model;

import com.squareup.moshi.Json;

/**
 * Created by Michael Tigas on 15/3/17.
 *
 * Represents a Search Definition object
 */
public class SearchDefinition {
    @Json(name = "Heading")         public String heading;
    @Json(name = "AbstractText")    public String abstractText;
    @Json(name = "Type")            public String type;
    @Json(name = "Image")           public String imageUrl;
}
