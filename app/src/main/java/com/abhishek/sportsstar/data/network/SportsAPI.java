package com.abhishek.sportsstar.data.network;

import com.abhishek.sportsstar.data.model.Events;
import com.abhishek.sportsstar.data.model.Teams;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SportsAPI {

    @GET("eventspastleague.php?")
    Call<Events> getPastEventForLeague(@Query("id") String id);

    @GET("searchteams.php?")
    Call<Teams> getTeamList(@Query("t") String team);

    @GET("eventslast.php?")
    Call<Events> getLastEvents(@Query("id") String id);

    @GET("eventsnext.php?")
    Call<Events> getNextEvents(@Query("id") String id);

    @GET("eventsnextleague.php?")
    Call<Events> getFutureEvents(@Query("id") String id);


}
