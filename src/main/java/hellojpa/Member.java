package hellojpa;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Member {

	@Id
	private Long id;

	// 필드와 컬럼 맵핑
	@Column(name = "name")
	// 문자 타입
	private String username;

	// 자동 타입
	private Integer age;

	// 문자 타입(@Enumerated 기본이 ORDINAL이라 STRING 명시 필요)
	@Enumerated(EnumType.STRING)
	private RoleType roleType;

	// java8이전 @Temporal 붙여야함
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;

	// java8부터 @Temporal 붙일 필요없이 새로 추가된 패키지 사용하면됨
	private LocalDate testLocalDate;

	private LocalDateTime testLocalDateTime;
	
	// 필드 타입이 문자면 BLOB 맵핑 ( String, char[], java.sql.CLOB ) 
	// 나머지 CLOB 맵핑 ( byte[], java.sql.BLOB 등등)
	@Lob
	private String description;

	// db와 관련없는 필드로 설정됨
	@Transient
	private String notContained;

}
