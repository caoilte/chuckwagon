package com.itv.aws.events

import com.itv.aws.{ARN, AWSService}
import com.amazonaws.services.cloudwatchevents.model.{PutRuleRequest => AWSPutRuleRequest}


case class PutRuleRequest(eventRule: EventRule)
case class PutRuleResponse(createdEventRule: CreatedEventRule)

object AWSPutRule extends AWSService[PutRuleRequest, PutRuleResponse] {
  override def apply(putRuleRequest: PutRuleRequest): PutRuleResponse = {
    import putRuleRequest._

    val awsPutRuleRequest = new AWSPutRuleRequest()
      .withName(eventRule.name.value)
      .withScheduleExpression(eventRule.scheduleExpression.value)
      .withDescription(eventRule.description)

    val ruleResponse = events.putRule(awsPutRuleRequest)
    PutRuleResponse(
      CreatedEventRule(eventRule, ARN(ruleResponse.getRuleArn))
    )
  }
}