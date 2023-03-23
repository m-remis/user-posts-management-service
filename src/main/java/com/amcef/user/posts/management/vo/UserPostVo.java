package com.amcef.user.posts.management.vo;

/**
 * @author Michal Remis
 */
public record UserPostVo(Integer id,
                         Integer userId,
                         String title,
                         String body) {
}
