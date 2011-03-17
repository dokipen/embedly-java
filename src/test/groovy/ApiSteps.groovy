this.metaClass.mixin(cuke4duke.GroovyDsl)

World {
}

Before() {
    api = new ly.embed.api.EmbedlyApi()
}

Before("@notused") {
    throw new RuntimeException("Never happens")
}

Before("@notused,@important", "@alsonotused") {
    throw new RuntimeException("Never happens")
}

Given(~"I have entered (\\d+) into (.*) calculator") { int number, String ignore ->
}

Given(~"(\\d+) into the") { ->
    throw new RuntimeException("should never get here since we're running with --guess")
}

When(~"I press (\\w+)") { String opname ->
}

Then(~"^error_message should be This service requires an Embedly Pro account$") { double expected ->
    assert 1 == 0
}
