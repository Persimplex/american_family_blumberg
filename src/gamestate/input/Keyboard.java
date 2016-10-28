package gamestate.input;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tim on 09/10/16.
 */
public class Keyboard {

    private Map<KeyCode, Boolean> keysPressed;

    public Keyboard(Scene scene, KeyCode... keys) {
        keysPressed = new HashMap<>();
        for (KeyCode kc : keys) {
            keysPressed.put(kc, false);
        }

        EventHandler<KeyEvent> keyEventHandler = event -> {
            if (!keysPressed.containsKey(event.getCode())) return;

                System.out.println("KeyEvent: " + event.getEventType().toString());

            if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                keysPressed.put(event.getCode(), true);
            } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                keysPressed.put(event.getCode(), false);
            }

            event.consume();
        };

        scene.setOnKeyPressed(keyEventHandler);
        scene.setOnKeyReleased(keyEventHandler);
//        scene.addEventHandler(KeyEvent.ANY, event -> {
//            System.out.println(event);
//        });
    }

    public boolean getKey(KeyCode keyCode){
        if(keysPressed.containsKey(keyCode)){
            return keysPressed.get(keyCode);
        }

        return false;
    }

    public void consumeKey(KeyCode keyCode){
        keysPressed.put(keyCode, false);
    }

    public boolean getAndConsumeKey(KeyCode keyCode){
        if(getKey(keyCode)){
            consumeKey(keyCode);
            return true;
        }

        return false;
    }
}
