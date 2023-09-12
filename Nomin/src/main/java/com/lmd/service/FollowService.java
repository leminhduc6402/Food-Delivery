package com.lmd.service;

import com.lmd.pojo.Follow;
import com.lmd.pojo.Restaurant;
import com.lmd.pojo.User;

import java.security.Principal;
import java.util.List;

public interface FollowService {
    Follow addFollow(int resId, Principal user);
    Boolean unFollow(int restaurantId, Principal user);
    List<Follow> getListFollowByUserId(int userId);
    List<User> getListFollowByRestaurantId(int id);

    Long countFollowerByRestaurantId(int id);
}
