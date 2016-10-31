# Fluentd plugin.

A Jenkins plugin which allows to publish JSON data directly to [Fluentd](https://www.fluentd.org).

Table of contents
---

1. [Overview](#overview)
1. [Building](#building)
1. [Basic Usage](#basic-usage)
1. [Examples](#examples)
1. [Authors](#authors)
1. [License](#license)

Overview
---

The main goal of this plugin is to provide an easy way to push data from Jenkins to some external data storage.
As there are many such external data solutions to use, instead of supporting all of them, we decided to use some dispatcher.
Our choice was to use [Fluentd](https://www.fluentd.org) as looks mature enough to handle a lot of data and it supports
a lot of destination endpoints (DBs, file, http, etc).

So, if you need to push some JSON data or statistics from Jenkins you can easily use this plugin in conjunction with Fluentd.

Known limitations
---

- It pushes from master only (as result it fetches a file from slave to master).
- Do not use it for publishing really huge JSON data yet.

Building
---

Prerequisites:

- JDK 7 (or above)
- Apache Maven

Build process is quite simple:

```Shell
mvn install
```

Basic Usage
---

To get started:

1. Install the plugin.
1. Go to the Jenkins Configuration.
1. Find the Logger for Fluentd panel.
1. Provide a logger name (it is used as a prefix for Fluentd the tag) and other setting if needed.
1. Go to any job configuration.
1. Add "Post Build Action" -> "Send to Fluentd".
1. Specify the desired file (and extension in JSON format if needed).

Examples
---

Plugin supports two types of JSON data: Single JSON and JSON array. Also, the plugin supports environment variable
expansion as part extending JSON data.

1. If you extend {"a1": "b1"} by {"a2": "b2"}, the plugin would send {"a1": "b1", "a2": "b2"}.
2. If you extend [{"a1": "b1"},{"a2": "b2"}] by {"a3": "b3"}, the plugin would send [{"a1": "b1", "a3": "b3"}, {"a2": "b2", "a3": "b3"}].
3. If you extend {"a1": "b1"} by {"number": "$BUILD_NUMBER"}, the plugin would expand the environment variable for each execution separately.
So, for the first run the plugin would submit
{"a1": "b1", "number": "1"}
and for second one
{"a1": "b1", "number": "2"}
etc.

Additionally, the plugin adds a "timestamp" field into the JSON data. The value of this field is equals the start time of the job.

Authors
---

Alexander Akbashev - <alexander.akbashev@here.com>

License
---

Licensed under the [MIT License (MIT)](LICENSE).
