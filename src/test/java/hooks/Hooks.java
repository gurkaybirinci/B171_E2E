package hooks;

import io.cucumber.java.Before;

import static baseurl.MedunnaBaseUrl.setUp;

public class Hooks {
    @Before
    public void beforeApi(){
        setUp();
    }

}
