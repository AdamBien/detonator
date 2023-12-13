package airhacks.detonator.cloudwatch.boundary;

import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;
import software.amazon.awssdk.services.cloudwatchlogs.model.DeleteLogGroupRequest;
import software.amazon.awssdk.services.cloudwatchlogs.model.LogGroup;

public interface LogGroups {

    static void removeAllLogGroups() {
        try(var client = CloudWatchLogsClient.create()){
            client
            .describeLogGroupsPaginator()
            .stream()
            .flatMap(response-> response.logGroups().stream())
            .map(LogGroup::logGroupName)
            .peek(System.out::println)
            .forEach(groupName -> LogGroups.delete(client,groupName));

        }
    }
    
    static void delete(CloudWatchLogsClient client,String logGroupName){
        var deleteRequest = DeleteLogGroupRequest.builder()
        .logGroupName(logGroupName)
        .build();
        client.deleteLogGroup(deleteRequest);
    }
}
