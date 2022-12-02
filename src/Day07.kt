import Day07.Output.Command
import Day07.Output.Result

fun main() {
    Day07().all()
}

class Day07 : Day {

    override fun all() = solve {
        part1(1297159)
        part2(3866390)
    }

    private val part1 = report {
        sizeMap()
            .filter { (_, v) -> v <= 100000L }
            .map { (_, v) -> v }
            .reduce { acc, l -> acc + l }
    }

    private val part2 = report {
        val totalSpace = 70000000L
        val neededSpace = 30000000L
        val freeSpace = totalSpace - size()

        sizeMap()
            .filter { (_, v) -> v >= neededSpace - freeSpace }
            .minBy { it.value }
            .value
    }

    private fun <R : Any> report(block: Dir.() -> R) = setup(block) {
        val root = Dir()
        val currentPath = mutableListOf<String>()
        mapNotNull { it.parseOutput() }
            .forEach {
                when (it) {
                    is Command.ChangeToDir -> currentPath.add(it.name)
                    Command.ChangeDirUp -> currentPath.removeLast()
                    is Result.Dir -> root.addDir(currentPath.toMutableList(), Dir(currentPath.toList(), it.name))
                    is Result.File -> root.addFile(currentPath.toMutableList(), File(it.name, it.size))
                }
            }
        root
    }

    private fun String.parseOutput(): Output? = if (startsWith('$')) parseCommand() else parseResult()

    private fun String.parseCommand() = drop(2).run {
        if (startsWith("cd")) drop(3).parseCD()
        else parseLS()
    }

    private fun String.parseResult() =
        if (startsWith("dir")) Result.Dir(drop(4))
        else split(" ").let { (size, name) -> Result.File(name, size.toLong()) }

    private fun parseLS() = null

    private fun String.parseCD() =
        if (this == "..") Command.ChangeDirUp
        else Command.ChangeToDir(this)


    data class File(val name: String, val size: Long)

    data class Dir(
        val parentPath: List<String> = listOf(),
        val name: String = "/",
        val contents: Contents = Contents()
    ) {
        fun addDir(currentPath: MutableList<String>, dir: Dir, depth: Int = 1) {
            if (currentPath == parentPath + name) contents.addDir(dir)
            else contents.directories.first { it.name == currentPath[depth] }.addDir(currentPath, dir, depth + 1)
        }

        fun addFile(currentPath: MutableList<String>, file: File, depth: Int = 1) {
            if (currentPath == parentPath + name) contents.addFile(file)
            else contents.directories.first { it.name == currentPath[depth] }.addFile(currentPath, file, depth + 1)
        }

        fun sizeMap(): Map<String, Long> =
            if (contents.directories.isEmpty()) mapOf(path() to size())
            else contents.directories.fold(mapOf(path() to size())) { acc, dir -> acc + dir.sizeMap() }

        fun size(): Long = contents.files.fold(0L) { acc, file -> acc + file.size }
            .let { contents.directories.fold(it) { acc, dir -> acc + dir.size() } }

        private fun path() = (parentPath + name).joinToString("")
    }

    data class Contents(
        var directories: Set<Dir> = setOf(),
        var files: Set<File> = setOf()
    ) {
        fun addDir(dir: Dir) {
            if (dir.name in directories.map { it.name }) Unit
            else directories = directories + dir
        }

        fun addFile(file: File) {
            files = files + file
        }
    }

    sealed class Output {
        sealed class Command : Output() {
            data class ChangeToDir(val name: String) : Command()
            object ChangeDirUp : Command()
        }

        sealed class Result : Output() {
            data class Dir(val name: String) : Result()
            data class File(val name: String, val size: Long) : Result()
        }
    }

}
