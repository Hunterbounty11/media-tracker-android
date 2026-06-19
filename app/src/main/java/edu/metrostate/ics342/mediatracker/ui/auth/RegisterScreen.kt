package edu.metrostate.ics342.mediatracker.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.metrostate.ics342.mediatracker.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ColorFilter
import edu.metrostate.ics342.mediatracker.data.UserRepository
import edu.metrostate.ics342.mediatracker.theme.OnPrimaryContainer
import edu.metrostate.ics342.mediatracker.theme.PrimaryContainer
import androidx.compose.runtime.LaunchedEffect

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: RegisterViewModel = viewModel()
) {
    val displayName     by viewModel.displayName.collectAsState()
    val email           by viewModel.email.collectAsState()
    val username        by viewModel.username.collectAsState()
    val password        by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val registerState    by viewModel.registerState.collectAsState()
    val focusManager = LocalFocusManager.current

    val isLoading = registerState is RegisterViewModel.RegisterUiState.Loading

    // React to state changes: navigate on success, nothing fancy needed for error
    // since we just read the message below.
    LaunchedEffect(registerState) {
        if (registerState is RegisterViewModel.RegisterUiState.Success) {
            onRegisterSuccess()
            viewModel.resetRegisterState()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement   = Arrangement.Center,
        horizontalAlignment   = Alignment.CenterHorizontally
    ) {
        // ... existing Image/Text/Spacer code unchanged ...

        OutlinedTextField(
            value         = displayName,
            onValueChange = viewModel::onDisplayNameChange,
            label         = { Text(stringResource(R.string.name_label))},
            singleLine    = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction    = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(
            value         = username,
            onValueChange = viewModel::onUsernameChange,
            label         = { Text(stringResource(R.string.username_label))},
            singleLine    = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction    = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(
            value         = email,
            onValueChange = viewModel::onEmailChange,
            label         = { Text(stringResource(R.string.email_label))},
            singleLine    = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction    = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(
            value         = password,
            onValueChange = viewModel::onPasswordChange,
            label         = { Text(stringResource(R.string.password_label))},
            singleLine    = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction    = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(
            value         = confirmPassword,
            onValueChange = viewModel::onConfirmPasswordChange,
            label         = { Text(stringResource(R.string.confirm_password_label))},
            singleLine    = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction    = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Show error message if present
        val errorState = registerState as? RegisterViewModel.RegisterUiState.Error
        if (errorState != null) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(errorState.msgResId),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(16.dp))
        Button(
            onClick  = { focusManager.clearFocus(); viewModel.onRegisterClick() },
            enabled  = !isLoading,
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(stringResource(R.string.register_button))
            }
        }

        Spacer(Modifier.height(16.dp))
        TextButton(onClick = onNavigateToLogin) {
            Text(stringResource(R.string.login_prompt))
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview(){
    RegisterScreen(
            onRegisterSuccess = {},
            onNavigateToLogin = {},

    )
}