package airhacks.detonator.cloudformation.boundary;

import software.amazon.awssdk.services.cloudformation.CloudFormationClient;
import software.amazon.awssdk.services.cloudformation.model.DeleteStackRequest;
import software.amazon.awssdk.services.cloudformation.model.ListStacksRequest;
import software.amazon.awssdk.services.cloudformation.model.StackStatus;
import software.amazon.awssdk.services.cloudformation.model.StackSummary;

public interface CloudFormationStacks {
   private static boolean proceed(String stackName) {
      var line = System
            .console()
            .readLine("delete %s?: ".formatted(stackName));
      return line.equalsIgnoreCase("y");
   }

   static void removeAllStacks() {

      try (var client = CloudFormationClient.create()) {

         var request = ListStacksRequest
               .builder()
               .stackStatusFilters(StackStatus.CREATE_COMPLETE, StackStatus.UPDATE_COMPLETE)
               .build();
         var listStacksResponse = client.listStacks(request);
         var stackSummaries = listStacksResponse.stackSummaries()
               .stream()
               .filter(CloudFormationStacks::notCDK)
               .toList();

      }
   }

   private static void deleteStack(CloudFormationClient client, StackSummary stackSummary) {
      var stackName = stackSummary.stackName();
      var deleteRequest = DeleteStackRequest
            .builder()
            .stackName(stackName)
            .build();
      if (proceed(stackName)) {
         info("deleting stack:");
         info(stackSummary);
         client.deleteStack(deleteRequest);
      }

   }

   private static boolean notCDK(StackSummary summary) {
      var stackName = summary.stackName();
      return !stackName.equalsIgnoreCase("CDKToolkit");
   }

   private static void info(StackSummary summary) {
      var message = "created: %s, updated: %s, name: ".formatted(summary.creationTime(),
            summary.lastUpdatedTime(), summary.stackName());
      info(message);
   }

   private static void info(String info) {
      System.out.println(info);
   }
}
