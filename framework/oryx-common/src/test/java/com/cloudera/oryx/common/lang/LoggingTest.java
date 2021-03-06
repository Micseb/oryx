/*
 * Copyright (c) 2014, Cloudera, Inc. and Intel Corp. All Rights Reserved.
 *
 * Cloudera, Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * This software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for
 * the specific language governing permissions and limitations under the
 * License.
 */

package com.cloudera.oryx.common.lang;

import java.io.IOException;

import org.junit.Test;

import com.cloudera.oryx.common.OryxTest;

public final class LoggingTest extends OryxTest {

  @Test(expected = IllegalStateException.class)
  public void testLoggingRunnableException() {
    new LoggingRunnable() {
      @Override
      public void doRun() throws IOException {
        throw buildIOE();
      }
    }.run();
  }

  @Test
  public void testLoggingCallable() {
    Integer result = new LoggingCallable<Integer>() {
      @Override
      public Integer doCall() {
        return 3;
      }
    }.call();
    assertEquals(3, result.intValue());
  }

  @Test(expected = IllegalStateException.class)
  public void testLoggingCallableException() {
    new LoggingCallable<Void>() {
      @Override
      public Void doCall() throws IOException {
        throw buildIOE();
      }
    }.call();
  }

  @Test(expected = IllegalStateException.class)
  public void testLoggingVoidCallableException() {
    new LoggingVoidCallable() {
      @Override
      public void doCall() throws IOException {
        throw buildIOE();
      }
    }.call();
  }

  private static IOException buildIOE() {
    IOException ioe = new IOException("It's safe to ignore this exception");
    ioe.setStackTrace(new StackTraceElement[0]); // to not pollute the logs
    return ioe;
  }

}
