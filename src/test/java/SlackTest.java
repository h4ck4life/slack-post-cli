import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SlackTest {

    String channelName;
    PostSlack postSlack;

    @BeforeEach
    void setupTest() {
        channelName = "dev";
        postSlack = new PostSlack();
    }

    @Test
    void postMessageToSlackChannelTest() {
        String postMessage = "test message";
        String userName = "testBot";
        boolean postStatus = postSlack.send(channelName, userName, postMessage);
        assertTrue(postStatus);
    }

    @Test
    void postMessageToSlackChannelWithAttachment_relativePathTest() {
        String filePath = "src/test/resources/demo.txt";
        String fileTitle = "demo txt file";
        String fileComment = "this is a demo file upload.";
        boolean postStatus = postSlack.sendWithFile(channelName, fileTitle, fileComment, filePath);
        assertTrue(postStatus);
    }

    @Test
    void postMessageToSlackChannelWithOnlyAttachFileAndChannelNameTest() {
        String filePath = "src/test/resources/demo.txt";
        boolean postStatus = postSlack.sendWithFile(channelName, "", "", filePath);
        assertTrue(postStatus);
    }

    //@Test
    void postMessageToSlackChannelWithAttachment_absolutePathTest() {
        String filePath = "C:\\Users\\Alif\\Documents\\SoftwareDevelopment\\JavaProjects\\slack\\src\\test\\resources\\demo.txt";
        String fileTitle = "demo txt file";
        String fileComment = "this is a demo file upload.";
        boolean postStatus = postSlack.sendWithFile(channelName, fileTitle, fileComment, filePath);
        assertTrue(postStatus);
    }
}
