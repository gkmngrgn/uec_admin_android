package com.alageek.ueca.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alageek.ueca.R
import java.util.*

private const val TAG = "MainActivity"


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                Column(
                    modifier = Modifier
                        .weight(8f)
                        .absolutePadding(top = 16.dp, right = 16.dp, bottom = 0.dp, left = 16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AddCardView(text = stringResource(id = R.string.button_add_time), onClick = {
                        Log.i(TAG, "BUTTON 1")
                    })
                    AddCardView(
                        text = stringResource(id = R.string.button_add_description),
                        onClick = {
                            Log.i(TAG, "BUTTON 2")
                        })
                    AddCardView(text = stringResource(id = R.string.button_add_links), onClick = {
                        Log.i(TAG, "BUTTON 3")
                    })
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                ) {
                    AddButton(text = stringResource(id = R.string.button_copy_to_clipboard))
                }
            }
        }
    }
}

@Composable
fun AddCardView(text: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.secondary,
        modifier = Modifier.clickable {
            onClick()
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(175.dp)
                .padding(16.dp),
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.h5
            )
        }
    }
    Spacer(modifier = Modifier.size(16.dp))
}

@Preview
@Composable
fun PreviewAddCardView() {
    AddCardView(text = "This is a card view.", onClick = {})
}

@Composable
fun AddButton(text: String) {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Text(text = text.toUpperCase(Locale.ROOT))
    }
}
