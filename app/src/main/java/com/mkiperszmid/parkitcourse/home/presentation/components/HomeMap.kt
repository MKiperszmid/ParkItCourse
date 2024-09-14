package com.mkiperszmid.parkitcourse.home.presentation.components


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.mkiperszmid.parkitcourse.R
import com.mkiperszmid.parkitcourse.home.domain.model.Location
import com.mkiperszmid.parkitcourse.home.domain.model.Route
import com.mkiperszmid.parkitcourse.ui.theme.AccentColor

@Composable
fun HomeMap(
    currentLocation: Location?,
    carLocation: Location?,
    route: Route?,
    modifier: Modifier = Modifier
) {
    val cameraPositionState = rememberCameraPositionState()
    val context = LocalContext.current
    val mapstyle = remember {
        MapStyleOptions.loadRawResourceStyle(context, R.raw.mapstyle)
    }

    LaunchedEffect(currentLocation) {
        currentLocation?.let {
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLng(
                    LatLng(
                        it.latitude,
                        it.longitude
                    )
                )
            )
        }
    }

    GoogleMap(
        modifier = modifier,
        properties = MapProperties(
            isMyLocationEnabled = true,
            maxZoomPreference = 20f,
            minZoomPreference = 15f,
            mapStyleOptions = mapstyle
        ),
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false
        ),
        cameraPositionState = cameraPositionState
    ) {
        carLocation?.let {
            CarMarker(position = it)
        }

        route?.let { route ->
            Polyline(
                points = route.polylines.map { LatLng(it.latitude, it.longitude) },
                color = AccentColor
            )
        }
    }
}

@Composable
private fun CarMarker(position: Location) {
    val context = LocalContext.current
    val state = remember {
        MarkerState(LatLng(position.latitude, position.longitude))
    }
    Marker(state = state, icon = bitmapDescriptorFromVector(context, R.drawable.ic_marker))
}

private fun bitmapDescriptorFromVector(context: Context, imageId: Int): BitmapDescriptor? {
    val vectorDrawable = ContextCompat.getDrawable(context, imageId) ?: return null
    vectorDrawable.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
    val bitmap = Bitmap.createBitmap(
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

@Preview
@Composable
fun HomeMapPreview() {
    HomeMap(null, null, null)
}
