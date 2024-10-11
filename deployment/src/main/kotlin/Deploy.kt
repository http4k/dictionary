import com.pulumi.Pulumi
import com.pulumi.asset.FileArchive
import com.pulumi.aws.iam.Role
import com.pulumi.aws.iam.RoleArgs
import com.pulumi.aws.iam.RolePolicyAttachment
import com.pulumi.aws.iam.RolePolicyAttachmentArgs
import com.pulumi.aws.lambda.Function
import com.pulumi.aws.lambda.FunctionArgs
import com.pulumi.aws.lambda.FunctionUrl
import com.pulumi.aws.lambda.FunctionUrlArgs

fun main() {
    Pulumi.run { context ->
        val lambdaRole = Role(
            "dictionary-lambda-role", RoleArgs
                .builder()
                .assumeRolePolicy(
                    """
                {
                    "Version": "2012-10-17",
                    "Statement": [
                        {
                            "Action": "sts:AssumeRole",
                            "Principal": {
                                "Service": "lambda.amazonaws.com"
                            },
                            "Effect": "Allow",
                            "Sid": ""
                        }
                    ]
                }
                """.trimIndent()
                ).build()
        )

        RolePolicyAttachment(
            "dictionary-lambda-role-attachment", RolePolicyAttachmentArgs
                .builder()
                .role(lambdaRole.name())
                .policyArn("arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole")
                .build()
        )

        val function = Function(
            "dictionary-function", FunctionArgs
                .builder()
                .code(FileArchive("dictionary-function/build/distributions/dictionary-function.zip"))
                .handler("org.http4k.dictionary.DictionaryApp")
                .role(lambdaRole.arn())
                .runtime("java11")
                .timeout(15)
                .build()
        )

        val functionUrl = FunctionUrl(
            "dictionary-function-url", FunctionUrlArgs
                .builder()
                .functionName(function.name())
                .authorizationType("NONE")
                .build()
        )

        val publicUrl = functionUrl.functionUrl()

        context.export("publishedUrl", publicUrl)
    };
}
