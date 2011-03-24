package com.embedly.api

import static org.hamcrest.MatcherAssert.*
import static org.hamcrest.Matchers.*
import static org.apache.commons.logging.LogFactory.getLog

import org.junit.Test
import java.util.regex.Pattern
import org.json.JSONArray

class ResponseMakerTest {
    @Test
    void shouldFilterInvalidUrls() {
        Api.setLog(getLog(Api.class));
        def resp = new ResponseMaker()
        def regex = Pattern.compile('.*testing.*')
        def urls = [
            "http://www.google.com/",
            "http://www.yahoo.com/",
            "http://mytestingsite.com/",
            "http://othertesting.com/",
            "http://twitter.com/"
        ]

        urls = resp.prepare(urls, regex)
        def response = resp.getResponse()

        assertThat(response.length(), is(5))
        assertThat(response.getJSONObject(0), is(notNullValue()))
        assertThat(response.getJSONObject(1), is(notNullValue()))
        assertThat(response.isNull(2), is(true))
        assertThat(response.isNull(3), is(true))
        assertThat(response.getJSONObject(4), is(notNullValue()))

        assertThat(urls.size(), is(2))
        assertThat(urls[0], is('http://mytestingsite.com/'))
        assertThat(urls[1], is('http://othertesting.com/'))

        urls = [
            "http://www.google.com/",
            "http://www.yahoo.com/",
            "http://twitter.com/"
        ]

        resp = new ResponseMaker();
        urls = resp.prepare(urls, regex)
        response = resp.getResponse()

        assertThat(response.length(), is(3))
        assertThat(response.getJSONObject(0), is(notNullValue()))
        assertThat(response.getJSONObject(1), is(notNullValue()))
        assertThat(response.getJSONObject(2), is(notNullValue()))

        assertThat(urls.size(), is(0))
    }

    void shouldFillResponse() {
        def resp = new ResponseMaker()
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

        urls = resp.prepare(urls, regex)
        def response = resp.getResponse()
        response.fill(new JSONArray(apiJSON))

        assertThat(response.getJSONObject(2), is(notNullValue()))
        assertThat(response.getJSONObject(3), is(notNullValue()))
        assertThat(response.getJSONObject(2).getString("url"),
                                    is("http://mytestingsite.com"))
        assertThat(response.getJSONObject(3).getString("url"),
                                    is("http://othertesting.com"))
    }
}
