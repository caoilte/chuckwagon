package com.itv.aws

import com.amazonaws.regions.Regions
import com.amazonaws.services.securitytoken.{AWSSecurityTokenService, AWSSecurityTokenServiceClientBuilder}

package object sts {

  def sts(region: Regions): AWSSecurityTokenService = {
    AWSSecurityTokenServiceClientBuilder.standard().withRegion(region).build
  }

}

package sts {

  import com.amazonaws.auth.{AWSCredentials, BasicSessionCredentials}

  case class AssumeRoleSessionName(value: String) extends AnyVal

  case class AccessKeyId(value: String)     extends AnyVal
  case class SecretAccessKey(value: String) extends AnyVal
  case class SessionToken(value: String)    extends AnyVal

  case class Credentials(accessKeyId: AccessKeyId, secretAccessKey: SecretAccessKey, sessionToken: SessionToken) {

    val awsCredentials: AWSCredentials = {
      new BasicSessionCredentials(
        accessKeyId.value,
        secretAccessKey.value,
        sessionToken.value
      )
    }
  }
}