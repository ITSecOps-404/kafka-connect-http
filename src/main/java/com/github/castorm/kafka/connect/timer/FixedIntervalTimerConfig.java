package com.github.castorm.kafka.connect.timer;

/*-
 * #%L
 * kafka-connect-http
 * %%
 * Copyright (C) 2020 CastorM
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import lombok.Getter;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

import static org.apache.kafka.common.config.ConfigDef.Importance.HIGH;
import static org.apache.kafka.common.config.ConfigDef.Type.LONG;

@Getter
public class FixedIntervalTimerConfig extends AbstractConfig {

    @Deprecated
    private static final String DEPRECATED_INTERVAL_MILLIS = "http.throttler.interval.millis";
    static final String TIMER_INTERVAL_MILLIS = "http.timer.interval.millis";
    private static final long DEFAULT_TAIL_INTERVAL_MILLIS = 60000L;

    private final Long pollIntervalMillis;

    public FixedIntervalTimerConfig(Map<String, ?> originals) {
        super(config(), originals);
        pollIntervalMillis = getFromPropertyOrDeprecatedProperty(TIMER_INTERVAL_MILLIS, DEPRECATED_INTERVAL_MILLIS, DEFAULT_TAIL_INTERVAL_MILLIS);
    }

    private Long getFromPropertyOrDeprecatedProperty(String property, String deprecatedProperty, Long defaultValue) {
        Long value = getLong(property);
        if (!value.equals(defaultValue)) {
            return value;
        } else {
            return getLong(deprecatedProperty);
        }
    }

    public static ConfigDef config() {
        return new ConfigDef()
                .define(TIMER_INTERVAL_MILLIS, LONG, DEFAULT_TAIL_INTERVAL_MILLIS, HIGH, "Timer Interval Millis")
                .define(DEPRECATED_INTERVAL_MILLIS, LONG, DEFAULT_TAIL_INTERVAL_MILLIS, HIGH, "(Deprecated) Timer Interval Millis");
    }
}
