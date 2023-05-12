/*
 * Copyright (c) 2023. caoccao.com Sam Cao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.caoccao.javet.perf;

import com.caoccao.javet.interop.V8Host;
import com.caoccao.javet.interop.V8Runtime;
import com.caoccao.javet.interop.loader.JavetLibLoader;
import com.caoccao.javet.interop.options.NodeRuntimeOptions;
import com.caoccao.javet.interop.options.V8Flags;
import com.caoccao.javet.interop.options.V8RuntimeOptions;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class BaseTestJavet {
    protected static boolean javetVersionLogged = false;
    protected Logger logger;
    protected V8Runtime nodeRuntime;
    protected List<V8Runtime> runtimes;
    protected StopWatch stopWatch;
    protected V8Runtime v8Runtime;

    public BaseTestJavet() {
        logger = LoggerFactory.getLogger(getClass());
        for (V8Flags v8Flags : new V8Flags[]{NodeRuntimeOptions.V8_FLAGS, V8RuntimeOptions.V8_FLAGS}) {
            if (!v8Flags.isSealed()) {
                v8Flags.setAllowNativesSyntax(true);
                v8Flags.setExposeGC(false);
                v8Flags.setExposeInspectorScripts(true);
                v8Flags.setMaxHeapSize(768);
                v8Flags.setMaxOldSpaceSize(512);
                v8Flags.setUseStrict(true);
                v8Flags.setTrackRetainingPath(true);
            }
        }
    }

    @AfterEach
    protected void afterEach() throws Exception {
        nodeRuntime.lowMemoryNotification();
        v8Runtime.lowMemoryNotification();
        assertEquals(0, nodeRuntime.getCallbackContextCount(),
                "Callback context count should be 0 after test case is ended.");
        assertEquals(0, nodeRuntime.getReferenceCount(),
                "Reference count should be 0 before test case is started.");
        assertEquals(0, v8Runtime.getCallbackContextCount(),
                "Callback context count should be 0 after test case is ended.");
        assertEquals(0, v8Runtime.getReferenceCount(),
                "Reference count should be 0 before test case is started.");
        nodeRuntime.close();
        v8Runtime.close();
        assertEquals(0, V8Host.getNodeInstance().getV8RuntimeCount());
        assertEquals(0, V8Host.getV8Instance().getV8RuntimeCount());
    }

    @BeforeEach
    protected void beforeEach() throws Exception {
        if (!javetVersionLogged) {
            logger.info("Javet version is {}.", JavetLibLoader.LIB_VERSION);
            javetVersionLogged = true;
        }
        nodeRuntime = V8Host.getNodeInstance().createV8Runtime();
        v8Runtime = V8Host.getV8Instance().createV8Runtime();
        runtimes = List.of(v8Runtime, nodeRuntime);
        stopWatch = new StopWatch();
    }
}
