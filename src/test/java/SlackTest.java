import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.files.FilesUploadRequest;
import com.github.seratch.jslack.api.methods.response.files.FilesUploadResponse;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class SlackTest {

    PostSlack postSlack;
    String channelName = "dev";
    String postMessage = "test message";
    String userName = "testBot";
    String filePath = "src/test/resources/demo.txt";
    String fileTitle = "demo txt file";
    String fileComment = "this is a demo file upload.";

    @AfterEach
    void tearDown() {
        //Mockito.reset(postSlack);
        //postSlack = null;
    }

    @BeforeEach
    void setupEach() throws SlackApiException, IOException {
        Slack slackMock = mock(Slack.class, RETURNS_DEEP_STUBS);
        FilesUploadResponse filesUploadResponseMock = mock(FilesUploadResponse.class);
        WebhookResponse webhookResponseMock = mock(WebhookResponse.class);

        when(webhookResponseMock.getCode()).thenReturn(200);
        when(filesUploadResponseMock.isOk()).thenReturn(true);

        doReturn(webhookResponseMock).when(slackMock).send(null, Payload.builder().channel("#" + channelName).text(postMessage).username(userName).build());
        when(slackMock.methods().filesUpload(FilesUploadRequest.builder()
                .token(null)
                .channels(Arrays.asList("#" + channelName))
                .file(new File(filePath))
                .filename("demo.txt")
                .initialComment(fileComment)
                .title(fileTitle)
                .build())).thenReturn(filesUploadResponseMock);


        when(slackMock.methods().filesUpload(FilesUploadRequest.builder()
                .token(null)
                .channels(Arrays.asList("#" + channelName))
                .file(new File(filePath))
                .filename("demo.txt")
                .initialComment("")
                .title("")
                .build())).thenReturn(filesUploadResponseMock);

        postSlack = new PostSlack(slackMock);
    }

    @Test
    void postMessageToSlackChannelTest() {
        boolean postStatus = postSlack.send(channelName, userName, postMessage);
        assertTrue(postStatus);
    }

    @Test
    void postMessageToSlackChannelWithAttachment_relativePathTest() {
        boolean postStatus = postSlack.sendWithFile(channelName, fileTitle, fileComment, filePath);
        assertTrue(postStatus);
    }

    @Test
    void postMessageToSlackChannelWithOnlyAttachFileAndChannelNameTest() {
        boolean postStatus = postSlack.sendWithFile(channelName, "", "", filePath);
        assertTrue(postStatus);
    }

}
