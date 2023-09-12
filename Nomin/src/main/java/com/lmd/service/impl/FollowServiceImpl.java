package com.lmd.service.impl;


import com.lmd.pojo.Follow;
import com.lmd.pojo.Restaurant;
import com.lmd.pojo.User;
import com.lmd.repository.FollowRepository;
import com.lmd.repository.RestaurantRepository;
import com.lmd.repository.UserRepository;
import com.lmd.service.FollowService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    private FollowRepository followRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RestaurantRepository restaurantRepo;

    @Override
    public Follow addFollow(int resId, Principal user) {
        try {
            String username = user.getName();
            User userId = this.userRepo.getUserByUsername(username);
            Restaurant restaurantId = this.restaurantRepo.getRestaurantById(resId);
            if (userId == null || restaurantId == null) {
                return null;
            }

            Follow follow = new Follow();
            follow.setRestaurantsId(restaurantId);
            follow.setUsersId(userId);

            this.followRepo.addFollow(follow);
            return follow;

        } catch (HibernateException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean unFollow(int restaurantId, Principal user) {
        try {
            String username = user.getName();
            User userId = this.userRepo.getUserByUsername(username);
            Restaurant resId = this.restaurantRepo.getRestaurantById(restaurantId);
            if (userId == null || resId == null) {
                return false;
            }
            this.followRepo.unFollow(restaurantId, userId.getId());
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Follow> getListFollowByUserId(int userId) {
        return this.followRepo.getListFollowByUserI(userId);
    }

    @Override
    public List<User> getListFollowByRestaurantId(int id) {
        return this.followRepo.getListFollowByRestaurantId(id);
    }

    @Override
    public Long countFollowerByRestaurantId(int id) {
        return this.followRepo.countFollowerByRestaurantId(id);
    }


}
