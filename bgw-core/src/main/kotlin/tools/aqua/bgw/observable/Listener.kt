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

package tools.aqua.bgw.observable

/**
 * Observable interface for observable Properties.
 */
@FunctionalInterface
fun interface IObservable {
	/**
	 * Indicates property update.
	 */
	fun update()
}

/**
 * Observable interface for observable properties with values.
 */
@FunctionalInterface
fun interface IValueObservable<T> {
	/**
	 * Indicates property update.
	 *
	 * @param oldValue Old value of property.
	 * @param newValue New value of property.
	 */
	fun update(oldValue: T, newValue: T)
}

/**
 * Basic observable.
 *
 * @constructor Empty constructor.
 */
abstract class Observable {
	/**
	 * Holds all listeners on this property.
	 */
	private val listeners: MutableList<IObservable> = mutableListOf()
	
	/**
	 * Used by renderer to listen on important properties for visualization.
	 */
	private var guiListenerHandler: IObservable? = null
	
	/**
	 * Used by renderer to listen on important properties for visualization.
	 */
	internal var guiListener: (() -> Unit)? = null
		set(value) {
			guiListenerHandler = if (value == null) null else IObservable(value)
		}
	
	/**
	 * Used by BGW framework containers to manage children.
	 *
	 * Should only be set by direct parent.
	 */
	private var internalListenerHandler: IObservable? = null
	
	/**
	 * Used by BGW framework containers to manage children.
	 *
	 * Should only be set by direct parent.
	 */
	internal var internalListener: (() -> Unit)? = null
		set(value) {
			internalListenerHandler = if (value == null) null else IObservable(value)
		}
	
	/**
	 * Sets [guiListener] and calls [IObservable.update].
	 *
	 * @param listener Listener to add and notify.
	 */
	internal fun setGUIListenerAndInvoke(listener: () -> Unit) {
		guiListener = listener
		guiListenerHandler?.update()
	}
	
	/**
	 * Sets [internalListener] and calls [IObservable.update].
	 *
	 * @param listener Listener to add and notify.
	 */
	internal fun setInternalListenerAndInvoke(listener: () -> Unit) {
		internalListener = listener
		internalListenerHandler?.update()
	}
	
	/**
	 * Adds a [listener] and calls [IObservable.update] on this new listener.
	 *
	 * @param listener Listener to add and notify.
	 */
	fun addListenerAndInvoke(listener: IObservable) {
		listeners.add(listener)
		listener.update()
	}
	
	/**
	 * Adds a [listener] silently.
	 *
	 * @param listener Listener to add
	 */
	fun addListener(listener: IObservable) {
		listeners.add(listener)
	}
	
	/**
	 * Removes a [listener].
	 *
	 * @param listener Listener to remove.
	 *
	 * @return `true` if the listener has been successfully removed, `false` if it was not found.
	 */
	fun removeListener(listener: IObservable): Boolean = listeners.remove(listener)
	
	/**
	 * Removes all listeners.
	 */
	fun clearListeners() {
		listeners.clear()
	}
	
	/**
	 * Notifies [guiListener] by calling [IObservable.update].
	 */
	internal fun notifyGUIListener() {
		guiListenerHandler?.update()
	}
	
	/**
	 * Notifies [internalListener] by calling [IObservable.update].
	 */
	internal fun notifyInternalListener() {
		internalListenerHandler?.update()
	}
	
	/**
	 * Notifies all [listeners] by calling [IObservable.update].
	 */
	fun notifyChange() {
		listeners.forEach { it.update() }
		internalListenerHandler?.update()
		guiListenerHandler?.update()
	}
}

/**
 * Basic observable with value.
 *
 * @constructor Empty constructor.
 */
abstract class ValueObservable<T> {
	/**
	 * Holds all listeners on this property.
	 */
	private val listeners: MutableList<IValueObservable<T>> = mutableListOf()
	
	/**
	 * Used by renderer to listen on important properties for visualization.
	 */
	private var guiListenerHandler: IValueObservable<T>? = null
	
	/**
	 * Used by renderer to listen on important properties for visualization.
	 */
	internal var guiListener: ((T, T) -> Unit)? = null
		set(value) {
			guiListenerHandler = if (value == null) null else IValueObservable(value)
		}
	
	/**
	 * Used by BGW framework containers to manage children.
	 *
	 * Should only be set by direct parent.
	 */
	private var internalListenerHandler: IValueObservable<T>? = null
	
	/**
	 * Used by BGW framework containers to manage children.
	 *
	 * Should only be set by direct parent.
	 */
	internal var internalListener: ((T, T) -> Unit)? = null
		set(value) {
			internalListenerHandler = if (value == null) null else IValueObservable(value)
		}
	
	/**
	 * Sets [guiListener] and calls [IValueObservable.update].
	 *
	 * @param initialValue Initial value to notify.
	 * @param listener Listener to add and notify.
	 */
	internal fun setGUIListenerAndInvoke(initialValue: T, listener: ((T, T) -> Unit)) {
		guiListener = listener
		guiListenerHandler?.update(initialValue, initialValue)
	}
	
	/**
	 * Sets [internalListener] and calls [IValueObservable.update].
	 *
	 * @param initialValue Initial value to notify.
	 * @param listener Listener to add and notify.
	 */
	internal fun setInternalListenerAndInvoke(initialValue: T, listener: ((T, T) -> Unit)) {
		internalListener = listener
		internalListenerHandler?.update(initialValue, initialValue)
	}
	
	/**
	 * Adds a [listener] and calls [IValueObservable.update] on this new listener with given initial value.
	 *
	 * @param initialValue Initial value to notify.
	 * @param listener Listener to add and notify.
	 */
	fun addListenerAndInvoke(initialValue: T, listener: ((T, T) -> Unit)) {
		listeners.add(listener)
		listener.invoke(initialValue, initialValue)
	}
	
	/**
	 * Adds a [listener] silently.
	 *
	 * @param listener listener to add.
	 */
	fun addListener(listener: ((T, T) -> Unit)) {
		listeners.add(listener)
	}
	
	/**
	 * Removes a [listener].
	 *
	 * @param listener listener to remove.
	 *
	 * @return `true` if the listener has been successfully removed, `false` if it was not found.
	 */
	fun removeListener(listener: ((T, T) -> Unit)): Boolean = listeners.remove(listener)
	
	/**
	 * Removes all listeners.
	 */
	fun clearListeners() {
		listeners.clear()
	}
	
	/**
	 * Notifies [guiListener] by calling [IValueObservable.update].
	 *
	 * @param oldValue Old value to notify.
	 * @param newValue New value to notify.
	 */
	internal fun notifyGUIListener(oldValue: T, newValue: T) {
		guiListenerHandler?.update(oldValue, newValue)
	}
	
	/**
	 * Notifies [internalListener] by calling [IValueObservable.update].
	 *
	 * @param oldValue Old value to notify.
	 * @param newValue New value to notify.
	 */
	internal fun notifyInternalListener(oldValue: T, newValue: T) {
		internalListenerHandler?.update(oldValue, newValue)
	}
	
	/**
	 * Notifies all [listeners] by calling [IValueObservable.update].
	 *
	 * @param oldValue Old value to notify.
	 * @param newValue New value to notify.
	 */
	internal fun notifyChange(oldValue: T, newValue: T) {
		listeners.forEach { it.update(oldValue, newValue) }
		internalListenerHandler?.update(oldValue, newValue)
		guiListenerHandler?.update(oldValue, newValue)
	}
}