package util;

import org.junit.Test;
import org.junit.Assert;

public class CommandBuilderTest {
    @Test
    public void properlyBuildsCommand() {
        CommandBuilder cb = new CommandBuilder();
        cb.setCommand("wow")
          .addParam("--foo", "v1")
          .addParam("--bar")
          .addParam("--baz", 123);

        String[] expectedParams = {"wow", "--foo", "v1", "--bar", "--baz", "123"};

        Assert.assertArrayEquals(expectedParams, cb.build());
    }
}
