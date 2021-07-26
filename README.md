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

[![codebeat badge](https://codebeat.co/badges/61dca9f9-6bd0-4bbb-9de4-5c95b8e195a2)](https://codebeat.co/projects/github-com-jdkstack-jdklog-dev)
[![CodeFactor](https://www.codefactor.io/repository/github/jdkstack/jdklog/badge/dev)](https://www.codefactor.io/repository/github/jdkstack/jdklog/overview/dev)

[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-black.svg)](https://sonarcloud.io/dashboard?id=org.jdkstack%3Ajdklog)
    
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=org.jdkstack%3Ajdklog&metric=bugs)](https://sonarcloud.io/dashboard?id=org.jdkstack%3Ajdklog&branch=dev)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=org.jdkstack%3Ajdklog&metric=code_smells)](https://sonarcloud.io/dashboard?id=org.jdkstack%3Ajdklog&branch=dev)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=org.jdkstack%3Ajdklog&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=org.jdkstack%3Ajdklog&branch=dev)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=org.jdkstack%3Ajdklog&metric=ncloc)](https://sonarcloud.io/dashboard?id=org.jdkstack%3Ajdklog&branch=dev)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=org.jdkstack%3Ajdklog&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=org.jdkstack%3Ajdklog&branch=dev)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=org.jdkstack%3Ajdklog&metric=alert_status)](https://sonarcloud.io/dashboard?id=org.jdkstack%3Ajdklog&branch=dev)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=org.jdkstack%3Ajdklog&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=org.jdkstack%3Ajdklog&branch=dev)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=org.jdkstack%3Ajdklog&metric=security_rating)](https://sonarcloud.io/dashboard?id=org.jdkstack%3Ajdklog&branch=dev)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=org.jdkstack%3Ajdklog&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=org.jdkstack%3Ajdklog&branch=dev)

A lightweight, high performance, open source, application layer log service framework.
Although the performance is lower than log4j2(It didn't use lock-free queue,such as ring buffer), but the advantage is  no dependency lib, only need openjdk.

After jmh benchmark,the performance of log4j2 is higher than that of jdklog, but less than an order of magnitude(Even lower).
Log4j2 performance is poor when output location information, the jdklog has no performance loss.
