package net.bluecow.pastepal.service.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface PasteItemRepository extends JpaRepository<PasteItem, Long> {

}
