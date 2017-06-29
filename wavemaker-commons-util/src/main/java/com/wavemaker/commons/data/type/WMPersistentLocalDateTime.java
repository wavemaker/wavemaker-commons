/**
 * Copyright © 2013 - 2017 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.commons.data.type;

/**
 * Created by sunilp on 21/7/15.
 */

import java.sql.Timestamp;

import org.jadira.usertype.spi.shared.AbstractSingleColumnUserType;
import org.joda.time.LocalDateTime;

import com.wavemaker.commons.data.mapper.WMDateColumnLocalDateTimeMapper;

/**
 * @author Uday Shankar
 */
public class WMPersistentLocalDateTime extends AbstractSingleColumnUserType<LocalDateTime, Timestamp, WMDateColumnLocalDateTimeMapper> {

    private static final long serialVersionUID = 1651096099046282660L;
}
