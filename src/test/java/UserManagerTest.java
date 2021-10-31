import entities.User;
import use_cases.UserManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class UserManagerTest {
    private User user;
    private UserManager usermanager;

    @BeforeEach
    void setupFeedTest() {
        this.user = new User("eren_yeager", "#1titan_slayer");
        this.usermanager = new UserManager();
    }

    @Test
    void testUpdateUsername() {
        this.usermanager.updateUsername(this.user, "Eren_Yeager");
        assert this.user.getUsername().equals("Eren_Yeager");
    }

    @Test
    void testUpdatePassword() {
        this.usermanager.updatePassword(this.user, "#1TitanSlayer");
        assert this.user.getPassword().equals("#1TitanSlayer");
    }

    @Test
    void testUpdateBio() {
        this.usermanager.updateBio(this.user, "Number 1 in the world!");
        assert this.user.getBio().equals("Number 1 in the world!");
    }

    @Test
    void testAddLike() {
        HashMap<String, Integer> like_history = new HashMap<String, Integer>();
        like_history.put("Russian Food", 4);
        like_history.put("Burnt Food", 1);
        this.user.setLikeHistory(like_history);
        this.usermanager.addLike(this.user, "Russian Food");
        this.usermanager.addLike(this.user,"Russian Food");
        this.usermanager.addLike(this.user,"Russian Food");
        assert (this.user.getLikeHistory().get("Russian Food") == 7 &&
                this.user.getLikeHistory().get("Burnt Food") == 1);
    }

    @Test
    void testFollowUserSuccess() {
        User john_jones = new User("john_jones","123");
        this.usermanager.followUser(this.user, john_jones);
        assert this.user.getFollowing().contains(john_jones);
    }

    @Test
    void testFollowUserThatsAlreadyFollowed() {
        User john_jones = new User("john_jones","123");
        this.usermanager.followUser(this.user, john_jones);
        assert !this.usermanager.followUser(this.user, john_jones);
    }

    @Test
    void testUnfollowUserSuccess() {
        User john_jones = new User("john_jones","123");
        User john_mike = new User("john_mike", "123");
        this.usermanager.followUser(this.user, john_jones);
        this.usermanager.followUser(this.user, john_mike);
        this.usermanager.unfollowUser(this.user, john_jones);
        assert !this.user.getFollowing().contains(john_jones) && this.user.getFollowing().contains(john_mike);
    }

    @Test
    void testAddFollowerSuccess() {
        User john_jones = new User("john_jones","123");
        this.usermanager.addFollower(this.user, john_jones);
        assert this.user.getFollowers().contains(john_jones);
    }

    @Test
    void testAddFollowerThatsAlreadyInFollowing() {
        User john_jones = new User("john_jones","123");
        this.usermanager.addFollower(this.user,john_jones);
        assert !this.usermanager.addFollower(this.user, john_jones);
    }

    @Test
    void testChangeUsernameSuccess() {
        this.usermanager.changeUsername(this.user, "Eren_yeager");
        assert this.user.getUsername().equals("Eren_yeager");
    }

    @Test
    void testChangeUsernameFail() {
        assert !this.usermanager.changeUsername(this.user, "eren_yeager");
    }

    @Test
    void testChangePasswordSuccess() {
        this.usermanager.changePassword(this.user, "123");
        assert this.user.getPassword().equals("123");
    }

    @Test
    void testChangePasswordFail() {
        assert !this.usermanager.changePassword(this.user, "#1titan_slayer");
    }

    @Test
    void testChangeBioSuccess() {
        this.user.setBio("Number 1 fan of 207");
        this.usermanager.changeBio(this.user, "Number 2 fan of 207");
        assert this.user.getBio().equals("Number 2 fan of 207");
    }

    @Test
    void testChangeBioFail() {
        this.user.setBio("Number 1 fan of 207");
        this.usermanager.changeBio(this.user, "Number 2 fan of 207");
        assert !this.usermanager.changeBio(this.user, "Number 2 fan of 207") &&
                this.user.getBio().equals("Number 2 fan of 207");
    }
}
