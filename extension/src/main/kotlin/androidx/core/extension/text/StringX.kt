package androidx.core.extension.text

import android.os.Build
import android.text.Html
import androidx.core.extension.compatible.fromHtmlCompatible
import androidx.core.extension.util.CHINESE2_REGEX
import androidx.core.extension.util.CHINESE_REGEX
import androidx.core.extension.util.DATE_REGEX
import androidx.core.extension.util.EMAIL2_REGEX
import androidx.core.extension.util.EMAIL3_REGEX
import androidx.core.extension.util.EMAIL_REGEX
import androidx.core.extension.util.ID_CARD15_REGEX
import androidx.core.extension.util.ID_CARD18_REGEX
import androidx.core.extension.util.IP_REGEX
import androidx.core.extension.util.MOBILE2_EXACT_REGEX
import androidx.core.extension.util.MOBILE_EXACT_REGEX
import androidx.core.extension.util.MOBILE_SIMPLE_REGEX
import androidx.core.extension.util.NUM_REGEX
import androidx.core.extension.util.PWD_REGEX
import androidx.core.extension.util.TE_REGEX
import androidx.core.extension.util.URL2_REGEX
import androidx.core.extension.util.URL_REGEX
import androidx.core.extension.util.USERNAME_REGEX
import java.util.regex.Pattern

val String.checkPhoneSimple: Boolean
    get() = matches(Regex(MOBILE_SIMPLE_REGEX))

val String.checkPhoneExact: Boolean
    get() = matches(Regex(MOBILE_EXACT_REGEX))

val String.checkPhoneExact2: Boolean
    get() = matches(Regex(MOBILE2_EXACT_REGEX))

val String.checkTe: Boolean
    get() = matches(Regex(TE_REGEX))

val String.checkIdCard15: Boolean
    get() = matches(Regex(ID_CARD15_REGEX))

val String.checkIdCard18: Boolean
    get() = matches(Regex(ID_CARD18_REGEX))

val String.checkEmail: Boolean
    get() = matches(Regex(EMAIL_REGEX))

val String.checkEmail2: Boolean
    get() = matches(Regex(EMAIL2_REGEX))

val String.checkEmail3: Boolean
    get() = matches(Regex(EMAIL3_REGEX))

val String.checkUrl: Boolean
    get() = matches(Regex(URL_REGEX))

val String.checkUrl2: Boolean
    get() = matches(Regex(URL2_REGEX))

val String.checkChinese: Boolean
    get() = matches(Regex(CHINESE2_REGEX))

val String.checkChinese2: Boolean
    get() = matches(Regex(CHINESE_REGEX))

val String.checkUserName: Boolean
    get() = matches(Regex(USERNAME_REGEX))

val String.checkDate: Boolean
    get() = matches(Regex(DATE_REGEX))

val String.checkIp: Boolean
    get() = matches(Regex(IP_REGEX))

val String.checkNum: Boolean
    get() = matches(Regex(NUM_REGEX))

val String.checkPwdRegex: Boolean
    get() = matches(Regex(PWD_REGEX))

val utf8Coding: String
    get() = "utf-8"

val textHtmlMimeType: String
    get() = "text/html"

val String.int: Int
    get() = Pattern.compile("[^0-9]").matcher(this).replaceAll("").trim { it <= ' ' }.toInt()

val String.convertToSnakeCase: String
    get() = replace(Regex("([a-z])([A-Z])"), "$1_$2").lowercase()

fun String.fromHtml(flag: Int? = null): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        Html.fromHtml(this, flag ?: Html.FROM_HTML_MODE_LEGACY).toString()
    else fromHtmlCompatible()
}