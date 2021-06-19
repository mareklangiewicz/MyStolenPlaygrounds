@file:OptIn(okio.ExperimentalFileSystem::class)
import okio.Path.Companion.toPath

task("stealAndroidxComposeUiGraphicsSamples") {
    doLast {
        val androidxRootDir = "/home/marek/code/android/androidx-main".toPath()
        val androidxSupportDir = androidxRootDir / "frameworks/support"
        val inputDir = androidxSupportDir / "compose/ui/ui-graphics/samples/src/main/java/androidx/compose/ui/graphics/samples"
        val outputDir = project.rootOkioPath / "app/src/main/kotlin/pl/ui-graphics-samples"
        processAllKtFiles(inputDir, outputDir) {
            println("processing next file...")
            it
        }
    }
}