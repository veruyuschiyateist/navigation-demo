package com.plko.bls.app.ui.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.plko.bls.app.R
import com.plko.bls.app.model.DuplicateException
import com.plko.bls.app.model.LoadDataException
import com.plko.bls.app.model.LoadResult

@Composable
fun <T> LoadResultContent(
    loadResult: LoadResult<T>,
    content: @Composable (T) -> Unit,
    modifier: Modifier = Modifier,
    onTryAgainAction: () -> Unit = {},
    exceptionToMessageMapper: ExceptionToMessageMapper = ExceptionToMessageMapper.Default
) {
    when (loadResult) {
        LoadResult.Loading ->
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

        is LoadResult.Success -> content(loadResult.data)
        is LoadResult.Error -> {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = exceptionToMessageMapper.getUserMessage(
                        exception = loadResult.exception,
                        context = LocalContext.current
                    ),
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = onTryAgainAction
                ) {
                    Text(text = "Try again!")
                }
            }
        }
    }
}

fun interface ExceptionToMessageMapper {
    fun getUserMessage(exception: Exception, context: Context): String

    companion object {
        val Default = ExceptionToMessageMapper { exception, context ->
            when (exception) {
                is LoadDataException -> context.getString(R.string.failed_to_load_data)
                is DuplicateException -> context.getString(
                    R.string.already_exists_in_the_list,
                    exception.duplicatedValue
                )

                else -> context.getString(R.string.unknown_error_please_try_again_later)
            }
        }
    }
}