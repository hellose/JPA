import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member {

	@Id
	@GeneratedValue
	@Column(name = "MEMBER_ID")
	private Long memberId;

	@Column(name = "MEMBER_NAME")
	private String memberName;

	// 객체지향적인 설계

	// JPA에게 연관관계를 알려줘야함
	
	//N : 1 <=> Member : Team
	@ManyToOne
	@JoinColumn(name = "TEAM_ID")
	private Team team;

}
