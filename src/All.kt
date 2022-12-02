fun main() {
    println("Start: ")
    (1..25)
        .map { it.toString().padStart(2, '0') }
        .map { Class.forName("Day$it") }
        .map { it.getDeclaredConstructor().newInstance() }
        .map { it as Day }
        .forEach { it.all() }
    println("All done!")
}
