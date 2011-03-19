package com.embedly.api

import static org.hamcrest.MatcherAssert.*
import static org.hamcrest.Matchers.*

import org.junit.Test
import java.util.regex.Pattern
import org.json.JSONArray

class ApiTest {
    @Test
    void shouldFilterInvalidUrls() {
        def api = new Api("embedly-java-junit")
        def regex = Pattern.compile('.*testing.*')
        def urls = [
            "http://www.google.com/",
            "http://www.yahoo.com/",
            "http://mytestingsite.com/",
            "http://othertesting.com/",
            "http://twitter.com/"
        ]

        def response = api.filterByServices(urls, regex)

        assertThat(response.length(), is(5))
        assertThat(response.getJSONObject(0), is(notNullValue()))
        assertThat(response.getJSONObject(1), is(notNullValue()))
        assertThat(response.isNull(2), is(true))
        assertThat(response.isNull(3), is(true))
        assertThat(response.getJSONObject(4), is(notNullValue()))

        assertThat(urls.size(), is(2))
        assertThat(urls[0], is('http://mytestingsite.com/'))
        assertThat(urls[1], is('http://othertesting.com/'))
    }

    void shouldFillResponse() {
        def api = new Api("embedly-java-junit")
        def regex = Pattern.compile('.*testing.*')
        def urls = [
            "http://www.google.com/",
            "http://www.yahoo.com/",
            "http://mytestingsite.com/",
            "http://othertesting.com/",
            "http://twitter.com/"
        ]
        def apiJSON = """\
        [ { url: "http://mytestingsite.com/" }
        , { url: "http://othertesting.com/"  }
        ]
        """

        def response = api.filterByServices(urls, regex)
        api.fillResponse(response, new JSONArray(apiJSON))

        assertThat(response.getJSONObject(2), is(notNullValue()))
        assertThat(response.getJSONObject(3), is(notNullValue()))
        assertThat(response.getJSONObject(2).getString("url"),
                                    is("http://mytestingsite.com"))
        assertThat(response.getJSONObject(3).getString("url"),
                                    is("http://othertesting.com"))
    }
}
