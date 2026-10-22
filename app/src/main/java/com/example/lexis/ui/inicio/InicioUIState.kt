package com.example.lexis.ui.inicio

data class LawCategory(
    val title: String,
    val countText: String,
    val iconRes: Int? = null
)

data class RecentChange(
    val day: String,
    val month: String,
    val tag: String,
    val title: String,
    val subtitle: String,
    val timeAndCount: String
)

data class InicioUiState(
    val userName: String = "Valentina",
    val userInitials: String = "VR",
    val lastReadTitle: String = "Ley de Protección de Datos Personales",
    val lastReadArticle: String = "Artículo 22 · Derechos ARCO",
    val progress: Float = 0.68f,
    val categories: List<LawCategory> = listOf(
        LawCategory("Constitucional", "24 normas"),
        LawCategory("Civil", "38 normas"),
        LawCategory("Laboral", "19 normas")
    ),
    val recentChanges: List<RecentChange> = listOf(
        RecentChange(
            day = "14",
            month = "MAY",
            tag = "MODIFICADA",
            title = "Ley Federal del Trabajo",
            subtitle = "Reforma sobre jornadas y descanso laboral",
            timeAndCount = "Hace 2 días · 4 artículos"
        )
    )
)