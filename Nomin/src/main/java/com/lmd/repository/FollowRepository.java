package com.lmd.repository;

import com.lmd.pojo.Follow;
import com.lmd.pojo.User;

import java.util.List;

public interface FollowRepository {
    Boolean addFollow(Follow follow);
    Boolean unFollow(int restaurantId, int userId);
    List<Follow> getListFollowByUserI(int userId);
    List<User> getListFollowByRestaurantId(int id);
    Long countFollowerByRestaurantId(int id);

}
