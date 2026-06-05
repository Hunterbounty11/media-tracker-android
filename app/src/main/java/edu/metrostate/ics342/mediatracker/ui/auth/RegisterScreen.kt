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
import androidx.compose.ui.graphics.ColorFilter
import edu.metrostate.ics342.mediatracker.theme.OnPrimaryContainer
import edu.metrostate.ics342.mediatracker.theme.PrimaryContainer

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    val newName =""
    val loginState  by viewModel.loginState.collectAsState()
    val focusManager = LocalFocusManager.current
    val isLoading = loginState is AuthViewModel.AuthUiState.Loading
    val errorMsg  = (loginState as? AuthViewModel.AuthUiState.Error)?.msgResId?.let { stringResource(it) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement   = Arrangement.Center,
        horizontalAlignment   = Alignment.CenterHorizontally
    ) {
        Image(painterResource(id=R.drawable.smart_display), contentDescription = "Application Icon",
            modifier = Modifier.size(width = 64.dp, height = 64.dp)
                .background(color= PrimaryContainer, RoundedCornerShape(size = 12.dp))
                .padding(all=12.dp),
            colorFilter = ColorFilter.tint(color= OnPrimaryContainer))
        Text(stringResource(R.string.registration_label), style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(8.dp))
        Text(stringResource(R.string.registration_tagline),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center)
        Spacer(Modifier.height(40.dp))
        OutlinedTextField(
            value         = newName,
            onValueChange = viewModel::onEmailChange,
            label         = { Text(stringResource(R.string.name_label))},
            singleLine    = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction    = ImeAction.Next
            ),

            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(
            value         = newName,
            onValueChange = viewModel::onEmailChange,
            label         = { Text(stringResource(R.string.username_label))},
            singleLine    = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction    = ImeAction.Next
            ),

            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(
            value         = newName,
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
            value         = newName,
            onValueChange = viewModel::onEmailChange,
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
            value         = newName,
            onValueChange = viewModel::onEmailChange,
            label         = { Text(stringResource(R.string.confirm_password_label))},
            singleLine    = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction    = ImeAction.Next
            ),

            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick  = { focusManager.clearFocus(); viewModel.onLoginClick() },
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
                Text(stringResource(edu.metrostate.ics342.mediatracker.R.string.register_button))
            }
        }

        Spacer(Modifier.height(16.dp))
        TextButton(onClick = onNavigateToLogin) {
            Text(stringResource(edu.metrostate.ics342.mediatracker.R.string.login_prompt))
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