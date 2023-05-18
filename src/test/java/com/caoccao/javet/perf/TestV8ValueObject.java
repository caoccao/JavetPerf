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

import com.caoccao.javet.values.primitive.V8ValueInteger;
import com.caoccao.javet.values.primitive.V8ValueString;
import com.caoccao.javet.values.reference.V8ValueObject;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.LongAdder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestV8ValueObject extends BaseTestJavet {
    @Test
    public void testForEachWithBiConsumer() {
        final int keyLength = 1000;
        final long loopCount = 1000L;
        runtimes.forEach(runtime -> {
            try (V8ValueObject v8ValueObject = runtime.getExecutor(
                    "const a = {};" +
                            "Array.from({ length: " + keyLength + " }, (_, i) => { a[' ' + i] = i; return i; });" +
                            "a;").execute()) {
                stopWatch.reset();
                stopWatch.start();
                for (long i = 0; i < loopCount; i++) {
                    v8ValueObject.forEach((V8ValueString key, V8ValueInteger value) -> value.getValue());
                }
                stopWatch.stop();
                final long tps = loopCount * 1000L / stopWatch.getTime();
                logger.info(
                        "[{}] V8ValueObjectForEachWithBiConsumer: {} calls in {}ms. TPS is {}.",
                        StringUtils.leftPad(runtime.getJSRuntimeType().getName(), 4), loopCount, stopWatch.getTime(), tps);
            } catch (Throwable t) {
                fail(t);
            }
        });
    }

    @Test
    public void testForEachWithBiIndexedConsumer() {
        final int keyLength = 1000;
        final long loopCount = 1000L;
        runtimes.forEach(runtime -> {
            try (V8ValueObject v8ValueObject = runtime.getExecutor(
                    "const a = {};" +
                            "Array.from({ length: " + keyLength + " }, (_, i) => { a[' ' + i] = i; return i; });" +
                            "a;").execute()) {
                stopWatch.reset();
                stopWatch.start();
                for (long i = 0; i < loopCount; i++) {
                    v8ValueObject.forEach((int index, V8ValueString key, V8ValueInteger value) -> value.getValue());
                }
                stopWatch.stop();
                final long tps = loopCount * 1000L / stopWatch.getTime();
                logger.info(
                        "[{}] V8ValueObjectForEachWithBiIndexedConsumer: {} calls in {}ms. TPS is {}.",
                        StringUtils.leftPad(runtime.getJSRuntimeType().getName(), 4), loopCount, stopWatch.getTime(), tps);
            } catch (Throwable t) {
                fail(t);
            }
        });
    }

    @Test
    public void testForEachWithUniConsumer() {
        final int keyLength = 1000;
        final long loopCount = 1000L;
        runtimes.forEach(runtime -> {
            try (V8ValueObject v8ValueObject = runtime.getExecutor(
                    "const a = {};" +
                            "Array.from({ length: " + keyLength + " }, (_, i) => { a[' ' + i] = i; return i; });" +
                            "a;").execute()) {
                stopWatch.reset();
                stopWatch.start();
                for (long i = 0; i < loopCount; i++) {
                    v8ValueObject.forEach((V8ValueString key) -> key.getValue().substring(1));
                }
                stopWatch.stop();
                final long tps = loopCount * 1000L / stopWatch.getTime();
                logger.info(
                        "[{}] V8ValueObjectForEachWithUniConsumer: {} calls in {}ms. TPS is {}.",
                        StringUtils.leftPad(runtime.getJSRuntimeType().getName(), 4), loopCount, stopWatch.getTime(), tps);
            } catch (Throwable t) {
                fail(t);
            }
        });
    }

    @Test
    public void testForEachWithUniIndexedConsumer() {
        final int keyLength = 1000;
        final long loopCount = 1000L;
        runtimes.forEach(runtime -> {
            try (V8ValueObject v8ValueObject = runtime.getExecutor(
                    "const a = {};" +
                            "Array.from({ length: " + keyLength + " }, (_, i) => { a[' ' + i] = i; return i; });" +
                            "a;").execute()) {
                stopWatch.reset();
                stopWatch.start();
                for (long i = 0; i < loopCount; i++) {
                    v8ValueObject.forEach((int index, V8ValueString key) -> key.getValue().substring(1));
                }
                stopWatch.stop();
                final long tps = loopCount * 1000L / stopWatch.getTime();
                logger.info(
                        "[{}] V8ValueObjectForEachWithUniIndexedConsumer: {} calls in {}ms. TPS is {}.",
                        StringUtils.leftPad(runtime.getJSRuntimeType().getName(), 4), loopCount, stopWatch.getTime(), tps);
            } catch (Throwable t) {
                fail(t);
            }
        });
    }

    @Test
    public void testGetBoolean() {
        final long loopCount = 1000_000L;
        runtimes.forEach(runtime -> {
            try (V8ValueObject v8ValueObject = runtime.createV8ValueObject()) {
                v8ValueObject.set("a", true);
                stopWatch.reset();
                stopWatch.start();
                for (long i = 0; i < loopCount; i++) {
                    v8ValueObject.getBoolean("a");
                }
                stopWatch.stop();
                final long tps = loopCount * 1000L / stopWatch.getTime();
                logger.info(
                        "[{}] V8ValueObjectGetBoolean: {} calls in {}ms. TPS is {}.",
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
            try (V8ValueObject v8ValueObject = runtime.createV8ValueObject()) {
                v8ValueObject.set("a", 1.23D);
                stopWatch.reset();
                stopWatch.start();
                for (long i = 0; i < loopCount; i++) {
                    v8ValueObject.getDouble("a");
                }
                stopWatch.stop();
                final long tps = loopCount * 1000L / stopWatch.getTime();
                logger.info(
                        "[{}] V8ValueObjectGetDouble: {} calls in {}ms. TPS is {}.",
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
            try (V8ValueObject v8ValueObject = runtime.createV8ValueObject()) {
                v8ValueObject.set("a", 1000);
                stopWatch.reset();
                stopWatch.start();
                for (long i = 0; i < loopCount; i++) {
                    v8ValueObject.getInteger("a");
                }
                stopWatch.stop();
                final long tps = loopCount * 1000L / stopWatch.getTime();
                logger.info(
                        "[{}] V8ValueObjectGetInteger: {} calls in {}ms. TPS is {}.",
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
            try (V8ValueObject v8ValueObject = runtime.createV8ValueObject()) {
                v8ValueObject.set("a", 1000L);
                stopWatch.reset();
                stopWatch.start();
                for (long i = 0; i < loopCount; i++) {
                    v8ValueObject.getLong("a");
                }
                stopWatch.stop();
                final long tps = loopCount * 1000L / stopWatch.getTime();
                logger.info(
                        "[{}] V8ValueObjectGetLong: {} calls in {}ms. TPS is {}.",
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
            try (V8ValueObject v8ValueObject = runtime.createV8ValueObject()) {
                v8ValueObject.set("a", "a");
                stopWatch.reset();
                stopWatch.start();
                for (long i = 0; i < loopCount; i++) {
                    v8ValueObject.getString("a");
                }
                stopWatch.stop();
                final long tps = loopCount * 1000L / stopWatch.getTime();
                logger.info(
                        "[{}] V8ValueObjectGetString: {} calls in {}ms. TPS is {}.",
                        StringUtils.leftPad(runtime.getJSRuntimeType().getName(), 4), loopCount, stopWatch.getTime(), tps);
            } catch (Throwable t) {
                fail(t);
            }
        });
    }
}
