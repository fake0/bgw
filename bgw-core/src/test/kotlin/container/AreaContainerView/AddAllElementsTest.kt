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

package container.AreaContainerView

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tools.aqua.bgw.elements.container.AreaContainerView
import tools.aqua.bgw.elements.gameelements.TokenView
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color
import java.lang.IllegalArgumentException
import kotlin.test.assertContains
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AddAllElementsTest: AreaContainerViewTestBase() {

    @Test
    @DisplayName("Add an empty list")
    fun addAllElementsEmptyList() {
        //add empty list
        tokenAreaContainer.addAll(listOf())
        assertTrue { tokenAreaContainer.elements.isEmpty() }
    }

    @Test
    @DisplayName("Add a list containing two ElementViews")
    fun addAllElementsNonEmptyList() {
        //add list with elements
        tokenAreaContainer.addAll(listOf(redToken, greenToken))
        assertContains(tokenAreaContainer.elements, redToken)
        assertContains(tokenAreaContainer.elements, greenToken)
    }

    @Test
    @DisplayName("Add a list containing two ElementViews, where one is already contained")
    fun addAllElementsAlreadyContained() {
        //add list with one element already contained in tokenAreaContainer
        tokenAreaContainer.addAll(listOf(redToken, greenToken))
        assertThrows<IllegalArgumentException> { tokenAreaContainer.addAll(listOf(blueToken, redToken)) }
        assertContains(tokenAreaContainer.elements, blueToken)
        //add list with one element already contained in another Container
        val cyanToken = TokenView(visual = ColorVisual(Color.CYAN))
        val otherAreaContainer = AreaContainerView<TokenView>()
        otherAreaContainer.add(cyanToken)
        assertThrows<IllegalArgumentException> { tokenAreaContainer.addAll(listOf(cyanToken)) }
        assertFalse { tokenAreaContainer.elements.contains(cyanToken) }
        assertContains(otherAreaContainer.elements, cyanToken)
    }

    @Test
    @DisplayName("Add an empty list")
    fun addAllElementsVarArgsEmptyList() {
        tokenAreaContainer.addAll()
        assertTrue { tokenAreaContainer.elements.isEmpty() }
    }

    @Test
    @DisplayName("Add a list containing two ElementViews")
    fun addAllElementsVarArgsNonEmptyList() {
        //add list with elements
        tokenAreaContainer.addAll(listOf(redToken, greenToken))
        assertContains(tokenAreaContainer.elements, redToken)
        assertContains(tokenAreaContainer.elements, greenToken)
    }

    @Test
    @DisplayName("Add a list containing two ElementViews, where one is already contained")
    fun addAllElementsVarArgsAlreadyContained() {
        //add list with one element already contained in tokenAreaContainer
        tokenAreaContainer.addAll(redToken, greenToken)
        assertThrows<IllegalArgumentException> { tokenAreaContainer.addAll(blueToken, redToken) }
        assertContains(tokenAreaContainer.elements, blueToken)
        //add list with one element already contained in another Container
        val cyanToken = TokenView(visual = ColorVisual(Color.CYAN))
        val otherAreaContainer = AreaContainerView<TokenView>()
        otherAreaContainer.add(cyanToken)
        assertThrows<IllegalArgumentException> { tokenAreaContainer.addAll(cyanToken) }
        assertFalse { tokenAreaContainer.elements.contains(cyanToken) }
        assertContains(otherAreaContainer.elements, cyanToken)
    }
}