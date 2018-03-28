package modules.externalContext;

import com.google.inject.AbstractModule;
import test.emnify.webapp.*;

public class ExternalGlobalsModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AkkaGlobals.class).asEagerSingleton();
    }

}