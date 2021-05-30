package com.msgilligan.keynote;

import app.supernaut.fx.FxForegroundApp;
import app.supernaut.fx.FxLauncher;
import app.supernaut.services.BrowserService;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import jakarta.inject.Singleton;
import java.net.URI;

@Singleton
public class KeyNoteApp implements FxForegroundApp.FxApplicationCompat  {
    private static final URI githubRepoUri = URI.create("https://github.com/SupernautApp/SupernautFX");
    private final BrowserService browserService;
    private final NotePlayer notePlayer;

    public KeyNoteApp(BrowserService browserService) {
        this.browserService = browserService;
        notePlayer = new NotePlayer();
    }

    @Override
    public void start(Stage stage) {
        stage.setScene(buildScene());
        stage.show();
    }

    private Scene buildScene() {
        var javaVersion = System.getProperty("java.version");
        var javafxVersion = System.getProperty("javafx.version");
        var label       = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var hyperlink   = new Hyperlink("Powered by Supernaut.FX");
        var vbox        = new VBox(label, hyperlink);
        vbox.setAlignment(Pos.CENTER);
        hyperlink.setOnAction(e -> browserService.showDocument(githubRepoUri));
        var scene = new Scene(vbox, 350, 100);
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, this::handleMousePressed);
        scene.addEventFilter(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased);
        return scene;
    }

    public void handleMousePressed(MouseEvent mouseEvent) {
        MouseButton button = mouseEvent.getButton();
        System.out.println(mouseEvent.getEventType());
        System.out.println(mouseEvent.getButton());
        if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
            notePlayer.play();
        }
    }

    public void handleMouseReleased(MouseEvent mouseEvent) {
        MouseButton button = mouseEvent.getButton();
        System.out.println(mouseEvent.getEventType());
        if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
            notePlayer.stop();
        }

    }

    public static void main(String[] args) {
        FxLauncher.byName("micronaut").launch(args, KeyNoteApp.class);
    }
}
