package com.monapp.repository;

import com.monapp.model.CommandeItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandeItemRepository extends JpaRepository<CommandeItem, Long> {

    List<CommandeItem> findByCommandeId(Long commandeId);
    List<CommandeItem> findByMenuId(Long menuId);

}

