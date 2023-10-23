package com.cipitech.tools.converters.exchange.model;

import com.cipitech.tools.converters.exchange.model.base.BaseRecord;
import jakarta.persistence.*;
import lombok.*;

/**
 * The hibernate record that represents an exchange rate
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = ExchangeRate.TABLE_NAME,
		indexes = {
				@Index(name = ExchangeRate.FROM_CURRENCY_db + "_" + ExchangeRate.TO_CURRENCY_db + "_" + BaseRecord.INSERTED_AT_db + BaseRecord.INDEX_KEY_SUFFIX,
						columnList = ExchangeRate.FROM_CURRENCY_db + BaseRecord.INDEX_KEY_ASCENDING_ORDER + ", "
									 + ExchangeRate.TO_CURRENCY_db + BaseRecord.INDEX_KEY_ASCENDING_ORDER + ", "
									 + BaseRecord.INSERTED_AT_db + BaseRecord.INDEX_KEY_DESCENDING_ORDER)
		})
@SequenceGenerator(name = BaseRecord.SEQUENCE_GENERATOR_NAME, sequenceName = ExchangeRate.TABLE_NAME + BaseRecord.SEQUENCE_KEY_SUFFIX, allocationSize = BaseRecord.SEQUENCE_ALLOCATION_SIZE)
public class ExchangeRate extends BaseRecord
{
	public static final String TABLE_NAME = "exchange_rate";

	public static final String FROM_CURRENCY_db = "from_curr_fk";
	public static final String FROM_CURRENCY    = "fromCurrency";
	public static final String TO_CURRENCY_db   = "to_curr_fk";
	public static final String TO_CURRENCY      = "toCurrency";
	public static final String RATE_db          = "rate";
	public static final String RATE             = "rate";

	@ManyToOne
	@JoinColumn(name = FROM_CURRENCY_db)
	private Currency fromCurrency;

	@ManyToOne
	@JoinColumn(name = TO_CURRENCY_db)
	private Currency toCurrency;

	@Column(name = RATE_db)
	private Double rate;
}
