package me.nepnep.msa4legacy.patches;

import me.nepnep.msa4legacy.HasLoginPopup;
import me.nepnep.msa4legacy.InteractiveAuth;
import me.nepnep.msa4legacy.MicrosoftAccount;
import me.nepnep.msa4legacy.MicrosoftAuth;
import net.minecraft.launcher.ui.popups.login.LogInPopup;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.function.Consumer;

public class MSALogInForm extends HasLoginPopup {
    private static final boolean deviceFlow = Boolean.parseBoolean(System.getProperty("msa4legacy.deviceFlow", "false"));
    private final AccountConsumer consumer = new AccountConsumer();
    private final Logger logger = LogManager.getLogger();
    public final LogInPopup popup;
    private final InteractiveAuth interactive = deviceFlow ? null : new InteractiveAuth();
    private final MicrosoftAuth msa;
    
    public MSALogInForm(LogInPopup popup, final MicrosoftAuth msa) {
        super(new GridLayout(deviceFlow ? 5 : 4, 1, 0, 10)); // Extra one for device codes
        this.popup = popup;
        this.msa = msa;
        
        JLabel label = new JLabel("Email");
        add(label);
        final JTextField emailField = new JTextField();
        add(emailField);
        JButton loginButton = new JButton("Log In");
        add(loginButton);
        JButton backButton = new JButton("Back");
        add(backButton);
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (emailField.getDocument() != null) {
                    msa.authenticate(emailField.getText(), MSALogInForm.this, interactive).thenAccept(consumer);
                }
            }
        });
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }
    
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        for (Component button : popup.buttonPanel.getComponents()) {
            button.setVisible(!visible);
        }
        popup.getLogInForm().setVisible(!visible);
    }

    @Override
    public LogInPopup getLoginPopup() {
        return this.popup;
    }

    private class AccountConsumer implements Consumer<MicrosoftAccount> {
        @Override
        public void accept(MicrosoftAccount microsoftAccount) {
            popup.setLoggedIn(microsoftAccount);
            File cacheFile = msa.getCacheFile();
            try {
                if (!cacheFile.exists()) {
                    cacheFile.createNewFile();
                }
                HashSet<MicrosoftAccount> cached = msa.getGson().fromJson(FileUtils.readFileToString(cacheFile, "UTF-8"), msa.getAccountSetType());
                cached.add(microsoftAccount);
                String raw = msa.getGson().toJson(cached);
                FileUtils.write(cacheFile, raw, "UTF-8");
            } catch (IOException e) {
                logger.error("IOException while adding account to info cache", e);
            }
        }
    }
}
