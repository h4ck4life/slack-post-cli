import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.request.files.FilesUploadRequest;
import com.github.seratch.jslack.api.methods.response.files.FilesUploadResponse;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;

import java.io.File;
import java.util.Arrays;

public class PostSlack {

    Slack slack = Slack.getInstance();

    public boolean send(String channelName, String userName, String postMessage) {
        try {
            //String url = System.getenv("SLACK_WEBHOOK_URL");
            String url = "https://hooks.slack.com/services/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";

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

    public boolean send(String channelName, String fileTitle, String fileComment, String filePath) {
        try {
            File file = new File(filePath);

            if(file.exists()) {
                FilesUploadResponse response = slack.methods().filesUpload(FilesUploadRequest.builder()
                        .token("xoxp-546868584132-547232425893-548450944070-5e709a3611c41864cbe7fae1eacedc80")
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
