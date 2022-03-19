package com.raywenderlich.android.disneyexplorer.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raywenderlich.android.disneyexplorer.ui.theme.DisneyExplorerTheme

@Composable
fun Toolbar(onClick: () -> Unit) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .height(40.dp),
    horizontalArrangement = Arrangement.End
  ) {
    Icon(
      imageVector = Icons.Default.Refresh,
      contentDescription = "Refresh",
      modifier = Modifier
        .padding(top = 16.dp, end = 16.dp)
        .clickable { onClick() }
    )
  }
}

@Preview
@Composable
fun PreviewToolbar() {
  DisneyExplorerTheme {
    Toolbar {}
  }
}
