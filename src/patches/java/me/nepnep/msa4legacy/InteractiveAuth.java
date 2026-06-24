package me.nepnep.msa4legacy;

import com.microsoft.aad.msal4j.OpenBrowserAction;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;

public class InteractiveAuth implements OpenBrowserAction {
    private WebView browser;
    private Stage stage;

    public InteractiveAuth() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Platform.setImplicitExit(false);
                InteractiveAuth.this.browser = new WebView();
                InteractiveAuth.this.stage = new Stage();
                hide();

                Group root = new Group();
                Scene scene = new Scene(root);
                stage.setScene(scene);

                root.getChildren().add(browser);
            }
        });
    }

    public void hide() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                InteractiveAuth.this.stage.close();
            }
        });
    }

    @Override
    public void openBrowser(final URL url) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                InteractiveAuth.this.stage.show();
                InteractiveAuth.this.browser.getEngine().load(url.toString());
            }
        });
    }
}
