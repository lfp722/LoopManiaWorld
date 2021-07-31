package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import unsw.loopmania.EntityAttribute;

public class AttributeTest {
    @Test
    public void test() {
        EntityAttribute attr = new EntityAttribute(5, 0, 35);
        String expected = new String("{\"defence\":0,\"attack\":5,\"health\":35,\"curHealth\":35}");
        assertEquals(expected, attr.toJSON().toString());
    }
}
