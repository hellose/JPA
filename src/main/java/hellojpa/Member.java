package hellojpa;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//클래스명에 해당하는 테이블에 저장하게됨. 관련 테이블을 설정하고 싶으면 @Table("name="테이블명") 사용
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Member {
	// primary key
	@Id
	private Long id;

	// 필드명이 컬럼명이되기 때문에 컬럼명을 변경하고 싶으면 @Column(name="컬럼명") 사용
	private String name;

	public Member(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}
