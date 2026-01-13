package app.kotlin.caelestial

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform