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

import com.caoccao.javet.values.reference.V8ValueMap;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class TestV8ValueMap extends BaseTestJavet {
    @Test
    public void testGetBoolean() {
        final long loopCount = 1000_000L;
        runtimes.forEach(runtime -> {
            try (V8ValueMap v8ValueMap = runtime.createV8ValueMap()) {
                v8ValueMap.set("a", true);
                stopWatch.reset();
                stopWatch.start();
                for (long i = 0; i < loopCount; i++) {
                    v8ValueMap.getBoolean("a");
                }
                stopWatch.stop();
                final long tps = loopCount * 1000L / stopWatch.getTime();
                logger.info(
                        "[{}] V8ValueMapGetBoolean: {} calls in {}ms. TPS is {}.",
                        StringUtils.leftPad(runtime.getJSRuntimeType().getName(), 4), loopCount, stopWatch.getTime(), tps);
            } catch (Throwable t) {
                fail(t);
            }
        });
    }

    @Test
    public void testGetDouble() {
        final long loopCount = 1000_000L;
        runtimes.forEach(runtime -> {
            try (V8ValueMap v8ValueMap = runtime.createV8ValueMap()) {
                v8ValueMap.set("a", 1.23D);
                stopWatch.reset();
                stopWatch.start();
                for (long i = 0; i < loopCount; i++) {
                    v8ValueMap.getDouble("a");
                }
                stopWatch.stop();
                final long tps = loopCount * 1000L / stopWatch.getTime();
                logger.info(
                        "[{}] V8ValueMapGetDouble: {} calls in {}ms. TPS is {}.",
                        StringUtils.leftPad(runtime.getJSRuntimeType().getName(), 4), loopCount, stopWatch.getTime(), tps);
            } catch (Throwable t) {
                fail(t);
            }
        });
    }

    @Test
    public void testGetInteger() {
        final long loopCount = 1000_000L;
        runtimes.forEach(runtime -> {
            try (V8ValueMap v8ValueMap = runtime.createV8ValueMap()) {
                v8ValueMap.set("a", 1000);
                stopWatch.reset();
                stopWatch.start();
                for (long i = 0; i < loopCount; i++) {
                    v8ValueMap.getInteger("a");
                }
                stopWatch.stop();
                final long tps = loopCount * 1000L / stopWatch.getTime();
                logger.info(
                        "[{}] V8ValueMapGetInteger: {} calls in {}ms. TPS is {}.",
                        StringUtils.leftPad(runtime.getJSRuntimeType().getName(), 4), loopCount, stopWatch.getTime(), tps);
            } catch (Throwable t) {
                fail(t);
            }
        });
    }

    @Test
    public void testGetLong() {
        final long loopCount = 1000_000L;
        runtimes.forEach(runtime -> {
            try (V8ValueMap v8ValueMap = runtime.createV8ValueMap()) {
                v8ValueMap.set("a", 1000L);
                stopWatch.reset();
                stopWatch.start();
                for (long i = 0; i < loopCount; i++) {
                    v8ValueMap.getLong("a");
                }
                stopWatch.stop();
                final long tps = loopCount * 1000L / stopWatch.getTime();
                logger.info(
                        "[{}] V8ValueMapGetLong: {} calls in {}ms. TPS is {}.",
                        StringUtils.leftPad(runtime.getJSRuntimeType().getName(), 4), loopCount, stopWatch.getTime(), tps);
            } catch (Throwable t) {
                fail(t);
            }
        });
    }

    @Test
    public void testGetString() {
        final long loopCount = 1000_000L;
        runtimes.forEach(runtime -> {
            try (V8ValueMap v8ValueMap = runtime.createV8ValueMap()) {
                v8ValueMap.set("a", "a");
                stopWatch.reset();
                stopWatch.start();
                for (long i = 0; i < loopCount; i++) {
                    v8ValueMap.getString("a");
                }
                stopWatch.stop();
                final long tps = loopCount * 1000L / stopWatch.getTime();
                logger.info(
                        "[{}] V8ValueMapGetString: {} calls in {}ms. TPS is {}.",
                        StringUtils.leftPad(runtime.getJSRuntimeType().getName(), 4), loopCount, stopWatch.getTime(), tps);
            } catch (Throwable t) {
                fail(t);
            }
        });
    }
}
