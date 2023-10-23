package com.cipitech.tools.converters.exchange.model;

import com.cipitech.tools.converters.exchange.model.base.BaseRecord;
import jakarta.persistence.*;
import lombok.*;

/**
 * The hibernate record that represents a currency
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = Currency.TABLE_NAME,
		indexes = {
				@Index(name = Currency.CODE_db + BaseRecord.INDEX_KEY_SUFFIX, columnList = Currency.CODE_db + BaseRecord.INDEX_KEY_ASCENDING_ORDER)
		},
		uniqueConstraints = {
				@UniqueConstraint(columnNames = {Currency.CODE_db})
		})
@SequenceGenerator(name = BaseRecord.SEQUENCE_GENERATOR_NAME, sequenceName = Currency.TABLE_NAME + BaseRecord.SEQUENCE_KEY_SUFFIX, allocationSize = BaseRecord.SEQUENCE_ALLOCATION_SIZE)
public class Currency extends BaseRecord
{
	public static final String TABLE_NAME = "currency";

	public static final String CODE_db        = "code";
	public static final String CODE           = "code";
	public static final String DESCRIPTION_db = "descr";
	public static final String DESCRIPTION    = "description";

	@Column(name = CODE_db)
	private String code;

	@Column(name = DESCRIPTION_db)
	private String description;
}
