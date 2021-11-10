import entities.User;
import use_cases.DatabaseManager;
import use_cases.UserManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class UserManagerTest {
    private User user;
    private UserManager userManager;

    @BeforeEach
    void setupFeedTest(DatabaseManager dbManager) {
        this.user = new User("eren_yeager", "#1titan_slayer");
        this.userManager = new UserManager(dbManager);
    }

    @Test
    void testUpdateUsername() {
        this.userManager.updateUsername(this.user, "Eren_Yeager");
        assert this.user.getUsername().equals("Eren_Yeager");
    }

    @Test
    void testUpdatePassword() {
        this.userManager.updatePassword(this.user, "#1TitanSlayer");
        assert this.user.getPassword().equals("#1TitanSlayer");
    }

    @Test
    void testUpdateBio() {
        this.userManager.updateBio(this.user, "Number 1 in the world!");
        assert this.user.getBio().equals("Number 1 in the world!");
    }

    @Test
    void testAddLike() {
        HashMap<String, Integer> like_history = new HashMap<>();
        like_history.put("Russian Food", 4);
        like_history.put("Burnt Food", 1);
        this.user.setLikeHistory(like_history);
        this.userManager.addLike(this.user, "Russian Food");
        this.userManager.addLike(this.user,"Russian Food");
        this.userManager.addLike(this.user,"Russian Food");
        assert (this.user.getLikeHistory().get("Russian Food") == 7 &&
                this.user.getLikeHistory().get("Burnt Food") == 1);
    }

    @Test
    void testFollowUserSuccess() {
        User john_jones = new User("john_jones","123");
        this.userManager.followUser(this.user, john_jones);
        assert this.user.getFollowing().contains(john_jones);
    }

    @Test
    void testFollowUserThatAlreadyFollowed() {
        User john_jones = new User("john_jones","123");
        this.userManager.followUser(this.user, john_jones);
        assert !this.userManager.followUser(this.user, john_jones);
    }

    @Test
    void testUnfollowUserSuccess() {
        User john_jones = new User("john_jones","123");
        User john_mike = new User("john_mike", "123");
        this.userManager.followUser(this.user, john_jones);
        this.userManager.followUser(this.user, john_mike);
        this.userManager.unfollowUser(this.user, john_jones);
        assert !this.user.getFollowing().contains(john_jones) && this.user.getFollowing().contains(john_mike);
    }

    @Test
    void testUnfollowUserFail() {
        User john_jones = new User("john_jones","123");
        User john_mike = new User("john_mike", "123");
        this.userManager.followUser(this.user, john_jones);
        this.userManager.followUser(this.user, john_mike);
        this.userManager.unfollowUser(this.user, john_jones);
        assert !this.userManager.unfollowUser(this.user, john_jones);
    }

    @Test
    void testAddFollowerSuccess() {
        User john_jones = new User("john_jones","123");
        this.userManager.addFollower(this.user, john_jones);
        assert this.user.getFollowers().contains(john_jones);
    }

    @Test
    void testAddFollowerThatAlreadyInFollowing() {
        User john_jones = new User("john_jones","123");
        this.userManager.addFollower(this.user,john_jones);
        assert !this.userManager.addFollower(this.user, john_jones);
    }

    @Test
    void testChangeUsernameSuccess() {
        this.userManager.changeUsername(this.user, "Eren_yeager");
        assert this.user.getUsername().equals("Eren_yeager");
    }

    @Test
    void testChangeUsernameFail() {
        assert !this.userManager.changeUsername(this.user, "eren_yeager");
    }

    @Test
    void testChangePasswordSuccess() {
        this.userManager.changePassword(this.user, "123");
        assert this.user.getPassword().equals("123");
    }

    @Test
    void testChangePasswordFail() {
        assert !this.userManager.changePassword(this.user, "#1titan_slayer");
    }

    @Test
    void testChangeBioSuccess() {
        this.user.setBio("Number 1 fan of 207");
        this.userManager.changeBio(this.user, "Number 2 fan of 207");
        assert this.user.getBio().equals("Number 2 fan of 207");
    }

    @Test
    void testChangeBioFail() {
        this.user.setBio("Number 1 fan of 207");
        this.userManager.changeBio(this.user, "Number 2 fan of 207");
        assert !this.userManager.changeBio(this.user, "Number 2 fan of 207") &&
                this.user.getBio().equals("Number 2 fan of 207");
    }

    @Test
    void testFindUserSuccess() {
        assert this.userManager.findUser("shawn") != null;
    }

    @Test
    void testFindUserFail() {
        assert this.userManager.findUser("Tom and Jerry") == null;
    }
}
