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
import com.caoccao.javet.values.reference.V8ValueArray;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.LongAdder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestV8ValueArray extends BaseTestJavet {
    @Test
    public void testForEachWithUniConsumer() {
        final int arrayLength = 1000;
        final long loopCount = 1000L;
        runtimes.forEach(runtime -> {
            try (V8ValueArray v8ValueArray = runtime.getExecutor(
                    "Array.from({ length: " + arrayLength + " }, (_, i) => i)").execute()) {
                LongAdder longAdder = new LongAdder();
                stopWatch.reset();
                stopWatch.start();
                for (long i = 0; i < loopCount; i++) {
                    v8ValueArray.forEach((V8ValueInteger v8Value) -> longAdder.add(v8Value.getValue()));
                }
                stopWatch.stop();
                assertEquals(arrayLength * (arrayLength - 1) / 2L * loopCount, longAdder.longValue(), "Count should match.");
                final long tps = loopCount * 1000L / stopWatch.getTime();
                logger.info(
                        "[{}] V8ValueArrayForEachWithUniConsumer: {} calls in {}ms. TPS is {}.",
                        StringUtils.leftPad(runtime.getJSRuntimeType().getName(), 4), loopCount, stopWatch.getTime(), tps);
            } catch (Throwable t) {
                fail(t);
            }
        });
    }

    @Test
    public void testForEachWithUniIndexedConsumer() {
        final int arrayLength = 1000;
        final long loopCount = 1000L;
        runtimes.forEach(runtime -> {
            try (V8ValueArray v8ValueArray = runtime.getExecutor(
                    "Array.from({ length: " + arrayLength + " }, (_, i) => i)").execute()) {
                LongAdder longAdder = new LongAdder();
                stopWatch.reset();
                stopWatch.start();
                for (long i = 0; i < loopCount; i++) {
                    v8ValueArray.forEach((int index, V8ValueInteger value) -> longAdder.add(value.getValue()));
                }
                stopWatch.stop();
                assertEquals(arrayLength * (arrayLength - 1) / 2L * loopCount, longAdder.longValue(), "Count should match.");
                final long tps = loopCount * 1000L / stopWatch.getTime();
                logger.info(
                        "[{}] V8ValueArrayForEachWithUniIndexedConsumer: {} calls in {}ms. TPS is {}.",
                        StringUtils.leftPad(runtime.getJSRuntimeType().getName(), 4), loopCount, stopWatch.getTime(), tps);
            } catch (Throwable t) {
                fail(t);
            }
        });
    }
}
