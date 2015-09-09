import org.junit.Test;
import pl.andrzejressel.romchecker.lib.Checking;

import java.lang.reflect.Constructor;

public class ToMakeJacocoHappy {

    @Test
    public void checkingPrivateConstructor() throws Exception {
        Constructor<Checking> constructor = Checking.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

}
