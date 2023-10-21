package com.cipitech.tools.converters.exchange.model.base;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseRecord implements Serializable
{
	public static final String SEQUENCE_GENERATOR_NAME  = "pk_sequence";
	public static final String SEQUENCE_KEY_SUFFIX      = "_id_seq";
	public static final int    SEQUENCE_ALLOCATION_SIZE = 1;

	public static final String INDEX_KEY_SUFFIX           = "_idx";
	public static final String INDEX_KEY_ASCENDING_ORDER  = " ASC";
	public static final String INDEX_KEY_DESCENDING_ORDER = " DESC";

	public static final String ID_db = "id";
	public static final String ID    = "id";

	public static final String VERSION_NUMBER_db = "ver_no";
	public static final String VERSION_NUMBER    = "versionNumber";

	public static final String INSERTED_AT_db = "ins_at";
	public static final String INSERTED_AT    = "insertedAt";

	public static final String UPDATED_AT_db = "up_at";
	public static final String UPDATED_AT    = "updatedAt";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_GENERATOR_NAME)
	@Column(name = ID_db, updatable = false, nullable = false)
	private Long id;

	@Version
	@Column(name = VERSION_NUMBER_db, nullable = false)
	private Long versionNumber;

	@Column(name = INSERTED_AT_db, updatable = false, nullable = false)
	private Long insertedAt;

	@Column(name = UPDATED_AT_db)
	private Long updatedAt;
}
