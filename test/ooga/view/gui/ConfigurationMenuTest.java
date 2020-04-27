package ooga.view.gui;

import static org.junit.jupiter.api.Assertions.*;

import com.github.strikerx3.jxinput.exceptions.XInputNotLoadedException;
import ooga.controller.GameController;
import ooga.util.EntityJSONParser;
import ooga.view.application.menu.ConfigurationMenu;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ooga.util.GameParser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConfigurationMenuTest {
    GameParser gameParser;
    ConfigurationMenu config;
    EntityJSONParser myParser;
    JSONObject check;

    @BeforeEach
    void setUp(){
//        gameParser = new GameParser("UnitTest", null, false);
//        config = new ConfigurationMenu(gameParser.getPlayerList());
//        myParser = new EntityJSONParser("UnitTest", "UnitTestEntity");
//        check = myParser.getJSONObject();
    }


    @Test
    void testGetPlayerList() {
        //config.updateControls(0, "D", "T");
//        JSONArray ja = (JSONArray) check.get("actionBundles");
//        System.out.println(ja.get(0));
    }

}
