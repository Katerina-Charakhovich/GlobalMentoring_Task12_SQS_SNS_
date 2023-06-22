# GlobalMentoring_Task12_SQS_SNS_
Create demo applications that implement the following order processing flow. 

First application read the following order details from console and send it to the order queue (orders):

The user who makes the order;
Type of goods for the order – liquids or countable item;
The volume of order for liquids;
Number of items for countable items;
Order total.
Second application should process orders with the following rules:

If order total greater than some threshold – order should be rejected;
If already ordered more than N liters – the order should be rejected;
Summary information for accepted and rejected logs should be passed to other queues or topics.
Third application to log summary about accepted and rejected orders into some file.

Tasks to be implemented:

Implement the full flow described above – 2 points.
Use message selectors to split orders for liquids and countable items – 1 point.
Use topics to implement message exchange – 1 point.
Add trigger to S3 bucket that will send message to SQS that file was changed. - 1 point.
Extramile:

1 point:

Show transactions in message processing
1 point:

Implement a simple listener (spring boot application) that will send an email to the administrator about changes. 
When you are ready with the task attach it to the "Result" field and change the status for "Needs review"
