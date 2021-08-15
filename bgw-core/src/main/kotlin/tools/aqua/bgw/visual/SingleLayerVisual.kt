/*
 *    Copyright 2021 The BoardGameWork Authors
 *    SPDX-License-Identifier: Apache-2.0
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

@file:Suppress("unused")

package tools.aqua.bgw.visual

import tools.aqua.bgw.observable.LimitedDoubleProperty

/**
 * Baseclass for single layer visuals.
 */
sealed class SingleLayerVisual : Visual() {
    /**
     * Property for the [transparency] / alpha channel for this [Visual].
     *
     * Must be set between 0 (full transparent) and 1 (non-transparent / solid). Default: 1.
     *
     * @see transparency
     */
    val transparencyProperty: LimitedDoubleProperty = LimitedDoubleProperty(0, 1, 1)

    /**
     * The [transparency] / alpha channel for this [Visual].
     *
     * Must be set between 0 (full transparent) and 1 (non-transparent / solid). Default: 1.
     *
     * @see transparencyProperty
     */
    var transparency: Double
        get() = transparencyProperty.value
        set(value) {
            transparencyProperty.value = value
        }
}