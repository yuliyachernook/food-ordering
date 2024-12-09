package by.ita.chernook.repository;

import by.ita.chernook.dto.enums.CategoryEnum;
import by.ita.chernook.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByCategory(CategoryEnum category);
}