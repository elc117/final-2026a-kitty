package me.nepnep.msa4legacy;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.Enumeration;

public class ExcludingClassLoader extends URLClassLoader {
    private final String[] exclusions;

    public ExcludingClassLoader(URL[] urls, String[] exclusions) {
        super(urls);
        this.exclusions = exclusions;
    }

    private boolean exclude(String path) {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        boolean relocated = false;
        for (StackTraceElement frame : stack) {
            if (frame.getClassName().contains("relocated")) {
                relocated = true;
                break;
            }
        }

        if (!relocated) {
            return false;
        }

        for (String exclusion : this.exclusions) {
            if (path.endsWith(exclusion)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public URL findResource(String name) {
        if (this.exclude(name)) {
            return null;
        }

        return super.findResource(name);
    }

    @Override
    public Enumeration<URL> findResources(String name) throws IOException {
        if (this.exclude(name)) {
            return Collections.emptyEnumeration();
        }

        return super.findResources(name);
    }

    @Override
    public URL getResource(String name) {
        if (this.exclude(name)) {
            return null;
        }

        return super.getResource(name);
    }
}
