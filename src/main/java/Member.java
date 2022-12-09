import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEMBER_ID")
	private Long memberId;

	@Column(name = "MEMBER_NAME")
	private String memberName;

	@ManyToOne
	@JoinColumn(name = "TEAM_ID")
	private Team team;

	public void setTeam(Team team) {
		this.team = team;
	
		//순수 객체 상태를 고려해서 현재 방향으로 추가하는 코드를 넣어줌
		team.getMembers().add(this);
	}

}
