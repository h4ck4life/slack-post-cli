import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.request.files.FilesUploadRequest;
import com.github.seratch.jslack.api.methods.response.files.FilesUploadResponse;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;

import java.io.File;
import java.util.Arrays;

class PostSlack {

    private Slack slack;

    PostSlack(Slack slack) {
        this.slack = slack;
    }

    /**
     * Post a text message to slack channel
     * @param channelName Slack channel name
     * @param userName Username to use to post with
     * @param postMessage Message to post
     * @return boolean true or false
     */
    boolean send(String channelName, String userName, String postMessage) {
        try {
            String url = System.getenv("SLACK_WEBHOOK_URL");
            Payload payload = Payload.builder()
                    .channel("#" + channelName)
                    .username(userName)
                    .text(postMessage)
                    .build();

            WebhookResponse response = slack.send(url, payload);
            return null != response && response.getCode().equals(200);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Post a text message to slack with file upload
     * @param channelName Slack channel name
     * @param fileTitle Title of the file
     * @param fileComment Comment of the file
     * @param filePath File path
     * @return boolean true or false
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
