package me.nepnep.msa4legacy;

import com.google.gson.Gson;

import java.io.File;
import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;

public interface MicrosoftAuth {
    CompletableFuture<MicrosoftAccount> authenticate(final String email, final HasLoginPopup form, InteractiveAuth interactive);
    void addAllToDatabase(HasLoginPopup form);
    File getCacheFile();
    Gson getGson();
    Type getAccountSetType();
    void removeAccount(String name);
}
