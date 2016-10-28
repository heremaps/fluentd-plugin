package org.jenkinsci.plugins.fluentd;

import com.gargoylesoftware.htmlunit.WebAssert;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.xml.sax.SAXException;

import java.io.IOException;

public class LogBuilderTest {
    @Rule
    public JenkinsRule jenkins = new JenkinsRule();

    @Test
    public void dumbTest() throws IOException, SAXException {
        final HtmlPage page = jenkins.createWebClient().goTo("configure");
        WebAssert.assertTextPresent(page, "Logger for Fluentd");
    }
}
