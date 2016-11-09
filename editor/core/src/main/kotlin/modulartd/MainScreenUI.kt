package modulartd

import com.badlogic.gdx.Input
import ktx.vis.menu
import ktx.vis.menuBar
import ktx.vis.menuItem
import ktx.vis.subMenu

/**
 * Created by Javier on 08/11/2016.
 */
class MainScreenUI {
    val menuBar = menuBar {
        menu("File") {
            menuItem("New") {
                subMenu {
                    menuItem("Project")
                    menuItem("Module")
                    menuItem("File")
                }
            }
            menuItem("Open") { /**/ }
        }
        menu("Edit") {
            menuItem("Undo") {
                setShortcut(Input.Keys.CONTROL_LEFT, Input.Keys.Z)
            }
            menuItem("Undo") { /**/ }
        }
        menu("Actions") {

        }
    }

}