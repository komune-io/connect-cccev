package cccev.infra.fs

import io.komune.fs.s2.file.client.FileClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(FsProperties::class)
class FsConfig {

    @Bean
    fun fsClient(properties: FsProperties): FileClient {
        val tokenFunction = properties.auth?.generateTokenFunction()
        return FileClient(properties.url) {
            tokenFunction?.let {
                Auth {
                    bearer {
                        this.loadTokens {
                            val token = tokenFunction()
                            BearerTokens(token.access_token, token.refresh_token ?: "")
                        }
                    }
                }
            }
        }
    }

    fun AuthProperties.generateTokenFunction(): suspend () -> AccessToken = {
        val url = "${url}/realms/${realm}/protocol/openid-connect/token"
        val result: AccessToken = HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }.use { client ->
            client.post(url) {
                contentType(ContentType.Application.FormUrlEncoded)
                setBody(
                    "grant_type=client_credentials&client_id=${clientId}&client_secret=${clientSecret}"
                )
            }.body()
        }
        result
    }


    @SuppressWarnings("ConstructorParameterNaming")
    @Serializable
    data class AccessToken(
        val access_token: String,
        val refresh_token: String? = null,
        val expires_in: Int,
        val refresh_expires_in: Int,
        val token_type: String,
        val scope: String
    )

}
