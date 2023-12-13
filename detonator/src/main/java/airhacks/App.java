package airhacks;

import airhacks.detonator.cloudformation.boundary.CloudFormationStacks;
import airhacks.detonator.cloudwatch.boundary.LogGroups;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.services.sts.StsClient;

/**
 *
 * @author airhacks.com
 */
interface App {
    String version = "detonator: v0.0.3, 14.12.2023";

    static void info(String message){
        System.out.println(message);
    }

    static void main(String... args) {
        info(version);
        //current accessKey
        var credentialsProvider = DefaultCredentialsProvider.builder().build();
        var accessKey = credentialsProvider.resolveCredentials().accessKeyId();
        System.out.println(accessKey);

        //who am I?
        var stsClient = StsClient.create();
        var response = stsClient.getCallerIdentity();
        var arn = response.arn();
        System.out.println(arn);
        CloudFormationStacks.removeAllStacks();
        LogGroups.removeAllLogGroups();
    }
}
