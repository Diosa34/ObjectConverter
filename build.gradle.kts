plugins {
    java
    `maven-publish`
}

publishing {
    publications{
        register<MavenPublication>("Beautiful"){
            from(components["java"])
        }
    }
}