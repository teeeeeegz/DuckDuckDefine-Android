package au.com.michaeltigas.duckduckdefine.data.remote;

import java.util.Map;

import au.com.michaeltigas.duckduckdefine.data.remote.model.SearchDefinition;
import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Michael Tigas on 15/3/17.
 *
 * All DuckDuckGo API endpoints go in this class
 */
public interface DuckDuckGoApiService {
    @GET(".")
    Flowable<SearchDefinition> searchRequest(@QueryMap Map<String, String> parameters);
}
