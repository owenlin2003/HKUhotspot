package com.hku_hotspot.frontend

import com.google.android.gms.maps.model.LatLng

val buildingsList = listOf(
    // Completed Buildings and Queues
    Building(
        0,
        "Main Building",
        "The Main Building of HKU",
        LatLng(22.284035130373443, 114.13784444955115),
        listOf(
            LatLng(22.283721395121994, 114.13740744242192),
            LatLng(22.284343856269032, 114.13743254429502),
            LatLng(22.284307503820735, 114.13822349721006),
            LatLng(22.2836917288536, 114.13819769305451)
        ),
        capacity = 500
    ),
    Building(
        1,
        "Hung Hing Ying Building",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. ",
        LatLng(22.284627412244824, 114.13812841061734),
        listOf(
            LatLng(22.284564049243684, 114.13768819832565),
            LatLng(22.284666636945442, 114.13768656790977),
            LatLng(22.28464099002706, 114.13824253973002),
            LatLng(22.284517281295823, 114.13822623557105)
        ),
        capacity = 300
    ),
    Building(
        2,
        "Tang Chi Ngong Building",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. ",
        LatLng(22.28351415956693, 114.14004253797795),
        listOf(
            LatLng(22.28347520403775, 114.13985542878652),
            LatLng(22.283628140497424, 114.13986322500283),
            LatLng(22.283618040925056, 114.14006436738362),
            LatLng(22.283457890465726, 114.14004253797795)
        ),
        capacity = 400
    ),
    Building(
        3,
        "Centennial Campus",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. ",
        LatLng(22.283587504088413, 114.13453457597026),
        listOf(
            LatLng(22.283535243529915, 114.13451951750586),
            LatLng(22.283737077395752, 114.13455509798018),
            LatLng(22.283724194391755, 114.13487377527179),
            LatLng(22.28352092906029, 114.13482581898033)
        ),
        capacity = 400
    ),
    Building(
        4,
        "Li Ka Shing Faculty of Medicine",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. ",
        LatLng(22.267659202868632, 114.12826691000343),
        listOf(
            LatLng(22.267518267820446, 114.12821907101095),
            LatLng(22.267645091274606, 114.12824866260166),
            LatLng(22.267589242210228, 114.12833114052863),
            LatLng(22.26754279236799, 114.12833749672903)
        ),
        capacity = 400
    ),
    Building(
        5,
        "Prince Philip Dental Hospital",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. ",
        LatLng(22.286709228421536, 114.14402623756116),
        listOf(
            LatLng(22.286296312926158, 114.14383297056186),
            LatLng(22.28645618847899, 114.14378113583706),
            LatLng(22.28648121412379, 114.14428141092122),
            LatLng(22.28648121412379, 114.14428141092122)
        ),
        capacity = 300
    ),

    // Placeholders for Remaining Buildings
    Building(
        6,
        "Chi Wah Learning Commons",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. ",
        LatLng(22.283540782294413, 114.13471627709865),
        listOf(
            LatLng(22.283525519249363, 114.1346436995135),
            LatLng(22.283619132566102, 114.13465359645693),
            LatLng(22.283615062423202, 114.1348702295519),
            LatLng(22.283510256202646, 114.13486033260848)
        ),
        capacity = 500
    ),
    Building(
        7,
        "Knowles Building",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. ",
        LatLng(22.283288012713605, 114.13845192006481),
        listOf(
            LatLng(22.283060703480174, 114.13822706075806),
            LatLng(22.283478139695152, 114.13825611633749),
            LatLng(22.283449838974207, 114.13862313418319),
            LatLng(22.283060703480174, 114.13861242949602)
        ),
        capacity = 1000
    ),
    Building(
        8,
        "Lee Shau Kee Lecture Centre",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. ",
        LatLng(22.283640326610275, 114.13426382183606),
        listOf(
            LatLng(22.2835460255895, 114.13415239807327),
            LatLng(22.283680561693107, 114.1341727804689),
            LatLng(22.28366170149946, 114.13428420423168),
            LatLng(22.283527165377713, 114.13428012775256)
        ),
        capacity = 300
    ),
    Building(
        9,
        "Library Building (New Wing)",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. ",
        LatLng(22.283489188607838, 114.13781025773604),
        listOf(
            LatLng(22.283411541026922, 114.1377919014682),
            LatLng(22.283654189574143, 114.13779452379218),
            LatLng(22.283634777705863, 114.13802922178809),
            LatLng(22.28340547480785, 114.13801742133019)
        ),
        capacity = 300
    ),
    Building(
        10,
        "Run Run Shaw Tower",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. ",
        LatLng(22.283681829693602, 114.1344001649712),
        listOf(
            LatLng(22.28355318544539, 114.13431890331314),
            LatLng(22.2836574703741, 114.13430594909603),
            LatLng(22.283644284927632, 114.13449637608754),
            LatLng(22.28353760263326, 114.13449767150927)
        ),
        capacity = 200
    ),

)
