package airhacks.detonator.cloudformation.boundary;

import airhacks.detonator.dialog.control.Dialog;
import airhacks.detonator.log.boundary.Logger;
import software.amazon.awssdk.services.cloudformation.CloudFormationClient;
import software.amazon.awssdk.services.cloudformation.model.DeleteStackRequest;
import software.amazon.awssdk.services.cloudformation.model.ListStacksRequest;
import software.amazon.awssdk.services.cloudformation.model.StackStatus;
import software.amazon.awssdk.services.cloudformation.model.StackSummary;

public interface CloudFormationStacks {

   static void removeAllStacks() {

      try (var client = CloudFormationClient.create()) {

         var request = ListStacksRequest
               .builder()
               .stackStatusFilters(StackStatus.CREATE_COMPLETE, StackStatus.UPDATE_COMPLETE)
               .build();
         var listStacksResponse = client.listStacks(request);
         listStacksResponse
               .stackSummaries()
               .stream()
               .filter(CloudFormationStacks::notCDK)
               .peek(CloudFormationStacks::info)
               .forEach(summary -> deleteStack(client, summary));

      }
   }

   private static void deleteStack(CloudFormationClient client, StackSummary stackSummary) {
      var stackName = stackSummary.stackName();
      var deleteRequest = DeleteStackRequest
            .builder()
            .stackName(stackName)
            .build();
      if (Dialog.proceed(stackName)) {
         Logger.info("deleting stack:");
         info(stackSummary);
         client.deleteStack(deleteRequest);
      }else{
         Logger.info("skipping");
      }

   }

   private static boolean notCDK(StackSummary summary) {
      var stackName = summary.stackName();
      return !stackName.equalsIgnoreCase("CDKToolkit");
   }

   private static void info(StackSummary summary) {
      var message = "created: %s, updated: %s, name: ".formatted(summary.creationTime(),
            summary.lastUpdatedTime(), summary.stackName());
      Logger.info(message);
   }
}
