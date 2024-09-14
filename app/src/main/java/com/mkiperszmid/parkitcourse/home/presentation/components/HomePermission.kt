package com.mkiperszmid.parkitcourse.home.presentation.components


import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomePermission(
    permissions: List<String>,
    permissionResult: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val permissionState = rememberMultiplePermissionsState(permissions = permissions)
    LaunchedEffect(Unit) {
        permissionState.launchMultiplePermissionRequest()
    }

    if (permissionState.allPermissionsGranted) {
        permissionResult(true)
    } else if (permissionState.shouldShowRationale) {
        AlertDialog(modifier = modifier, onDismissRequest = { }, confirmButton = {
            HomeButton(onClick = {
                permissionState.launchMultiplePermissionRequest()
            }, text = "Accept", imageVector = null)
        }, title = {
            Text(text = "Permission Required")
        }, text = {
            Text(text = "We need your location for the app to work correctly.")
        })
    } else {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround) {
            val context = LocalContext.current
            Text("We need your location for the app to work correctly.")
            HomeButton(onClick = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", context.packageName, null)
                }
                context.startActivity(intent)
            }, text = "Open App Settings", imageVector = null)
        }
    }
}

@Preview
@Composable
fun HomePermissionPreview() {
    HomePermission(listOf(), {})
}
