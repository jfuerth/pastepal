package net.bluecow.pastepal.service.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="paste_items")
public class PasteItem {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  private String value;

  public PasteItem() {}
  
  public PasteItem(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "PasteItem [id=" + id + ", value=" + value + "]";
  }
}
