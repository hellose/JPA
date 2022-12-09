import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
	@Column(name = "TEAM_ID")
	private Long teamId;

	@Column(name = "TEAM_NAME")
	private String teamName;
}
