package net.bluecow.pastepal.service.desktop;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.bluecow.pastepal.service.domain.PasteItem;
import net.bluecow.pastepal.service.domain.PasteItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PastePalUI {

  @Autowired
  private PasteItemRepository repo;
  
  private final JLabel errorLabel = new JLabel("Everything is awesome");
  
  @PostConstruct
  public void show() {
    JFrame f = new JFrame("PastePal");
    Container cp = f.getContentPane();
    JButton b = new JButton("Grab Current Clipboard Contents");
    b.addActionListener(e -> copyClipboardToRepo());
    
    cp.setLayout(new BorderLayout());
    cp.add(b, BorderLayout.NORTH);
    cp.add(errorLabel, BorderLayout.SOUTH);
    
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.pack();
    f.setVisible(true);
  }

  private void copyClipboardToRepo() {
    try {
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      String text = (String) clipboard.getContents(null).getTransferData(DataFlavor.stringFlavor);
      
      // TODO check for dups first
      repo.saveAndFlush(new PasteItem(text));
    } catch (UnsupportedFlavorException | IOException | RuntimeException e) {
      errorLabel.setText("Couldn't grab contents: " + e);
    }
  }
}
