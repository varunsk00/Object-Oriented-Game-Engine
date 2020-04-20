package ooga.util;

import static org.junit.jupiter.api.Assertions.*;

import ooga.model.actions.SetX;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ActionBundleTest {
  private ActionBundle myBundle;

  @BeforeEach
  void setUp() {
    myBundle = new ActionBundle();
  }

  @Test
  void testActions() {
    assertTrue(myBundle.getActions().size() == 0);
    myBundle.addAction(new SetX("10"));
    assertEquals(myBundle.getActions().size(), 1);
  }

  @Test
  void testId() {
    assertEquals(myBundle.getId(), "");
    myBundle.setId("Yeet");
    assertEquals(myBundle.getId(), "Yeet");
  }
}