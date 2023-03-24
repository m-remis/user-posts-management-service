package com.amcef.user.posts.management.service;

import com.amcef.user.posts.management.entity.UserPostEntity;
import com.amcef.user.posts.management.vo.UserPostVo;

import java.util.List;
import java.util.Optional;

/**
 * @author Michal Remis
 */
public interface UserPostsService {

    UserPostEntity save(UserPostVo userPostVo);

    Optional<UserPostEntity> findById(Integer id);

    List<UserPostEntity> findAllByUserId(Integer userId);

    UserPostEntity update(UserPostVo userPostVo);

    void deleteById(Integer id);
}
