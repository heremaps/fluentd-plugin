# FluentD plugin.

A Jenkins plugin which allows to publish json data directly to [FluentD](https://www.fluentd.org) agent.

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
The main goal of this plugin is providing easy way to push data from Jenkins to some external data storage.
We didn't want to support tons of possible destinations on Jenkins side, so we decided to use some dispatcher for that.
[FluentD](https://www.fluentd.org) looks mature enough to handle a lot of data.
And it supports a lot of destination points (DBs, file, http, etc).

So, if you need to push some json data or stats from Jenkins, you can easily use this plugin + FluentD.

But right now plugin has some limitations: it pushes from master only (as result it fetches file from slave to master).
Don't use it for publishing really huge json data.


Building
---

Prerequisites:

- JDK 7 (or above)
- Apache Maven

Build process is quite simple

```Shell
mvn install
```

Basic Usage
---

To get started:

1. Install the plugin
1. Go to Jenkins Configuration
1. Find Logger for Fluentd panel.
1. Provide logger name (it would be used as prefix for Fluentd tag) and other setting if it's needed
1. Go to any job -> Configuration
1. Add "Post Build Action" -> "Send to FluentD"
1. Specify desired file (and extension in json format if it's needed)

Examples
---

Plugin supports two type of json data: single json and json array.
Also plugin allows to add some constant extension to json which supports environment variables.

1. If you extend {"a1": "b1"} by {"a2": "b2"}, plugin would send {"a1": "b1", "a2": "b2"}.
2. If you extend [{"a1": "b1"},{"a2": "b2"}] by {"a3": "b3"}, plugin would send [{"a1": "b1", "a3": "b3"}, {"a2": "b2", "a3": "b3"}].
3. If you extend {"a1": "b1"} by {"number": "$BUILD_NUMBER"}, plugin would decode environment variables for each execution separately.
So, for first run plugin would submit:
{"a1": "b1", "number": "1"}
and for second one:
{"a1": "b1", "number": "2"}

Also plugin adds "timestamp" field for each json. Value of this field is equal to time then job started.

Authors
---

Alexander Akbashev - <alexander.akbashev@here.com>

License
---

Licensed under the [MIT License (MIT)](LICENSE)