package com.example.passforwork.features.registration.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passforwork.core.base.network.ApiService
import com.example.passforwork.features.registration.data.RegData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class RegistrationViewModel(private val service: ApiService) : ViewModel() {

    private val cyrillic: Pattern = Pattern.compile("[-А-яЁё `']+")

    private var objectId: Int? = null
    val fieldNameValidationState = MutableStateFlow<ValidationState>(ValidationState.Empty)
    val fieldNumberValidationState = MutableStateFlow<ValidationState>(ValidationState.Empty)
    val fieldPatronymicValidationState = MutableStateFlow<ValidationState>(ValidationState.Empty)
    val fieldSurnameValidationState = MutableStateFlow<ValidationState>(ValidationState.Empty)
    val objectListValidationState = MutableStateFlow<List<ObjectItem>>(emptyList())
    val objectIdFlow = MutableStateFlow<String?>("Выбрать объект")

    init {
        getObjects()
    }

    private fun getObjects() {
        viewModelScope.launch {
            delay(500)
            objectListValidationState.value = listOf(
                ObjectItem(1, "Moskva") { selectObjectId(1, "Moskva") },
                ObjectItem(2, "Piter") { selectObjectId(2, "Piter") },
                ObjectItem(3, "Voronezh") { selectObjectId(3, "Voronezh") }
            )
        }
    }

    private fun selectObjectId(id: Int, name: String) {
        objectId = id
        objectIdFlow.value = name
    }

    fun validateRegData(
        name: String?,
        surname: String?,
        patronymic: String? = "",
        phoneNumber: String?
    ) {

        if (validateFieldIsNotOkay(surname, FieldName.ErrorSurname)) return
        if (validateFieldIsNotOkay(name, FieldName.ErrorName)) return
        if (validateFieldIsNotOkay(patronymic, FieldName.ErrorPatronymic)) return
        if (validateFieldIsNotOkay(phoneNumber, FieldName.ErrorNumber)) return
        if (objectId == null) {
            objectIdFlow.value = null
            return
        }

        viewModelScope.launch {
//            TODO
            try {
                service.createReg(
                    RegData(
                        fullName = "$surname $name $patronymic",
                        phoneNumber = phoneNumber!!,
                        objectId = objectId!!,
                        position = "TODO",
                    )
                )
            } catch (e: Exception) {
                //TODO показывать диалог с ошибкой
            }

        }
    }

    private fun validateFieldIsNotOkay(
        text: String?,
        fieldError: FieldName
    ): Boolean {
        val errorText = returnTextOfError(text, fieldError)
        val fieldState = when (errorText) {
            null -> ValidationState.Success
            else -> ValidationState.Error(errorText)
        }

        when (fieldError) {
            FieldName.ErrorName -> fieldNameValidationState.value = fieldState
            FieldName.ErrorNumber -> fieldNumberValidationState.value = fieldState
            FieldName.ErrorPatronymic -> fieldPatronymicValidationState.value = fieldState
            FieldName.ErrorSurname -> fieldSurnameValidationState.value = fieldState
        }
        return errorText != null
    }

    private fun returnTextOfError(text: String?, fieldError: FieldName): String? {
        when (fieldError) {
            FieldName.ErrorSurname -> {
                if (text.isNullOrEmpty()) return "Заполните поле"
                if (!cyrillic.matcher(text)
                        .matches()
                ) return "Поле может содержать только русские буквы, пробел или апостроф"
            }
            FieldName.ErrorName -> {
                if (text.isNullOrEmpty()) return "Заполните поле"
                if (!cyrillic.matcher(text)
                        .matches()
                ) return "Поле может содержать только русские буквы, пробел или апостроф"
            }
            FieldName.ErrorPatronymic -> {
                if (!text.isNullOrEmpty() && !cyrillic.matcher(text).matches())
                    return "Поле может содержать только русские буквы, пробел или апостроф"
            }
            FieldName.ErrorNumber -> {
                val phone = text?.deleteExtraSymbols()
                if (phone.isNullOrEmpty() || phone.length < 11) return "Заполните поле"
            }
        }
        return null
    }

    private fun String?.deleteExtraSymbols() = this?.replace("+", "")
        ?.replace(" ", "")
        ?.replace(".", "")
        ?.replace(",", "")
        ?.replace("/", "")
        ?.replace("(", "")
        ?.replace(")", "")
        ?.replace("-", "") ?: ""


    sealed class FieldName {
        object ErrorNumber : FieldName()
        object ErrorName : FieldName()
        object ErrorSurname : FieldName()
        object ErrorPatronymic : FieldName()
    }

    sealed class ValidationState {
        object Success : ValidationState()
        class Error(val errorText: String) : ValidationState()
        object Empty : ValidationState()
    }

    data class ObjectItem(
        val objectId: Int,
        val objectString: String,
        val onClick: () -> Unit
    )


}