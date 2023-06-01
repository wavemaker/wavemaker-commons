/*******************************************************************************
 * Copyright (C) 2022-2023 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.wavemaker.commons.indexing.model;

import java.util.ArrayList;
import java.util.List;

public class Query {
    private BoolConditionClause clause;
    private List<MatchPhrase> matchPhrases = new ArrayList<>();

    public Query() {
    }

    public Query(BoolConditionClause clause, List<MatchPhrase> matchPhrase) {
        this.clause = clause;
        this.matchPhrases = matchPhrase;
    }

    public BoolConditionClause getClause() {
        return clause;
    }

    public void setClause(BoolConditionClause clause) {
        this.clause = clause;
    }

    public List<MatchPhrase> getMatchPhrases() {
        return matchPhrases;
    }

    public void addMatchPhrases(MatchPhrase matchPhrases) {
        this.matchPhrases.add(matchPhrases);
    }
}
