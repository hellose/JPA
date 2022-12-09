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

	// 양방향 맵핑을 위한 List추가

	// 1:N <=> Team:Member
	// mappedBy = "Member의 Team타입 필드 team"
	@OneToMany(mappedBy = "team")
	private List<Member> members = new ArrayList<>();
}
