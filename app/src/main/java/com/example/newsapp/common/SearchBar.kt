package com.example.newsapp.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    text: String,
    readOnly: Boolean = false,
    onSearch: () -> Unit,
    onClick: (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
    onResetQuery: (() -> Unit)? = null
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }


    val isClicked = interactionSource.collectIsPressedAsState().value
    LaunchedEffect(isClicked) {
        if (isClicked) {
            onClick?.invoke()
        }
    }
    Box(modifier = modifier) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(16.dp)),
            value = text,
            label = {
                Text(
                    text = "Search",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            },
            readOnly = readOnly,
            onValueChange = { onValueChange(it) },
            leadingIcon = {
                IconButton(
                    modifier = Modifier.size(20.dp),
                    onClick = onSearch
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search"
                    )
                }

            },
            trailingIcon = {
                IconButton(
                    modifier = Modifier.size(20.dp),
                    onClick = {
                        onResetQuery?.invoke()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Clear"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch()
                }
            ),
            singleLine = true,
            interactionSource = interactionSource

        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}