package examples.components.uicomponents

import tools.aqua.bgw.components.uicomponents.*
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.BoardGameApplication
import tools.aqua.bgw.core.MenuScene
import java.awt.Color

fun main() {
	UIComponentExample()
}

class UIComponentExample : BoardGameApplication("UIComponent Example") {
	private val menuScene = MenuScene(width = 800)

	private val outputLabel = Label(posX = 50, posY = 50, width = 300, text = "I am a label.")

	init {
		menuScene.addComponents(outputLabel)

		val button = Button(posX = 450, posY = 50, text = "I am a button.")
		button.onMouseClicked = {
			outputLabel.text = "Someone pressed the Button!"
		}
		menuScene.addComponents(button)

		val checkBox =
			CheckBox(posX = 50, posY = 150, width = 300, text = "I am a CheckBox", alignment = Alignment.CENTER_LEFT)
		checkBox.allowIndeterminate = true
		checkBox.checked = true
		checkBox.checkedProperty.addListener { _, newValue ->
			outputLabel.text = if (newValue) "The check box is checked!" else "The check box is unchecked!"
		}
		checkBox.indeterminateProperty.addListener { _, newValue ->
			if (newValue) outputLabel.text = "The check box is indeterminate!"
		}
		menuScene.addComponents(checkBox)

		val colorPicker = ColorPicker(posX = 450, posY = 150, width = 300)
		colorPicker.selectedColor = Color.YELLOW
		colorPicker.selectedColorProperty.addListener { _, newValue ->
			outputLabel.text = "Selected color is ${newValue.toString().dropWhile { it != '[' }}"
		}
		menuScene.addComponents(colorPicker)

		val comboBox = ComboBox<Double>(posX = 50, 300, width = 300, prompt = "I am a combo box.")
		comboBox.items = mutableListOf(0.0, 1.0, 2.0)
		comboBox.formatFunction = {
			"Option ${it.toInt()}"
		}
		comboBox.selectedItemProperty.addListener { _, newValue ->
			outputLabel.text = "Combo box selection is : $newValue"
		}
		menuScene.addComponents(comboBox)

		val progressBar = ProgressBar(posX = 450, posY = 300, width = 300, progress = 0.5, barColor = Color.GREEN)
		outputLabel.textProperty.addListener { _, _ ->
			progressBar.progress = if (progressBar.progress + 0.05 > 1.0) 0.0 else progressBar.progress + 0.05
		}
		progressBar.progressProperty.addListener { _, newValue ->
			when {
				newValue > 0.8 -> progressBar.barColor = Color.RED
				newValue > 0.5 -> progressBar.barColor = Color.GREEN
				else -> progressBar.barColor = Color.BLUE
			}
		}
		menuScene.addComponents(progressBar)

		val toggleGroup = ToggleGroup()
		val radioButton = RadioButton(posX = 50, posY = 450, toggleGroup = toggleGroup)
		val radioLabel = Label(
			posX = radioButton.posX + radioButton.width,
			posY = radioButton.posY,
			width = 300 - radioButton.width,
			height = radioButton.height,
			text = "This is a radio button.",
			alignment = Alignment.CENTER_LEFT
		).apply { isWrapText = true }
		radioButton.selectedProperty.addListener { _, newValue ->
			radioLabel.text = if (newValue) "This is a selected radio button!" else "This is a deselected radio button!"
		}
		val toggleButton = ToggleButton(posX = 450, posY = 450, toggleGroup = toggleGroup)
		val toggleLabel = Label(
			posX = toggleButton.posX + toggleButton.width,
			posY = toggleButton.posY,
			width = 300 - toggleButton.width,
			height = toggleButton.height,
			text = "This is a toggle button.",
			alignment = Alignment.CENTER_LEFT
		).apply { isWrapText = true }
		toggleButton.selectedProperty.addListener { _, newValue ->
			toggleLabel.text =
				if (newValue) "This is a selected toggle button!" else "This is a deselected toggle button!"
		}
		menuScene.addComponents(radioButton, radioLabel, toggleButton, toggleLabel)

		val textArea = TextArea(posX = 50, posY = 600, prompt = "I am a text area.")
		textArea.textProperty.addListener { _, newValue ->
			outputLabel.text = newValue
		}
		val textField = TextField(posX = 450, posY = 600, prompt = "I am a text field.")
		textField.textProperty.addListener { _, newValue ->
			outputLabel.text = newValue
		}
		menuScene.addComponents(textArea, textField)

		val listView = ListView<Int>(posX = 50, posY = 800, width = 300, height = 200)
		listView.formatFunction = {
			"Value for this cell is $it"
		}
		listView.items = mutableListOf(42, 1337, 1, 2, 3)
		val listViewLabel = Label(
			posX = listView.posX,
			posY = listView.posY - 50,
			width = listView.width,
			height = 50,
			text = "This is a list view.",
			alignment = Alignment.CENTER
		)
		menuScene.addComponents(listView, listViewLabel)

		val table = TableView<Int>(posX = 450, posY = 800, width = 300, height = 200)
		table.columns.add(TableColumn(title = "Value", width = 100) { "$it" })
		table.columns.add(TableColumn(title = "Squared", width = 100) { "${it * it}" })
		table.columns.add(TableColumn(title = "Even?", width = 100) { "${it % 2 == 0}" })
		table.items.addAll(listView.items)
		val tableLabel = Label(
			posX = table.posX,
			posY = table.posY - 50,
			width = table.width,
			height = 50,
			text = "This is a table view.",
			alignment = Alignment.CENTER
		)
		menuScene.addComponents(table, tableLabel)


		showMenuScene(menuScene)
		show()
	}
}