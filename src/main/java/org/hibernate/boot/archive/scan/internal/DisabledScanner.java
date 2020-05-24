package org.hibernate.boot.archive.scan.internal;

import org.hibernate.boot.archive.scan.internal.ScanResultImpl;
import org.hibernate.boot.archive.scan.spi.*;
import org.springframework.context.ApplicationContext;

import java.util.Collections;

public class DisabledScanner implements Scanner {
    private static final ScanResult emptyScanResult = new ScanResultImpl(Collections.emptySet(), Collections.emptySet(), Collections.emptySet());

    public DisabledScanner() {
    }

    public ScanResult scan(ScanEnvironment environment, ScanOptions options, ScanParameters parameters) {

        return emptyScanResult;
    }
}