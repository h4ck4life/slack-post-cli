import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SlackTest {

    String channelName;
    PostSlack postSlack;

    @BeforeEach
    void testSetup() {
        channelName = "dev";
        postSlack = new PostSlack();
    }

    @Test
    void testPostMessageToSlackChannel() {
        String postMessage = "test message";
        String userName = "testBot";
        boolean postStatus = postSlack.send(channelName, userName, postMessage);
        assertTrue(postStatus);
    }

    @Test
    void testPostMessageToSlackChannelWithAttachment() {
        String filePath = "src/test/resources/demo.txt";
        String fileTitle = "demo txt file";
        String fileComment = "this is a demo file upload.";
        boolean postStatus = postSlack.sendWithFile(channelName, fileTitle, fileComment, filePath);
        assertTrue(postStatus);
    }
}
