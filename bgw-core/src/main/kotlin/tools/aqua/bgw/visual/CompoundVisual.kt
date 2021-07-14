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

@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package tools.aqua.bgw.visual

import tools.aqua.bgw.observable.ObservableArrayList
import tools.aqua.bgw.observable.ObservableList

/**
 * A compound visual containing stacked [SingleLayerVisual]s.
 * Hint: Each [SingleLayerVisual] besides the bottom should have opacity in order to display all layers properly.
 *
 * @param children children [SingleLayerVisual]s in the order they should be displayed, where the first [SingleLayerVisual]
 * gets displayed at the bottom of the stack.
 */
open class CompoundVisual(children: List<SingleLayerVisual>) : Visual() {
	
	/**
	 * [ObservableList] for the [children] of this stack.
	 * The first [SingleLayerVisual] gets displayed at the bottom of the stack.
	 */
	val childrenProperty: ObservableArrayList<SingleLayerVisual> = ObservableArrayList(children)
	
	/**
	 * The [children] of this stack.
	 * The first [SingleLayerVisual] gets displayed at the bottom of the stack.
	 */
	var children: List<SingleLayerVisual>
		get() = childrenProperty.toList()
		set(value) {
			childrenProperty.clear()
			childrenProperty.addAll(value)
		}
	
	/**
	 * [CompoundVisual] constructor with vararg parameter.
	 *
	 * @param children children [SingleLayerVisual]s in the order they should be displayed, where the first [SingleLayerVisual]
	 * gets displayed at the bottom of the stack.
	 */
	constructor(vararg children: SingleLayerVisual) : this(children.toList())
	
	init {
		childrenProperty.internalListener = { notifyGUIListener() }
	}
	
	/**
	 * Copies this [CompoundVisual] to a new object recursively including children.
	 */
	override fun copy(): CompoundVisual = CompoundVisual(children.map { it.copy() as SingleLayerVisual }.toList())
}