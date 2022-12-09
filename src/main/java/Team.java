import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TEAM_ID")
	private Long teamId;

	@Column(name = "TEAM_NAME")
	private String teamName;

	@OneToMany(mappedBy = "team")
	private List<Member> members = new ArrayList<>();
	
	//OneToMany방향을 위한 편의 메서드 추가
	public void addMember(Member member) {
		this.members.add(member);
	}
}
