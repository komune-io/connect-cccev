package cccev.f2.request.client

import cccev.f2.request.domain.command.RequestAddEvidenceFunction
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import f2.client.F2Client
import f2.client.ktor.F2ClientBuilder
import f2.client.ktor.get
import f2.client.ktor.http.HttpF2Client
import f2.dsl.fnc.F2Function
import f2.dsl.fnc.F2SupplierSingle
import f2.dsl.fnc.f2SupplierSingle
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormPart
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.content.PartData
import kotlinx.coroutines.flow.map

actual fun F2Client.requestClient(): F2SupplierSingle<RequestClient> = f2SupplierSingle {
    RequestClient(this)
}

actual fun requestClient(
    urlBase: String
): F2SupplierSingle<RequestClient> = f2SupplierSingle {
    RequestClient(
        F2ClientBuilder.get(urlBase)
    )
}

fun RequestClient.requestAddEvidence(): RequestAddEvidenceFunction = F2Function { msgs ->
    msgs.map { (cmd, file) ->
    val httpF2Client = (client as HttpF2Client)
    httpF2Client.httpClient.submitFormWithBinaryData(
        url = "${httpF2Client.urlBase}/requestAddEvidence",
        formData = FormDataBodyBuilder().apply {
            param("command", cmd)
            file("file", file, cmd.name)
        }.toFormData()
    ).body()
    }
}

class FormDataBodyBuilder {
    private val formParts = mutableListOf<FormPart<*>>()

    fun toFormData() = formData { formParts.forEach { append(it) } }

    fun param(key: String, value: String, contentType: String? = null) {
        val headers = contentType
            ?.let { Headers.build { append(HttpHeaders.ContentType, contentType) } }
            ?: Headers.Empty

        FormPart(
            key = key,
            value = value,
            headers = headers
        ).let(formParts::add)
    }

    fun <T> param(key: String, value: T) {
        param(key, value.toJson(), "application/json")
    }

    fun file(key: String, file: ByteArray, filename: String) {
        FormPart(
            key = key,
            value = file,
            headers = Headers.build { append(HttpHeaders.ContentDisposition, "filename=$filename") }
        ).let(formParts::add)
    }

    private fun <T> T.toJson(): String = jacksonObjectMapper()
        .writeValueAsString(this)
}
