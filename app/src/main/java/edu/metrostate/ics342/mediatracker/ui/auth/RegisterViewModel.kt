package edu.metrostate.ics342.mediatracker.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.metrostate.ics342.mediatracker.data.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay

class RegisterViewModel(
    private val userRepository: UserRepository  = UserRepository(),
) : ViewModel() {

    sealed class RegisterUiState{
        object Idle: RegisterUiState()
        object Loading: RegisterUiState()
        object Success : RegisterUiState()
        data class Error(val msgResId: Int): RegisterUiState()
    }
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _displayName = MutableStateFlow("")
    val displayName = _displayName.asStateFlow()

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()


    fun setDisplayName(newValue: String) { _displayName.value = newValue }
    fun setUsername(newValue: String) { _username.value = newValue }
    fun setEmail(newValue: String) { _email.value = newValue }
    fun setPassword(newValue: String) { _password.value = newValue }
    fun setConfirmPassword(newValue: String) { _confirmPassword.value = newValue }

    private val _registerState = MutableStateFlow(value ="")
    val registerState = _registerState

    fun onSignUpClicked() {
        viewModelScope.launch {

            _registerState.value = RegisterUiState.Loading
            delay(1000)
            //Find out why register UI state is errors
            _registerState.value = when {
                _displayName.value.isBlank() ||
                _email.value.isBlank() ||
                        _username.value.isBlank() ||
                        _password.value.isBlank() ||
                        _confirmPassword.value.isBlank() ->
                    RegisterUiState.Error("Please fill in fields")
            }
            _password.value != _confirmPassword.value
            //TODO Password Check
            userRepository.createAccount(
                displayName = _displayName.value,
                username = _username.value,
                email = _email.value,
                password = _password.value
            )
        }
    }
}