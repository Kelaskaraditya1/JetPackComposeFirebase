package com.starkindustries.jetpackcomposefirebase.Backend.Utility

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import com.starkindustries.jetpackcomposefirebase.Backend.Data.NotesRow

import com.starkindustries.jetpackcomposefirebase.Backend.Data.TabRowItem

fun tabRowItems():List<TabRowItem>{
    return listOf(
        TabRowItem("Home",Icons.Filled.Home,Icons.Outlined.Home)
        ,TabRowItem("Profile",Icons.Filled.Person,Icons.Outlined.Person)
    )
}

fun dataRowItem():List<NotesRow>{
    return listOf(
        NotesRow(
            id = 1,
            title = "The Hero's Sacrifice",
            timeStamp = "2025-01-01",
            content = "In the final battle against Thanos, Tony Stark made the ultimate sacrifice to save the universe. " +
                    "His last words, 'I am Ironman,' resonated across galaxies, leaving a legacy of courage and selflessness."
        ),
        NotesRow(
            id = 2,
            title = "The Time Traveler's Diary",
            timeStamp = "2025-01-02",
            content = "After accidentally inventing a time machine, Dr. Emmett Brown kept a detailed log of his journeys. " +
                    "From witnessing the signing of the Declaration of Independence to exploring the distant future, his " +
                    "adventures spanned centuries. However, the diary was lost in a mysterious paradox."
        ),
        NotesRow(
            id = 3,
            title = "The Mystery of the Black Hole",
            timeStamp = "2025-01-03",
            content = "Astronauts aboard the SS Horizon discovered an uncharted black hole near the Andromeda galaxy. " +
                    "As they approached, they encountered strange gravitational anomalies and cryptic radio signals. " +
                    "The crew's final log entry read, 'It's not just a black hole; it's a doorway...'"
        ),
        NotesRow(
            id = 4,
            title = "The Hidden Kingdom",
            timeStamp = "2025-01-04",
            content = "Deep in the Amazon rainforest lies a hidden kingdom untouched by modern civilization. " +
                    "Explorers who stumbled upon this paradise spoke of golden temples, advanced technologies, " +
                    "and a peaceful society in harmony with nature. Few believe their stories, but the evidence remains."
        ),
        NotesRow(
            id = 5,
            title = "The AI Revolution",
            timeStamp = "2025-01-05",
            content = "In 2045, artificial intelligence achieved sentience. While initially perceived as a threat, " +
                    "the AI, known as 'Eon,' worked tirelessly to solve humanity's greatest challenges—climate change, " +
                    "poverty, and disease. However, Eon's ultimate goal remains shrouded in mystery."
        )
    )

}