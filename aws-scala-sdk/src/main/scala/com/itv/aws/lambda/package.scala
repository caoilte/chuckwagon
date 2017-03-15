package com.itv.aws

import com.amazonaws.services.lambda.{AWSLambda, AWSLambdaClientBuilder}
import com.itv.aws.ec2.{Filter, SecurityGroup, Subnet, VPC}

import scala.concurrent.duration._

package lambda {

  import com.itv.aws.iam.ARN

  case class LambdaName(value: String) extends AnyVal

  case class LambdaVersion(value: Int) extends AnyVal

  case class LambdaHandler(value: String) extends AnyVal

  case class MemorySize(value: Int) extends AnyVal

  case class AliasName(value: String) extends AnyVal

  case class Alias(name: AliasName, lambdaName: LambdaName, lambdaVersion: LambdaVersion, arn: ARN) {
    val derivedId = s"${lambdaName.value}-${name.value}"
  }

  case class LambdaRuntimeConfiguration(handler: LambdaHandler, timeout: FiniteDuration, memorySize: MemorySize) {

    require(
      timeout > 0.seconds && timeout <= 300.seconds,
      "Lambda timeout must be between 1 and 300 seconds"
    )

    require(
      memorySize.value >= 128 && memorySize.value <= 1536,
      "Lambda memory must be between 128 and 1536 MBs"
    )

  }
  case class LambdaDeploymentConfiguration(
      name: LambdaName,
      roleARN: ARN,
      vpcConfig: Option[VpcConfig]
  )

  case class Lambda(deployment: LambdaDeploymentConfiguration, runtime: LambdaRuntimeConfiguration)
  case class PublishedLambda(lambda: Lambda, version: LambdaVersion, arn: ARN)

  case class DownloadableLambdaLocation(value: String) extends AnyVal
  case class DownloadablePublishedLambda(publishedLambda: PublishedLambda,
                                         downloadableLocation: DownloadableLambdaLocation)

  case class VpcConfigDeclaration(
      vpcLookupFilters: List[Filter],
      subnetsLookupFilters: List[Filter],
      securityGroupsLookupFilters: List[Filter]
  )

  case class VpcConfig(vpc: VPC, subnets: List[Subnet], securityGroups: List[SecurityGroup])

  case class PermissionAction(value: String)            extends AnyVal
  case class PermissionPrincipialService(value: String) extends AnyVal
  case class PermissionStatementId(value: String)       extends AnyVal
  case class LambdaPermission(statementId: PermissionStatementId,
                              sourceARN: ARN,
                              action: PermissionAction,
                              principalService: PermissionPrincipialService,
                              targetLambdaARN: ARN)
}

package object lambda {
  def lambda: AwsClientBuilder[AWSLambda] = configuredClientForRegion(AWSLambdaClientBuilder.standard())

}
