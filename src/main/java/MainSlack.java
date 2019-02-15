import com.github.seratch.jslack.Slack;
import org.apache.commons.cli.*;

public class MainSlack {

    public static void main(String[] args) {

        Options options = new Options();
        options.addRequiredOption("m", "message", true, "Message to post");
        options.addRequiredOption("c", "channel", true, "Channel to post");
        options.addOption("f", "filepath", true, "(optional) Filepath to upload");

        if (args.length < 1) {
            printHelp(options);
            System.exit(-1);
        }

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);

            // Post only message
            if (cmd.hasOption("m") && cmd.hasOption("c") && cmd.hasOption("u")) {
                PostSlack postSlack = new PostSlack(Slack.getInstance());
                validateSendMessage(postSlack.send(cmd.getOptionValue("c"), "", cmd.getOptionValue("m")));
            }

            // Post message with file upload
            if (cmd.hasOption("m") && cmd.hasOption("c") && cmd.hasOption("u") && cmd.hasOption("f")) {
                PostSlack postSlack = new PostSlack(Slack.getInstance());
                validateSendMessage(postSlack.sendWithFile(cmd.getOptionValue("c"), "", "", cmd.getOptionValue("f")));
            }

        } catch (ParseException e) {
            printHelp(options);
        }
    }

    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar slack-cli-x.x.x.jar", "Make sure environment variables below configured:\nSLACK_WEBHOOK_URL=\nSLACK_TOKEN=\n\n", options, "\nMore information:\nhttps://github.com/h4ck4life/slack-post-cli");
    }

    private static void validateSendMessage(boolean sentStatus) {
        if (sentStatus) {
            System.out.println("Message successfully posted!");
            System.exit(0);
        } else {
            System.out.println("Oops, message failed to send. Please retry");
            System.exit(-1);
        }
    }
}
