package com.itv.aws

import com.amazonaws.services.ec2.{AmazonEC2, AmazonEC2ClientBuilder}

package object ec2 {

  val ec2: AmazonEC2 =
    AmazonEC2ClientBuilder
      .standard()
      .withCredentials(provider)
      .build()
}

package ec2 {
  case class VPC(id: String) extends AnyVal
  case class Filter(key: String, value: String)

  case class SecurityGroup(id: String) extends AnyVal


  case class Subnet(id: String) extends AnyVal
}