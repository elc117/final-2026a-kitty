package me.nepnep.msa4legacy;

import net.minecraft.launcher.ui.popups.login.LogInPopup;

import javax.swing.*;
import java.awt.*;

public abstract class HasLoginPopup extends JPanel {
    public HasLoginPopup(LayoutManager layout) {
        super(layout);
    }

    public abstract LogInPopup getLoginPopup();
}
