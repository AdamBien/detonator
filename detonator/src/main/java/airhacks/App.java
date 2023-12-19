package airhacks;

import airhacks.detonator.cloudformation.boundary.CloudFormationStacks;
import airhacks.detonator.cloudwatch.boundary.LogGroups;
import airhacks.detonator.dialog.control.Dialog;
import airhacks.detonator.log.boundary.Logger;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.services.sts.StsClient;

/**
 *
 * @author airhacks.com
 */
interface App {
    String version = "detonator: v0.0.6, 19.12.2023";


    static void main(String... args) {
        Logger.info(version);
        //current accessKey
        var credentialsProvider = DefaultCredentialsProvider.builder().build();
        var accessKey = credentialsProvider.resolveCredentials().accessKeyId();
        Logger.info(accessKey);

        //who am I?
        var stsClient = StsClient.create();
        var response = stsClient.getCallerIdentity();
        var arn = response.arn();
        Logger.info(arn);
        CloudFormationStacks.removeAllStacks();
        if(Dialog.ask("Delete log groups")){
            LogGroups.removeAllLogGroups();
        }else{
            Logger.info("Log groups are not deleted");
        }

    }
}
