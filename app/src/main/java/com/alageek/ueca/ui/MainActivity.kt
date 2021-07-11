package com.alageek.ueca.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Help
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.timePicker
import com.alageek.ueca.R
import com.alageek.ueca.models.Event
import com.alageek.ueca.models.EventSaver
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

const val TAG = "MainActivity"
val DEFAULT_PADDING = 16.dp

@ExperimentalMaterialApi
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainApp()
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MainApp() {
    val navController = rememberNavController()
    val event by rememberSaveable(stateSaver = EventSaver) {
        mutableStateOf(
            Event(
                time = LocalDateTime.now(),
                timezones = listOf()
            )
        )
    }

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainContent(navController = navController, event = event)
        }
        composable("about") {
            AboutContent(navController = navController)
        }
        composable("edit_time") {
            TimeContent(navController = navController, event = event)
        }
        composable("edit_timezones") {
            TimezoneContent(navController = navController, event = event)
        }
        composable("edit_description") {
            DescriptionContent(navController = navController, event = event)
        }
        composable("edit_links") {
            LinksContent(navController = navController)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MainContent(navController: NavHostController, event: Event) = AppTheme {
    if (event.description.isEmpty()) {
        event.description = stringResource(id = R.string.default_description)
    }

    Column {
        TopBar(
            title = R.string.title_default,
            actions = {
                IconButton(onClick = { navController.navigate("about") }) {
                    Icon(
                        imageVector = Icons.Default.Help,
                        contentDescription = stringResource(R.string.desc_about)
                    )
                }
            }
        )
        Column(
            modifier = Modifier
                .weight(8f)
                .padding(DEFAULT_PADDING)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            val dateStr = event.time.format(dateFormatter)
            val textTime = "$dateStr\n" + when (event.timezones.size) {
                0 -> stringResource(id = R.string.text_no_timezone)
                else -> event.getTimes().joinToString("\n") {
                    "bla bla bla"
                }
            }

            CardView(
                title = stringResource(id = R.string.title_description),
                text = event.description,
                onClick = { navController.navigate("edit_description") }
            )

            CardView(
                title = stringResource(id = R.string.title_time),
                text = textTime,
                onClick = { navController.navigate("edit_time") }
            )

            CardView(
                title = stringResource(id = R.string.title_links),
                text = "EDIT LINKS",
                onClick = { navController.navigate("edit_links") }
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(DEFAULT_PADDING),
        ) {
            AddButton(text = stringResource(id = R.string.button_copy_to_clipboard))
        }
    }
}

@Composable
fun DescriptionContent(navController: NavHostController, event: Event) = AppTheme {
    var description by remember { mutableStateOf(event.description) }

    Scaffold(
        topBar = {
            TopBar(
                title = R.string.title_description,
                navButton = {
                    event.description = description
                    BackButton(navController = navController)
                }
            )
        }
    ) {
        DescriptionTextField(description = description, onValueChange = { description = it })
    }
}

@Composable
fun AboutContent(navController: NavHostController) = AppTheme {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopBar(
                title = R.string.title_about,
                navButton = {
                    BackButton(navController = navController)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(DEFAULT_PADDING)
                .fillMaxSize()
        ) {
            Text(text = stringResource(id = R.string.about_content))
            Text(
                text = stringResource(id = R.string.about_title_authors),
                fontSize = 25.sp,
                modifier = Modifier.padding(top = 20.dp)
            )
            Text(text = "- Gökmen Görgen, <gkmngrgn@gmail.com>")
            Text(
                text = stringResource(id = R.string.about_title_license),
                fontSize = 25.sp,
                modifier = Modifier.padding(top = 20.dp)
            )
            Text(text = stringResource(id = R.string.about_content_license))
            Button(
                onClick = {
                    linkToWebpage(context = context)
                },
                modifier = Modifier.padding(top = 20.dp)
            ) {
                Text(text = "UECA v1.0.0")
            }
        }
    }
}

@Composable
fun TimeContent(navController: NavHostController, event: Event) = AppTheme {
    Scaffold(
        topBar = {
            TopBar(
                title = R.string.title_time,
                navButton = { BackButton(navController = navController) },
                actions = {
                    IconButton(onClick = { navController.navigate("edit_timezones") }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.desc_timezone)
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(DEFAULT_PADDING)
                .fillMaxSize()
        ) {
            TimeView(time = event.time)
            Text(text = "no timezone specified.")
        }
    }
}

@Composable
fun TimezoneContent(navController: NavHostController, event: Event) = AppTheme {
    var searchText by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopBar(
                title = R.string.title_timezone,
                navButton = { BackButton(navController = navController) }
            )
        }
    ) {
        Column(modifier = Modifier.padding(DEFAULT_PADDING)) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Search") },
                modifier = Modifier.fillMaxWidth()
            )
            LazyColumn {
                items(5) { index ->
                    Text(text = "Item: $index")
                }
            }
        }
    }
}

@Composable
fun LinksContent(navController: NavHostController) = AppTheme {
    Scaffold(
        topBar = {
            TopBar(
                title = R.string.title_links,
                navButton = { BackButton(navController = navController) }
            )
        }
    ) {
        Text(text = "Edit Links Content")
    }
}

@Composable
fun TopBar(
    title: Int,
    navButton: @Composable (() -> Unit)? = null,
    actions: @Composable (() -> Unit)? = null,
) {
    TopAppBar(
        title = { Text(stringResource(title)) },
        navigationIcon = navButton,
        actions = {
            if (actions != null) {
                actions()
            }
        }
    )
}

@Composable
fun BackButton(navController: NavHostController) = IconButton(
    onClick = {
        navController.popBackStack()
    }
) {
    Icon(
        imageVector = Icons.Filled.ArrowBack,
        contentDescription = stringResource(R.string.desc_back)
    )
}

@ExperimentalMaterialApi
@Composable
fun CardView(title: String, text: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.secondary,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(175.dp)
                .padding(DEFAULT_PADDING),
        ) {
            Column {
                Text(text = title, style = MaterialTheme.typography.h5)
                Text(text = text, style = MaterialTheme.typography.body1)
            }
        }
    }
    Spacer(modifier = Modifier.size(DEFAULT_PADDING))
}

@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewCardView() {
    CardView(title = "Title", text = "This is a card view.", onClick = {})
}

@Composable
fun TimeBox(value: Int) {
    Box(
        modifier = Modifier
            .padding(DEFAULT_PADDING)
            .clip(RoundedCornerShape(10.dp))
            .background(colorResource(id = R.color.color_5))
    ) {
        Text(
            text = value.toString().padStart(2, '0'),
            style = TextStyle(fontSize = 72.sp, fontFamily = FontFamily.Monospace),
            modifier = Modifier
                .padding(DEFAULT_PADDING)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun TimeView(time: LocalDateTime) {
    val context = LocalContext.current
    Log.i(TAG, "Current time is: $time")

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {
                    MaterialDialog(context).show {
                        timePicker { _, _ ->
                            Log.i(TAG, "I am here.")
                        }
                    }
                }
        ) {
            TimeBox(time.hour)
            Text(
                text = ":",
                fontSize = 72.sp,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            TimeBox(time.minute)
        }
    }
}

@Preview
@Composable
fun PreviewTimeView() {
    TimeView(LocalDateTime.now())
}

@Composable
fun AddButton(text: String) {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Text(text = text.uppercase(Locale.ROOT))
    }
}

@Composable
fun DescriptionTextField(description: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(DEFAULT_PADDING)) {
        TextField(
            value = description,
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            onValueChange = { onValueChange(it) },
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        )
    }
}

@Preview
@Composable
fun PreviewDescriptionTextField() {
    DescriptionTextField(description = "A description test.", onValueChange = {})
}

fun linkToWebpage(context: Context) {
    val openURL = Intent(Intent.ACTION_VIEW)
    openURL.data = Uri.parse("https://github.com/gkmngrgn/ueca/releases")
    ContextCompat.startActivity(context, openURL, null)
}
