![Language](https://img.shields.io/badge/language-java-orange.svg)
![JDK](https://img.shields.io/badge/OpenJDK-11-yellow.svg)
![Maven](https://raster.shields.io/badge/Maven-3.6.3-red.svg)
![License](https://img.shields.io/badge/license-GPL2.0-000000.svg)

![google-java-format](https://img.shields.io/badge/google-javaformat-red.svg)
![tool-checkstyle](https://img.shields.io/badge/(google/sun)-checkstyle-orange.svg)
![editor-code-style](https://img.shields.io/badge/(google/sun)-codestyle-yellow.svg)
![editor-inspections](https://img.shields.io/badge/idea-inspections-red.svg)

![sonar-check](https://img.shields.io/badge/sonar-check-yellow.svg)
![ali-check](https://img.shields.io/badge/ali-check-red.svg)
![no-cyclic-dependency](https://img.shields.io/badge/cyclic-dependency-red.svg)
![annotation-rate](https://img.shields.io/badge/annotation-rate-red.svg)
![(api/core)-lib](https://img.shields.io/badge/(api/core)-lib-red.svg)

A lightweight, high performance, open source, application layer log service framework.
Although the performance is lower than log4j2(It didn't use lock-free queue,such as ring buffer), but the advantage is  no dependency lib, only need openjdk.

After jmh benchmark,the performance of log4j2 is higher than that of jdklog, but less than an order of magnitude(Even lower).
Log4j2 performance is poor when output location information, the jdklog has no performance loss.
