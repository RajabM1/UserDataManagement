package edu.najah.cap.data.data_deletion;

import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.exceptions.Util;
import edu.najah.cap.posts.IPostService;
import edu.najah.cap.posts.Post;

import java.util.ArrayList;
import java.util.List;

public class UserPostDelete extends BaseDataDelete {

    private final IPostService postService;

    public UserPostDelete(IPostService postService) {
        this.postService = postService;
    }

    @Override
    protected void performDeletion(String userName) throws SystemBusyException, BadRequestException, NotFoundException {
        Util.setSkipValidation(true);
        logger.info("Start deleting user posts data 4");
        List<Post> postsByAuthor = postService.getPosts(userName);
        List<String> postIdsToDelete = new ArrayList<>();

        for (Post post : postsByAuthor) {
            postIdsToDelete.add(post.getId());
        }

        for (String postId : postIdsToDelete) {
            postService.deletePost(userName, postId);
        }
        logger.info("User posts deleted successfully ");

        Util.setSkipValidation(false);
    }
}
