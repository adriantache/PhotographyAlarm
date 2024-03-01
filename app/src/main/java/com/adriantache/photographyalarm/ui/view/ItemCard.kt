package com.adriantache.photographyalarm.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ItemCard(
    title: String,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(8.dp))
    ) {
        Text(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.secondary,
                    RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
                )
                .fillMaxWidth(),
            text = title,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 8.sp,
                letterSpacing = 6.sp,
            ),
        )

        Spacer(Modifier.height(8.dp))

        this.content()

        Spacer(Modifier.height(16.dp))
    }
}

@Preview
@Composable
private fun ItemCardPreview() {
    ItemCard(title = "TEST") {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "test",
            textAlign = TextAlign.Center
        )
    }
}