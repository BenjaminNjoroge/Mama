package net.webnetworksolutions.mama.support;

/**
 * Created by Benja on 3/28/2018.
 */

public class Application extends android.app.Application {
    private Prefs prefs;
    private static Application app;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        prefs = new Prefs(this);
    }
    public static Application getApp() {
        return app;
    }
    public Prefs getPrefs() {
        return prefs;
    }
    public void setPrefs(Prefs prefs) {
        this.prefs = prefs;
    }
}
