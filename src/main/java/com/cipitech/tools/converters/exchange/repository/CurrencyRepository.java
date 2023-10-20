package com.cipitech.tools.converters.exchange.repository;

import com.cipitech.tools.converters.exchange.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrencyRepository extends JpaRepository<Currency, Long>
{
	Currency findByCodeIgnoreCase(String code);

	List<Currency> findAllByDescriptionContainsIgnoreCase(String description);
}
