package com.michal.examples.user.posts.management.service;

import com.michal.examples.user.posts.management.client.JsonPlaceHolderClient;
import com.michal.examples.user.posts.management.exception.MultipleResourcesFoundException;
import com.michal.examples.user.posts.management.exception.NotFoundException;
import com.michal.examples.user.posts.management.vo.UserPostVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Michal Remis
 */
@Service
public class IntegrationServiceImpl implements IntegrationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IntegrationServiceImpl.class);

    private final JsonPlaceHolderClient jsonPlaceHolderClient;

    private final UserPostsService userPostsService;
    private final ConvertService convertService;

    private final String clientBaseUrl;

    public IntegrationServiceImpl(final JsonPlaceHolderClient jsonPlaceHolderClient,
                                  final UserPostsService userPostsService,
                                  final ConvertService convertService,
                                  @Value("${application.placeholder-client.base-url}") final String clientBaseUrl) {
        this.jsonPlaceHolderClient = jsonPlaceHolderClient;
        this.userPostsService = userPostsService;
        this.convertService = convertService;
        this.clientBaseUrl = clientBaseUrl;
    }

    @Override
    public UserPostVo findPostById(Integer postId) {
        // try to look in db
        return userPostsService.findById(postId).map(convertService::convert).orElseGet(() -> {
            // try to look into external system
            LOGGER.info("Could not find post in internal system, searching in external...");
            final var responseExternal = jsonPlaceHolderClient.findPostById(clientBaseUrl, postId);

            // this should not happen but cover it anyway
            if (responseExternal.size() > 1) {
                throw MultipleResourcesFoundException.of(String.format("Multiple resources for id: [%s] found", postId));
            }

            LOGGER.info("User post found in external service, saving to system...");
            return convertService.convert(userPostsService.save(convertService.convert(responseExternal.stream().findAny()
                    .orElseThrow(() -> NotFoundException.of(String.format("Post with id: [%s] not found", postId))))));
        });
    }

    @Override
    public List<UserPostVo> findPostByUserId(Integer userId) {
        return userPostsService.findAllByUserId(userId).stream().map(convertService::convert).toList();
    }

    @Override
    public UserPostVo createPost(UserPostVo userPostVo) {
        checkForUserOrThrow(userPostVo.userId());
        return convertService.convert(userPostsService.save(userPostVo));
    }

    @Override
    public UserPostVo updatePost(UserPostVo userPostVo) {
        return convertService.convert(userPostsService.update(userPostVo));
    }

    private void checkForUserOrThrow(Integer userId) {
        final var response = jsonPlaceHolderClient.findUserById(clientBaseUrl, userId);

        // this should not happen but cover it anyway
        if (response.size() > 1) {
            throw MultipleResourcesFoundException.of(String.format("Multiple users for id: [%s] found", userId));
        }

        // if nothing is found in external system
        if (response.isEmpty()) {
            throw NotFoundException.of(String.format("User with id: [%s] not found", userId));
        }
    }

    @Override
    public void deletePost(Integer id) {
        userPostsService.deleteById(id);
    }
}
