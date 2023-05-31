package foreignKey.unidirectional.manyToOne;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Entity
@Getter
@Setter
@NoArgsConstructor
class Member {

	@Id
	@Column(name = "member_id")
	private Long memberId;

	@Column(name = "member_name")
	private String memberName;

	// 일반적인 케이스
	@ManyToOne(optional = false)
	@JoinColumn(name = "team_id", nullable = false)
	private Team team;
	
	// @ManyToOne의 optional 속성
	// VS
	// @JoinColumn의 nullable 속성

	// 전자는 객체 모델에서 필드가 null이면 안됨. 후자는 db모델에서 컬럼이 null이면 안됨
	// 그렇다면 전자와 후자와 별개로 동작하는 것인가 궁금해서 테스트 해봄

	
	// 테스트
	// nullable이 true여서 not null 제약이 안걸릴 것 같지만 optional이 false로 되어있어 not null제약 걸림
//	@ManyToOne(optional = false)
//	@JoinColumn(name = "team_id", nullable = true)
//	private Team team;

	// 테스트
	// nullable은 false여서 not null 제약이 걸리지만 null객체로 persist하고 EntityManager를 clear는 될까 테스트해본 결과 안됨
	// nullable false로 인해 객체 프로퍼티 무조건 셋팅되야함
//	@ManyToOne(optional = true)
//	@JoinColumn(name = "team_id", nullable = false)
//	private Team team;
	
	// 지금까지의 수준으로는... 둘중하나만 false로 설정되면 둘다 false처리로 되는 것 같다...

}
