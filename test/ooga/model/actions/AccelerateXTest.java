package ooga.model.actions;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.text.html.parser.Entity;
import ooga.controller.EntityWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccelerateXTest {
  private String param;
  private Action myAction;
  private EntityWrapper myEntity;

  @BeforeEach
  void setUp() {
    param = "10";
    myAction = new AccelerateX(param);
//    myEntity = new EntityWrapper("");
  }

  @Test
  void execute() {
  }
}