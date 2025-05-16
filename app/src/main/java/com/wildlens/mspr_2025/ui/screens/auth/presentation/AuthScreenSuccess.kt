package com.wildlens.mspr_2025.ui.screens.auth.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.wildlens.mspr_2025.R
import com.wildlens.mspr_2025.ui.screens.auth.state.AuthAction
import com.wildlens.mspr_2025.ui.screens.auth.state.AuthFormState
import com.wildlens.mspr_2025.ui.screens.auth.state.AuthMode

@Composable
fun AuthScreenSuccess(
    form: AuthFormState,
    onAction: (AuthAction) -> Unit
) {
    if(form.mode == AuthMode.REGISTER){

        OutlinedTextField(
            value = form.firstName,
            onValueChange = { onAction(AuthAction.FirstNameChanged(it)) },
            label = { Text(stringResource(R.string.first_name)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )

        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = form.lastName,
            onValueChange = { onAction(AuthAction.LastNameChanged(it)) },
            label = { Text(stringResource(R.string.last_name)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )
    }

    Spacer(Modifier.height(16.dp))

    OutlinedTextField(
        value = form.email,
        onValueChange = { onAction(AuthAction.EmailChanged(it)) },
        label = { Text(stringResource(R.string.email)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    )

    Spacer(Modifier.height(16.dp))

    OutlinedTextField(
        value = form.password,
        onValueChange = { onAction(AuthAction.PasswordChanged(it)) },
        label = { Text(stringResource(R.string.password)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        visualTransformation = if (form.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val icon = if (form.isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
            val description = if (form.isPasswordVisible)
                stringResource(R.string.hide_password)
            else
                stringResource(R.string.show_password)
            IconButton(onClick = { onAction(AuthAction.TogglePasswordVisibility) }) {
                Icon(imageVector = icon, contentDescription = description)
            }
        },
        singleLine = true
    )

    Spacer(Modifier.height(16.dp))

    Button(
        onClick = { onAction(AuthAction.Submit(form.mode)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    ) {
        Text(if (form.mode == AuthMode.LOGIN)
            stringResource(R.string.prompt_register)
        else
            stringResource(R.string.prompt_login)
        )
    }

    TextButton(
        onClick = {
            val newMode = if (form.mode == AuthMode.LOGIN) AuthMode.REGISTER else AuthMode.LOGIN
            onAction(AuthAction.SwitchMode(newMode))
        }
    ) {
        Text(if (form.mode == AuthMode.LOGIN)
            stringResource(R.string.prompt_register)
        else
            stringResource(R.string.prompt_login))

    }
}
