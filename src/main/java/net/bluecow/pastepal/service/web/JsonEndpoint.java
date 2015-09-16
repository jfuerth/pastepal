package net.bluecow.pastepal.service.web;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.bluecow.pastepal.service.domain.PasteItem;
import net.bluecow.pastepal.service.domain.PasteItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/v1/json")
public class JsonEndpoint {

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
  String paste(@RequestBody Long id) throws NotFoundException {
    PasteItem item = repo.findOne(id);
    if (item == null) {
      throw new NotFoundException();
    }

    Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
    c.setContents(new StringSelection(item.getValue()), null);
    // TODO value is in the clipboard now, but we should actually do the paste
    
    return "ok";
  }

}
