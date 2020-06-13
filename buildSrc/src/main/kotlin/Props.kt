import java.io.File
import java.io.FileInputStream
import java.util.Properties

object Props {
    object GitHub {
        private val githubProperties: Properties by lazy {
            val properties = Properties()
            properties.load(FileInputStream(File( "github.properties")))
            properties
        }

        val ID: String? = githubProperties["gpr.usr"] as? String
        val KEY: String? = githubProperties["gpr.key"] as? String
    }
}
