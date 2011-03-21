package com.embedly.api

import static org.hamcrest.MatcherAssert.*
import static org.hamcrest.Matchers.*

import org.junit.Test
import java.util.regex.Pattern

class ApiParametersTest {
    @Test
    void shouldCombineUrlAndUrls() {
        def params = new ApiParameters()
        params.push("url", "http://www.google.com/")
        params.push("url", "http://www.yahoo.com/")
        params.push("urls", "http://www.twitter.com/")
        params.push("urls", "http://embed.ly/")
        params.push("url", "http://bit.ly/")

        def urls = params.getParam("urls")

        assertThat(urls.size(), is(5))
        assertThat(urls[0], is('http://www.google.com/'))
        assertThat(urls[1], is('http://www.yahoo.com/'))
        assertThat(urls[2], is('http://www.twitter.com/'))
        assertThat(urls[3], is('http://embed.ly/'))
        assertThat(urls[4], is('http://bit.ly/'))

    }
}
