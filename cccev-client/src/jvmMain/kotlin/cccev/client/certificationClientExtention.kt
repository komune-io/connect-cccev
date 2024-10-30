package cccev.client

import cccev.f2.certification.command.CertificationAddEvidenceFunction
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import f2.client.ktor.http.HttpF2Client
import f2.dsl.fnc.F2Function
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormPart
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.map

fun CertificationClient.certificationAddEvidence(): CertificationAddEvidenceFunction = F2Function { messages ->
    messages.map { (file, cmd) ->
        val httpF2Client = (client as HttpF2Client)
        httpF2Client.httpClient.submitFormWithBinaryData(
            url = "${httpF2Client.urlBase}/certificationAddEvidence",
            formData = FormDataBodyBuilder().apply {
                param("command", cmd)
                file("file", file.byteArray, file.filename)
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
