// RUN_PIPELINE_TILL: BACKEND
class Wrapper(val tag: String)

fun foo(wrappers: List<Wrapper>) = buildList {
    wrappers.mapTo(this) { it.tag }
}
