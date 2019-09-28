package net.bluecow.pastepal.service.web;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.bluecow.pastepal.service.domain.PasteItem;
import net.bluecow.pastepal.service.domain.PasteItemRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.*;

@Controller
@RequestMapping("/v1/json")
public class JsonEndpoint {

  private static final Logger log = LoggerFactory.getLogger(JsonEndpoint.class);
  private static final int PASTE_MODIFIER_KEY = choosePasteModifierKey();

  private final PasteItemRepository repo;
  
  @Autowired
  public JsonEndpoint(PasteItemRepository repo) {
    this.repo = repo;
  }

  @RequestMapping("/list")
  @ResponseBody
  List<PasteItem> list() {
    return repo.findAll();
  }
  
  @RequestMapping(method=RequestMethod.POST, value="/paste")
  @ResponseBody
  String paste(@RequestBody Long id) throws NotFoundException, AWTException, InterruptedException {
    log.debug("Attempting to paste item {}", id);
    PasteItem item = repo.findOne(id);
    if (item == null) {
      throw new NotFoundException();
    }

    log.debug("Found {} character string", item.getValue().length());
    Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
    c.setContents(new StringSelection(item.getValue()), null);

    Robot robot = new Robot();
    log.debug("Pressing Cmd-V using separate key codes");
    robot.keyPress(PASTE_MODIFIER_KEY);
    Thread.sleep(10);
    robot.keyPress(KeyEvent.VK_V);
    Thread.sleep(10);
    robot.keyRelease(KeyEvent.VK_V);
    Thread.sleep(10);
    robot.keyRelease(PASTE_MODIFIER_KEY);

    return "ok";
  }

  private static int choosePasteModifierKey() {
    String osName = System.getProperty("os.name");
    if (osName.startsWith("Mac")) {
      log.info("Detected Mac OS ({}). Using VK_META + 'v' for paste.", osName);
      return KeyEvent.VK_META;
    }
    log.info("Detected non-Mac OS ({}). Using VK_CONTROL + 'v' for paste.", osName);
    return KeyEvent.VK_CONTROL;
  }

}
