/**
 * Copyright (C) 2014 WaveMaker, Inc. All rights reserved.
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
package com.wavemaker.studio.common.util;

/**
 * @author Simon Toens
 */
public class Tuple {

    private Tuple() {
    }

    /**
     * Convenience methods for creating tuple instances.
     */
    public static <T1, T2> Two<T1, T2> tuple(T1 v1, T2 v2) {
        return new Two<T1, T2>(v1, v2);
    }

    public static <T1, T2, T3> Three<T1, T2, T3> tuple(T1 v1, T2 v2, T3 v3) {
        return new Three<T1, T2, T3>(v1, v2, v3);
    }

    public static class Two<T1, T2> {

        public T1 v1;

        public T2 v2;

        public Two(T1 v1, T2 v2) {
            this.v1 = v1;
            this.v2 = v2;
        }

        protected String toString(Object... values) {
            return "(" + ObjectUtils.toString(values, ", ") + ")";
        }

        @Override
        public String toString() {
            return toString(this.v1, this.v2);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Two two = (Two) o;

            if (v1 != null ? !v1.equals(two.v1) : two.v1 != null) return false;
            if (v2 != null ? !v2.equals(two.v2) : two.v2 != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = v1 != null ? v1.hashCode() : 0;
            result = 31 * result + (v2 != null ? v2.hashCode() : 0);
            return result;
        }
    }

    public static class Three<T1, T2, T3> extends Two<T1, T2> {

        public final T3 v3;

        public Three(T1 v1, T2 v2, T3 v3) {
            super(v1, v2);
            this.v3 = v3;
        }

        @Override
        public String toString() {
            return toString(this.v1, this.v2, this.v3);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            Three three = (Three) o;

            if (v3 != null ? !v3.equals(three.v3) : three.v3 != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (v3 != null ? v3.hashCode() : 0);
            return result;
        }
    }

    public static class Four<T1, T2, T3, T4> extends Three<T1, T2, T3> {

        public final T4 v4;

        public Four(T1 v1, T2 v2, T3 v3, T4 v4) {
            super(v1, v2, v3);
            this.v4 = v4;
        }

        @Override
        public String toString() {
            return toString(this.v1, this.v2, this.v3, this.v4);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            Four four = (Four) o;

            if (v4 != null ? !v4.equals(four.v4) : four.v4 != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (v4 != null ? v4.hashCode() : 0);
            return result;
        }
    }
}
