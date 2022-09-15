package com.trype.core.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OutlinedTextFieldNoPadding(value: String,
                               onValueChange: (String) -> Unit,
                               paddingValues: PaddingValues,
                               modifier: Modifier = Modifier,
                               enabled: Boolean = true,
                               readOnly: Boolean = false,
                               textStyle: TextStyle = LocalTextStyle.current,
                               label: @Composable (() -> Unit)? = null,
                               placeholder: @Composable (() -> Unit)? = null,
                               leadingIcon: @Composable (() -> Unit)? = null,
                               trailingIcon: @Composable (() -> Unit)? = null,
                               isError: Boolean = false,
                               visualTransformation: VisualTransformation = VisualTransformation.None,
                               keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
                               keyboardActions: KeyboardActions = KeyboardActions.Default,
                               singleLine: Boolean = false,
                               maxLines: Int = Int.MAX_VALUE,
                               interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
                               shape: Shape = RoundedCornerShape(8.dp),
                               colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors()){
    // If color is not provided via the text style, use content color as a default
    val textColor = textStyle.color.takeOrElse {
        colors.textColor(enabled).value
    }
    BasicTextField(
        value = value,
        modifier = modifier
            .background(colors.backgroundColor(enabled).value, shape)
            .padding(paddingValues),
        onValueChange = {},
        enabled = false,
        readOnly = true,
        singleLine = true,
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.OutlinedTextFieldDecorationBox(
                value = value,
                visualTransformation = visualTransformation,
                innerTextField = innerTextField,
                placeholder = placeholder,
                label = label,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                singleLine = true,
                enabled = false,
                isError = isError,
                interactionSource = interactionSource,
                colors = colors,
                contentPadding = PaddingValues(0.dp),
                border = {
                    TextFieldDefaults.BorderBox(
                        enabled,
                        isError,
                        interactionSource,
                        colors,
                        shape
                    )
                }
            )
        }
    )
}