package com.hku_hotspot.frontend

import com.google.android.gms.maps.model.LatLng

val queuesList = listOf(
    Queue(
        id = 11,
        name = "HKU Exit A Canteen Queue",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. ",
        centerPosition = LatLng(22.282751915859485, 114.13647525099975),
        points = listOf(
            LatLng(22.282777355455945, 114.1363813736853),
            LatLng(22.28285739708289, 114.13644440559642),
            LatLng(22.282848710396916, 114.13660936144898),
            LatLng(22.282728337692784, 114.1366053381355)
        ),
        capacity = 100
    ),
    Queue(
        id = 12,
        name = "Alfafa Queue",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. ",
        centerPosition = LatLng(22.28330312769633, 114.13475405404485),
        points = listOf(
            LatLng(22.283301244883884, 114.13469082837659),
            LatLng(22.283304667459948, 114.13488778956324),
            LatLng(22.28321311352164, 114.13469637657903),
            LatLng(22.283200278852597, 114.13489518716648)
        ),
        capacity = 200
    ),
    Queue(
        id = 13,
        name = "MTR Exit A Queue",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. ",
        centerPosition = LatLng(22.28334340364146, 114.1367187275126),
        points = listOf(
            LatLng(22.283448813664403, 114.13662394126473),
            LatLng(22.28344318106977, 114.13680742657026),
            LatLng(22.283014391788218, 114.13665738908563),
            LatLng(22.28297560323878, 114.13682616806219)
        ),
        capacity = 150
    ),
    Queue(
        id = 14,
        name = "HKU Exit C1 Pok Fu Lam Road Queue",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. ",
        centerPosition = LatLng(22.28443387352742, 114.13429467700168),
        points = listOf(
            LatLng(22.284430882836382, 114.13400067717792),
            LatLng(22.284314234320966, 114.13433863550995),
            LatLng(22.284231550109485, 114.13400064046796),
            LatLng(22.284096746573802, 114.13447579447386)
        ),
        capacity = 100
    )
)

