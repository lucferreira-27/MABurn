package com.lucas.ferreira.maburn.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class JarUtils {

    public static boolean isResourceInJar() throws URISyntaxException {

        URI uri = Objects.requireNonNull(Resources.class.getClassLoader().getResource("")).toURI();
        return uri.toString().contains("!");
    }
}
