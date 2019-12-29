package mediaconvertexample

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.mediaconvert.model.{CreateJobRequest, CreateJobResult, JobSettings}
import com.amazonaws.services.mediaconvert.{AWSMediaConvert, AWSMediaConvertClient}
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.{DefaultScalaModule, ScalaObjectMapper}
import com.typesafe.scalalogging.Logger

import scala.io.Source

object MediaConvertExample {

  def main(args: Array[String]): Unit = {

    val logger = Logger[this.type]
    val bucketName: String = "<bucket_name>"
    val inputFileName: String = "<s3_input_file_path>"
    val outputFileName: String = "<s3_output_file_path>"

    // media converter endpoint is specific to the AWS account and never changes. get it from MediaConvert --> Account section
    val mediaConvertEndpoint: String = "https://xxxxxxxxx.mediaconvert.xx-xxxx-x.amazonaws.com"

    // this role will help grab input file from S3 and place output file back into bucket
    val roleForS3Access = "arn:aws:iam::<aws_account_num>:role/<role_name>"

    val awsRegion: String = mediaConvertEndpoint.split('.')(2)

    // uses environment variables AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY
    val mediaConvertClient: AWSMediaConvert = AWSMediaConvertClient.builder().withCredentials(new EnvironmentVariableCredentialsProvider).withEndpointConfiguration(new EndpointConfiguration(mediaConvertEndpoint, awsRegion)).build()
    val settingsFile: String = "src/main/resources/hd_1920x1080.json"

    val source: Source = Source.fromFile(settingsFile)
    val settingsAsString: String = source.mkString
    source.close()

    val mapper = new ObjectMapper() with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)
    val jobSettings = mapper.readValue[JobSettings](settingsAsString)

    jobSettings.getInputs.get(0).setFileInput("s3://" + bucketName + "/" + inputFileName)
    jobSettings.getOutputGroups.get(0).getOutputGroupSettings.getFileGroupSettings.setDestination("s3://" + bucketName + "/" + outputFileName)
    logger.info("Creating MediaConvert job with input file: " + inputFileName + ", output file: " + outputFileName + " using settings file at: " + settingsFile)
    val mediaConvertJobRequest: CreateJobRequest = new CreateJobRequest().withSettings(jobSettings).withRole(roleForS3Access)
    val mediaConvertJobResponse: CreateJobResult = mediaConvertClient.createJob(mediaConvertJobRequest)
    logger.info("MediaConvert job response is: " + mediaConvertJobResponse)


  }
}
