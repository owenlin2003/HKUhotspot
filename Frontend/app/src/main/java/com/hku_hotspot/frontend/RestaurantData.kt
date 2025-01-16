package com.hku_hotspot.frontend

import com.google.android.gms.maps.model.LatLng

val restaurantList = listOf(

    Restaurant( 15,
        "27 Kebab House",
        "Kebab and Pizza place",
        "https://google.com",
        LatLng(22.283538720251425, 114.13973820863716),
        listOf(
            LatLng(22.283538720251425, 114.13973820863716),
            LatLng(22.383538720251425, 114.23973820863716)
        ),
        capacity = 50,
        imageResId = R.drawable.restaurant_27_kebab_house
    ),


)
