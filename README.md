# parent-pom

A parent POM for silnith.org projects.

To use this, set up a [Maven toolchain](http://maven.apache.org/guides/mini/guide-using-toolchains.html).
By default it looks for a toolchain with version `1.11` and vendor `openjdk`, but that can be changed by setting
the project properties `silnith.toolchain.vendor` and `silnith.toolchain.version`.
