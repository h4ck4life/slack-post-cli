import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.request.files.FilesUploadRequest;
import com.github.seratch.jslack.api.methods.response.files.FilesUploadResponse;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;

import java.io.File;
import java.util.Arrays;

public class PostSlack {

    Slack slack = Slack.getInstance();

    /**
     * Post a text message to slack channel
     * @param channelName
     * @param userName
     * @param postMessage
     * @return
     */
    public boolean send(String channelName, String userName, String postMessage) {
        try {
            String url = System.getenv("SLACK_WEBHOOK_URL");
            Payload payload = Payload.builder()
                    .channel("#" + channelName)
                    .username(userName)
                    .text(postMessage)
                    .build();

            WebhookResponse response = slack.send(url, payload);
            return response.getCode().equals(200) ? true : false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Post a text message to slack with file upload
     * @param channelName
     * @param fileTitle
     * @param fileComment
     * @param filePath
     * @return
     */
    public boolean sendWithFile(String channelName, String fileTitle, String fileComment, String filePath) {
        try {
            File file = new File(filePath);
            String token = System.getenv("SLACK_TOKEN");
            if(file.exists()) {
                FilesUploadResponse response = slack.methods().filesUpload(FilesUploadRequest.builder()
                        .token(token)
                        .channels(Arrays.asList("#" + channelName))
                        .file(file)
                        .filename(file.getName())
                        .initialComment(fileComment)
                        .title(fileTitle)
                        .build());
                return response.isOk() ? true : false;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
